package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.kernel.components.DescriptionManagerInterface;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeDefinition;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
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

    @EJB
    HelperTableManagerInterface helperTableManager;


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

    // Placeholder para los target de las relaciones
    private BasicTypeValue basicTypeValue = new BasicTypeValue();

    private Object selectedHelperTableRecord = new HelperTableRecord();

    private ConceptSMTK conceptSMTK;

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }

    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Manejar el usuario desde la sesión
        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        /////////////////////////////////////////////

        category = categoryManager.getCategoryById(1);
        //category = categoryManager.getCategoryById(105590001);
        descriptionTypes = descriptionManager.getOtherTypes();
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));
        concept = conceptManager.newConcept(category, "electrocardiograma de urgencia");


    }


    public void removeRelationship(RelationshipDefinition rd, Relationship r){
        rd.getRelationships().remove(r);
        concept.getRelationships().remove(r);
    }

    public void addOrChangeRelationship(RelationshipDefinition relationshipDefinition, Target target){

        Relationship relationship= new Relationship(relationshipDefinition);
        relationship.setTarget(target);
        relationshipDefinition.addRelationship(relationship);

        if(relationshipDefinition.getRelationships().size()==0){
            relationshipDefinition.addRelationship(relationship);
            this.concept.addRelationship(relationship);
        }else{
            this.concept.getRelationships().remove(relationshipDefinition.getRelationships().get(0));
            relationshipDefinition.getRelationships().clear();

            relationshipDefinition.addRelationship(relationship);
            this.concept.addRelationship(relationship);
        }
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

    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target){

        Relationship relationship= new Relationship(relationshipDefinition);
        relationship.setTarget(target);
        relationshipDefinition.addRelationship(relationship);

        this.concept.addRelationship(relationship);
    }

    public HelperTableManagerInterface getHelperTableManager() {
        return helperTableManager;
    }

    public void setHelperTableManager(HelperTableManagerInterface helperTableManager) {
        this.helperTableManager = helperTableManager;
    }

    public BasicTypeValue getBasicTypeValue() {
        return basicTypeValue;
    }

    public void setBasicTypeValue(BasicTypeValue basicTypeValue) {
        this.basicTypeValue = basicTypeValue;
    }

    public Object getSelectedHelperTableRecord() {
        return selectedHelperTableRecord;
    }

    public void setSelectedHelperTableRecord(Object selectedHelperTableRecord) {
        this.selectedHelperTableRecord = selectedHelperTableRecord;
    }

    public boolean limitRelationship(RelationshipDefinition relationshipD){
        if(relationshipD.getMultiplicity().getUpperBoundary()!=0){
            if(relationshipD.getRelationships().size()==relationshipD.getMultiplicity().getUpperBoundary()){
                return true;
            }
        }
        return false;
    }

    public void saveConcept(){
        for (int i = 0; i < category.getRelationshipDefinitions().size() ; i++) {

        }
    }
}

