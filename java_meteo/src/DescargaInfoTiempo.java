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


public class DescargaInfoTiempo {

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

	public static void main(String[] args) throws JDOMException, IOException {

		String xmlSource = "http://www.aemet.es/xml/municipios/localidad_" + args[0] + ".xml";

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

			//foo.print();

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
	}
}
