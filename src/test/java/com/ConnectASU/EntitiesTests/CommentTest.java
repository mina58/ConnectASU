package com.ConnectASU.EntitiesTests;

import com.ConnectASU.entities.Comment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CommentTest {

    private static Comment comment;

    @BeforeAll
    public static void setUp() {
        int id = 1;
        String content = "This is a comment.";
        String authorName = "John Doe";
        comment = new Comment(id, content, authorName);
    }

    @Test
    public void testGetId() {
        int expectedId = 1;
        int actualId = comment.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetContent() {
        String expectedContent = "This is a comment.";
        String actualContent = comment.getContent();
        assertEquals(expectedContent, actualContent, "Expected content: " + expectedContent + ", Actual content: " + actualContent);
    }

    @Test
    public void testGetAuthorName() {
        String expectedAuthorName = "John Doe";
        String actualAuthorName = comment.getAuthorName();
        assertEquals(expectedAuthorName, actualAuthorName, "Expected author name: " + expectedAuthorName + ", Actual author name: " + actualAuthorName);
    }

    @Test
    public void testEqualsTrue() {
        Comment comment2 = new Comment(1, "This is a comment.", "John Doe");
        assertEquals(comment, comment2, "Expected the comment objects to be equal");
    }

    @Test
    public void testEqualsFalse() {
        Comment comment2 = new Comment(2, "This is a comment.", "John Doe");
        assertNotEquals(comment, comment2, "Expected the comment objects to be not equal");
    }

    @Test
    public void testEqualsFalse2() {
        Comment comment2 = new Comment(1, "This is a comment2.", "John Doe");
        assertNotEquals(comment, comment2, "Expected the comment objects to be not equal");
    }

    @Test
    public void testEqualsFalse3() {
        Comment comment2 = new Comment(1, "This is a comment.", "John Doe2");
        assertNotEquals(comment, comment2, "Expected the comment objects to be not equal");
    }

}