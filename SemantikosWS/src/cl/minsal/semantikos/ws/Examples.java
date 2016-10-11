package cl.minsal.semantikos.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Development on 2016-10-11.
 *
 */
@WebService
public class Examples {

    @WebMethod(operationName = "example")
    @WebResult(name = "data")
    public String example(
            @XmlElement(required = true)
            @WebParam(name = "key")
                String key
    ) throws Exception {
        return key;
    }

}
