package com.ConnectASU.entitiesTests;

import com.ConnectASU.entities.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {

    private static Post post;

    @BeforeAll
    public static void setUp() {
        int id = 1;
        String content = "Hello, world!";
        String authorName = "John Doe";
        post = new Post(id, content, authorName);
    }

    @Test
    public void testGetId() {
        int expectedId = 1;
        int actualId = post.getId();
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetContent() {
        String expectedContent = "Hello, world!";
        String actualContent = post.getContent();
        assertEquals(expectedContent, actualContent);
    }

    @Test
    public void testGetAuthorName() {
        String expectedAuthorName = "John Doe";
        String actualAuthorName = post.getAuthorName();
        assertEquals(expectedAuthorName, actualAuthorName);
    }
}