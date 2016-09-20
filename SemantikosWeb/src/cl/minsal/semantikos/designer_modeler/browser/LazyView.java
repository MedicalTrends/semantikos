package cl.minsal.semantikos.designer_modeler.browser;

/**
 * Created by root on 16-09-16.
 */

import cl.minsal.semantikos.designer_modeler.lazy.CarService;
import cl.minsal.semantikos.model.Car;
import cl.minsal.semantikos.model.ConceptSMTK;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name="dtLazyView")
@ViewScoped
public class LazyView implements Serializable {

    private LazyDataModel<ConceptSMTK> lazyModel;

    private ConceptSMTK selectedConcept;

    @ManagedProperty("#{conceptService}")
    private CarService service;

    @PostConstruct
    public void init() {
        //lazyModel = new LazyConceptDataModel(service.createCars(200));
    }
    /*
    public LazyDataModel<Car> getLazyModel() {
        return lazyModel;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }
    */
    public void setService(CarService service) {
        this.service = service;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}