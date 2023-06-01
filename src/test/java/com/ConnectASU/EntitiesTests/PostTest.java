package com.ConnectASU.EntitiesTests;

import com.ConnectASU.entities.Post;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertEquals(expectedId, actualId, "Expected: " + expectedId + " Actual: " + actualId);
    }

    @Test
    public void testGetContent() {
        String expectedContent = "Hello, world!";
        String actualContent = post.getContent();
        assertEquals(expectedContent, actualContent, "Expected: " + expectedContent + " Actual: " + actualContent);
    }

    @Test
    public void testGetAuthorName() {
        String expectedAuthorName = "John Doe";
        String actualAuthorName = post.getAuthorName();
        assertEquals(expectedAuthorName, actualAuthorName, "Expected: " + expectedAuthorName + " Actual: " + actualAuthorName);
    }

    @Test
    public void testEqualsTrue() {
        Post post2 = new Post(1, "Hello, world!", "John Doe");
        assertEquals(post, post2, "Expected: " + post + " Actual: " + post2);
    }

    @Test
    public void testEqualsFalse() {
        Post post2 = new Post(2, "This is a post.", "John Doe");
        assertNotEquals(post, post2, "Expected the post objects to be not equal");
    }

    @Test
    public void testEqualsFalse2() {
        Post post2 = new Post(1, "This is a different post.", "John Doe");
        assertNotEquals(post, post2, "Expected the post objects to be not equal");
    }

    @Test
    public void testEqualsFalse3() {
        Post post2 = new Post(1, "This is a post.", "Jane Smith");
        assertNotEquals(post, post2, "Expected the post objects to be not equal");
    }

}