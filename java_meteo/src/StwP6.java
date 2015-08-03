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

import freemarker.template.*;

public class StwP6 {

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

	private static String xmlSource = "http://www.aemet.es/xml/municipios/localidad_50297.xml";

	public static void main(String[] args) throws JDOMException, IOException, TemplateException {

		SAXBuilder jdomBuilder = new SAXBuilder();
		Document jdomDocument = jdomBuilder.build(xmlSource);

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

			foo.print();

			predicciones.add(foo);
		}

		// Completar la plantilla con los datos obtenidos
		Configuration cfg = new Configuration();
		Template template = cfg.getTemplate("src/tabla.ftl");
		Map < String, String > data = new HashMap < String, String > ();

		// Fecha
		data.put("d0_f", predicciones.get(0).fecha);
		data.put("d1_f", predicciones.get(1).fecha);
		data.put("d2_f", predicciones.get(2).fecha);
		data.put("d3_f", predicciones.get(3).fecha);
		data.put("d4_f", predicciones.get(4).fecha);
		data.put("d5_f", predicciones.get(5).fecha);
		data.put("d6_f", predicciones.get(6).fecha);

		// Dia 0 //////////////////////////////////////////////////////////
		Prediccion d = predicciones.get(0);
		data.put("d0_i1_estados", d.estados[2]);
		data.put("d0_i2_estados", d.estados[3]);

		data.put("d0_i1_precip", d.precip[2]);
		data.put("d0_i2_precip", d.precip[3]);

		data.put("d0_i1_viento", d.viento[2]);
		data.put("d0_i2_viento", d.viento[3]);

		data.put("d0_i1_vviento", d.vviento[2]);
		data.put("d0_i2_vviento", d.vviento[3]);

		data.put("d0_i1_temp", d.temp[2]);
		data.put("d0_i2_temp", d.temp[3]);
		data.put("d0_i1_stemp", d.stemp[2]);
		data.put("d0_i2_stemp", d.stemp[3]);
		data.put("d0_i1_humedad", d.humedad[2]);
		data.put("d0_i2_humedad", d.humedad[3]);

		data.put("d0_tmin", d.tmin);
		data.put("d0_tmax", d.tmax);
		data.put("d0_stmin", d.stmin);
		data.put("d0_stmax", d.stmax);
		data.put("d0_hmin", d.hmin);
		data.put("d0_hmax", d.hmax);

		data.put("d0_uv", d.uv);


		// Dia 1 //////////////////////////////////////////////////////////
		d = predicciones.get(1);
		data.put("d1_i1_estados", d.estados[0]);
		data.put("d1_i2_estados", d.estados[1]);
		data.put("d1_i3_estados", d.estados[2]);
		data.put("d1_i4_estados", d.estados[3]);

		data.put("d1_i1_precip", d.precip[0]);
		data.put("d1_i2_precip", d.precip[1]);
		data.put("d1_i3_precip", d.precip[2]);
		data.put("d1_i4_precip", d.precip[3]);

		data.put("d1_i1_viento", d.viento[0]);
		data.put("d1_i2_viento", d.viento[1]);
		data.put("d1_i3_viento", d.viento[2]);
		data.put("d1_i4_viento", d.viento[3]);

		data.put("d1_i1_vviento", d.vviento[0]);
		data.put("d1_i2_vviento", d.vviento[1]);
		data.put("d1_i3_vviento", d.vviento[2]);
		data.put("d1_i4_vviento", d.vviento[3]);

		data.put("d1_i1_temp", d.temp[0]);
		data.put("d1_i2_temp", d.temp[1]);
		data.put("d1_i3_temp", d.temp[2]);
		data.put("d1_i4_temp", d.temp[3]);
		data.put("d1_i1_stemp", d.stemp[0]);
		data.put("d1_i2_stemp", d.stemp[1]);
		data.put("d1_i3_stemp", d.stemp[2]);
		data.put("d1_i4_stemp", d.stemp[3]);
		data.put("d1_i1_humedad", d.humedad[0]);
		data.put("d1_i2_humedad", d.humedad[1]);
		data.put("d1_i3_humedad", d.humedad[2]);
		data.put("d1_i4_humedad", d.humedad[3]);

		data.put("d1_tmin", d.tmin);
		data.put("d1_tmax", d.tmax);
		data.put("d1_stmin", d.stmin);
		data.put("d1_stmax", d.stmax);
		data.put("d1_hmin", d.hmin);
		data.put("d1_hmax", d.hmax);

		data.put("d1_uv", d.uv);

		// Dia 2 //////////////////////////////////////////////////////////
		d = predicciones.get(2);
		data.put("d2_i1_estados", d.estados[0]);
		data.put("d2_i2_estados", d.estados[1]);

		data.put("d2_i1_precip", d.precip[0]);
		data.put("d2_i2_precip", d.precip[1]);

		data.put("d2_i1_viento", d.viento[0]);
		data.put("d2_i2_viento", d.viento[1]);

		data.put("d2_i1_vviento", d.vviento[0]);
		data.put("d2_i2_vviento", d.vviento[1]);

		data.put("d2_i1_temp", d.temp[0]);
		data.put("d2_i2_temp", d.temp[1]);
		data.put("d2_i1_temp", d.stemp[0]);
		data.put("d2_i2_temp", d.stemp[1]);
		data.put("d2_i1_temp", d.humedad[0]);
		data.put("d2_i2_temp", d.humedad[1]);

		data.put("d2_tmin", d.tmin);
		data.put("d2_tmax", d.tmax);
		data.put("d2_stmin", d.stmin);
		data.put("d2_stmax", d.stmax);
		data.put("d2_hmin", d.hmin);
		data.put("d2_hmax", d.hmax);

		data.put("d2_uv", d.uv);

		// Dia 3 //////////////////////////////////////////////////////////
		d = predicciones.get(3);
		data.put("d3_i1_estados", d.estados[0]);
		data.put("d3_i2_estados", d.estados[1]);

		data.put("d3_i1_precip", d.precip[0]);
		data.put("d3_i2_precip", d.precip[1]);

		data.put("d3_i1_viento", d.viento[0]);
		data.put("d3_i2_viento", d.viento[1]);

		data.put("d3_i1_vviento", d.vviento[0]);
		data.put("d3_i2_vviento", d.vviento[1]);

		data.put("d3_i1_temp", d.temp[0]);
		data.put("d3_i2_temp", d.temp[1]);
		data.put("d3_i1_temp", d.stemp[0]);
		data.put("d3_i2_temp", d.stemp[1]);
		data.put("d3_i1_temp", d.humedad[0]);
		data.put("d3_i2_temp", d.humedad[1]);

		data.put("d3_tmin", d.tmin);
		data.put("d3_tmax", d.tmax);
		data.put("d3_stmin", d.stmin);
		data.put("d3_stmax", d.stmax);
		data.put("d3_hmin", d.hmin);
		data.put("d3_hmax", d.hmax);

		data.put("d3_uv", d.uv);

		// Dia 4 //////////////////////////////////////////////////////////
		d = predicciones.get(4);
		data.put("d4_i1_estados", d.estados[0]);

		data.put("d4_i1_precip", d.precip[0]);

		data.put("d4_i1_viento", d.viento[0]);

		data.put("d4_i1_vviento", d.vviento[0]);

		data.put("d4_i1_temp", d.temp[0]);
		data.put("d4_i1_stemp", d.stemp[0]);
		data.put("d4_i1_humedad", d.humedad[0]);

		data.put("d4_tmin", d.tmin);
		data.put("d4_tmax", d.tmax);
		data.put("d4_stmin", d.stmin);
		data.put("d4_stmax", d.stmax);
		data.put("d4_hmin", d.hmin);
		data.put("d4_hmax", d.hmax);

		data.put("d4_uv", d.uv);

		// Dia 5 //////////////////////////////////////////////////////////
		d = predicciones.get(5);
		data.put("d5_i1_estados", d.estados[0]);

		data.put("d5_i1_precip", d.precip[0]);

		data.put("d5_i1_viento", d.viento[0]);

		data.put("d5_i1_vviento", d.vviento[0]);

		data.put("d5_i1_temp", d.temp[0]);
		data.put("d5_i1_temp", d.stemp[0]);
		data.put("d5_i1_temp", d.humedad[0]);

		data.put("d5_tmin", d.tmin);
		data.put("d5_tmax", d.tmax);
		data.put("d5_stmin", d.stmin);
		data.put("d5_stmax", d.stmax);
		data.put("d5_hmin", d.hmin);
		data.put("d5_hmax", d.hmax);

		// Dia 6 //////////////////////////////////////////////////////////
		d = predicciones.get(6);
		data.put("d6_i1_estados", d.estados[0]);

		data.put("d6_i1_precip", d.precip[0]);

		data.put("d6_i1_viento", d.viento[0]);

		data.put("d6_i1_vviento", d.vviento[0]);

		data.put("d6_i1_temp", d.temp[0]);
		data.put("d6_i1_temp", d.stemp[0]);
		data.put("d6_i1_temp", d.humedad[0]);

		data.put("d6_tmin", d.tmin);
		data.put("d6_tmax", d.tmax);
		data.put("d6_stmin", d.stmin);
		data.put("d6_stmax", d.stmax);
		data.put("d6_hmin", d.hmin);
		data.put("d6_hmax", d.hmax);

		Writer file = new FileWriter(new File("www/tabla.html"));
		template.process(data, file);
		file.flush();
		file.close();

	}
}
