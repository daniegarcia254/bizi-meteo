package stw.p6;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.utils.Options;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
public class Descargar {
    public static void main(String[] args) {
        try {
            String endpointURL = "http://localhost:8080/axis/services/DescargarInfoTiempo";
            String textToSend = args[0];

            Service service = new Service();
            Call call = (Call) service.createCall();

            call.setTargetEndpointAddress(new java.net.URL(endpointURL));
            call.setOperationName(new QName("http://stw", "serviceMethod"));
            call.addParameter("arg1", XMLType.XSD_STRING, ParameterMode.IN);
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

            Object foo[] = new Object[] {
                textToSend
            };

            //String ret = (String) call.invoke( foo );
            String ret = (String) call.invoke(foo);

            //ret=ret.replace("\t<","\t\n<");

            System.out.print(ret);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
