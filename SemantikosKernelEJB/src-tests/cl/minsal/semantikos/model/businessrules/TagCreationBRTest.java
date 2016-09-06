package cl.minsal.semantikos.model.businessrules;

import cl.minsal.semantikos.kernel.daos.DAO;
import cl.minsal.semantikos.model.Tag;
import cl.minsal.semantikos.model.exceptions.BusinessRuleException;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class TagCreationBRTest {

    @Test
    public void testBr001ADeepnessLevelDown() throws Exception {

        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Tag Test", "rojo", "red",  null);

        new TagCreationBR().applyRules(tag);
    }

    @Test
    public void testBr001ADeepnessLevelDown02() throws Exception {

        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Tag Test", "rojo", "red",  null);
        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Tag Child", "blue", "blue",  tag);
        tag.addChild(childTag);

        new TagCreationBR().applyRules(tag);
    }

    @Test
    public void testBr001ADeepnessLevelDown03() throws Exception {

        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Tag Test", "rojo", "red",  null);
        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Tag Child", "blue", "blue",  tag);
        tag.addChild(childTag);

        Tag grandChildTag = new Tag(DAO.NON_PERSISTED_ID, "Tag Grand Child", "green", "green",  childTag);
        childTag.addChild(grandChildTag);

        new TagCreationBR().applyRules(tag);
    }

    @Test(expected = BusinessRuleException.class)
    public void testBr001ADeepnessLevelDown04() throws Exception {

        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Parent Tag", "rojo", "red",  null);

        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Child Tag", "blue", "blue",  tag);
        tag.addChild(childTag);

        Tag grandChildTag = new Tag(DAO.NON_PERSISTED_ID, "Grand Child Tag", "green", "green",  childTag);
        childTag.addChild(grandChildTag);

        Tag grandGrandChildTag = new Tag(DAO.NON_PERSISTED_ID, "GrandGrand Child Tag", "black", "black", grandChildTag);
        grandChildTag.addChild(grandGrandChildTag);

        new TagCreationBR().applyRules(tag);
    }
}