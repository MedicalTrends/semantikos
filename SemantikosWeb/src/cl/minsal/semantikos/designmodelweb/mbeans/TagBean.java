package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.TagManager;
import cl.minsal.semantikos.model.Tag;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 26-08-16.
 */

@ManagedBean(name = "tagBean")
@ViewScoped
public class TagBean {


    private List<Tag> tagList;

    private List<Tag> tagListTable;


    private List<Tag> findSonTagList;

    private List<Tag> listTagSon;


    private Tag selectTagListTags;

    private Tag tagCreate;

    private Tag parentTagToCreate;

    private Tag tagSelected;

    private Tag tagEdit;

    private Tag parentTag;

    private Tag parentTagSelect;

    private Tag sonTagSelect;

    private Tag addSonSelect;


    private String nameTag;

    private String colorText;

    private String colorBackground;


    @EJB
    private TagManager tagManager;


    @PostConstruct
    public void init() {
        tagListTable= tagManager.getAllTags();
        tagList= tagManager.getAllTags();
        findSonTagList=tagManager.getAllTagsWithoutParent();
        listTagSon=tagManager.getAllTagsWithoutParent();
        tagCreate= new Tag(-1,null,null,null,null);
        parentTagToCreate= new Tag(-1,null,null,null,null);
    }

    /**
     *    Methods
     */

    public List<Tag> findTagByName(String nameTag) {

        if (nameTag != null) {
            return tagManager.findTag(tagCreate,nameTag);
        }
        return new ArrayList<Tag>();
    }

    public List<Tag> findTagByNameEditTag(String nameTag) {
        if (nameTag != null) {
            return tagManager.findTag(tagEdit,nameTag);
        }
        return new ArrayList<Tag>();
    }

    public List<Tag> findTagSon() {

        findSonTagList = tagManager.getAllTagsWithoutParent();

        if(tagCreate.getParentTag()!=null){
            if (tagCreate.getParentTag().getId() !=-1) {
                findSonTagList = tagManager.getOtherTags(tagCreate.getParentTag());
            }
        }
        if(tagCreate.getSonTag().size()>0){
            for (Tag tagSonToCreate: tagCreate.getSonTag()) {
                for (Tag tagSonList : findSonTagList) {
                    if (tagSonList.equals(tagSonToCreate)) {
                        findSonTagList.remove(tagSonList);
                        break;
                    }
                }
            }
            return findSonTagList;
        }
        return findSonTagList;
    }

    public List<Tag> listTagSonEdit() {
        return listTagSon= tagManager.getOtherTags(tagEdit);
    }

    public void createTagParent(){
        tagCreate.setParentTag(parentTagToCreate);
    }

    public void createTagSon(){
        tagCreate.addSon(new Tag(-1,nameTag,colorBackground,colorText,null));

    }

    public void addTagParent(){
        tagCreate.setParentTag(parentTagSelect);
        findTagSon();
    }

    public void addTagSon(){
        tagCreate.addSon(sonTagSelect);
        findTagSon();

    }

    public void removeTagSon(Tag son){
        tagCreate.getSonTag().remove(son);
    }

    public void removeTagParent(){
        tagCreate.setParentTag(null);
        parentTagSelect=null;
        parentTagToCreate= new Tag(-1,null,null,null,null);
    }

    public void createTag(){
        tagManager.persist(tagCreate);
        tagListTable= tagManager.getAllTags();
        tagList= tagManager.getAllTags();
        findSonTagList=tagManager.getAllTagsWithoutParent();
        listTagSon=tagManager.getAllTagsWithoutParent();
        tagCreate= new Tag(-1,null,null,null,null);
        parentTagToCreate= new Tag(-1,null,null,null,null);

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Etiqueta creada", "La etiqueta se creo exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void linkSon() {
        tagEdit.addSon(addSonSelect);
        tagManager.link(tagEdit,addSonSelect);
        listTagSonEdit();
        tagListTable= tagManager.getAllTags();
    }

    public void linkParent(){
        tagEdit.setParentTag(parentTag);
        tagManager.link(parentTag,tagEdit);
        listTagSonEdit();
        tagListTable= tagManager.getAllTags();
        parentTag=null;
    }

    public void unlinkSon(Tag tagUnlink) {
        tagManager.unlink(tagEdit, tagUnlink);
        tagEdit.getSonTag().remove(tagUnlink);
        tagListTable= tagManager.getAllTags();
    }

    public void unlinkParent() {
        tagManager.unlink(tagEdit.getParentTag(), tagEdit);
        tagEdit.setParentTag(null);
        tagListTable= tagManager.getAllTags();
    }

    public void deleteTag() {
        tagManager.removeTag(tagEdit);
        tagListTable= tagManager.getAllTags();
    }

    public void onRowEdit(CellEditEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        Tag tagToEdit = context.getApplication().evaluateExpressionGet(context, "#{tag}", Tag.class);
        tagManager.update(tagToEdit);

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Etiqueta actualizada", "La etiqueta se actualizo exitosamente");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }




    /**
     * Getter & Setter
     */


    public List<Tag> getFindSonTagList() {
        return findSonTagList;
    }

    public List<Tag> getListTagSon() {
        return listTagSon;
    }

    public void setListTagSon(List<Tag> listTagSon) {
        this.listTagSon = listTagSon;
    }

    public void setFindSonTagList(List<Tag> findSonTagList) {
        this.findSonTagList = findSonTagList;
    }

    public Tag getTagCreate() {
        return tagCreate;
    }

    public void setTagCreate(Tag tagCreate) {
        this.tagCreate = tagCreate;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public Tag getSelectTagListTags() {
        return selectTagListTags;
    }

    public void setSelectTagListTags(Tag selectTagListTags) {
        this.selectTagListTags = selectTagListTags;
    }

    public Tag getTagSelected() {
        return tagSelected;
    }

    public void setTagSelected(Tag tagSelected) {
        this.tagSelected = tagSelected;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(String colorBackground) {
        this.colorBackground = colorBackground;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }

    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        this.parentTag = parentTag;
    }

    public Tag getTagEdit() {
        return tagEdit;
    }

    public void setTagEdit(Tag tagEdit) {
        this.tagEdit = tagEdit;
        listTagSonEdit();
    }

    public Tag getParentTagToCreate() {
        return parentTagToCreate;
    }

    public void setParentTagToCreate(Tag parentTagToCreate) {
        this.parentTagToCreate = parentTagToCreate;
    }

    public Tag getParentTagSelect() {
        return parentTagSelect;
    }

    public void setParentTagSelect(Tag parentTagSelect) {
        this.parentTagSelect = parentTagSelect;
    }

    public Tag getSonTagSelect() {
        return sonTagSelect;
    }

    public void setSonTagSelect(Tag sonTagSelect) {
        this.sonTagSelect = sonTagSelect;
    }

    public Tag getAddSonSelect() {
        return addSonSelect;
    }

    public void setAddSonSelect(Tag addSonSelect) {
        this.addSonSelect = addSonSelect;
    }

    public List<Tag> getTagListTable() {
        return tagListTable;
    }

    public void setTagListTable(List<Tag> tagListTable) {
        this.tagListTable = tagListTable;
    }

    public TagManager getTagManager() {
        return tagManager;
    }


}
