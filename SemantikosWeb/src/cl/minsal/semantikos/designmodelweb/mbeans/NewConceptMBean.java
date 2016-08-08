package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.kernel.components.ConceptManagerInterface;
import cl.minsal.semantikos.kernel.components.DescriptionManagerInterface;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import org.omnifaces.util.Ajax;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
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

    @ManagedProperty(value="#{smtkBean}")
    private SMTKTypeBean smtkTypeBean;

    public User user;

    private ConceptSMTK concept;

    private Category category;
    private List<DescriptionType> descriptionTypes = new ArrayList<DescriptionType>();
    private List<State> descriptionStates = new ArrayList<State>();
    private List<Description> selectedDescriptions = new ArrayList<Description>();


    //Nombre del concepto (Descripcion preferida)

    private String favoriteDescription;

    // Placeholder para las descripciones
    private String otherTermino;

    private boolean otherSensibilidad;

    private DescriptionType otherDescriptionType;

    // Placeholder para los target de las relaciones
    private BasicTypeValue basicTypeValue = new BasicTypeValue();

    private Object selectedHelperTableRecord = new HelperTableRecord();

    private ConceptSMTK conceptSMTK;

    private ConceptSMTK conceptSelected;

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }


    //Inicializacion del Bean



    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Manejar el usuario desde la sesión
        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        /////////////////////////////////////////////

        // Iniciar cuadro de dialogo

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').show();");

        category = categoryManager.getCategoryById(1);
        //category = categoryManager.getCategoryById(105590001);
        descriptionTypes = DescriptionTypeFactory.getInstance().getDescriptionTypes();
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));



    }





    // Getter and Setter


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

    public ConceptSMTK getConceptSelected() {
        return conceptSelected;
    }

    public void setConceptSelected(ConceptSMTK conceptSelected) {
        this.conceptSelected = conceptSelected;
    }

    public SMTKTypeBean getSmtkTypeBean() {
        return smtkTypeBean;
    }

    public void setSmtkTypeBean(SMTKTypeBean smtkTypeBean) {
        this.smtkTypeBean = smtkTypeBean;
    }

    public String getFavoriteDescription() {
        return favoriteDescription;
    }

    public void setFavoriteDescription(String favoriteDescription) {
        this.favoriteDescription = favoriteDescription;
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



    //      Methods



    public void createConcept(){
        concept = conceptManager.newConcept(category, favoriteDescription);
        concept= new ConceptSMTK();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').hide();");

    }


    /**
     * Este metodo es el responsable de retornar verdadero en caso que se cumpla el UpperBoundary de la multiplicidad, para asi desactivar
     * la opcion de agregar mas relaciones en la vista. En el caso que se retorne falso este seguira activo el boton en la presentacion.
     *
     * @param relationshipD
     * @return
     */
    public boolean limitRelationship(RelationshipDefinition relationshipD){
        if(relationshipD.getMultiplicity().getUpperBoundary()!=0){
            if(concept.getRelationshipsByRelationDefinition(relationshipD).size()==relationshipD.getMultiplicity().getUpperBoundary()){
                return true;
            }
        }
        return false;
    }

    /**
     * Este metodo es el encargado de remover una descripcion especifica de la lista de descripciones del concepto.
     * @param item
     */

    public void removeItem(Description item) {
        concept.getDescriptions().remove(item);
    }

    /**
     * Este metodo es el encargado de agregar descripciones al concepto
     */

    public void addDescription() {
        Description description = new Description(otherTermino, otherDescriptionType);
        description.setCaseSensitive(otherSensibilidad);
        description.setState(concept.getState());
        concept.addDescription(description);
        otherTermino = "";
        Ajax.update("mainForm:otherTermino");

    }


    /**
     * Este metodo es el encargado de agregar relaciones al concepto recibiendo como parametro un Relationship Definition
     * @param relationshipDefinition
     */

    public void addRelationship(RelationshipDefinition relationshipDefinition){

        Relationship relationship= new Relationship(relationshipDefinition);
        this.concept.addRelationship(relationship);

    }

    /**
     * Este metodo es el encargado de agregar una nuva relacion con los parametros que se indican.
     * @param relationshipDefinition
     * @param target
     */

    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target){

        Relationship relationship= new Relationship(relationshipDefinition);
        relationship.setTarget(target);
        this.concept.addRelationship(relationship);
    }

    public void setRelationship(RelationshipDefinition rd, Relationship relationship){

        LOGGER.debug("setRelationship, Target ={} ",relationship.getTarget());

        int index = concept.getRelationships().indexOf(relationship);
        concept.getRelationships().set(index, relationship);

        for (Relationship r : rd.getRelationships()) {
            BasicTypeValue basicTypeValue = (BasicTypeValue) r.getTarget();
        }

    }

    /**
     * Este metodo es el encargado de remover una relacion especifica del concepto.
     * @param rd
     * @param r
     */

    public void removeRelationship(RelationshipDefinition rd, Relationship r){
        concept.getRelationships().remove(r);
    }

    /**
     * Este metodo se encarga de agregar o cambiar la relacion para el caso de multiplicidad 1.
     * @param relationshipDefinition
     * @param target
     */

    public void addOrChangeRelationship(RelationshipDefinition relationshipDefinition, Target target){

        Relationship relationship= new Relationship(relationshipDefinition);
        relationship.setTarget(conceptSelected);

        if(concept.getRelationshipsByRelationDefinition(relationshipDefinition).size()==0){
            this.concept.addRelationship(relationship);
        }else{
            for (int i = 0; i < this.concept.getRelationships().size(); i++) {
                if(this.concept.getRelationships().get(i).getRelationshipDefinition().equals(relationshipDefinition)){
                    this.concept.getRelationships().remove(i);
                }
            }
            this.concept.addRelationship(relationship);
        }
        for (int i = 0; i < concept.getRelationships().size(); i++) {
            System.out.println(((ConceptSMTK)(concept.getRelationships().get(i).getTarget())).getDescriptionFavorite().getTerm());
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

    public void saveConcept(){

    }
}

