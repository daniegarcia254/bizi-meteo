/*
 * AUTOR: Javier Briz Alastrue
 * NIA: 576695
 */

package stw.p6;

import java.io.*;
import java.net.*;

public class Printinfotiempo {
	public String serviceMethod(String arg) {

		URL url;
		String out = "";

		try {
			// get URL content
			String urls = "http://www.aemet.es/xml/municipios/localidad_" + arg + ".xml";
			url = new URL(urls);
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(
			new InputStreamReader(conn.getInputStream()));

			String inputLine;

			while ((inputLine = br.readLine()) != null) {
				out = out + inputLine + "\n";
			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;

	}
}
