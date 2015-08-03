/*
 * AUTOR: Javier Briz Alastrue
 * NIA: 576695
 */

package stw.p6;

import java.io.*;
import java.util.*;

import org.jdom2.*;
import org.jdom2.input.*;
import org.jdom2.util.*;
import org.jdom2.output.XMLOutputter;

import com.google.gson.*;

import org.apache.log4j.*;

import java.net.URL;
import java.nio.charset.Charset;

import freemarker.template.*;
import java.text.ParseException;

import org.xml.sax.InputSource;

import org.json.XML;
import org.json.JSONObject;



public class Print2columnas_xml {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JsonObject readJsonFromUrl(String url) throws IOException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);

			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(jsonText);
			JsonObject json = jsonElement.getAsJsonObject();
			return json;
		} finally {
			is.close();
		}
	}


	private static String getValor(Element p, String child) {
		try {
			return (p.getChild(child).getText());
		} catch (Exception e) {
			return ("");
		}
	}

	private static String getValor(Element p, String child1, String child2) {
		try {
			return (p.getChild(child1).getChild(child2).getText());
		} catch (Exception e) {
			return ("");
		}
	}

	private static String buscaIntervalo(Element p, String child, String periodo) {
		// Busca de entre los hijos de p, aquel de la forma <child periodo="periodo">
		// Devuelve su texto
		// Si no lo encuentra devuelve cadena vacía
		try {
			List < Element > hijos = p.getChildren(child);
			for (Element h: hijos) {
				try {
					if (h.getAttribute("periodo").getValue().equals(periodo)) {
						return h.getText();
					}
				} catch (Exception e) {}
			}
		} catch (Exception e) {}
		return "";
	}

	private static String buscaIntervalo(Element p, String child1, String child2, String periodo) {
		// Busca de entre los hijos de p, aquel de la forma <child1 periodo="periodo"><child2>
		// Devuelve su texto
		// Si no lo encuentra devuelve cadena vacía
		List < Element > hijos = p.getChildren(child1);
		for (Element h: hijos) {
			try {
				if (h.getAttribute("periodo").getValue().equals(periodo)) {
					return h.getChild(child2).getText();
				}
			} catch (Exception e) {}
		}
		return "";
	}


	private static String buscahora(Element p, String child, String hora) {
		// Busca de entre las "child" hijas de p, aquella de la forma <dato hora="hora">
		// Devuelve su texto
		// Si no lo encuentra devuelve cadena vacía
		try {
			Element t = p.getChild(child);
			List < Element > hijos = t.getChildren("dato");
			for (Element h: hijos) {
				try {
					if (h.getAttribute("hora").getValue().equals(hora)) {
						return h.getText();
					}
				} catch (Exception e) {}
			}
		} catch (Exception e) {}
		return "";
	}

	private static List < Prediccion > predicciones = new ArrayList < Prediccion > ();

	//private static String xmlSource = "http://www.aemet.es/xml/municipios/localidad_50297.xml";


	static private String implodeArray(String[] inputArray) {
		String output = "";
		if (inputArray.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < inputArray.length; i++) {
				sb.append(inputArray[i]);
				sb.append(" ");
			}
			output = sb.toString();
		}
		return output;
	}


	public static void main(String arg[]) throws JDOMException, IOException {
                String respuesta = serviceMethod(implodeArray(arg));
                respuesta = respuesta.trim().replaceFirst("^([\\W]+)<","<");
		System.out.println(respuesta);
	}

	private static org.apache.log4j.Logger log = Logger.getLogger(Printjson.class);


	public static String serviceMethod(String arg) throws JDOMException, IOException {

		log.setLevel(Level.WARN);

		SAXBuilder jdomBuilder = new SAXBuilder();

                URL url = new URL(arg);
                InputSource is = new InputSource(url.openStream());
                is.setEncoding("ISO-8859-1"); // Also Try UTF-8 or UTF-16
                BufferedReader br = new BufferedReader(new InputStreamReader(is.getByteStream()));

		//Document jdomDocument = jdomBuilder.build(new StringReader(arg));
		Document jdomDocument = jdomBuilder.build(br);
                
		Element raiz = jdomDocument.getRootElement();
		Element prediccion = raiz.getChild("prediccion");
		List < Element > rss_pred = prediccion.getChildren();


		Integer dia = 0;
		for (Element p: rss_pred) {
			dia++;
			Prediccion foo = new Prediccion();
			foo.setSimples(p.getAttribute("fecha").getValue(),
			getValor(p, "temperatura", "minima"),
			getValor(p, "temperatura", "maxima"),
			getValor(p, "sens_termica", "minima"),
			getValor(p, "sens_termica", "maxima"),
			getValor(p, "humedad_relativa", "minima"),
			getValor(p, "humedad_relativa", "maxima"),
			getValor(p, "uv_max"));

			switch (dia) {
				case 1:
				case 2:
					foo.setIntervalo("00-06",
					buscaIntervalo(p, "estado_cielo", "00-06"),
					buscaIntervalo(p, "prob_precipitacion", "00-06"),
					buscaIntervalo(p, "viento", "direccion", "00-06"),
					buscaIntervalo(p, "viento", "velocidad", "00-06"),
					buscahora(p, "temperatura", "06"),
					buscahora(p, "sens_termica", "06"),
					buscahora(p, "humedad_relativa", "06"));

					foo.setIntervalo("06-12",
					buscaIntervalo(p, "estado_cielo", "06-12"),
					buscaIntervalo(p, "prob_precipitacion", "06-12"),
					buscaIntervalo(p, "viento", "direccion", "06-12"),
					buscaIntervalo(p, "viento", "velocidad", "06-12"),
					buscahora(p, "temperatura", "12"),
					buscahora(p, "sens_termica", "12"),
					buscahora(p, "humedad_relativa", "12"));

					foo.setIntervalo("12-18",
					buscaIntervalo(p, "estado_cielo", "12-18"),
					buscaIntervalo(p, "prob_precipitacion", "12-18"),
					buscaIntervalo(p, "viento", "direccion", "12-18"),
					buscaIntervalo(p, "viento", "velocidad", "12-18"),
					buscahora(p, "temperatura", "18"),
					buscahora(p, "sens_termica", "18"),
					buscahora(p, "humedad_relativa", "18"));
					foo.setIntervalo("18-24",
					buscaIntervalo(p, "estado_cielo", "18-24"),
					buscaIntervalo(p, "prob_precipitacion", "18-24"),
					buscaIntervalo(p, "viento", "direccion", "18-24"),
					buscaIntervalo(p, "viento", "velocidad", "18-24"),
					buscahora(p, "temperatura", "24"),
					buscahora(p, "sens_termica", "24"),
					buscahora(p, "humedad_relativa", "24"));
					break;
			}

			predicciones.add(foo);
		}

		Map < String, String > data = new HashMap < String, String > ();

		// BASE DE DATOS (stw1/prediccion)
		// ID - dia - intervalo - campo - valor
		Mysql mysql = new Mysql();

		// Vacío la tabla para eliminar datos anteriores
		mysql.clear();

		// Fecha
		Prediccion d = predicciones.get(0);
		mysql.setFila("d0", "0", "f", predicciones.get(0).fecha);
		mysql.setFila("d1", "0", "f", predicciones.get(1).fecha);

		mysql.setFila("d0", "i1", "estados", d.estados[2]);
		mysql.setFila("d0", "i2", "estados", d.estados[3]);

		mysql.setFila("d0", "i1", "precip", d.precip[2]);
		mysql.setFila("d0", "i2", "precip", d.precip[3]);

		mysql.setFila("d0", "i1", "viento", d.viento[2]);
		mysql.setFila("d0", "i2", "viento", d.viento[3]);

		mysql.setFila("d0", "i1", "vviento", d.vviento[2]);
		mysql.setFila("d0", "i2", "vviento", d.vviento[3]);

		mysql.setFila("d0", "i1", "temp", d.temp[2]);
		mysql.setFila("d0", "i2", "temp", d.temp[3]);
		mysql.setFila("d0", "i1", "stemp", d.stemp[2]);
		mysql.setFila("d0", "i2", "stemp", d.stemp[3]);
		mysql.setFila("d0", "i1", "humedad", d.humedad[2]);
		mysql.setFila("d0", "i2", "humedad", d.humedad[3]);

		mysql.setFila("d0", "0", "tmin", d.tmin);
		mysql.setFila("d0", "0", "tmax", d.tmax);
		mysql.setFila("d0", "0", "stmin", d.stmin);
		mysql.setFila("d0", "0", "stmax", d.stmax);
		mysql.setFila("d0", "0", "hmin", d.hmin);
		mysql.setFila("d0", "0", "hmax", d.hmax);

		mysql.setFila("d0", "0", "uv", d.uv);



		d = predicciones.get(1);
		mysql.setFila("d1", "i1", "estados", d.estados[0]);
		mysql.setFila("d1", "i2", "estados", d.estados[1]);
		mysql.setFila("d1", "i3", "estados", d.estados[2]);
		mysql.setFila("d1", "i4", "estados", d.estados[3]);

		mysql.setFila("d1", "i1", "precip", d.precip[0]);
		mysql.setFila("d1", "i2", "precip", d.precip[1]);
		mysql.setFila("d1", "i3", "precip", d.precip[2]);
		mysql.setFila("d1", "i4", "precip", d.precip[3]);

		mysql.setFila("d1", "i1", "viento", d.viento[0]);
		mysql.setFila("d1", "i2", "viento", d.viento[1]);
		mysql.setFila("d1", "i3", "viento", d.viento[2]);
		mysql.setFila("d1", "i4", "viento", d.viento[3]);

		mysql.setFila("d1", "i1", "vviento", d.vviento[0]);
		mysql.setFila("d1", "i2", "vviento", d.vviento[1]);
		mysql.setFila("d1", "i3", "vviento", d.vviento[2]);
		mysql.setFila("d1", "i4", "vviento", d.vviento[3]);

		mysql.setFila("d1", "i1", "temp", d.temp[0]);
		mysql.setFila("d1", "i2", "temp", d.temp[1]);
		mysql.setFila("d1", "i3", "temp", d.temp[2]);
		mysql.setFila("d1", "i4", "temp", d.temp[3]);

		mysql.setFila("d1", "i1", "stemp", d.stemp[0]);
		mysql.setFila("d1", "i2", "stemp", d.stemp[1]);
		mysql.setFila("d1", "i3", "stemp", d.stemp[2]);
		mysql.setFila("d1", "i4", "stemp", d.stemp[3]);

		mysql.setFila("d1", "i1", "humedad", d.humedad[0]);
		mysql.setFila("d1", "i2", "humedad", d.humedad[1]);
		mysql.setFila("d1", "i3", "humedad", d.humedad[2]);
		mysql.setFila("d1", "i4", "humedad", d.humedad[3]);

		mysql.setFila("d1", "0", "tmin", d.tmin);
		mysql.setFila("d1", "0", "tmax", d.tmax);
		mysql.setFila("d1", "0", "stmin", d.stmin);
		mysql.setFila("d1", "0", "stmax", d.stmax);
		mysql.setFila("d1", "0", "hmin", d.hmin);
		mysql.setFila("d1", "0", "hmax", d.hmax);

		mysql.setFila("d1", "0", "uv", d.uv);


                
		JsonObject prediccionjson = new JsonObject();

		JsonArray dias = new JsonArray();

		JsonObject dia0 = new JsonObject();
		JsonObject dia0c = new JsonObject();
		dia0c.addProperty("f", mysql.getFila("d0", "0", "f"));
		dia0c.addProperty("d0_i1_estados", mysql.getFila("d0", "i1", "estados"));
		dia0c.addProperty("d0_i2_estados", mysql.getFila("d0", "i2", "estados"));
		dia0c.addProperty("d0_i1_precip", mysql.getFila("d0", "i1", "precip"));
		dia0c.addProperty("d0_i2_precip", mysql.getFila("d0", "i2", "precip"));
		dia0c.addProperty("d0_i1_viento", mysql.getFila("d0", "i1", "viento"));
		dia0c.addProperty("d0_i2_viento", mysql.getFila("d0", "i2", "viento"));
		dia0c.addProperty("d0_i1_vviento", mysql.getFila("d0", "i1", "vviento"));
		dia0c.addProperty("d0_i2_vviento", mysql.getFila("d0", "i2", "vviento"));
		dia0c.addProperty("d0_i1_temp", mysql.getFila("d0", "i1", "temp"));
		dia0c.addProperty("d0_i2_temp", mysql.getFila("d0", "i2", "temp"));
		dia0c.addProperty("d0_i1_stemp", mysql.getFila("d0", "i1", "stemp"));
		dia0c.addProperty("d0_i2_stemp", mysql.getFila("d0", "i2", "stemp"));
		dia0c.addProperty("d0_i1_humedad", mysql.getFila("d0", "i1", "humedad"));
		dia0c.addProperty("d0_i2_humedad", mysql.getFila("d0", "i2", "humedad"));
		dia0c.addProperty("d0_uv", mysql.getFila("d0", "0", "uv"));
		dia0c.addProperty("d0_tmin", mysql.getFila("d0", "0", "tmin"));
		dia0c.addProperty("d0_tmax", mysql.getFila("d0", "0", "tmax"));
		dia0c.addProperty("d0_stmin", mysql.getFila("d0", "0", "stmin"));
		dia0c.addProperty("d0_stmax", mysql.getFila("d0", "0", "stmax"));
		dia0c.addProperty("d0_hmin", mysql.getFila("d0", "0", "hmin"));
		dia0c.addProperty("d0_hmax", mysql.getFila("d0", "0", "hmax"));
		dia0.add("dia0", dia0c);
		dias.add(dia0);

		JsonObject dia1 = new JsonObject();
		JsonObject dia1c = new JsonObject();
		dia1c.addProperty("f", mysql.getFila("d1", "0", "f"));
		dia1c.addProperty("d1_i1_estados", mysql.getFila("d1", "i1", "estados"));
		dia1c.addProperty("d1_i2_estados", mysql.getFila("d1", "i2", "estados"));
		dia1c.addProperty("d1_i3_estados", mysql.getFila("d1", "i3", "estados"));
		dia1c.addProperty("d1_i4_estados", mysql.getFila("d1", "i4", "estados"));
		dia1c.addProperty("d1_i1_precip", mysql.getFila("d1", "i1", "precip"));
		dia1c.addProperty("d1_i2_precip", mysql.getFila("d1", "i2", "precip"));
		dia1c.addProperty("d1_i3_precip", mysql.getFila("d1", "i3", "precip"));
		dia1c.addProperty("d1_i4_precip", mysql.getFila("d1", "i4", "precip"));
		dia1c.addProperty("d1_i1_viento", mysql.getFila("d1", "i1", "viento"));
		dia1c.addProperty("d1_i2_viento", mysql.getFila("d1", "i2", "viento"));
		dia1c.addProperty("d1_i3_viento", mysql.getFila("d1", "i3", "viento"));
		dia1c.addProperty("d1_i4_viento", mysql.getFila("d1", "i4", "viento"));
		dia1c.addProperty("d1_i1_vviento", mysql.getFila("d1", "i1", "vviento"));
		dia1c.addProperty("d1_i2_vviento", mysql.getFila("d1", "i2", "vviento"));
		dia1c.addProperty("d1_i3_vviento", mysql.getFila("d1", "i3", "vviento"));
		dia1c.addProperty("d1_i4_vviento", mysql.getFila("d1", "i4", "vviento"));
		dia1c.addProperty("d1_i1_temp", mysql.getFila("d1", "i1", "temp"));
		dia1c.addProperty("d1_i2_temp", mysql.getFila("d1", "i2", "temp"));
		dia1c.addProperty("d1_i3_temp", mysql.getFila("d1", "i3", "temp"));
		dia1c.addProperty("d1_i4_temp", mysql.getFila("d1", "i4", "temp"));
		dia1c.addProperty("d1_i1_stemp", mysql.getFila("d1", "i1", "stemp"));
		dia1c.addProperty("d1_i2_stemp", mysql.getFila("d1", "i2", "stemp"));
		dia1c.addProperty("d1_i3_stemp", mysql.getFila("d1", "i3", "stemp"));
		dia1c.addProperty("d1_i4_stemp", mysql.getFila("d1", "i4", "stemp"));
		dia1c.addProperty("d1_i1_humedad", mysql.getFila("d1", "i1", "humedad"));
		dia1c.addProperty("d1_i2_humedad", mysql.getFila("d1", "i2", "humedad"));
		dia1c.addProperty("d1_i3_humedad", mysql.getFila("d1", "i3", "humedad"));
		dia1c.addProperty("d1_i4_humedad", mysql.getFila("d1", "i4", "humedad"));
		dia1c.addProperty("d1_uv", mysql.getFila("d1", "0", "uv"));
		dia1c.addProperty("d1_tmin", mysql.getFila("d1", "0", "tmin"));
		dia1c.addProperty("d1_tmax", mysql.getFila("d1", "0", "tmax"));
		dia1c.addProperty("d1_stmin", mysql.getFila("d1", "0", "stmin"));
		dia1c.addProperty("d1_stmax", mysql.getFila("d1", "0", "stmax"));
		dia1c.addProperty("d1_hmin", mysql.getFila("d1", "0", "hmin"));
		dia1c.addProperty("d1_hmax", mysql.getFila("d1", "0", "hmax"));
		dia1.add("dia1", dia1c);
		dias.add(dia1);



		prediccionjson.add("prediccion", dias);
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		String jsonencoded = gson.toJson(prediccionjson);

		JsonParser jsonParser = new JsonParser();
		JsonObject json = (JsonObject) jsonParser.parse(jsonencoded);

		//JsonObject json = new JsonObject(jsonencoded);

		JsonArray prediccion3 = json.getAsJsonArray("prediccion");

		//JsonObject[] diax = new JsonObject[2];
		String[] diax = new String[2];


		for (int i = 0; i <= 1; i++) {
                	try {
				//diax[i] = prediccion3.get(i).getAsJsonObject().get("dia" + i).getAsJsonObject();
				JSONObject jsonxml = new JSONObject(prediccion3.get(i).getAsJsonObject().get("dia" + i).toString());
				diax[i] = XML.toString(jsonxml);
			}
			catch(Exception e){
				System.err.println(e);
			}
		}


		SAXBuilder jdomBuilder2 = new SAXBuilder();

		String[] diaxml = new String[2];
		StringBufferInputStream[] is2 = new StringBufferInputStream[2];
		Document[] jdomDocument2 = new Document[2];
                Element[] raiz_dia = new Element[2];
                for (int i = 0; i < diax.length; i++) {
			// Pasamos de los strings xml a documentos xml
			diaxml[i] = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><prediccion>" + diax[i] + "</prediccion>";
                	//diaxml[i] = diaxml[i].replaceAll("><",">\n<").replaceAll("_","");

			// Construimos los objetos DOM
			is2[i] = new StringBufferInputStream(diaxml[i]);
                	jdomDocument2[i] = jdomBuilder.build(is2[i]);

			// Obtenemos los elementos raiz de cada dia para trabajar mas adelante sobre ellos
			raiz_dia[i] = jdomDocument2[i].getRootElement();
		}


		// Obtenemos los valores de la forma
                // System.out.println(getValor(raiz_dia[0], "f"));


                Configuration cfg = null;
                Template template = null;
                Map < String, String > datax = new HashMap < String, String > ();

                try {
                        // Completar la plantilla con los datos obtenidos
                        cfg = new Configuration();
                        template = cfg.getTemplate("src/2columnas.ftl");
                } catch (Exception e) {
                        System.out.println(e);
                }

                // Fecha
                datax.put("d0_f", getValor(raiz_dia[0], "f"));
                datax.put("d1_f", getValor(raiz_dia[1], "f"));

    // Dia 0 //////////////////////////////////////////////////////////
                datax.put("d0_i1_estados", getValor(raiz_dia[0], "d0_i1_estados"));
                datax.put("d0_i2_estados", getValor(raiz_dia[0], "d0_i2_estados"));
                datax.put("d0_i1_precip", getValor(raiz_dia[0], "d0_i1_precip"));
                datax.put("d0_i2_precip", getValor(raiz_dia[0], "d0_i2_precip"));
                datax.put("d0_i1_viento", getValor(raiz_dia[0], "d0_i1_viento"));
                datax.put("d0_i2_viento", getValor(raiz_dia[0], "d0_i2_viento"));
                datax.put("d0_i1_vviento", getValor(raiz_dia[0], "d0_i1_vviento"));
                datax.put("d0_i2_vviento", getValor(raiz_dia[0], "d0_i2_vviento"));
                datax.put("d0_i1_temp", getValor(raiz_dia[0], "d0_i1_temp"));
                datax.put("d0_i2_temp", getValor(raiz_dia[0], "d0_i2_temp"));
                datax.put("d0_i1_stemp", getValor(raiz_dia[0], "d0_i1_stemp"));
                datax.put("d0_i2_stemp", getValor(raiz_dia[0], "d0_i2_stemp"));
                datax.put("d0_i1_humedad", getValor(raiz_dia[0], "d0_i1_humedad"));
                datax.put("d0_i2_humedad", getValor(raiz_dia[0], "d0_i2_humedad"));
                datax.put("d0_tmin", getValor(raiz_dia[0], "d0_tmin"));
                datax.put("d0_tmax", getValor(raiz_dia[0], "d0_tmax"));
                datax.put("d0_stmin", getValor(raiz_dia[0], "d0_stmin"));
                datax.put("d0_stmax", getValor(raiz_dia[0], "d0_stmax"));
                datax.put("d0_hmin", getValor(raiz_dia[0], "d0_hmin"));
                datax.put("d0_hmax", getValor(raiz_dia[0], "d0_hmax"));
                datax.put("d0_uv", getValor(raiz_dia[0], "d0_uv"));


                // Dia 1 //////////////////////////////////////////////////////////
                datax.put("d1_i1_estados", getValor(raiz_dia[1], "d1_i1_estados"));
                datax.put("d1_i2_estados", getValor(raiz_dia[1], "d1_i2_estados"));
                datax.put("d1_i3_estados", getValor(raiz_dia[1], "d1_i3_estados"));
                datax.put("d1_i4_estados", getValor(raiz_dia[1], "d1_i4_estados"));
                datax.put("d1_i1_precip", getValor(raiz_dia[1], "d1_i1_precip"));
                datax.put("d1_i2_precip", getValor(raiz_dia[1], "d1_i2_precip"));
                datax.put("d1_i3_precip", getValor(raiz_dia[1], "d1_i3_precip"));
                datax.put("d1_i4_precip", getValor(raiz_dia[1], "d1_i4_precip"));
                datax.put("d1_i1_viento", getValor(raiz_dia[1], "d1_i1_viento"));
                datax.put("d1_i2_viento", getValor(raiz_dia[1], "d1_i2_viento"));
                datax.put("d1_i3_viento", getValor(raiz_dia[1], "d1_i3_viento"));
                datax.put("d1_i4_viento", getValor(raiz_dia[1], "d1_i4_viento"));
                datax.put("d1_i1_vviento", getValor(raiz_dia[1], "d1_i1_vviento"));
                datax.put("d1_i2_vviento", getValor(raiz_dia[1], "d1_i2_vviento"));
                datax.put("d1_i3_vviento", getValor(raiz_dia[1], "d1_i3_vviento"));
                datax.put("d1_i4_vviento", getValor(raiz_dia[1], "d1_i4_vviento"));
                datax.put("d1_i1_temp", getValor(raiz_dia[1], "d1_i1_temp"));
                datax.put("d1_i2_temp", getValor(raiz_dia[1], "d1_i2_temp"));
                datax.put("d1_i3_temp", getValor(raiz_dia[1], "d1_i3_temp"));
                datax.put("d1_i4_temp", getValor(raiz_dia[1], "d1_i4_temp"));
                datax.put("d1_i1_stemp", getValor(raiz_dia[1], "d1_i1_stemp"));
                datax.put("d1_i2_stemp", getValor(raiz_dia[1], "d1_i2_stemp"));
                datax.put("d1_i3_stemp", getValor(raiz_dia[1], "d1_i3_stemp"));
                datax.put("d1_i4_stemp", getValor(raiz_dia[1], "d1_i4_stemp"));
                datax.put("d1_i1_humedad", getValor(raiz_dia[1], "d1_i1_humedad"));
                datax.put("d1_i2_humedad", getValor(raiz_dia[1], "d1_i2_humedad"));
                datax.put("d1_i3_humedad", getValor(raiz_dia[1], "d1_i3_humedad"));
                datax.put("d1_i4_humedad", getValor(raiz_dia[1], "d1_i4_humedad"));
                datax.put("d1_tmin", getValor(raiz_dia[1], "d1_tmin"));
                datax.put("d1_tmax", getValor(raiz_dia[1], "d1_tmax"));
                datax.put("d1_stmin", getValor(raiz_dia[1], "d1_stmin"));
                datax.put("d1_stmax", getValor(raiz_dia[1], "d1_stmax"));
                datax.put("d1_hmin", getValor(raiz_dia[1], "d1_hmin"));
                datax.put("d1_hmax", getValor(raiz_dia[1], "d1_hmax"));
                datax.put("d1_uv", getValor(raiz_dia[1], "d1_uv"));

		//mysql.clear();

		try {
			Writer salida = new StringWriter(); //new File(args[1] + "/tiempo-zaragoza.html"));
			template.process(datax, salida);
			salida.flush();
			return salida.toString();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
}
