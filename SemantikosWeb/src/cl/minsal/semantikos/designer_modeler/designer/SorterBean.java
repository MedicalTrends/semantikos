package cl.minsal.semantikos.designer_modeler.designer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

/**
 * Created by root on 17-10-16.
 */
@ManagedBean(name = "sorterBean")
@ViewScoped
public class SorterBean implements Serializable {


    /**
     * Compares two objects that are Strings on their int value. Can be used to sort any column that contains Integer-based data.
     * @param obj1
     * @param obj2
     * @return
     */
    public int sortByOrder(Object obj1,Object obj2){
        int id1 = Integer.parseInt(obj1.toString());
        int id2 = Integer.parseInt(obj2.toString());
        if(id1 < id2){
            return 1;
        }else if(id1 == id2){
            return 0;
        }else{
            return -1;
        }
    }
}
