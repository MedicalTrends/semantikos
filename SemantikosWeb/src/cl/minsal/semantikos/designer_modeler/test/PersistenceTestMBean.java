package cl.minsal.semantikos.designer_modeler.test;

import cl.minsal.semantikos.kernel.TestEJB;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Created by BluePrints Developer on 12-07-2016.
 */

@ManagedBean
@ViewScoped
public class PersistenceTestMBean {

@EJB
    TestEJB test;

    public String getTest(){


        return test.getTest();
    }

}
