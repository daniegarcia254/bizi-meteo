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

import com.google.gson.*;

import org.apache.log4j.*;

import java.net.URL;
import java.nio.charset.Charset;

import freemarker.template.*;
import java.text.ParseException;

import org.xml.sax.InputSource;



public class Printhtml {

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
				case 3:
				case 4:
					foo.setIntervalo("00-12",
					buscaIntervalo(p, "estado_cielo", "00-12"),
					buscaIntervalo(p, "prob_precipitacion", "00-12"),
					buscaIntervalo(p, "viento", "direccion", "00-12"),
					buscaIntervalo(p, "viento", "velocidad", "00-12"),
					buscahora(p, "temperatura", "12"),
					buscahora(p, "sens_termica", "12"),
					buscahora(p, "humedad_relativa", "12"));

					foo.setIntervalo("12-24",
					buscaIntervalo(p, "estado_cielo", "12-24"),
					buscaIntervalo(p, "prob_precipitacion", "12-24"),
					buscaIntervalo(p, "viento", "direccion", "12-24"),
					buscaIntervalo(p, "viento", "velocidad", "12-24"),
					buscahora(p, "temperatura", "24"),
					buscahora(p, "sens_termica", "24"),
					buscahora(p, "humedad_relativa", "24"));
					break;
				case 5:
				case 6:
				case 7:
					foo.setIntervalo("00-24",
					p.getChild("estado_cielo").getText(),
					p.getChild("prob_precipitacion").getText(),
					p.getChild("viento").getChild("direccion").getText(),
					p.getChild("viento").getChild("velocidad").getText(),
						"",
						"",
						"");
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
		mysql.setFila("d2", "0", "f", predicciones.get(2).fecha);
		mysql.setFila("d3", "0", "f", predicciones.get(3).fecha);
		mysql.setFila("d4", "0", "f", predicciones.get(4).fecha);
		mysql.setFila("d5", "0", "f", predicciones.get(5).fecha);
		mysql.setFila("d6", "0", "f", predicciones.get(6).fecha);

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


		d = predicciones.get(2);
		mysql.setFila("d2", "i1", "estados", d.estados[0]);
		mysql.setFila("d2", "i2", "estados", d.estados[1]);

		mysql.setFila("d2", "i1", "precip", d.precip[0]);
		mysql.setFila("d2", "i2", "precip", d.precip[1]);

		mysql.setFila("d2", "i1", "viento", d.viento[0]);
		mysql.setFila("d2", "i2", "viento", d.viento[1]);

		mysql.setFila("d2", "i1", "vviento", d.vviento[0]);
		mysql.setFila("d2", "i2", "vviento", d.vviento[1]);

		mysql.setFila("d2", "i1", "temp", d.temp[0]);
		mysql.setFila("d2", "i2", "temp", d.temp[1]);

		mysql.setFila("d2", "i1", "stemp", d.stemp[0]);
		mysql.setFila("d2", "i2", "stemp", d.stemp[1]);

		mysql.setFila("d2", "i1", "humedad", d.humedad[0]);
		mysql.setFila("d2", "i2", "humedad", d.humedad[1]);

		mysql.setFila("d2", "0", "tmin", d.tmin);
		mysql.setFila("d2", "0", "tmax", d.tmax);
		mysql.setFila("d2", "0", "stmin", d.stmin);
		mysql.setFila("d2", "0", "stmax", d.stmax);
		mysql.setFila("d2", "0", "hmin", d.hmin);
		mysql.setFila("d2", "0", "hmax", d.hmax);

		mysql.setFila("d2", "0", "uv", d.uv);


		d = predicciones.get(3);
		mysql.setFila("d3", "i1", "estados", d.estados[0]);
		mysql.setFila("d3", "i2", "estados", d.estados[1]);

		mysql.setFila("d3", "i1", "precip", d.precip[0]);
		mysql.setFila("d3", "i2", "precip", d.precip[1]);

		mysql.setFila("d3", "i1", "viento", d.viento[0]);
		mysql.setFila("d3", "i2", "viento", d.viento[1]);

		mysql.setFila("d3", "i1", "vviento", d.vviento[0]);
		mysql.setFila("d3", "i2", "vviento", d.vviento[1]);

		mysql.setFila("d3", "i1", "temp", d.temp[0]);
		mysql.setFila("d3", "i2", "temp", d.temp[1]);

		mysql.setFila("d3", "i1", "stemp", d.stemp[0]);
		mysql.setFila("d3", "i2", "stemp", d.stemp[1]);

		mysql.setFila("d3", "i1", "humedad", d.humedad[0]);
		mysql.setFila("d3", "i2", "humedad", d.humedad[1]);

		mysql.setFila("d3", "0", "tmin", d.tmin);
		mysql.setFila("d3", "0", "tmax", d.tmax);
		mysql.setFila("d3", "0", "stmin", d.stmin);
		mysql.setFila("d3", "0", "stmax", d.stmax);
		mysql.setFila("d3", "0", "hmin", d.hmin);
		mysql.setFila("d3", "0", "hmax", d.hmax);

		mysql.setFila("d3", "0", "uv", d.uv);


		d = predicciones.get(4);
		mysql.setFila("d4", "i1", "estados", d.estados[0]);

		mysql.setFila("d4", "i1", "precip", d.precip[0]);

		mysql.setFila("d4", "i1", "viento", d.viento[0]);

		mysql.setFila("d4", "i1", "vviento", d.vviento[0]);

		mysql.setFila("d4", "i1", "temp", d.temp[0]);

		mysql.setFila("d4", "i1", "stemp", d.stemp[0]);

		mysql.setFila("d4", "i1", "humedad", d.humedad[0]);

		mysql.setFila("d4", "0", "tmin", d.tmin);
		mysql.setFila("d4", "0", "tmax", d.tmax);
		mysql.setFila("d4", "0", "stmin", d.stmin);
		mysql.setFila("d4", "0", "stmax", d.stmax);
		mysql.setFila("d4", "0", "hmin", d.hmin);
		mysql.setFila("d4", "0", "hmax", d.hmax);

		mysql.setFila("d4", "0", "uv", d.uv);


		d = predicciones.get(5);
		mysql.setFila("d5", "i1", "estados", d.estados[0]);

		mysql.setFila("d5", "i1", "precip", d.precip[0]);

		mysql.setFila("d5", "i1", "viento", d.viento[0]);

		mysql.setFila("d5", "i1", "vviento", d.vviento[0]);

		mysql.setFila("d5", "i1", "temp", d.temp[0]);
		mysql.setFila("d5", "i1", "stemp", d.stemp[0]);
		mysql.setFila("d5", "i1", "humedad", d.humedad[0]);

		mysql.setFila("d5", "0", "tmin", d.tmin);
		mysql.setFila("d5", "0", "tmax", d.tmax);
		mysql.setFila("d5", "0", "stmin", d.stmin);
		mysql.setFila("d5", "0", "stmax", d.stmax);
		mysql.setFila("d5", "0", "hmin", d.hmin);
		mysql.setFila("d5", "0", "hmax", d.hmax);


		d = predicciones.get(6);
		mysql.setFila("d6", "i1", "estados", d.estados[0]);

		mysql.setFila("d6", "i1", "precip", d.precip[0]);

		mysql.setFila("d6", "i1", "viento", d.viento[0]);

		mysql.setFila("d6", "i1", "vviento", d.vviento[0]);

		mysql.setFila("d6", "i1", "temp", d.temp[0]);
		mysql.setFila("d6", "i1", "stemp", d.stemp[0]);
		mysql.setFila("d6", "i1", "humedad", d.humedad[0]);

		mysql.setFila("d6", "0", "tmin", d.tmin);
		mysql.setFila("d6", "0", "tmax", d.tmax);
		mysql.setFila("d6", "0", "stmin", d.stmin);
		mysql.setFila("d6", "0", "stmax", d.stmax);
		mysql.setFila("d6", "0", "hmin", d.hmin);
		mysql.setFila("d6", "0", "hmax", d.hmax);


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

		JsonObject dia2 = new JsonObject();
		JsonObject dia2c = new JsonObject();
		dia2c.addProperty("f", mysql.getFila("d2", "0", "f"));
		dia2c.addProperty("d2_i1_estados", mysql.getFila("d2", "i1", "estados"));
		dia2c.addProperty("d2_i2_estados", mysql.getFila("d2", "i2", "estados"));
		dia2c.addProperty("d2_i1_precip", mysql.getFila("d2", "i1", "precip"));
		dia2c.addProperty("d2_i2_precip", mysql.getFila("d2", "i2", "precip"));
		dia2c.addProperty("d2_i1_viento", mysql.getFila("d2", "i1", "viento"));
		dia2c.addProperty("d2_i2_viento", mysql.getFila("d2", "i2", "viento"));
		dia2c.addProperty("d2_i1_vviento", mysql.getFila("d2", "i1", "vviento"));
		dia2c.addProperty("d2_i2_vviento", mysql.getFila("d2", "i2", "vviento"));
		dia2c.addProperty("d2_uv", mysql.getFila("d2", "0", "uv"));
		dia2c.addProperty("d2_tmin", mysql.getFila("d2", "0", "tmin"));
		dia2c.addProperty("d2_tmax", mysql.getFila("d2", "0", "tmax"));
		dia2c.addProperty("d2_stmin", mysql.getFila("d2", "0", "stmin"));
		dia2c.addProperty("d2_stmax", mysql.getFila("d2", "0", "stmax"));
		dia2c.addProperty("d2_hmin", mysql.getFila("d2", "0", "hmin"));
		dia2c.addProperty("d2_hmax", mysql.getFila("d2", "0", "hmax"));
		dia2.add("dia2", dia2c);
		dias.add(dia2);

		JsonObject dia3 = new JsonObject();
		JsonObject dia3c = new JsonObject();
		dia3c.addProperty("f", mysql.getFila("d3", "0", "f"));
		dia3c.addProperty("d3_i1_estados", mysql.getFila("d3", "i1", "estados"));
		dia3c.addProperty("d3_i2_estados", mysql.getFila("d3", "i2", "estados"));
		dia3c.addProperty("d3_i1_precip", mysql.getFila("d3", "i1", "precip"));
		dia3c.addProperty("d3_i2_precip", mysql.getFila("d3", "i2", "precip"));
		dia3c.addProperty("d3_i1_viento", mysql.getFila("d3", "i1", "viento"));
		dia3c.addProperty("d3_i2_viento", mysql.getFila("d3", "i2", "viento"));
		dia3c.addProperty("d3_i1_vviento", mysql.getFila("d3", "i1", "vviento"));
		dia3c.addProperty("d3_i2_vviento", mysql.getFila("d3", "i2", "vviento"));
		dia3c.addProperty("d3_uv", mysql.getFila("d3", "0", "uv"));
		dia3c.addProperty("d3_tmin", mysql.getFila("d3", "0", "tmin"));
		dia3c.addProperty("d3_tmax", mysql.getFila("d3", "0", "tmax"));
		dia3c.addProperty("d3_stmin", mysql.getFila("d3", "0", "stmin"));
		dia3c.addProperty("d3_stmax", mysql.getFila("d3", "0", "stmax"));
		dia3c.addProperty("d3_hmin", mysql.getFila("d3", "0", "hmin"));
		dia3c.addProperty("d3_hmax", mysql.getFila("d3", "0", "hmax"));
		dia3.add("dia3", dia3c);
		dias.add(dia3);

		JsonObject dia4 = new JsonObject();
		JsonObject dia4c = new JsonObject();
		dia4c.addProperty("f", mysql.getFila("d4", "0", "f"));
		dia4c.addProperty("d4_i1_estados", mysql.getFila("d4", "i1", "estados"));
		dia4c.addProperty("d4_i1_precip", mysql.getFila("d4", "i1", "precip"));
		dia4c.addProperty("d4_i1_viento", mysql.getFila("d4", "i1", "viento"));
		dia4c.addProperty("d4_i1_vviento", mysql.getFila("d4", "i1", "vviento"));
		dia4c.addProperty("d4_uv", mysql.getFila("d4", "0", "uv"));
		dia4c.addProperty("d4_tmin", mysql.getFila("d4", "0", "tmin"));
		dia4c.addProperty("d4_tmax", mysql.getFila("d4", "0", "tmax"));
		dia4c.addProperty("d4_stmin", mysql.getFila("d4", "0", "stmin"));
		dia4c.addProperty("d4_stmax", mysql.getFila("d4", "0", "stmax"));
		dia4c.addProperty("d4_hmin", mysql.getFila("d4", "0", "hmin"));
		dia4c.addProperty("d4_hmax", mysql.getFila("d4", "0", "hmax"));
		dia4.add("dia4", dia4c);
		dias.add(dia4);

		JsonObject dia5 = new JsonObject();
		JsonObject dia5c = new JsonObject();
		dia5c.addProperty("f", mysql.getFila("d5", "0", "f"));
		dia5c.addProperty("d5_i1_estados", mysql.getFila("d5", "i1", "estados"));
		dia5c.addProperty("d5_i1_precip", mysql.getFila("d5", "i1", "precip"));
		dia5c.addProperty("d5_i1_viento", mysql.getFila("d5", "i1", "viento"));
		dia5c.addProperty("d5_i1_vviento", mysql.getFila("d5", "i1", "vviento"));
		dia5c.addProperty("d5_tmin", mysql.getFila("d5", "0", "tmin"));
		dia5c.addProperty("d5_tmax", mysql.getFila("d5", "0", "tmax"));
		dia5c.addProperty("d5_stmin", mysql.getFila("d5", "0", "stmin"));
		dia5c.addProperty("d5_stmax", mysql.getFila("d5", "0", "stmax"));
		dia5c.addProperty("d5_hmin", mysql.getFila("d5", "0", "hmin"));
		dia5c.addProperty("d5_hmax", mysql.getFila("d5", "0", "hmax"));
		dia5.add("dia5", dia5c);
		dias.add(dia5);

		JsonObject dia6 = new JsonObject();
		JsonObject dia6c = new JsonObject();
		dia6c.addProperty("f", mysql.getFila("d6", "0", "f"));
		dia6c.addProperty("d6_i1_estados", mysql.getFila("d6", "i1", "estados"));
		dia6c.addProperty("d6_i1_precip", mysql.getFila("d6", "i1", "precip"));
		dia6c.addProperty("d6_i1_viento", mysql.getFila("d6", "i1", "viento"));
		dia6c.addProperty("d6_i1_vviento", mysql.getFila("d6", "i1", "vviento"));
		dia6c.addProperty("d6_tmin", mysql.getFila("d6", "0", "tmin"));
		dia6c.addProperty("d6_tmax", mysql.getFila("d6", "0", "tmax"));
		dia6c.addProperty("d6_stmin", mysql.getFila("d6", "0", "stmin"));
		dia6c.addProperty("d6_stmax", mysql.getFila("d6", "0", "stmax"));
		dia6c.addProperty("d6_hmin", mysql.getFila("d6", "0", "hmin"));
		dia6c.addProperty("d6_hmax", mysql.getFila("d6", "0", "hmax"));
		dia6.add("dia6", dia6c);
		dias.add(dia6);


		prediccionjson.add("prediccion", dias);
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		String jsonencoded = gson.toJson(prediccionjson);

		JsonParser jsonParser = new JsonParser();
		JsonObject json = (JsonObject) jsonParser.parse(jsonencoded);

		//JsonObject json = new JsonObject(jsonencoded);

		JsonArray prediccion3 = json.getAsJsonArray("prediccion");

		JsonObject[] diax = new JsonObject[7];

		for (int i = 0; i <= 6; i++) {
			diax[i] = prediccion3.get(i).getAsJsonObject().get("dia" + i).getAsJsonObject();
		}

		Configuration cfg = null;
		Template template = null;
		Map < String, String > datax = new HashMap < String, String > ();

		try {
			// Completar la plantilla con los datos obtenidos
			cfg = new Configuration();
			template = cfg.getTemplate("src/tabla.ftl");
		} catch (Exception e) {
			System.out.println(e);
		}

		// Fecha
		datax.put("d0_f", diax[0].get("f").toString());
		datax.put("d1_f", diax[1].get("f").toString());
		datax.put("d2_f", diax[2].get("f").toString());
		datax.put("d3_f", diax[3].get("f").toString());
		datax.put("d4_f", diax[4].get("f").toString());
		datax.put("d5_f", diax[5].get("f").toString());
		datax.put("d6_f", diax[6].get("f").toString());

		// Dia 0 //////////////////////////////////////////////////////////
		datax.put("d0_i1_estados", diax[0].get("d0_i1_estados").toString().replace("\"", ""));
		datax.put("d0_i2_estados", diax[0].get("d0_i2_estados").toString().replace("\"", ""));
		datax.put("d0_i1_precip", diax[0].get("d0_i1_precip").toString().replace("\"", ""));
		datax.put("d0_i2_precip", diax[0].get("d0_i2_precip").toString().replace("\"", ""));
		datax.put("d0_i1_viento", diax[0].get("d0_i1_viento").toString().replace("\"", ""));
		datax.put("d0_i2_viento", diax[0].get("d0_i2_viento").toString().replace("\"", ""));
		datax.put("d0_i1_vviento", diax[0].get("d0_i1_vviento").toString().replace("\"", ""));
		datax.put("d0_i2_vviento", diax[0].get("d0_i2_vviento").toString().replace("\"", ""));
		datax.put("d0_i1_temp", diax[0].get("d0_i1_temp").toString().replace("\"", ""));
		datax.put("d0_i2_temp", diax[0].get("d0_i2_temp").toString().replace("\"", ""));
		datax.put("d0_i1_stemp", diax[0].get("d0_i1_stemp").toString().replace("\"", ""));
		datax.put("d0_i2_stemp", diax[0].get("d0_i2_stemp").toString().replace("\"", ""));
		datax.put("d0_i1_humedad", diax[0].get("d0_i1_humedad").toString().replace("\"", ""));
		datax.put("d0_i2_humedad", diax[0].get("d0_i2_humedad").toString().replace("\"", ""));
		datax.put("d0_tmin", diax[0].get("d0_tmin").toString().replace("\"", ""));
		datax.put("d0_tmax", diax[0].get("d0_tmax").toString().replace("\"", ""));
		datax.put("d0_stmin", diax[0].get("d0_stmin").toString().replace("\"", ""));
		datax.put("d0_stmax", diax[0].get("d0_stmax").toString().replace("\"", ""));
		datax.put("d0_hmin", diax[0].get("d0_hmin").toString().replace("\"", ""));
		datax.put("d0_hmax", diax[0].get("d0_hmax").toString().replace("\"", ""));
		datax.put("d0_uv", diax[0].get("d0_uv").toString().replace("\"", ""));


		// Dia 1 //////////////////////////////////////////////////////////
		datax.put("d1_i1_estados", diax[1].get("d1_i1_estados").toString().replace("\"", ""));
		datax.put("d1_i2_estados", diax[1].get("d1_i2_estados").toString().replace("\"", ""));
		datax.put("d1_i3_estados", diax[1].get("d1_i3_estados").toString().replace("\"", ""));
		datax.put("d1_i4_estados", diax[1].get("d1_i4_estados").toString().replace("\"", ""));
		datax.put("d1_i1_precip", diax[1].get("d1_i1_precip").toString().replace("\"", ""));
		datax.put("d1_i2_precip", diax[1].get("d1_i2_precip").toString().replace("\"", ""));
		datax.put("d1_i3_precip", diax[1].get("d1_i3_precip").toString().replace("\"", ""));
		datax.put("d1_i4_precip", diax[1].get("d1_i4_precip").toString().replace("\"", ""));
		datax.put("d1_i1_viento", diax[1].get("d1_i1_viento").toString().replace("\"", ""));
		datax.put("d1_i2_viento", diax[1].get("d1_i2_viento").toString().replace("\"", ""));
		datax.put("d1_i3_viento", diax[1].get("d1_i3_viento").toString().replace("\"", ""));
		datax.put("d1_i4_viento", diax[1].get("d1_i4_viento").toString().replace("\"", ""));
		datax.put("d1_i1_vviento", diax[1].get("d1_i1_vviento").toString().replace("\"", ""));
		datax.put("d1_i2_vviento", diax[1].get("d1_i2_vviento").toString().replace("\"", ""));
		datax.put("d1_i3_vviento", diax[1].get("d1_i3_vviento").toString().replace("\"", ""));
		datax.put("d1_i4_vviento", diax[1].get("d1_i4_vviento").toString().replace("\"", ""));
		datax.put("d1_i1_temp", diax[1].get("d1_i1_temp").toString().replace("\"", ""));
		datax.put("d1_i2_temp", diax[1].get("d1_i2_temp").toString().replace("\"", ""));
		datax.put("d1_i3_temp", diax[1].get("d1_i3_temp").toString().replace("\"", ""));
		datax.put("d1_i4_temp", diax[1].get("d1_i4_temp").toString().replace("\"", ""));
		datax.put("d1_i1_stemp", diax[1].get("d1_i1_stemp").toString().replace("\"", ""));
		datax.put("d1_i2_stemp", diax[1].get("d1_i2_stemp").toString().replace("\"", ""));
		datax.put("d1_i3_stemp", diax[1].get("d1_i3_stemp").toString().replace("\"", ""));
		datax.put("d1_i4_stemp", diax[1].get("d1_i4_stemp").toString().replace("\"", ""));
		datax.put("d1_i1_humedad", diax[1].get("d1_i1_humedad").toString().replace("\"", ""));
		datax.put("d1_i2_humedad", diax[1].get("d1_i2_humedad").toString().replace("\"", ""));
		datax.put("d1_i3_humedad", diax[1].get("d1_i3_humedad").toString().replace("\"", ""));
		datax.put("d1_i4_humedad", diax[1].get("d1_i4_humedad").toString().replace("\"", ""));
		datax.put("d1_tmin", diax[1].get("d1_tmin").toString().replace("\"", ""));
		datax.put("d1_tmax", diax[1].get("d1_tmax").toString().replace("\"", ""));
		datax.put("d1_stmin", diax[1].get("d1_stmin").toString().replace("\"", ""));
		datax.put("d1_stmax", diax[1].get("d1_stmax").toString().replace("\"", ""));
		datax.put("d1_hmin", diax[1].get("d1_hmin").toString().replace("\"", ""));
		datax.put("d1_hmax", diax[1].get("d1_hmax").toString().replace("\"", ""));
		datax.put("d1_uv", diax[1].get("d1_uv").toString().replace("\"", ""));



		// Dia 2 //////////////////////////////////////////////////////////
		datax.put("d2_i1_estados", diax[2].get("d2_i1_estados").toString().replace("\"", ""));
		datax.put("d2_i2_estados", diax[2].get("d2_i2_estados").toString().replace("\"", ""));
		datax.put("d2_i1_precip", diax[2].get("d2_i1_precip").toString().replace("\"", ""));
		datax.put("d2_i2_precip", diax[2].get("d2_i2_precip").toString().replace("\"", ""));
		datax.put("d2_i1_viento", diax[2].get("d2_i1_viento").toString().replace("\"", ""));
		datax.put("d2_i2_viento", diax[2].get("d2_i2_viento").toString().replace("\"", ""));
		datax.put("d2_i1_vviento", diax[2].get("d2_i1_vviento").toString().replace("\"", ""));
		datax.put("d2_i2_vviento", diax[2].get("d2_i2_vviento").toString().replace("\"", ""));
		datax.put("d2_tmin", diax[2].get("d2_tmin").toString().replace("\"", ""));
		datax.put("d2_tmax", diax[2].get("d2_tmax").toString().replace("\"", ""));
		datax.put("d2_stmin", diax[2].get("d2_stmin").toString().replace("\"", ""));
		datax.put("d2_stmax", diax[2].get("d2_stmax").toString().replace("\"", ""));
		datax.put("d2_hmin", diax[2].get("d2_hmin").toString().replace("\"", ""));
		datax.put("d2_hmax", diax[2].get("d2_hmax").toString().replace("\"", ""));
		datax.put("d2_uv", diax[2].get("d2_uv").toString().replace("\"", ""));


		// Dia 3 //////////////////////////////////////////////////////////
		datax.put("d3_i1_estados", diax[3].get("d3_i1_estados").toString().replace("\"", ""));
		datax.put("d3_i2_estados", diax[3].get("d3_i2_estados").toString().replace("\"", ""));
		datax.put("d3_i1_precip", diax[3].get("d3_i1_precip").toString().replace("\"", ""));
		datax.put("d3_i2_precip", diax[3].get("d3_i2_precip").toString().replace("\"", ""));
		datax.put("d3_i1_viento", diax[3].get("d3_i1_viento").toString().replace("\"", ""));
		datax.put("d3_i2_viento", diax[3].get("d3_i2_viento").toString().replace("\"", ""));
		datax.put("d3_i1_vviento", diax[3].get("d3_i1_vviento").toString().replace("\"", ""));
		datax.put("d3_i2_vviento", diax[3].get("d3_i2_vviento").toString().replace("\"", ""));
		datax.put("d3_tmin", diax[3].get("d3_tmin").toString().replace("\"", ""));
		datax.put("d3_tmax", diax[3].get("d3_tmax").toString().replace("\"", ""));
		datax.put("d3_stmin", diax[3].get("d3_stmin").toString().replace("\"", ""));
		datax.put("d3_stmax", diax[3].get("d3_stmax").toString().replace("\"", ""));
		datax.put("d3_hmin", diax[3].get("d3_hmin").toString().replace("\"", ""));
		datax.put("d3_hmax", diax[3].get("d3_hmax").toString().replace("\"", ""));
		datax.put("d3_uv", diax[3].get("d3_uv").toString().replace("\"", ""));


		// Dia 4 //////////////////////////////////////////////////////////
		datax.put("d4_i1_estados", diax[4].get("d4_i1_estados").toString().replace("\"", ""));
		datax.put("d4_i1_precip", diax[4].get("d4_i1_precip").toString().replace("\"", ""));
		datax.put("d4_i1_viento", diax[4].get("d4_i1_viento").toString().replace("\"", ""));
		datax.put("d4_i1_vviento", diax[4].get("d4_i1_vviento").toString().replace("\"", ""));
		datax.put("d4_tmin", diax[4].get("d4_tmin").toString().replace("\"", ""));
		datax.put("d4_tmax", diax[4].get("d4_tmax").toString().replace("\"", ""));
		datax.put("d4_stmin", diax[4].get("d4_stmin").toString().replace("\"", ""));
		datax.put("d4_stmax", diax[4].get("d4_stmax").toString().replace("\"", ""));
		datax.put("d4_hmin", diax[4].get("d4_hmin").toString().replace("\"", ""));
		datax.put("d4_hmax", diax[4].get("d4_hmax").toString().replace("\"", ""));
		datax.put("d4_uv", diax[4].get("d4_uv").toString().replace("\"", ""));


		// Dia 5 //////////////////////////////////////////////////////////
		datax.put("d5_i1_estados", diax[5].get("d5_i1_estados").toString().replace("\"", ""));
		datax.put("d5_i1_precip", diax[5].get("d5_i1_precip").toString().replace("\"", ""));
		datax.put("d5_i1_viento", diax[5].get("d5_i1_viento").toString().replace("\"", ""));
		datax.put("d5_i1_vviento", diax[5].get("d5_i1_vviento").toString().replace("\"", ""));
		datax.put("d5_tmin", diax[5].get("d5_tmin").toString().replace("\"", ""));
		datax.put("d5_tmax", diax[5].get("d5_tmax").toString().replace("\"", ""));
		datax.put("d5_stmin", diax[5].get("d5_stmin").toString().replace("\"", ""));
		datax.put("d5_stmax", diax[5].get("d5_stmax").toString().replace("\"", ""));
		datax.put("d5_hmin", diax[5].get("d5_hmin").toString().replace("\"", ""));
		datax.put("d5_hmax", diax[5].get("d5_hmax").toString().replace("\"", ""));


		// Dia 6 //////////////////////////////////////////////////////////
		datax.put("d6_i1_estados", diax[6].get("d6_i1_estados").toString().replace("\"", ""));
		datax.put("d6_i1_precip", diax[6].get("d6_i1_precip").toString().replace("\"", ""));
		datax.put("d6_i1_viento", diax[6].get("d6_i1_viento").toString().replace("\"", ""));
		datax.put("d6_i1_vviento", diax[6].get("d6_i1_vviento").toString().replace("\"", ""));
		datax.put("d6_tmin", diax[6].get("d6_tmin").toString().replace("\"", ""));
		datax.put("d6_tmax", diax[6].get("d6_tmax").toString().replace("\"", ""));
		datax.put("d6_stmin", diax[6].get("d6_stmin").toString().replace("\"", ""));
		datax.put("d6_stmax", diax[6].get("d6_stmax").toString().replace("\"", ""));
		datax.put("d6_hmin", diax[6].get("d6_hmin").toString().replace("\"", ""));
		datax.put("d6_hmax", diax[6].get("d6_hmax").toString().replace("\"", ""));

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
