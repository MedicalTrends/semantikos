package cl.minsal.semantikos.model;

/**
 * Created by des01c7 on 17-10-16.
 */
public class AutogenerateMCCE {

    private String MC;
    private String cantidad;
    private String unidadMedidaCantidad;
    private String volumen;
    private String volumenUnidad;
    private String pack;
    private String packUnidad;

    public AutogenerateMCCE() {
        this.MC = "";
        this.cantidad = "";
        this.unidadMedidaCantidad = "";
        this.volumen = "";
        this.volumenUnidad = "";
        this.pack = "";
        this.packUnidad = "";
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getCantidad() {

        if(cantidad!=null){
            return "";
        }else{
            return cantidad;
        }
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedidaCantidad() {
        if(unidadMedidaCantidad!=null){
            return "";
        }else{
            return unidadMedidaCantidad;
        }
    }

    public void setUnidadMedidaCantidad(String unidadMedidaCantidad) {
        this.unidadMedidaCantidad = unidadMedidaCantidad;
    }

    public String getVolumen() {
        if(volumen!=null){
            return "";
        }else{
            return volumen;
        }

    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public String getVolumenUnidad() {
        if(volumenUnidad!=null){
            return "";
        }else{
            return volumenUnidad;
        }
    }

    public void setVolumenUnidad(String volumenUnidad) {
        this.volumenUnidad = volumenUnidad;
    }

    public String getPack() {

        if(pack!=null){
            return "";
        }else{
            return pack;
        }
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getPackUnidad() {
        if(packUnidad!=null){
            return "";
        }else{
            return packUnidad;
        }

    }

    public void setPackUnidad(String packUnidad) {
        this.packUnidad = packUnidad;
    }

    @Override
    public String toString() {
        return  MC + ' '  + getCantidad() + ' ' + getUnidadMedidaCantidad() + ' ' +  getVolumen() + ' ' + getVolumenUnidad() + ' ' + getPack() + ' '+ getPackUnidad();
    }
}
