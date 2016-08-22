package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by diego on 26/06/2016.
 * Created by diego on 26/06/2016.
 */

@ManagedBean(name = "newConceptMBean")
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

    @ManagedProperty(value = "#{smtkBean}")
    private SMTKTypeBean smtkTypeBean;

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
    private BasicTypeValue basicTypeValue = new BasicTypeValue("");

    private HelperTableRecord selectedHelperTableRecord = new HelperTableRecord();

    private ConceptSMTK conceptSMTK;

    private ConceptSMTK conceptSelected;

    private Map<Long, ConceptSMTK> targetSelected;

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }


    //Parametros del formulario

    private String FSN;

    private String favoriteDescription;


    //Inicializacion del Bean


    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Manejar el usuario desde la sesión
        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        Profile designerProfile = new Profile();
        designerProfile.setName("designerProfile");
        designerProfile.setDescription("designerProfile");
        user.getProfiles().add(designerProfile);


        /////////////////////////////////////////////

        // Iniciar cuadro de dialogo

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').show();");

        //category = categoryManager.getCategoryById(1);
        //category = categoryManager.getCategoryById(105590001);

        category = categoryManager.getCategoryById(71388002);
        //descriptionManager.getAllTypes();
        //DescriptionTypeFactory.getInstance().getDescriptionTypes();

        descriptionTypes = descriptionManager.getAllTypes();
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));

    }


    // Getter and Setter


    public String getFSN() {
        return FSN;
    }

    public void setFSN(String FSN) {
        this.FSN = FSN;
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

    public HelperTableRecord getSelectedHelperTableRecord() {
        return selectedHelperTableRecord;
    }

    public void setSelectedHelperTableRecord(HelperTableRecord selectedHelperTableRecord) {
        this.selectedHelperTableRecord = selectedHelperTableRecord;
    }


    //      Methods

    public void createConcept() {
        concept = newConcept(category, favoriteDescription);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').hide();");
    }


    public ConceptSMTK getTargetForRD(RelationshipDefinition relationshipDefinition, ConceptSMTK conceptSel) {
        if (targetSelected == null) {
            targetSelected = new HashMap<Long, ConceptSMTK>();
        }

        if (!targetSelected.containsKey(relationshipDefinition.getId())) {
            targetSelected.put(relationshipDefinition.getId(), conceptSel);
        }
        return targetSelected.get(relationshipDefinition.getId());
    }


    /**
     * Este método es el encargado de remover una descripción específica de la lista de descripciones del concepto.
     */

    public void removeItem(Description item) {
        concept.getDescriptions().remove(item);
    }

    /**
     * Este método es el encargado de agregar descripciones al concepto
     */

    public void addDescription() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (otherTermino != null) {
            if (otherTermino.length() > 0) {
                if (otherDescriptionType != null) {
                    Description description = new Description(otherTermino, otherDescriptionType);
                    description.setCaseSensitive(otherSensibilidad);
                    description.setState(concept.getState());
                    description.setDescriptionId(descriptionManager.generateDescriptionId());
                    concept.addDescription(description);
                    otherTermino = "";
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha seleccionado el tipo de descripción"));
                }

            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha ingresado el término a la descripción"));
                context.getAttributes();
            }

        } else {

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha ingresado el término a la descripción"));

        }

    }


    /**
     * Este método es el encargado de agregar relaciones al concepto recibiendo como parámetro un Relationship
     * Definition
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition) {

        Target target = new BasicTypeValue(null);

        Relationship relationship = new Relationship(this.concept,target,relationshipDefinition);
        this.concept.addRelationship(relationship);

    }


    /**
     * Este método es el encargado de agregar una nuva relacion con los parémetros que se indican.
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        Relationship relationship = new Relationship(this.concept,target,relationshipDefinition);
        this.concept.addRelationship(relationship);
        conceptSelected = null;
    }


    public void addDescriptionToConcept(String term, DescriptionType descriptionType, boolean caseSensitive) {
        Description description = new Description(term, descriptionType);
        description.setCaseSensitive(caseSensitive);
        description.setState(concept.getState());
        description.setDescriptionId(descriptionManager.generateDescriptionId());
        concept.addDescription(description);
    }

    /**
     * Este método es el responsable de retornar verdadero en caso que se cumpla el UpperBoundary de la multiplicidad,
     * para asi desactivar
     * la opción de agregar más relaciones en la vista. En el caso que se retorne falso este seguirá activo el boton en
     * la presentación.
     *
     * @return
     */
    public boolean limitRelationship(RelationshipDefinition relationshipD) {
        if (relationshipD.getMultiplicity().getUpperBoundary() != 0) {
            if (concept.getRelationshipsByRelationDefinition(relationshipD).size() == relationshipD.getMultiplicity().getUpperBoundary()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este método es el encargado de remover una relación específica del concepto.
     */
    public void removeRelationship(RelationshipDefinition rd, Relationship r) {
        concept.getRelationships().remove(r);
    }

    /**
     * Este método se encarga de agregar o cambiar la relación para el caso de multiplicidad 1.
     */
    public void addOrChangeRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        boolean isRelationshipFound = false;

        for (Relationship relationship : concept.getRelationships()) {
            if (relationship.getRelationshipDefinition().equals(relationshipDefinition)) {
                relationship.setTarget(target);
                isRelationshipFound = true;
                break;
            }
        }

        if (!isRelationshipFound) {
            Relationship newRelationship = new Relationship(this.concept,target,relationshipDefinition);
            concept.addRelationship(newRelationship);
        }

        conceptSelected = null;
    }

    public void validateRelationships(ComponentSystemEvent event) {

        System.out.println("validateRelationships");

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        for (RelationshipDefinition relationshipDefinition : category.getRelationshipDefinitions()) {

            //if(concept.getRelationshipsByRelationDefinition(relationshipDefinition).size()<relationshipDefinition.getMultiplicity().getLowerBoundary()){
            UIColumn component = (UIColumn) components.findComponent(String.valueOf(relationshipDefinition.getId())+"_basic");

            //component.
            Map<String, Object> attrMap = component.getAttributes();
            String className = (String) attrMap.get("styleClass");

            System.out.println(className);
            //}


        }

    }


    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public boolean validateRelationships() {
        for (int i = 0; i < category.getRelationshipDefinitions().size(); i++) {
            if (!(concept.getRelationshipsByRelationDefinition(category.getRelationshipDefinitions().get(i)).size() >= category.getRelationshipDefinitions().get(i).getMultiplicity().getLowerBoundary())) {
                return false;
            }
        }
        return true;
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

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public ConceptSMTK newConcept(Category category, String term) {

        /* Valores iniciales para el concepto */
        Description favouriteDescription = new Description(term, descriptionManager.getTypeFavorite());
        State initialState = stateMachineManager.getConceptStateMachine().getInitialState();
        favouriteDescription.setState(initialState);
        favouriteDescription.setCaseSensitive(false);
        favouriteDescription.setDescriptionId(descriptionManager.generateDescriptionId());
        ConceptSMTKWeb concept = new ConceptSMTKWeb(category, favouriteDescription, initialState);
        concept.setCategory(category);
        concept.setState(initialState);
        concept.setId(-1);
        concept.setConceptID(conceptManager.generateConceptId());


        return concept;
    }

    public void onRowSelect(SelectEvent event) {
        //System.out.println("selectedHelperTableRecord.getFields().get('id')="+selectedHelperTableRecord.getFields().get("id"));
        //FacesMessage msg = new FacesMessage("Car Selected", new Long(((HelperTableRecord) event.getObject()).getId()).toString());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void saveConcept() {

        FacesContext context = FacesContext.getCurrentInstance();


        if (FSN != null || FSN.length() > 0) {
            addDescriptionToConcept(FSN, descriptionManager.getTypeFSN(), true);

            if (!validateRelationships()) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las relaciones no cumplen con el minimo requerido"));

            } else {

                conceptManager.persist(concept, user);
                context.addMessage(null, new FacesMessage("Successful", "Concepto guardado "));
            }

        } else {

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Falta el FSN al concepto"));
        }

    }


}

