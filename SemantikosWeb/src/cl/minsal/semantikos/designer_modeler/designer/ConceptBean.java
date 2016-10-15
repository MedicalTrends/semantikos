package cl.minsal.semantikos.designer_modeler.designer;

import cl.minsal.semantikos.designer_modeler.auth.AuthenticationBean;
import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.helpertables.HelperTable;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.*;
import cl.minsal.semantikos.util.Pair;
import cl.minsal.semantikos.view.components.ViewAugmenter;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.ReorderEvent;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;


/**
 * Created by diego on 26/06/2016.
 */

@ManagedBean(name = "conceptBean")
@ViewScoped
public class ConceptBean implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(ConceptBean.class);

    @EJB
    ConceptManager conceptManager;

    @EJB
    DescriptionManager descriptionManager;

    @EJB
    RelationshipManager relationshipManager;

    @EJB
    CategoryManager categoryManager;

    @EJB
    HelperTableManager helperTableManager;

    @EJB
    TagSMTKManager tagSMTKManager;

    @EJB
    AuditManager auditManager;

    @ManagedProperty(value = "#{smtkBean}")
    private SMTKTypeBean smtkTypeBean;

    DescriptionTypeFactory descriptionTypeFactory = DescriptionTypeFactory.getInstance();

    public User user;

    private List<Category> categoryList;

    private Category categorySelected;

    private ConceptSMTKWeb concept;

    private List<Description> descriptionsToTraslate;

    /**
     * El concepto original
     */
    private ConceptSMTKWeb _concept;

    /**
     * La categoría asociada a la vista, de la cual se crea un nuevo concepto
     */
    private Category category;

    private List<DescriptionType> descriptionTypes = new ArrayList<DescriptionType>();

    private List<DescriptionType> descriptionTypesEdit = new ArrayList<DescriptionType>();

    private List<Description> selectedDescriptions = new ArrayList<Description>();

    private List<TagSMTK> tagSMTKs = new ArrayList<TagSMTK>();

    // Placeholder para las descripciones
    private String otherTermino;

    private boolean otherSensibilidad;

    private DescriptionType otherDescriptionType;

    private ConceptSMTK conceptSMTKTranslateDes;

    private Description descriptionToTranslate;

    // Placeholders para los target de las relaciones
    private BasicTypeValue basicTypeValue = new BasicTypeValue(null);

    private HelperTableRecord selectedHelperTableRecord = new HelperTableRecord();

    private ConceptSMTK conceptSMTK;

    private ConceptSMTK conceptSelected;

    private Map<Long, ConceptSMTK> targetSelected;

    ////////////////////////////////////////////////////

    // Placeholders para los atributos de relacion
    private RelationshipWeb relationshipWeb;

    private Map<Long, Relationship> relationshipPlaceholders = new HashMap<Long, Relationship>();

    private Map<RelationshipDefinition, List<RelationshipAttribute>> relationshipAttributesPlaceholder = new HashMap<RelationshipDefinition, List<RelationshipAttribute>>();


    //Parametros del formulario

    private String FSN;

    private String favoriteDescription;

    private int categorySelect;

    private List<ConceptAuditAction> auditAction;

    //para tipo helpertable
    private int helperTableValuePlaceholder;


    @ManagedProperty(value = "#{conceptExport}")
    private ConceptExportMBean conceptBeanExport;


    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    @EJB
    private ViewAugmenter viewAugmenter;

    /**
     * Un map para almacenar localmente las relaciones aumentadas
     */
    private List<RelationshipDefinitionWeb> orderedRelationshipDefinitionsList;

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    public ConceptExportMBean getConceptBeanExport() {
        return conceptBeanExport;
    }

    public void setConceptBeanExport(ConceptExportMBean conceptBean) {
        this.conceptBeanExport = conceptBean;
    }

    //Inicializacion del Bean

    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Terminar esto o cambiar en el futuro

        user = authenticationBean.getLoggedUser();
        Profile designerProfile = new Profile(2, "Diseñador", "Usuario Diseñador");
        user.getProfiles().add(designerProfile);

        Description d;


        // Iniciar cuadro de dialogo

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').show();");

        categoryList = categoryManager.getCategories();

        descriptionTypes = descriptionTypeFactory.getDescriptionTypesButFSNandFavorite();

        descriptionTypesEdit = descriptionTypeFactory.getDescriptionTypesButFSN();

        tagSMTKs = tagSMTKManager.getAllTagSMTKs();

        orderedRelationshipDefinitionsList = new ArrayList<>();

        descriptionsToTraslate = new ArrayList<>();
    }

    /**
     * Este método se ejecuta al inicio del proceso de creación de un concepto.
     *
     * @throws ParseException
     */
    public void createConcept() throws ParseException {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idconceptselect == 0) {

            setCategory(categorySelected);

            /* Se valida que el término propuesto no exista previamente */
            if (categoryManager.categoryContains(category, favoriteDescription)) {
                FacesContext c = FacesContext.getCurrentInstance();
                c.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La descripción " + favoriteDescription + " ya existe dentro de la categoría " + category.getName()));
            } else {
                newConcept(category, favoriteDescription);
            }
        } else {
            getConceptById(idconceptselect);
        }
        // Una vez que se ha inicializado el concepto, inicializar los placeholders para las relaciones
        for (RelationshipDefinition relationshipDefinition : category.getRelationshipDefinitions()) {
            if (!relationshipDefinition.getRelationshipAttributeDefinitions().isEmpty())
                relationshipPlaceholders.put(relationshipDefinition.getId(), new Relationship(concept, null, relationshipDefinition, new ArrayList<RelationshipAttribute>()));
        }
        context.execute("PF('dialogNameConcept').hide();");
    }

    //Este método es responsable de a partir de un concepto SMTK y un término, devolver un concepto WEB con su FSN y su Preferida
    public ConceptSMTKWeb initConcept(ConceptSMTK concept, String term) {
        ConceptSMTKWeb conceptWeb = new ConceptSMTKWeb(concept);

        DescriptionWeb fsnDescription = new DescriptionWeb(conceptWeb, term, descriptionManager.getTypeFSN());
        fsnDescription.setCaseSensitive(false);
        fsnDescription.setModeled(false);
        fsnDescription.setDescriptionId(descriptionManager.generateDescriptionId());

        DescriptionWeb favouriteDescription = new DescriptionWeb(conceptWeb, term, descriptionManager.getTypeFavorite());
        favouriteDescription.setCaseSensitive(false);
        fsnDescription.setModeled(false);
        favouriteDescription.setDescriptionId(descriptionManager.generateDescriptionId());

        for (DescriptionWeb description : new DescriptionWeb[]{favouriteDescription, fsnDescription})
            conceptWeb.addDescriptionWeb(description);

        return conceptWeb;
    }

    //Este método es responsable de pasarle a la vista un concepto plantilla
    //(llamado desde la vista cuando se desea crear un nuevo concepto)
    public void newConcept(Category category, String term) {

        /* Valores iniciales para el concepto */

        String observation = "";

        // TODO: Diego
        TagSMTK tagSMTK = new TagSMTK(category.getTagSemantikos().getId(), category.getTagSemantikos().getName());

        ConceptSMTK conceptSMTK = new ConceptSMTK(conceptManager.generateConceptId(), category, false, false, false, false, false, observation, tagSMTK);
        // Se crea el concepto WEB a partir del concepto SMTK
        concept = initConcept(conceptSMTK, term);
        // Se crea una copia con la imagen original del concepto
        _concept = initConcept(conceptSMTK, term);

        conceptBeanExport.setConceptSMTK(concept);
        conceptBeanExport.loadConcept();
    }

    //Este método es responsable de pasarle a la vista un concepto, dado el id del concepto
    //(llamado desde la vista cuando se desea editar un concepto)
    public void getConceptById(long conceptId) {
        ConceptSMTK conceptSMTK = conceptManager.getConceptByID(conceptId);
        conceptSMTK.setRelationships(conceptManager.loadRelationships(conceptSMTK));
        // Se crea el concepto WEB a partir del concepto SMTK
        concept = new ConceptSMTKWeb(conceptSMTK);
        // Se crea una copia con la imagen original del concepto
        _concept = new ConceptSMTKWeb(conceptSMTK);

        auditAction = auditManager.getConceptAuditActions(concept, true);
        category = concept.getCategory();
        conceptBeanExport.setConceptSMTK(concept);
        conceptBeanExport.loadConcept();
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
        concept.removeDescriptionWeb(description);
    }


    /**
     * Este método es el encargado de agregar relaciones al concepto recibiendo como parámetro un Relationship
     * Definition. Este método es utilizado por el componente BasicType, el cual agrega relaciones con target sin valor
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition) {

        Target target = new BasicTypeValue(null);

        Relationship relationship = new Relationship(this.concept, target, relationshipDefinition, new ArrayList<RelationshipAttribute>());
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationshipWeb(relationship);
    }

    /**
     * Este método es el encargado de agregar relaciones al concepto recibiendo como parámetro un Relationship
     * Definition. Este método es utilizado por el componente BasicType, el cual agrega relaciones con target sin valor
     */
    public void addRelationshipWithAttributes(RelationshipDefinition relationshipDefinition) {

        Relationship relationship = relationshipPlaceholders.get(relationshipDefinition.getId());
        if(relationshipDefinition.getAttributeOrder()!=null) {
            RelationshipAttribute attribute = new RelationshipAttribute(relationshipDefinition.getAttributeOrder(), relationship, new BasicTypeValue(concept.getValidRelationshipsByRelationDefinition(relationshipDefinition).size()+1));
            relationship.getRelationshipAttributes().add(attribute);
        }
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationshipWeb(new RelationshipWeb(relationship));
        // Reinicializar placeholder relaciones
        relationshipPlaceholders.put(relationshipDefinition.getId(), new Relationship(concept, null, relationshipDefinition, new ArrayList<RelationshipAttribute>()));
        // Resetear placeholder targets
        basicTypeValue = new BasicTypeValue(null);
        selectedHelperTableRecord = new HelperTableRecord();
        conceptSelected = null;
    }

    /**
     * Este método es el encargado de agregar una nuva relacion con los parémetros que se indican.
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        Relationship relationship = new Relationship(this.concept, target, relationshipDefinition, new ArrayList<RelationshipAttribute>());
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationshipWeb(relationship);
        // Resetear placeholder targets
        basicTypeValue = new BasicTypeValue(null);
        selectedHelperTableRecord = new HelperTableRecord();
        conceptSelected = null;
    }


    /**
     * Este método es el encargado de agregar una nuva relacion con los parémetros que se indican.
     */
    public void addHelperTableRelationshipWithAttributes(RelationshipDefinition relationshipDefinition) {


        HelperTableRecord record = helperTableManager.getRecord(helperTableValuePlaceholder);

        Relationship relationship = new Relationship(this.concept, record, relationshipDefinition, getRelationshipAttributesByRelationshipDefinition(relationshipDefinition));

        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationshipWeb(relationship);
        conceptSelected = null;
    }

    /**
     * Este método se encarga de agregar o cambiar la relación para el caso de multiplicidad 1.
     */
    public void addOrChangeRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        boolean isRelationshipFound = false;

        // Se busca la relación
        for (Relationship relationshipWeb : concept.getRelationshipsWeb()) {
            if (relationshipWeb.getRelationshipDefinition().equals(relationshipDefinition)) {
                relationshipWeb.setTarget(target);
                isRelationshipFound = true;
                break;
            }
        }
        // Si no se encuentra la relación, se crea una nueva
        if (!isRelationshipFound)
            this.concept.addRelationshipWeb(new Relationship(this.concept, target, relationshipDefinition, new ArrayList<RelationshipAttribute>()));

        // Se resetean los placeholder para los target de las relaciones
        basicTypeValue = new BasicTypeValue(null);
        selectedHelperTableRecord = new HelperTableRecord();
        conceptSelected = null;
    }

    /**
     * Este método se encarga de agregar o cambiar el atributo para el caso de multiplicidad 1.
     */
    public void addOrChangeRelationshipAttribute(RelationshipDefinition relationshipDefinition, RelationshipAttributeDefinition relationshipAttributeDefinition, Target target) {

        boolean isRelationshipFound = false;
        boolean isAttributeFound = false;

        // Se busca la relación y el atributo
        for (Relationship relationship : concept.getRelationshipsWeb()) {
            if (relationship.getRelationshipDefinition().equals(relationshipDefinition)) {
                isRelationshipFound = true;
                for (RelationshipAttribute attribute : relationship.getRelationshipAttributes()) {
                    if (attribute.getRelationAttributeDefinition().equals(relationshipAttributeDefinition)) {
                        attribute.setTarget(target);
                        isAttributeFound = true;
                        break;
                    }
                }
                // Si se encuentra la relación, pero no el atributo, se crea un nuevo atributo
                if (!isAttributeFound) {
                    RelationshipAttribute attribute = new RelationshipAttribute(relationshipAttributeDefinition, relationship, target);
                    relationship.getRelationshipAttributes().add(attribute);
                }
            }
        }

        // Si no se encuentra la relación, se crea una nueva relación con el atributo y target vacio
        if (!isRelationshipFound) {
            Relationship relationship = new Relationship(this.concept, null, relationshipDefinition, new ArrayList<RelationshipAttribute>());
            RelationshipAttribute attribute = new RelationshipAttribute(relationshipAttributeDefinition, relationship, target);
            relationship.getRelationshipAttributes().add(attribute);
            this.concept.addRelationshipWeb(new RelationshipWeb(relationship, relationship.getRelationshipAttributes())); //  new ArrayList<RelationshipAttribute>()));
        }

        // Se resetean los placeholder para los target de las relaciones
        basicTypeValue = new BasicTypeValue(null);
        conceptSelected = null;
        selectedHelperTableRecord = new HelperTableRecord();
    }

    public void setTarget(RelationshipDefinition relationshipDefinition, Target target) {
        relationshipPlaceholders.get(relationshipDefinition.getId()).setTarget(target);
    }

    /**
     * Este método se encarga de agregar o cambiar el atributo para el caso de multiplicidad 1.
     */
    public void setTargetAttribute(RelationshipDefinition relationshipDefinition, RelationshipAttributeDefinition relationshipAttributeDefinition, Target target) {

        //relationshipWeb.getRelationshipAttributes().add()

        Relationship relationship = relationshipPlaceholders.get(relationshipDefinition.getId());

        boolean isAttributeFound = false;

        // Se busca el atributo
        for (RelationshipAttribute attribute : relationship.getRelationshipAttributes()) {
            if (attribute.getRelationAttributeDefinition().equals(relationshipAttributeDefinition)) {
                attribute.setTarget(target);
                isAttributeFound = true;
                break;
            }
        }
        // Si no se encuentra el atributo, se crea uno nuevo
        if (!isAttributeFound) {
            relationship.getRelationshipAttributes().add(new RelationshipAttribute(relationshipAttributeDefinition, relationship, target));
        }

        // Se resetean los placeholder para los target de las relaciones
        basicTypeValue = new BasicTypeValue(null);
        conceptSelected = null;
        selectedHelperTableRecord = new HelperTableRecord();
    }

    /**
     * Este método es el encargado de remover una relación específica del concepto.
     */
    public void removeRelationship(RelationshipDefinition rd, Relationship r) {
        concept.removeRelationshipWeb(r);
    }

    /**
     * Este método es el encargado de agregar descripciones al concepto
     */
    public void addDescription() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (!otherTermino.trim().equals("")) {
            if (otherDescriptionType != null) {
                if (otherDescriptionType.getName().equalsIgnoreCase("abreviada") && concept.getValidDescriptionAbbreviated() != null) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Solo puede existir una descripción abreviada"));
                    return;
                }

                DescriptionWeb description = new DescriptionWeb(concept, otherTermino, otherDescriptionType);
                if (categoryManager.categoryContains(category, description.getTerm())) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta descripcion ya existe en esta categoria"));
                    return;
                }

                if (containDescription(description)) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta descripcion ya existe en este concepto"));
                    return;
                }

                description.setCaseSensitive(otherSensibilidad);

                if (otherDescriptionType.getName().equalsIgnoreCase("abreviada") || otherDescriptionType.getName().equalsIgnoreCase("sinónimo")) {
                    description.setCaseSensitive(concept.getValidDescriptionFavorite().isCaseSensitive());
                }


                description.setModeled(concept.isModeled());
                description.setCreatorUser(user);
                description.setDescriptionId(descriptionManager.generateDescriptionId());
                concept.addDescriptionWeb(description);

                otherTermino = "";
                otherDescriptionType = null;
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha seleccionado el tipo de descripción"));
            }

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha ingresado el término a la descripción"));
            context.getAttributes();
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
            if (!(concept.getValidRelationshipsWebByRelationDefinition(category.getRelationshipDefinitions().get(i)).size() >= category.getRelationshipDefinitions().get(i).getMultiplicity().getLowerBoundary())) {
                return false;
            }
        }
        return true;
    }

    public boolean containDescription(DescriptionWeb descriptionWeb) {
        for (DescriptionWeb description : concept.getDescriptionsWeb()) {
            if (description.getTerm().trim().equals(descriptionWeb.getTerm().trim())) {
                return true;
            }
        }
        return false;
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

    public List<RelationshipAttribute> getRelationshipAttributesByRelationshipDefinition(RelationshipDefinition definition) {

        if (definition == null)
            return new ArrayList<RelationshipAttribute>();

        if (!relationshipAttributesPlaceholder.containsKey(definition)) {


            List<RelationshipAttribute> attributes = new ArrayList<RelationshipAttribute>(definition.getRelationshipAttributeDefinitions().size());

            for (RelationshipAttributeDefinition attributeDefinition : definition.getRelationshipAttributeDefinitions()) {

                RelationshipAttribute attribute = new RelationshipAttribute();

                attribute.setRelationAttributeDefinition(attributeDefinition);

                Target t = new TargetFactory().createPlaceholderTargetFromTargetDefinition(definition.getTargetDefinition());

                attribute.setTarget(t);

                attributes.add(attribute);
            }

            relationshipAttributesPlaceholder.put(definition, attributes);
        }

        return relationshipAttributesPlaceholder.get(definition);

    }

    public void saveConcept() {

        FacesContext context = FacesContext.getCurrentInstance();
        if (validateRelationships()) {
            // Si el concepto está persistido, actualizarlo
            if (concept.isPersistent()) {
                updateConcept(context);
            }

            // Si el concepto no está persistido, persistirlo
            else {
                persistConcept(context);
            }
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las relaciones no cumplen con el minimo requerido"));

        }

    }

    private void persistConcept(FacesContext context) {
        // TODO: Investigar cómo capturar la excepción de negocio
        try {
            conceptManager.persist(concept, user);
            context.addMessage(null, new FacesMessage("Successful", "Concepto guardado "));
            // Se resetea el concepto, como el concepto está persistido, se le pasa su id
            getConceptById(concept.getId());
        } catch (BusinessRuleException bre) {
            context.addMessage(null, new FacesMessage("Error", bre.getMessage()));
        }
    }

    /**
     * @param context
     */
    private void updateConcept(FacesContext context) {

        /* Se actualizan los campos básicos */
        int changes = updateConceptFields();

        /* Se actualizan las descripciones */
        changes += updateConceptDescriptions();

        /* Se actualizan las relaciones */
        changes += updateConceptRelationships();

        if (changes == 0)
            context.addMessage(null, new FacesMessage("Warning", "No se ha realizado ningún cambio al concepto!!"));
        else {
            context.addMessage(null, new FacesMessage("Successful", "Se han registrado " + changes + " cambios en el concepto."));

            // Se restablece el concepto, como el concepto está persistido, se le pasa su id
            getConceptById(concept.getId());
        }
    }

    /**
     * @return
     */
    private int updateConceptRelationships() {

        List<RelationshipWeb> relationshipsForPersist = concept.getUnpersistedRelationshipsWeb();

        /* Se persisten las nuevas relaciones */
        for (RelationshipWeb relationshipWeb : relationshipsForPersist) {
            relationshipManager.bindRelationshipToConcept(concept, relationshipWeb, user);
        }

        /* Se elimina las relaciones eliminadas */
        List<RelationshipWeb> relationshipsForDelete = concept.getRemovedRelationshipsWeb(_concept);
        for (RelationshipWeb relationshipWeb : relationshipsForDelete) {
            relationshipManager.removeRelationship(relationshipWeb, user);
            _concept.removeRelationship(relationshipWeb);
        }

        /* Se actualizan las relaciones actualizadas */
        List<Pair<RelationshipWeb, RelationshipWeb>> relationshipsForUpdate = concept.getModifiedRelationships(_concept);
        for (Pair<RelationshipWeb, RelationshipWeb> relationship : relationshipsForUpdate) {

            relationshipManager.updateRelationship(concept, relationship.getFirst(), relationship.getSecond(), user);
        }

        return relationshipsForPersist.size() + relationshipsForDelete.size() + relationshipsForUpdate.size();
    }

    /**
     * Este método es responsable de actualizar las descripciones del concepto.
     *
     * @return La cantidad de cambios realizados: agregadas, eliminadas y actualizadas.
     */
    private int updateConceptDescriptions() {

        /* Se persisten las nuevas descripciones */
        List<DescriptionWeb> unpersistedDescriptions = concept.getUnpersistedDescriptions();
        for (Description description : unpersistedDescriptions) {
            descriptionManager.bindDescriptionToConcept(concept, description, true, user);
        }

        /* Se invalidan las descripciones eliminadas */
        List<DescriptionWeb> descriptionsForDelete = concept.getRemovedDescriptionsWeb(_concept);
        for (Description description : descriptionsForDelete) {
            if(!containDescriptionToTranslate(description))descriptionManager.deleteDescription(description, user);
            _concept.removeDescription(description);
        }

        /* Se actualizan las que tienen cambios */
        List<Pair<DescriptionWeb, DescriptionWeb>> descriptionsForUpdate = concept.getModifiedDescriptionsWeb(_concept);
        for (Pair<DescriptionWeb, DescriptionWeb> description : descriptionsForUpdate) {
            description.getSecond().setId(PersistentEntity.NON_PERSISTED_ID);
            descriptionManager.updateDescription(concept, description.getFirst(), description.getSecond(), user);
        }

        return unpersistedDescriptions.size() + descriptionsForDelete.size() + descriptionsForUpdate.size();
    }

    private int updateConceptFields() {
        int changes = 0;

        if (attributeChanges()) {
            conceptManager.updateFields(_concept, concept, user);
            changes++;
        }
        return changes;
    }

    private boolean attributeChanges() {
        return _concept.isToBeReviewed() != concept.isToBeReviewed()
                || _concept.isToBeConsulted() != concept.isToBeConsulted()
                || !_concept.getObservation().equalsIgnoreCase(concept.getObservation())
                || !_concept.getTagSMTK().equals(concept.getTagSMTK());
    }


    public String deleteConcept() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();

        // Si el concepto está persistido, invalidarlo
        if (concept.isPersistent() && !concept.isModeled()) {
            conceptManager.delete(concept, user);
            context.addMessage(null, new FacesMessage("Successful", "Concepto eliminado"));
            ExternalContext eContext = FacesContext.getCurrentInstance().getExternalContext();
            eContext.redirect(eContext.getRequestContextPath() + "/views/concept/conceptEdit.xhtml");
            return "mainMenu.xhtml";
        } else {
            conceptManager.invalidate(concept, user);
            context.addMessage(null, new FacesMessage("Successful", "Concepto invalidado"));
            return "mainMenu.xhtml";
        }

    }

    public void cancelConcept() {

        FacesContext context = FacesContext.getCurrentInstance();
        concept.restore(_concept);
        context.addMessage(null, new FacesMessage("Info", "Los cambios se han descartado"));
    }

    public void updateFSN(Description d) {
        concept.getValidDescriptionFSN().setTerm(d.getTerm());
    }

    public void translateDescription() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (conceptSMTKTranslateDes == null) {

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se seleccionó el concepto de destino"));

        } else {
            descriptionToTranslate.setConceptSMTK(conceptSMTKTranslateDes);
            descriptionsToTraslate.add(descriptionToTranslate);
            descriptionManager.moveDescriptionToConcept(conceptSMTKTranslateDes, descriptionToTranslate, user);
            ConceptSMTKWeb conceptnew = new ConceptSMTKWeb(conceptManager.getConceptByID(concept.getId()));
            concept.setDescriptionsWeb(conceptnew.getDescriptionsWeb());
            conceptSMTKTranslateDes = null;
            descriptionToTranslate = null;
            auditAction = auditManager.getConceptAuditActions(concept, true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "La descripción se ha trasladado a otro concepto correctamente"));
        }


    }


    public boolean containDescriptionToTranslate(Description description){
        for (Description descriptionTraslate: descriptionsToTraslate) {
            if(descriptionTraslate.getId()==description.getId()){
                return true;
            }
        }return false;
    }

    /**
     * Metodo encargado de hacer el "enroque" con la preferida.
     */

    public void descriptionEditRow(RowEditEvent event) {

        long SYNONYMOUS_ID = 3;
        DescriptionWeb descriptionWeb = (DescriptionWeb) event.getObject();

        FacesContext context = FacesContext.getCurrentInstance();

        for (DescriptionWeb descriptionRowEdit : concept.getDescriptionsWeb()) {
            if (descriptionRowEdit.equals(descriptionWeb) /*|| descriptionRowEdit.getId() == ((DescriptionWeb) event.getObject()).getId()*/) {
                if (descriptionRowEdit.getDescriptionType().equals(descriptionTypeFactory.getFavoriteDescriptionType())) {
                    descriptionRowEdit.setDescriptionType(descriptionTypeFactory.getDescriptionTypeByID(SYNONYMOUS_ID));
                    DescriptionWeb descriptionFavorite = concept.getValidDescriptionFavorite();
                    descriptionFavorite.setDescriptionType(descriptionTypeFactory.getDescriptionTypeByID(SYNONYMOUS_ID));
                    descriptionRowEdit.setDescriptionType(descriptionTypeFactory.getFavoriteDescriptionType());
                }
            }
        }
    }

    public void onRowReorder(ReorderEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row Moved", "From: " + event.getFromIndex() + ", To:" + event.getToIndex());
        FacesContext context = FacesContext.getCurrentInstance();

        RelationshipDefinition relationshipDefinition = (RelationshipDefinition) UIComponent.getCurrentComponent(context).getAttributes().get("relationshipDefinition");

        int sourceOrder = event.getFromIndex()+1;
        int targetOrder = event.getToIndex()+1;

        RelationshipAttribute att1 = concept.getAttributeOrder(relationshipDefinition, sourceOrder);
        RelationshipAttribute att2 = concept.getAttributeOrder(relationshipDefinition, targetOrder);

        RelationshipAttribute _att1 = _concept.getAttributeOrder(relationshipDefinition, sourceOrder);
        RelationshipAttribute _att2 = _concept.getAttributeOrder(relationshipDefinition, targetOrder);

        att1.setTarget(new BasicTypeValue(targetOrder));
        att2.setTarget(new BasicTypeValue(sourceOrder));
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

    public HelperTableManager getHelperTableManager() {
        return helperTableManager;
    }

    public void setHelperTableManager(HelperTableManager helperTableManager) {
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

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }

    public List<TagSMTK> getTagSMTKs(String query) {
        List<TagSMTK> results = new ArrayList<TagSMTK>();

        for (TagSMTK tagSMTK : tagSMTKs) {
            if (tagSMTK.getName().toLowerCase().contains(query.toLowerCase()))
                results.add(tagSMTK);
        }

        return results;
    }

    public List<TagSMTK> getTagSMTKs() {

        return tagSMTKs;
    }

    public int getCategorySelect() {
        return categorySelect;
    }

    public void setCategorySelect(int categorySelect) {
        this.categorySelect = categorySelect;
    }

    public List<ConceptAuditAction> getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(List<ConceptAuditAction> auditAction) {
        this.auditAction = auditAction;
    }

    public Description getDescriptionToTranslate() {
        return descriptionToTranslate;
    }

    public void setDescriptionToTranslate(Description descriptionToTranslate) {
        this.descriptionToTranslate = descriptionToTranslate;
    }

    public CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public ConceptSMTK getConceptSMTKTranslateDes() {
        return conceptSMTKTranslateDes;
    }

    public void setConceptSMTKTranslateDes(ConceptSMTK conceptSMTKTranslateDes) {
        this.conceptSMTKTranslateDes = conceptSMTKTranslateDes;
    }

    //TODO: editar concepto

    private long idconceptselect;

    public long getIdconceptselect() {
        return idconceptselect;
    }

    public void setIdconceptselect(long idconceptselect) {
        this.idconceptselect = idconceptselect;

    }

    public int getHelperTableValuePlaceholder() {
        return helperTableValuePlaceholder;
    }

    public void setHelperTableValuePlaceholder(int helperTableValuePlaceholder) {
        this.helperTableValuePlaceholder = helperTableValuePlaceholder;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Category getCategorySelected() {
        return categorySelected;
    }

    public void setCategorySelected(Category categorySelected) {
        this.categorySelected = categorySelected;
    }

    public List<DescriptionType> getDescriptionTypesEdit() {
        return descriptionTypesEdit;
    }

    public void setDescriptionTypesEdit(List<DescriptionType> descriptionTypesEdit) {
        this.descriptionTypesEdit = descriptionTypesEdit;
    }

    public Map<Long, Relationship> getRelationshipPlaceholders() {
        return relationshipPlaceholders;
    }

    /**
     * Este método retorna una lista ordenada de relaciones.
     *
     * @return Una lista ordenada de las relaciones de la categoría.
     */
    public List<RelationshipDefinitionWeb> getOrderedRelationshipDefinitions() {

        if (orderedRelationshipDefinitionsList.isEmpty()) {
            for (RelationshipDefinition relationshipDefinition : category.getRelationshipDefinitions()) {
                orderedRelationshipDefinitionsList.add(viewAugmenter.augmentRelationshipDefinition(category, relationshipDefinition));
            }

            Collections.sort(orderedRelationshipDefinitionsList);
        }

        return orderedRelationshipDefinitionsList;
    }
}