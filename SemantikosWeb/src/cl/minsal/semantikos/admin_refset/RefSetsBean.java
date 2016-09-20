package cl.minsal.semantikos.admin_refset;

import cl.minsal.semantikos.kernel.components.RefSetManager;
import cl.minsal.semantikos.model.RefSet;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

/**
 * Created by des01c7 on 20-09-16.
 */
@ManagedBean(name="refsetsBean")
@ViewScoped
public class RefSetsBean {


    private RefSet refSetToCreate;

    private List<RefSet> refSetList;

    @EJB
    private RefSetManager refSetManager;


    @PostConstruct
    public void init(){


    }

}
