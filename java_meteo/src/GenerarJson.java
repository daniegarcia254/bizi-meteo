/*
 * AUTOR: Javier Briz Alastrue
 * NIA: 576695
 */

package stw.p6;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.*;
import java.util.*;

public class GenerarJson {
	public static void main(String[] args) {
		String path = args[1] + args[0];
		System.out.println("Información obtenida desde la base de datos MySQL local.");
		System.out.print("JSON generado y almacenado en ");
		System.out.println(path);

		Mysql mysql = new Mysql();

		JsonObject prediccion = new JsonObject();
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

		prediccion.add("prediccion", dias);
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		try {
			PrintWriter file = new PrintWriter("www/prediccion.json");
			file.println(gson.toJson(prediccion));
			file.flush();
			file.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
