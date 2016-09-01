package cl.minsal.semantikos.designmodelweb.mbeans;

import cl.minsal.semantikos.kernel.components.TagManager;
import cl.minsal.semantikos.model.ConceptSMTK;
import cl.minsal.semantikos.model.Tag;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by des01c7 on 26-08-16.
 */

@ManagedBean(name = "tagBean")
@ViewScoped
public class TagBean {

    private List<Tag> tagList;

    private List<Tag> fingTagList;

    private String findNameTag;

    private String nameTag;

    private String colorText;

    private String colorBackground;

    private ConceptSMTK conceptSMTK;

    private Tag tagSelected;

    private Tag tagEdit;

    private Tag tag;

    private Tag parentTag;

    private Tag childTag;


    @EJB
    private TagManager tagManager;

    @PostConstruct
    public void init() {

        tagList = tagManager.getAllTags();

    }

    //Getter & Setter

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Tag> getFingTagList() {
        return fingTagList;
    }

    public void setFingTagList(List<Tag> fingTagList) {
        this.fingTagList = fingTagList;
    }

    public String getFindNameTag() {
        return findNameTag;
    }

    public void setFindNameTag(String findNameTag) {
        this.findNameTag = findNameTag;
    }

    public ConceptSMTK getConceptSMTK() {
        return conceptSMTK;
    }

    public void setConceptSMTK(ConceptSMTK conceptSMTK) {
        this.conceptSMTK = conceptSMTK;
    }

    public Tag getTagSelected() {
        return tagSelected;
    }

    public void setTagSelected(Tag tagSelected) {
        this.tagSelected = tagSelected;
    }

    public TagManager getTagManager() {
        return tagManager;
    }

    public void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getParentTag() {
        return parentTag;
    }

    public void setParentTag(Tag parentTag) {
        this.parentTag = parentTag;
    }

    public Tag getChildTag() {
        return childTag;
    }

    public void setChildTag(Tag childTag) {
        this.childTag = childTag;
    }

    public Tag getTagEdit() {
        return tagEdit;
    }

    public void setTagEdit(Tag tagEdit) {
        this.tagEdit = tagEdit;
    }

    //Methods

    public List<Tag> findTagByName(String nameTag) {

        if (nameTag != null) {
            fingTagList = tagManager.findTagByNamePattern(nameTag);
            return fingTagList;
        }
        return new ArrayList<Tag>();
    }

    public void createTag() {
        if (parentTag == null) {
            parentTag = new Tag(-1, null, null, null, null, null);
        }
        Tag tagCreate = new Tag(-1, nameTag, colorBackground, colorText, new ArrayList<Tag>(), parentTag);
        tagManager.persist(tagCreate);
        parentTag = null;
        nameTag = null;
        colorBackground = null;
        colorText = null;
    }

    public void addChild() {
        tagEdit.getChildTag().add(tagSelected);
        tagManager.link(tagEdit,tagSelected);
    }

    public void addParent() {
        tagEdit.setParentTag(tagSelected);
        tagManager.link(tagSelected,tagEdit);
    }


    public void unlinkChild(Tag tagUnlink) {
        tagManager.unlink(tagEdit, tagUnlink);
        tagEdit.getChildTag().remove(tagUnlink);
        tagList = tagManager.getAllTags();
    }

    public void unlinkParent() {
        tagManager.unlink(tagEdit.getParentTag(), tagEdit);
        tagEdit.setParentTag(null);
        tagList = tagManager.getAllTags();
    }

    public void link() {

    }

    public void deleteTag() {
        tagManager.removeTag(tagEdit);
        tagList = tagManager.getAllTags();
    }


}
