package com.ConnectASU.entitiesTests;

import com.ConnectASU.entities.Comment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testGetAuthorName() {
        String expectedAuthorName = "John Doe";
        String actualAuthorName = comment.getAuthorName();
        assertEquals(expectedAuthorName, actualAuthorName);
    }
}