package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.kernel.components.DescriptionManagerInterface;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

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

    private List<BasicTypeDefinition> basicTypeDefinitions = new ArrayList<BasicTypeDefinition>();


    private T basicValue;

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
        descriptionTypes = descriptionManager.getOtherTypes();
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));
        concept = conceptManager.newConcept(category, "electrocardiograma de urgencia");
        System.out.println("concept.getRelationships().size()="+concept.getRelationships().size());
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

    public void removeBasicType(BasicTypeDefinition item) {
        getBasicTypeDefinitions().remove(item);
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

    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target){
        Relationship relationship= new Relationship();

        relationship.setRelationshipDefinition(relationshipDefinition);
        relationship.setTarget(target);

        this.concept.addRelationship(relationship);
    }

    public T getBasicValue() {
        return basicValue;
    }

    public void setBasicValue(T basicValue) {
        System.out.println("setBasicValue");
        this.basicValue = basicValue;
    }

    public BasicTypeValue getBasicTypeValue() {
        return basicTypeValue;
    }

    public List<BasicTypeDefinition> getBasicTypeDefinitions() {
        return basicTypeDefinitions;
    }

    public void setBasicTypeDefinitions(List<BasicTypeDefinition> basicTypeDefinitions) {
        this.basicTypeDefinitions = basicTypeDefinitions;
    }

    public void setBasicTypeValue(BasicTypeValue basicTypeValue) {
        System.out.println("setBasicTypeValue");
        this.basicTypeValue = basicTypeValue;
    }

    public void addBasicTypeDefinition(TargetDefinition targetDefinition){
        //Relationship relationship = new Relationship();
        //concept.addRelationship(new Relationship());
        category.getRelationshipDefinitions().get
        basicTypeDefinitions.add((BasicTypeDefinition) targetDefinition);
    }

    public void addRelationship() {

        /* Relationship relationship = new Relationship();
        relationship.setTarget(basicTypeValue);
        concept.addRelationship(relationship); */
    }

    public void setRelationship(){
        System.out.println("setRelationship");
        //concept.getRelationships()
    }
}

