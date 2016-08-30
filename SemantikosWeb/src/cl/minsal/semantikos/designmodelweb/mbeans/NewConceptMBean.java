package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.Relationship;
import cl.minsal.semantikos.model.relationships.RelationshipDefinition;
import cl.minsal.semantikos.model.relationships.Target;
import cl.minsal.semantikos.model.validations.RelationshipConstraint;
import cl.minsal.semantikos.util.Pair;
import org.primefaces.context.RequestContext;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.sql.Timestamp;
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

    static final Logger logger = LoggerFactory.getLogger(NewConceptMBean.class);

    @EJB
    ConceptManagerInterface conceptManager;

    @EJB
    DescriptionManagerInterface descriptionManager;

    DescriptionTypeFactory descriptionTypeFactory = DescriptionTypeFactory.getInstance();

    @EJB
    CategoryManagerInterface categoryManager;

    @EJB
    HelperTableManagerInterface helperTableManager;

    @ManagedProperty(value = "#{smtkBean}")
    private SMTKTypeBean smtkTypeBean;

    public User user;

    @RelationshipConstraint
    private ConceptSMTKWeb concept;

    private Category category;
    private List<DescriptionType> descriptionTypes = new ArrayList<DescriptionType>();
    private List<Description> selectedDescriptions = new ArrayList<Description>();


    // Placeholder para las descripciones
    private String otherTermino;

    private boolean otherSensibilidad;

    private DescriptionType otherDescriptionType;

    // Placeholders para los target de las relaciones
    private BasicTypeValue basicTypeValue = new BasicTypeValue(null);

    private HelperTableRecord selectedHelperTableRecord = new HelperTableRecord();

    private ConceptSMTK conceptSMTK;

    private ConceptSMTK conceptSelected;

    private Map<Long, ConceptSMTK> targetSelected;
    ////////////////////////////////////////////////////

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }


    //Parametros del formulario

    private String FSN;

    private String favoriteDescription;

    private int categorySelect;

    public int getCategorySelect() {
        return categorySelect;
    }

    public void setCategorySelect(int categorySelect) {
        this.categorySelect = categorySelect;
    }

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


        // Iniciar cuadro de dialogo

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').show();");

        //category = categoryManager.getCategoryById(1);
        //category = categoryManager.getCategoryById(105590001);
        //category = categoryManager.getCategoryById(71388002);


        descriptionTypes = descriptionTypeFactory.getDescriptionTypes();
        // TODO: Inicializar lista de estados de descripción con todos los estados posibles
        //descriptionStates = stateMachineManager.getConceptStateMachine().
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));

    }

    // Getter and Setter


    public String getFSN() {
        return FSN;
    }

    public void setFSN(String FSN) {
        this.FSN = FSN;
    }

    public ConceptSMTKWeb getConcept() {
        return concept;
    }

    public void setConcept(ConceptSMTKWeb concept) {
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

    public List<Description> getSelectedDescriptions() {
        return selectedDescriptions;
    }

    public void setSelectedDescriptions(List<Description> selectedDescriptions) {
        this.selectedDescriptions = selectedDescriptions;
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
    public void createConcept() throws ParseException {
        category = categoryManager.getCategoryById(categorySelect);
        concept = newConcept(category, favoriteDescription);
        //concept = getConceptById(80602);

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').hide();");
    }

    //Este método es responsable de pasarle a la vista un concepto plantilla
    //(llamado desde la vista cuando se desea crear un nuevo concepto)
    public ConceptSMTKWeb newConcept(Category category, String term) {

        /* Valores iniciales para el concepto */
        Description fsn = new Description(term, descriptionManager.getTypeFSN());
        fsn.setCaseSensitive(false);
        fsn.setDescriptionId(descriptionManager.generateDescriptionId());

        Description favouriteDescription = new Description(term, descriptionManager.getTypeFavorite());
        favouriteDescription.setCaseSensitive(false);
        favouriteDescription.setDescriptionId(descriptionManager.generateDescriptionId());

        Description fsnDescription = new Description(term + " (" + category.getName() + ")", descriptionManager.getTypeFSN());

        fsnDescription.setCaseSensitive(false);
        fsnDescription.setDescriptionId(descriptionManager.generateDescriptionId());

        Description[] descriptions = {favouriteDescription, fsnDescription};
        return new ConceptSMTKWeb(new ConceptSMTK(conceptManager.generateConceptId(), category, true, true, false, false, false, descriptions));
    }

    //Este método es responsable de pasarle a la vista un concepto, dado el id del concepto
    //(llamado desde la vista cuando se desea editar un concepto)
    public ConceptSMTKWeb getConceptById(long conceptId) {
        ConceptSMTKWeb conceptSMTKWeb = new ConceptSMTKWeb(conceptManager.getConceptByID(conceptId));
        //conceptSMTKWeb.setRelationships(conceptManager.loadRelationships(conceptSMTKWeb));
        conceptSMTKWeb.setRelationships(conceptManager.loadRelationships(conceptSMTKWeb));
        category = conceptSMTKWeb.getCategory();
        return conceptSMTKWeb;
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
    public void removeDescription(Description description) {
        concept.getDescriptions().remove(description);
    }


    /**
     * Este método es el encargado de agregar relaciones al concepto recibiendo como parámetro un Relationship
     * Definition. Este método es utilizado por el componente BasicType, el cual agrega relaciones con target sin valor
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition) {

        Target target = new BasicTypeValue(null);

        Relationship relationship = new Relationship(this.concept, target, relationshipDefinition);
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationship(new RelationshipWeb(relationship, false));

    }

    /**
     * Este método es el encargado de agregar una nuva relacion con los parémetros que se indican.
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        Relationship relationship = new Relationship(this.concept, target, relationshipDefinition);
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationship(new RelationshipWeb(relationship, false));
        conceptSelected = null;
    }

    /**
     * Este método se encarga de agregar o cambiar la relación para el caso de multiplicidad 1.
     */
    public void addOrChangeRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        boolean isRelationshipFound = false;

        // Se busca la relación
        for (Relationship relationshipWeb : concept.getRelationships()) {
            if (relationshipWeb.getRelationshipDefinition().equals(relationshipDefinition)) {
                relationshipWeb.setTarget(target);
                isRelationshipFound = true;
                break;
            }
        }
        // Si no se encuentra la relación, se crea una nueva
        if (!isRelationshipFound) {
            Relationship newRelationship = new Relationship(this.concept, target, relationshipDefinition);
            concept.addRelationship(newRelationship);
        }

        conceptSelected = null;
    }

    /**
     * Este método es el encargado de remover una relación específica del concepto.
     */
    public void removeRelationship(RelationshipDefinition rd, Relationship r) {
        concept.removeRelationship(r);
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

    public void addDescriptionToConcept(String term, DescriptionType descriptionType, boolean caseSensitive) {

        Description description = new Description(term, descriptionType);
        description.setCaseSensitive(caseSensitive);
        description.setDescriptionId(descriptionManager.generateDescriptionId());
        concept.addDescription(description);
    }

    public void editDescription(DescriptionWeb description) {
        /*
        if (description.isPersisted() && !description.hasBeenModified())
            concept.editDescription(description);
        */
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
            if (concept.getValidRelationshipsByRelationDefinition(relationshipD).size() == relationshipD.getMultiplicity().getUpperBoundary()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public void validateRelationships(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String msg = "Error!!!!!!";

        //component.getParent().getAttributes().

        if (!concept.isValid())
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    /**
     * Este metodo revisa que las relaciones cumplan el lower_boundary del
     * relationship definition, en caso de no cumplir la condicion se retorna falso.
     *
     * @return
     */
    public void validateDescription(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String msg = concept.validateDescription((DescriptionWeb) value);

        //component.getParent().getAttributes().

        if (!msg.equals(""))
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }

    public String getMyFormattedDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public void onRowEdit(RowEditEvent event) {

        DescriptionWeb description = (DescriptionWeb) event.getObject();

        editDescription(description);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Description) event.getObject()).getTerm());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowSelect(SelectEvent event) {
        //System.out.println("selectedHelperTableRecord.getFields().get('id')="+selectedHelperTableRecord.getFields().get("id"));
        //FacesMessage msg = new FacesMessage("Car Selected", new Long(((HelperTableRecord) event.getObject()).getId()).toString());
        //FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveConcept() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (!concept.isValid()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las relaciones no cumplen con el minimo requerido"));

        } else {
            // Si el concepto está persistido, actualizarlo
            if (concept.isPersistent()) {
                for (Pair<Description, Description> description : concept.getDescriptionsForUpdate()) {
                    descriptionManager.updateDescription(concept, description.getFirst(), description.getSecond(), user);
                }
                // Se prepara para la actualización
                //if(concept.prepareForUpdate())
                //conceptManager.update(concept, user);
            }
            // Si el concepto no está persistido, persistirlo
            else {
                conceptManager.persist(concept, user);
            }
            context.addMessage(null, new FacesMessage("Successful", "Concepto guardado "));
        }

    }


}

