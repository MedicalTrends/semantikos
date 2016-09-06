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

    private List<Tag> tagListTable;

    private List<Tag> tagEditList;

    private List<Tag> findTagList;

    private List<Tag> findSonTagList;

    private List<Tag> listTagSon;


    private List<Tag> children;


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
            return tagManager.findTagByNamePattern(nameTag);
        }
        return new ArrayList<Tag>();
    }

    public List<Tag> findTagByNameEditTag(String nameTag) {

        if (nameTag != null) {
            List<Tag> tags= tagManager.findTagByNamePattern(nameTag);
            for (int i = 0; i < tags.size(); i++) {
                if(tags.get(i).equals(tagEdit)){
                    tags.remove(i);
                    return tags;
                }
            }
            return tags;
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

        if(tagCreate.getChildrenTag().size()>0){
            for (Tag tagSonToCreate: tagCreate.getChildrenTag()) {
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

        listTagSon = tagManager.getAllTagsWithoutParent();

        for (int i = 0; i < listTagSon.size(); i++) {
            if(listTagSon.get(i).equals(tagEdit)){
                listTagSon.remove(i);
                break;
            }
        }

        if(tagEdit.getParentTag()!=null){
            if (tagEdit.getParentTag().getId() !=-1) {
                listTagSon = tagManager.getOtherTags(tagEdit.getParentTag());
            }
        }
        if(tagEdit.getChildrenTag().size()>0){
            for (Tag tagSonToEdit: tagEdit.getChildrenTag()) {
                for (Tag tagSonList : listTagSon) {
                    if (tagSonList.equals(tagSonToEdit)) {
                        listTagSon.remove(tagSonList);
                        break;
                    }
                }
            }
            return listTagSon;
        }

        return listTagSon;


    }

    public void createTagParent(){
        tagCreate.setParentTag(parentTagToCreate);
    }

    public void createTagSon(){
        tagCreate.addChild(new Tag(-1,nameTag,colorBackground,colorText,null));

    }

    public void addTagParent(){
        tagCreate.setParentTag(parentTagSelect);
        findTagSon();
    }

    public void addTagSon(){
        tagCreate.addChild(sonTagSelect);
        findTagSon();

    }

    public void removeTagSon(Tag son){
        tagCreate.getChildrenTag().remove(son);
    }

    public void removeTagParent(){
        tagCreate.setParentTag(null);
        parentTagSelect=null;
        parentTagToCreate= new Tag(-1,null,null,null,null);
    }

    public void createTag(){
        tagManager.persist(tagCreate);
    }

    public void linkSon() {
        tagEdit.addChild(addSonSelect);
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
        tagEdit.getChildrenTag().remove(tagUnlink);
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


    /**
     * Getter & Setter
     */


    public List<Tag> getTagEditList() {
        return tagEditList;
    }

    public void setTagEditList(List<Tag> tagEditList) {
        this.tagEditList = tagEditList;
    }

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

    public List<Tag> getFindTagList() {
        return findTagList;
    }

    public Tag getSelectTagListTags() {
        return selectTagListTags;
    }

    public void setSelectTagListTags(Tag selectTagListTags) {
        this.selectTagListTags = selectTagListTags;
    }

    public void setFindTagList(List<Tag> findTagList) {
        this.findTagList = findTagList;
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

    public List<Tag> getChildren() {
        return children;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
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

    //Methods

    public List<Tag> findTagChildByName(String nameTag) {

        /*if (nameTag != null) {
            findTagList = tagManager.findTagByNamePattern(nameTag);
            return findTagList;
        }*/
        return new ArrayList<Tag>();
    }

    public List<Tag> findTagParentByName(String nameTag) {

        /*if (nameTag != null) {
            findTagList = tagManager.findTagByNamePattern(nameTag);
            return findTagList;
        }*/
        return new ArrayList<Tag>();
    }


    public void addChild() {
        tagEdit.getChildrenTag().add(tagSelected);
        tagManager.link(tagEdit,tagSelected);
    }

    public void addParent() {
        tagEdit.setParentTag(parentTagSelect);
        tagManager.link(tagSelected,tagEdit);
    }











}
