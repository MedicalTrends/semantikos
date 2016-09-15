package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.*;
import cl.minsal.semantikos.model.*;
import cl.minsal.semantikos.model.audit.ConceptAuditAction;
import cl.minsal.semantikos.model.basictypes.BasicTypeValue;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import cl.minsal.semantikos.model.helpertables.HelperTableRecord;
import cl.minsal.semantikos.model.relationships.*;
import cl.minsal.semantikos.util.ConceptUtils;
import cl.minsal.semantikos.util.Pair;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    HelperTableManagerInterface helperTableManager;

    @EJB
    TagSMTKManager tagSMTKManager;

    @EJB
    AuditManagerInterface auditManager;

    @ManagedProperty(value = "#{smtkBean}")
    private SMTKTypeBean smtkTypeBean;

    DescriptionTypeFactory descriptionTypeFactory = DescriptionTypeFactory.getInstance();

    public User user;

    private ConceptSMTKWeb concept;
    //Concepto respaldo
    private ConceptSMTKWeb _concept;

    private Category category;

    private List<DescriptionType> descriptionTypes = new ArrayList<DescriptionType>();

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

    private Map<RelationshipDefinition, List<RelationshipAttribute>> relationshipAttributesPlaceholder = new HashMap<RelationshipDefinition, List<RelationshipAttribute>>();


    //Parametros del formulario

    private String FSN;

    private String favoriteDescription;

    private int categorySelect;

    private List<ConceptAuditAction> auditAction;

    //para tipo helpertable
    private int helperTableValuePlaceholder;


    @ManagedProperty(value="#{conceptExport}")
    private ConceptExportMBean conceptBeanExport;

    public ConceptExportMBean getConceptBeanExport() {
        return conceptBeanExport;
    }

    public void setConceptBeanExport(ConceptExportMBean conceptBean) {
        this.conceptBeanExport = conceptBean;
    }

    //Inicializacion del Bean

    @PostConstruct
    protected void initialize() throws ParseException {

        // TODO: Manejar el usuario desde la sesión

        user = new User();

        user.setIdUser(1);
        user.setUsername("amauro");
        user.setPassword("amauro");
        Profile DESIGNER_PROFILE = new Profile(2, "Diseñador", "Usuario Diseñador");
        user.getProfiles().add(DESIGNER_PROFILE);


        // Iniciar cuadro de dialogo

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').show();");

        //category = categoryManager.getCategoryById(1);
        //category = categoryManager.getCategoryById(105590001);
        //category = categoryManager.getCategoryById(71388002);
        category = categoryManager.getCategoryById(419891008);


        descriptionTypes = descriptionTypeFactory.getDescriptionTypesButFSNandFavorite();

        tagSMTKs = tagSMTKManager.getAllTagSMTKs();


        // TODO: Inicializar lista de estados de descripción con todos los estados posibles
        //descriptionStates = stateMachineManager.getConceptStateMachine().
        //concept = new ConceptSMTK(category, new Description("electrocardiograma de urgencia", descriptionTypes.get(0)));

    }

    //Methods

    public void addTagToConcept(Tag tag){

    }

    public void createConcept() throws ParseException {

        if(idconceptselect==0){
            category = categoryManager.getCategoryById(categorySelect);
            //category = categoryManager.getCategoryById(71388002);
            newConcept(category, favoriteDescription);
        }else{
            getConceptById(idconceptselect);
            //getConceptById(80602);
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogNameConcept').hide();");
    }

    //Este método es responsable de a partir de un concepto SMTK y un término, devolver un concepto WEB con su FSN y su Preferida
    public ConceptSMTKWeb initConcept(ConceptSMTK concept, String term){
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
        // Se crea una copia de respaldo
        _concept = initConcept(conceptSMTK, term);

        conceptBeanExport.setConceptSMTK(concept);
        conceptBeanExport.loadConcept();
    }

    //Este método es responsable de pasarle a la vista un concepto, dado el id del concepto
    //(llamado desde la vista cuando se desea editar un concepto)
    public void getConceptById(long conceptId) {
        ConceptSMTK conceptSMTK = conceptManager.getConceptByID(conceptId);
        conceptSMTK.setRelationships(conceptManager.loadRelationships(conceptSMTK));

        concept = new ConceptSMTKWeb(conceptSMTK);
        //Se respalda estado original del concepto
        _concept = new ConceptSMTKWeb(conceptSMTK);
        auditAction=auditManager.getConceptAuditActions(concept,10,true);
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

        Relationship relationship = new Relationship(this.concept, target, relationshipDefinition,new ArrayList<RelationshipAttribute>());
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationshipWeb(relationship);

    }

    /**
     * Este método es el encargado de agregar una nuva relacion con los parémetros que se indican.
     */
    public void addRelationship(RelationshipDefinition relationshipDefinition, Target target) {

        Relationship relationship = new Relationship(this.concept, target, relationshipDefinition,new ArrayList<RelationshipAttribute>());
        // Se utiliza el constructor mínimo (sin id)
        this.concept.addRelationshipWeb(relationship);
        conceptSelected = null;
    }


    /**
     * Este método es el encargado de agregar una nuva relacion con los parémetros que se indican.
     */
    public void addHelperTableRelationshipWithAttributes(RelationshipDefinition relationshipDefinition) {


        HelperTableRecord record = helperTableManager.getRecord(helperTableValuePlaceholder);

        Relationship relationship = new Relationship(this.concept, record, relationshipDefinition,getRelationshipAttributesByRelationshipDefinition(relationshipDefinition));

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
        if (!isRelationshipFound) {
            this.concept.addRelationshipWeb(new Relationship(this.concept, target, relationshipDefinition,new ArrayList<RelationshipAttribute>()));
        }
        // Se resetean los placeholder para los target de las relaciones
        basicTypeValue= new BasicTypeValue(null);
        conceptSelected = null;
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
                if(otherDescriptionType.getName().equalsIgnoreCase("abreviada") && concept.getValidDescriptionAbbreviated()!=null) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Solo puede existir una descripción abreviada"));
                    return;
                }

                DescriptionWeb description = new DescriptionWeb(concept, otherTermino, otherDescriptionType);
                if( categoryManager.categoryContains(category,description.getTerm())){
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta descripcion ya existe en esta categoria"));
                    return;
                }

                if(containDescription(description)){
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Esta descripcion ya existe en este concepto"));
                    return;
                }

                description.setCaseSensitive(otherSensibilidad);

                if(otherDescriptionType.getName().equalsIgnoreCase("abreviada") || otherDescriptionType.getName().equalsIgnoreCase("sinónimo") ){
                    description.setCaseSensitive(concept.getValidDescriptionFavorite().isCaseSensitive());
                }


                description.setModeled(false);
                description.setDescriptionId(descriptionManager.generateDescriptionId());
                concept.addDescriptionWeb(description);

                otherTermino = "";
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha seleccionado el tipo de descripción"));
            }

        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se ha ingresado el término a la descripción"));
            context.getAttributes();
        }

    }


    public boolean containDescription(DescriptionWeb descriptionWeb){
        for (DescriptionWeb description: concept.getDescriptionsWeb()) {
            if( description.getTerm().equals(descriptionWeb.getTerm())){
                   return true;
            }
        }return false;
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

    public List<RelationshipAttribute> getRelationshipAttributesByRelationshipDefinition(RelationshipDefinition definition){

        if(definition==null)
            return new ArrayList<RelationshipAttribute>();

        if(!relationshipAttributesPlaceholder.containsKey(definition)) {


            List<RelationshipAttribute> attributes = new ArrayList<RelationshipAttribute>(definition.getRelationshipAttributeDefinitions().size());

            for (RelationshipAttributeDefinition attributeDefinition : definition.getRelationshipAttributeDefinitions()) {

                RelationshipAttribute attribute = new RelationshipAttribute();

                attribute.setRelationAttributeDefinition(attributeDefinition);

                Target t = new TargetFactory().createPlaceholderTargetFromTargetDefinition(definition.getTargetDefinition());

                attribute.setTarget(t);

                attributes.add(attribute);
            }

            relationshipAttributesPlaceholder.put(definition,attributes);
        }

        return relationshipAttributesPlaceholder.get(definition);

    }

    public void saveConcept()  {

        FacesContext context = FacesContext.getCurrentInstance();

        // Si el concepto está persistido, actualizarlo
        if (concept.isPersistent()) {

            int changes = 0;

            if(_concept.isToBeReviewed()!=concept.isToBeReviewed() || _concept.isToBeConsulted()!= concept.isToBeConsulted()  || !_concept.getObservation().equalsIgnoreCase(concept.getObservation())){
                conceptManager.updateFields(_concept,concept,user);
                changes++;
            }

            List<Pair<Description, Description>> descriptionsForUpdate= ConceptUtils.getModifiedDescriptions(_concept.getDescriptionsWeb(), concept.getDescriptionsWeb());
            List<Description> descriptionsForPersist= ConceptUtils.getNewDescriptions(_concept.getDescriptionsWeb(), concept.getDescriptionsWeb());
            List<Description> descriptionsForDelete= ConceptUtils.getDeletedDescriptions(_concept.getDescriptionsWeb(), concept.getDescriptionsWeb());

            changes = changes + descriptionsForUpdate.size();

            for (Pair<Description, Description> description : descriptionsForUpdate) {
                descriptionManager.updateDescription(concept, description.getFirst(), description.getSecond(), user);
            }

            changes = changes + descriptionsForPersist.size();

            for (Description description : descriptionsForPersist)
                descriptionManager.bindDescriptionToConcept(concept, description, true, user);

            changes = changes + descriptionsForDelete.size();

            for (Description description : descriptionsForDelete)
                descriptionManager.deleteDescription(description, user);

            List<Pair<RelationshipWeb, RelationshipWeb>> relationshipsForUpdate= ConceptUtils.getModifiedRelationships(_concept.getValidPersistedRelationshipsWeb(), concept.getValidPersistedRelationshipsWeb());
            List<RelationshipWeb> relationshipsForPersist= concept.getUnpersistedRelationshipsWeb(); //ConceptUtils.getNewRelationships(_concept.getRelationshipsWeb(), concept.getRelationshipsWeb());
            List<Relationship> relationshipsForDelete= ConceptUtils.getDeletedRelationships(_concept.getRelationshipsWeb(), concept.getRelationshipsWeb());

            changes = changes + relationshipsForUpdate.size();

            for (Pair<RelationshipWeb, RelationshipWeb> relationship : relationshipsForUpdate)
                relationshipManager.updateRelationship(concept, relationship.getFirst(), relationship.getSecond(), user);

            changes = changes + relationshipsForPersist.size();

            for (RelationshipWeb relationshipWeb : relationshipsForPersist)
                relationshipManager.bindRelationshipToConcept(concept, relationshipWeb, user);

            for (Description description : descriptionsForDelete)
                descriptionManager.deleteDescription(description, user);

            if(changes == 0)
                context.addMessage(null, new FacesMessage("Warning", "No se ha realizado ningún cambio al concepto!!"));
            else {
                context.addMessage(null, new FacesMessage("Successful", "Concepto modificado "));
                // Se resetea el concepto, como el concepto está persistido, se le pasa su id
                getConceptById(concept.getId());
            }

        }
        // Si el concepto no está persistido, persistirlo
        else {

            // TODO: Investigar cómo capturar la excepción de negocio
            try{
                conceptManager.persist(concept, user);
                context.addMessage(null, new FacesMessage("Successful", "Concepto guardado "));
                // Se resetea el concepto, como el concepto está persistido, se le pasa su id
                getConceptById(concept.getId());
            }
            catch (BusinessRuleException bre){
                context.addMessage(null, new FacesMessage("Error", bre.getMessage()));
            }

        }

    }

    public void deleteConcept() {

        FacesContext context = FacesContext.getCurrentInstance();

        // Si el concepto está persistido, invalidarlo
        if (concept.isPersistent() && !concept.isModeled()) {
            conceptManager.delete(concept,user);
            context.addMessage(null, new FacesMessage("Successful", "Concepto eliminado"));
        }else{
            conceptManager.invalidate(concept, user);
            context.addMessage(null, new FacesMessage("Successful", "Concepto invalidado"));
        }

    }

    public void cancelConcept() {

        FacesContext context = FacesContext.getCurrentInstance();
        concept.restore(_concept);
        context.addMessage(null, new FacesMessage("Info", "Los cambios se han descartado"));
    }

    public void updateFSN(Description d){
        concept.getValidDescriptionFSN().setTerm(d.getTerm());
    }

    public void translateDescription(){
        FacesContext context = FacesContext.getCurrentInstance();
        if(conceptSMTKTranslateDes==null){

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error", "No se seleccionó el concepto de destino"));

        }else{
            descriptionManager.moveDescriptionToConcept(conceptSMTKTranslateDes,descriptionToTranslate,user);
            concept= new ConceptSMTKWeb(conceptManager.getConceptByID(concept.getId()));
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Successful", "La descripción se ha trasladado a otro concepto correctamente"));
        }



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
        if(selectedHelperTableRecord == null)
            selectedHelperTableRecord = new HelperTableRecord();

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
            if(tagSMTK.getName().toLowerCase().contains(query.toLowerCase()))
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
}

