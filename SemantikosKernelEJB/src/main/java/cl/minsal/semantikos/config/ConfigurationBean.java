package cl.minsal.semantikos.config;

import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Created by BluePrints Developer on 19-05-2016.
 */
@Startup
@Singleton(name = "ConfigurationEJB")
public class ConfigurationBean {


    public ConfigurationBean(){
    }


    public String getConfig(String key){

        if("metlife.auth.service.wsdl".equals(key)) return "http://webdesacp01:302/WS_AUTENTIFICA.asmx?WSDL";
        if("metlife.auth.service.endpoint".equals(key)) return "http://webdesacp01:302/WS_AUTENTIFICA.asmx";

        return null;
    }


}
