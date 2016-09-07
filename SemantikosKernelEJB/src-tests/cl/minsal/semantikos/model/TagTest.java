package cl.minsal.semantikos.model;

import cl.minsal.semantikos.kernel.daos.DAO;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class TagTest {

    @Test
    public void testDeepnessLevel01() throws Exception {
        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Parent Tag", "rojo", "red",  null);

        assertEquals(1, tag.deepnessLevel());
    }

    @Test
    public void testDeepnessLevel02() throws Exception {
        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Parent Tag", "rojo", "red",  null);

        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Child Tag", "blue", "blue",  tag);
        tag.addChild(childTag);

        assertEquals(2, tag.deepnessLevel());
    }

    @Test
    public void testDeepnessLevel03() throws Exception {
        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Parent Tag", "rojo", "red",  null);

        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Child Tag", "blue", "blue",  tag);
        tag.addChild(childTag);

        Tag grandChildTag = new Tag(DAO.NON_PERSISTED_ID, "Grand Child Tag", "green", "green",  childTag);
        childTag.addChild(grandChildTag);

        assertEquals(3, tag.deepnessLevel());
    }

    @Test
    public void testDeepnessLevel04() throws Exception {
        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Parent Tag", "rojo", "red",  null);

        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Child Tag", "blue", "blue",  tag);
        tag.addChild(childTag);

        Tag grandChildTag = new Tag(DAO.NON_PERSISTED_ID, "Grand Child Tag", "green", "green",  childTag);
        childTag.addChild(grandChildTag);

        Tag grandGrandChildTag = new Tag(DAO.NON_PERSISTED_ID, "GrandGrand Child Tag", "black", "black",  grandChildTag);
        grandChildTag.addChild(grandGrandChildTag);

        assertEquals(4, tag.deepnessLevel());
    }

    @Test
    public void testDeepnessLevel05() throws Exception {
        Tag tag = new Tag(DAO.NON_PERSISTED_ID, "Parent Tag", "rojo", "red",  null);

        Tag childTag = new Tag(DAO.NON_PERSISTED_ID, "Child Tag", "blue", "blue",  tag);
        Tag childTag2 = new Tag(DAO.NON_PERSISTED_ID, "Child Tag", "Deep blue", "blue",  tag);
        tag.addChild(childTag);
        tag.addChild(childTag2);

        Tag grandChildTag = new Tag(DAO.NON_PERSISTED_ID, "Grand Child Tag", "green", "green",  childTag);
        childTag.addChild(grandChildTag);

        Tag grandGrandChildTag = new Tag(DAO.NON_PERSISTED_ID, "GrandGrand Child Tag", "black", "black",  grandChildTag);
        grandChildTag.addChild(grandGrandChildTag);

        assertEquals(4, tag.deepnessLevel());
    }
}