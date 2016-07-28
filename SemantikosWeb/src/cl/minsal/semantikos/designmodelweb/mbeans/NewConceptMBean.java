package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.kernel.components.DescriptionManagerInterface;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.relationships.TargetDefinition;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by diego on 26/06/2016.
 */

@ManagedBean(name="newConceptMBean")
@ViewScoped
public class NewConceptMBean<T extends Comparable> implements Serializable {

    static final Logger LOGGER = LoggerFactory.getLogger(NewConceptMBean.class);

    @EJB
    ConceptManagerInterface conceptManager;

    @EJB
    DescriptionManagerInterface descriptionManager;

    @EJB
    CategoryManagerInterface categoryManager;

    @EJB
    StateMachineManagerInterface stateMachineManager;

    /*
    @EJB
    StateManagerInterface stateManager;
    */

    public User user;

    private ConceptSMTK concept;

    private Category category;
    private List<DescriptionType> descriptionTypes = new ArrayList<DescriptionType>();
    private List<State> descriptionStates = new ArrayList<State>();
    private List<Description> selectedDescriptions = new ArrayList<Description>();

    // Placeholder para las descripciones
    private String otherTermino;

    private boolean otherSensibilidad;

    private DescriptionType otherDescriptionType;

    // Placeholder para los target (multiplicidad N)
    private BasicTypeValue basicTypeValue = new BasicTypeValue();


    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Manejar el usuario desde la sesión
        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        /////////////////////////////////////////////

        //category = categoryManager.getCategoryById(1);
        category = categoryManager.getCategoryById(105590001);
        for (int i = 0; i < category.getRelationshipDefinitions().size() ; i++) {
            System.out.println(category.getRelationshipDefinitions().get(i).getName());
        }
        descriptionTypes = descriptionManager.getOtherTypes();
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));
        concept = conceptManager.newConcept(category, "electrocardiograma de urgencia");
        System.out.println("concept.getRelationships().size()= "+concept.getRelationships().size());
        System.out.println("category: "+ category.getRelationshipDefinitions().get(0).getRelationships().size());

    }

    public void setRelationship(RelationshipDefinition rd, Relationship r){

        LOGGER.debug("setRelationship, Target ={} ",r.getTarget());


        int index = concept.getRelationships().indexOf(r);
        concept.getRelationships().set(index, r);

        if(r.getTarget()!= null){

            int index1 = category.getRelationshipDefinitions().indexOf(rd);
            int index2 = category.getRelationshipDefinitions().get(index1).getRelationships().indexOf(r);

            System.out.println("voy a actualizar la relacion");
            System.out.println("index="+index1+" index2="+index2);
            //category.getRelationshipDefinitions().get(index).getRelationships().set(index2,r);

            for (Relationship relationship : category.getRelationshipDefinitions().get(0).getRelationships()) {
                BasicTypeValue basic=(BasicTypeValue)relationship.getTarget();
                System.out.println("basic.getValue()="+basic.getValue());
            }
            System.out.println("Concept");
            for (Relationship relationship : concept.getRelationships()) {
                BasicTypeValue basic=(BasicTypeValue)relationship.getTarget();
                System.out.println("basic.getValue()="+basic.getValue());
            }

        }
        basicTypeValue = new BasicTypeValue();
    }


    public void addRelationSMTK(RelationshipDefinition rd,Target c){

        Relationship r= new Relationship(rd);
        r.setTarget(c);
        /*for (int i = 0; i < category.getRelationshipDefinitions().size() ; i++) {
            if(category.getRelationshipDefinitions().get(i).getId()==rd.getId()){
                category.getRelationshipDefinitions().get(i).getRelationships().add(r);
            }
        }*/
        System.out.println("addRelationSMTK");
        System.out.println("Target: "+c);
        if(c!= null){
            rd.getRelationships().add(r);
        }

        concept.addRelationship(r);

        //concept.addRelationship(r);
        //concept.addRelationship(r);
        /*
        for (int i = 0; i < category.getRelationshipDefinitions().get(0).getRelationships().size(); i++) {
            System.out.println(category.getRelationshipDefinitions().get(0).getRelationships().get(i));
            System.out.println(category.getRelationshipDefinitions().get(0).getRelationships().get(i).getTarget());
        }
        */

        basicTypeValue = new BasicTypeValue();

    }


    public String getMyFormattedDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Description Edited", ((Description) event.getObject()).getTerm());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Description) event.getObject()).getTerm());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public ConceptSMTK getConcept() {
        return concept;
    }

    public void setConcept(ConceptSMTK concept) {
        this.concept = concept;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getOtherTermino() {
        return otherTermino;
    }

    public void setOtherTermino(String otherTermino) {
        this.otherTermino = otherTermino;
    }

    public boolean getOtherSensibilidad() {
        return otherSensibilidad;
    }

    public void setOtherSensibilidad(boolean otherSensibilidad) {
        this.otherSensibilidad = otherSensibilidad;
    }

    public DescriptionType getOtherDescriptionType() {
        return otherDescriptionType;
    }

    public void setOtherDescriptionType(DescriptionType otherDescriptionType) {
        this.otherDescriptionType = otherDescriptionType;
    }

    public List<DescriptionType> getDescriptionTypes() {
        return descriptionTypes;
    }

    public void setDescriptionTypes(List<DescriptionType> descriptionTypes) {
        this.descriptionTypes = descriptionTypes;
    }

    public List<State> getDescriptionStates() {
        return descriptionStates;
    }

    public void setDescriptionStates(List<State> descriptionStates) {
        this.descriptionStates = descriptionStates;
    }

    public List<Description> getSelectedDescriptions() {
        return selectedDescriptions;
    }

    public void setSelectedDescriptions(List<Description> selectedDescriptions) {
        this.selectedDescriptions = selectedDescriptions;
    }

    public StateMachineManagerInterface getStateMachineManager() {
        return stateMachineManager;
    }

    public void setStateMachineManager(StateMachineManagerInterface stateMachineManager) {
        this.stateMachineManager = stateMachineManager;
    }

    public void removeItem(Description item) {
        concept.getOtherDescriptions().remove(item);
    }

    public void addDescription() {
        Description description = new Description(otherTermino, otherDescriptionType);
        description.setTerm(otherTermino);
        description.setCaseSensitive(otherSensibilidad);
        description.setState(concept.getState());
        description.setCreationDate(Calendar.getInstance().getTime());
        description.setUser(user);
        concept.addDescription(description);
    }

    public BasicTypeValue getBasicTypeValue() {
        return basicTypeValue;
    }

    public void setBasicTypeValue(BasicTypeValue basicTypeValue) {
        System.out.println("setBasicTypeValue");
        this.basicTypeValue = basicTypeValue;
    }

}

