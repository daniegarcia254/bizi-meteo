/*
 * AUTOR: Javier Briz Alastrue
 * NIA: 576695
 */

package stw.p6;

import com.google.gson.*;

import java.net.URL;
import java.nio.charset.Charset;

import java.io.*;
import java.util.*;

import freemarker.template.*;
import java.text.ParseException;

public class GenerarPaginaTiempo {

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


	public static void main(String[] args) {
		JsonObject json = null;
		try {
			json = readJsonFromUrl(args[0]);
		} catch (Exception e) {}

		JsonArray prediccion = json.getAsJsonArray("prediccion");

		JsonObject[] dia = new JsonObject[7];

		for (int i = 0; i <= 6; i++) {
			dia[i] = prediccion.get(i).getAsJsonObject().get("dia" + i).getAsJsonObject();
		}

		Configuration cfg = null;
		Template template = null;
		Map < String, String > data = new HashMap < String, String > ();

		try {
			// Completar la plantilla con los datos obtenidos
			cfg = new Configuration();
			template = cfg.getTemplate("src/tabla.ftl");
		} catch (Exception e) {
			System.out.println(e);
		}

		// Fecha
		data.put("d0_f", dia[0].get("f").toString());
		data.put("d1_f", dia[1].get("f").toString());
		data.put("d2_f", dia[2].get("f").toString());
		data.put("d3_f", dia[3].get("f").toString());
		data.put("d4_f", dia[4].get("f").toString());
		data.put("d5_f", dia[5].get("f").toString());
		data.put("d6_f", dia[6].get("f").toString());

		// Dia 0 //////////////////////////////////////////////////////////
		data.put("d0_i1_estados", dia[0].get("d0_i1_estados").toString().replace("\"", ""));
		data.put("d0_i2_estados", dia[0].get("d0_i2_estados").toString().replace("\"", ""));
		data.put("d0_i1_precip", dia[0].get("d0_i1_precip").toString().replace("\"", ""));
		data.put("d0_i2_precip", dia[0].get("d0_i2_precip").toString().replace("\"", ""));
		data.put("d0_i1_viento", dia[0].get("d0_i1_viento").toString().replace("\"", ""));
		data.put("d0_i2_viento", dia[0].get("d0_i2_viento").toString().replace("\"", ""));
		data.put("d0_i1_vviento", dia[0].get("d0_i1_vviento").toString().replace("\"", ""));
		data.put("d0_i2_vviento", dia[0].get("d0_i2_vviento").toString().replace("\"", ""));
		data.put("d0_i1_temp", dia[0].get("d0_i1_temp").toString().replace("\"", ""));
		data.put("d0_i2_temp", dia[0].get("d0_i2_temp").toString().replace("\"", ""));
		data.put("d0_i1_stemp", dia[0].get("d0_i1_stemp").toString().replace("\"", ""));
		data.put("d0_i2_stemp", dia[0].get("d0_i2_stemp").toString().replace("\"", ""));
		data.put("d0_i1_humedad", dia[0].get("d0_i1_humedad").toString().replace("\"", ""));
		data.put("d0_i2_humedad", dia[0].get("d0_i2_humedad").toString().replace("\"", ""));
		data.put("d0_tmin", dia[0].get("d0_tmin").toString().replace("\"", ""));
		data.put("d0_tmax", dia[0].get("d0_tmax").toString().replace("\"", ""));
		data.put("d0_stmin", dia[0].get("d0_stmin").toString().replace("\"", ""));
		data.put("d0_stmax", dia[0].get("d0_stmax").toString().replace("\"", ""));
		data.put("d0_hmin", dia[0].get("d0_hmin").toString().replace("\"", ""));
		data.put("d0_hmax", dia[0].get("d0_hmax").toString().replace("\"", ""));
		data.put("d0_uv", dia[0].get("d0_uv").toString().replace("\"", ""));


		// Dia 1 //////////////////////////////////////////////////////////
		data.put("d1_i1_estados", dia[1].get("d1_i1_estados").toString().replace("\"", ""));
		data.put("d1_i2_estados", dia[1].get("d1_i2_estados").toString().replace("\"", ""));
		data.put("d1_i3_estados", dia[1].get("d1_i3_estados").toString().replace("\"", ""));
		data.put("d1_i4_estados", dia[1].get("d1_i4_estados").toString().replace("\"", ""));
		data.put("d1_i1_precip", dia[1].get("d1_i1_precip").toString().replace("\"", ""));
		data.put("d1_i2_precip", dia[1].get("d1_i2_precip").toString().replace("\"", ""));
		data.put("d1_i3_precip", dia[1].get("d1_i3_precip").toString().replace("\"", ""));
		data.put("d1_i4_precip", dia[1].get("d1_i4_precip").toString().replace("\"", ""));
		data.put("d1_i1_viento", dia[1].get("d1_i1_viento").toString().replace("\"", ""));
		data.put("d1_i2_viento", dia[1].get("d1_i2_viento").toString().replace("\"", ""));
		data.put("d1_i3_viento", dia[1].get("d1_i3_viento").toString().replace("\"", ""));
		data.put("d1_i4_viento", dia[1].get("d1_i4_viento").toString().replace("\"", ""));
		data.put("d1_i1_vviento", dia[1].get("d1_i1_vviento").toString().replace("\"", ""));
		data.put("d1_i2_vviento", dia[1].get("d1_i2_vviento").toString().replace("\"", ""));
		data.put("d1_i3_vviento", dia[1].get("d1_i3_vviento").toString().replace("\"", ""));
		data.put("d1_i4_vviento", dia[1].get("d1_i4_vviento").toString().replace("\"", ""));
		data.put("d1_i1_temp", dia[1].get("d1_i1_temp").toString().replace("\"", ""));
		data.put("d1_i2_temp", dia[1].get("d1_i2_temp").toString().replace("\"", ""));
		data.put("d1_i3_temp", dia[1].get("d1_i3_temp").toString().replace("\"", ""));
		data.put("d1_i4_temp", dia[1].get("d1_i4_temp").toString().replace("\"", ""));
		data.put("d1_i1_stemp", dia[1].get("d1_i1_stemp").toString().replace("\"", ""));
		data.put("d1_i2_stemp", dia[1].get("d1_i2_stemp").toString().replace("\"", ""));
		data.put("d1_i3_stemp", dia[1].get("d1_i3_stemp").toString().replace("\"", ""));
		data.put("d1_i4_stemp", dia[1].get("d1_i4_stemp").toString().replace("\"", ""));
		data.put("d1_i1_humedad", dia[1].get("d1_i1_humedad").toString().replace("\"", ""));
		data.put("d1_i2_humedad", dia[1].get("d1_i2_humedad").toString().replace("\"", ""));
		data.put("d1_i3_humedad", dia[1].get("d1_i3_humedad").toString().replace("\"", ""));
		data.put("d1_i4_humedad", dia[1].get("d1_i4_humedad").toString().replace("\"", ""));
		data.put("d1_tmin", dia[1].get("d1_tmin").toString().replace("\"", ""));
		data.put("d1_tmax", dia[1].get("d1_tmax").toString().replace("\"", ""));
		data.put("d1_stmin", dia[1].get("d1_stmin").toString().replace("\"", ""));
		data.put("d1_stmax", dia[1].get("d1_stmax").toString().replace("\"", ""));
		data.put("d1_hmin", dia[1].get("d1_hmin").toString().replace("\"", ""));
		data.put("d1_hmax", dia[1].get("d1_hmax").toString().replace("\"", ""));
		data.put("d1_uv", dia[1].get("d1_uv").toString().replace("\"", ""));



		// Dia 2 //////////////////////////////////////////////////////////
		data.put("d2_i1_estados", dia[2].get("d2_i1_estados").toString().replace("\"", ""));
		data.put("d2_i2_estados", dia[2].get("d2_i2_estados").toString().replace("\"", ""));
		data.put("d2_i1_precip", dia[2].get("d2_i1_precip").toString().replace("\"", ""));
		data.put("d2_i2_precip", dia[2].get("d2_i2_precip").toString().replace("\"", ""));
		data.put("d2_i1_viento", dia[2].get("d2_i1_viento").toString().replace("\"", ""));
		data.put("d2_i2_viento", dia[2].get("d2_i2_viento").toString().replace("\"", ""));
		data.put("d2_i1_vviento", dia[2].get("d2_i1_vviento").toString().replace("\"", ""));
		data.put("d2_i2_vviento", dia[2].get("d2_i2_vviento").toString().replace("\"", ""));
		data.put("d2_tmin", dia[2].get("d2_tmin").toString().replace("\"", ""));
		data.put("d2_tmax", dia[2].get("d2_tmax").toString().replace("\"", ""));
		data.put("d2_stmin", dia[2].get("d2_stmin").toString().replace("\"", ""));
		data.put("d2_stmax", dia[2].get("d2_stmax").toString().replace("\"", ""));
		data.put("d2_hmin", dia[2].get("d2_hmin").toString().replace("\"", ""));
		data.put("d2_hmax", dia[2].get("d2_hmax").toString().replace("\"", ""));
		data.put("d2_uv", dia[2].get("d2_uv").toString().replace("\"", ""));


		// Dia 3 //////////////////////////////////////////////////////////
		data.put("d3_i1_estados", dia[3].get("d3_i1_estados").toString().replace("\"", ""));
		data.put("d3_i2_estados", dia[3].get("d3_i2_estados").toString().replace("\"", ""));
		data.put("d3_i1_precip", dia[3].get("d3_i1_precip").toString().replace("\"", ""));
		data.put("d3_i2_precip", dia[3].get("d3_i2_precip").toString().replace("\"", ""));
		data.put("d3_i1_viento", dia[3].get("d3_i1_viento").toString().replace("\"", ""));
		data.put("d3_i2_viento", dia[3].get("d3_i2_viento").toString().replace("\"", ""));
		data.put("d3_i1_vviento", dia[3].get("d3_i1_vviento").toString().replace("\"", ""));
		data.put("d3_i2_vviento", dia[3].get("d3_i2_vviento").toString().replace("\"", ""));
		data.put("d3_tmin", dia[3].get("d3_tmin").toString().replace("\"", ""));
		data.put("d3_tmax", dia[3].get("d3_tmax").toString().replace("\"", ""));
		data.put("d3_stmin", dia[3].get("d3_stmin").toString().replace("\"", ""));
		data.put("d3_stmax", dia[3].get("d3_stmax").toString().replace("\"", ""));
		data.put("d3_hmin", dia[3].get("d3_hmin").toString().replace("\"", ""));
		data.put("d3_hmax", dia[3].get("d3_hmax").toString().replace("\"", ""));
		data.put("d3_uv", dia[3].get("d3_uv").toString().replace("\"", ""));


		// Dia 4 //////////////////////////////////////////////////////////
		data.put("d4_i1_estados", dia[4].get("d4_i1_estados").toString().replace("\"", ""));
		data.put("d4_i1_precip", dia[4].get("d4_i1_precip").toString().replace("\"", ""));
		data.put("d4_i1_viento", dia[4].get("d4_i1_viento").toString().replace("\"", ""));
		data.put("d4_i1_vviento", dia[4].get("d4_i1_vviento").toString().replace("\"", ""));
		data.put("d4_tmin", dia[4].get("d4_tmin").toString().replace("\"", ""));
		data.put("d4_tmax", dia[4].get("d4_tmax").toString().replace("\"", ""));
		data.put("d4_stmin", dia[4].get("d4_stmin").toString().replace("\"", ""));
		data.put("d4_stmax", dia[4].get("d4_stmax").toString().replace("\"", ""));
		data.put("d4_hmin", dia[4].get("d4_hmin").toString().replace("\"", ""));
		data.put("d4_hmax", dia[4].get("d4_hmax").toString().replace("\"", ""));
		data.put("d4_uv", dia[4].get("d4_uv").toString().replace("\"", ""));


		// Dia 5 //////////////////////////////////////////////////////////
		data.put("d5_i1_estados", dia[5].get("d5_i1_estados").toString().replace("\"", ""));
		data.put("d5_i1_precip", dia[5].get("d5_i1_precip").toString().replace("\"", ""));
		data.put("d5_i1_viento", dia[5].get("d5_i1_viento").toString().replace("\"", ""));
		data.put("d5_i1_vviento", dia[5].get("d5_i1_vviento").toString().replace("\"", ""));
		data.put("d5_tmin", dia[5].get("d5_tmin").toString().replace("\"", ""));
		data.put("d5_tmax", dia[5].get("d5_tmax").toString().replace("\"", ""));
		data.put("d5_stmin", dia[5].get("d5_stmin").toString().replace("\"", ""));
		data.put("d5_stmax", dia[5].get("d5_stmax").toString().replace("\"", ""));
		data.put("d5_hmin", dia[5].get("d5_hmin").toString().replace("\"", ""));
		data.put("d5_hmax", dia[5].get("d5_hmax").toString().replace("\"", ""));


		// Dia 6 //////////////////////////////////////////////////////////
		data.put("d6_i1_estados", dia[6].get("d6_i1_estados").toString().replace("\"", ""));
		data.put("d6_i1_precip", dia[6].get("d6_i1_precip").toString().replace("\"", ""));
		data.put("d6_i1_viento", dia[6].get("d6_i1_viento").toString().replace("\"", ""));
		data.put("d6_i1_vviento", dia[6].get("d6_i1_vviento").toString().replace("\"", ""));
		data.put("d6_tmin", dia[6].get("d6_tmin").toString().replace("\"", ""));
		data.put("d6_tmax", dia[6].get("d6_tmax").toString().replace("\"", ""));
		data.put("d6_stmin", dia[6].get("d6_stmin").toString().replace("\"", ""));
		data.put("d6_stmax", dia[6].get("d6_stmax").toString().replace("\"", ""));
		data.put("d6_hmin", dia[6].get("d6_hmin").toString().replace("\"", ""));
		data.put("d6_hmax", dia[6].get("d6_hmax").toString().replace("\"", ""));

		try {
			Writer file = new FileWriter(new File(args[1] + "/tiempo-zaragoza.html"));
			template.process(data, file);
			file.flush();
			file.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
