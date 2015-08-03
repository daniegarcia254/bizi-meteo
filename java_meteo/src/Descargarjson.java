package stw.p6;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.utils.Options;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.util.Arrays;
public class Descargarjson {

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


	public static void main(String[] args) {

		try {

			String endpointURL = "http://localhost:8080/axis/services/GenerarJSON";
			String textToSend = implodeArray(args);

			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(new java.net.URL(endpointURL));
			call.setOperationName(new QName("http://stw", "serviceMethod"));
			//call.addParameter( "arg1", XMLType.XSD_STRING, ParameterMode.IN);
			//call.setReturnType( org.apache.axis.encoding.XMLType.XSD_STRING );

			Object foo[] = new Object[] {
				textToSend
			};

			String ret = (String) call.invoke(foo);

			System.out.println(ret);

		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
