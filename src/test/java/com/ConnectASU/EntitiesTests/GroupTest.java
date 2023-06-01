package com.ConnectASU.EntitiesTests;

import com.ConnectASU.entities.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GroupTest {

    private static Group group;

    @BeforeAll
    public static void setUp() {
        int id = 1;
        String name = "Group A";
        group = new Group(id, name);
    }

    @Test
    public void testGetId() {
        int expectedId = 1;
        int actualId = group.getId();
        assertEquals(expectedId, actualId, "Expected: " + expectedId + " Actual: " + actualId);
    }

    @Test
    public void testGetName() {
        String expectedName = "Group A";
        String actualName = group.getName();
        assertEquals(expectedName, actualName, "Expected: " + expectedName + " Actual: " + actualName);
    }

    @Test
    public void testEqualsTrue() {
        Group group2 = new Group(1, "Group A");
        assertEquals(group, group2, "Expected: " + group + " Actual: " + group2);
    }

    @Test
    public void testEqualsFalse() {
        Group group2 = new Group(2, "Group A");
        assertNotEquals(group, group2, "Expected the comment objects to be not equal");
    }

    @Test
    public void testEqualsFalse2() {
        Group group2 = new Group(1, "Group B");
        assertNotEquals(group, group2, "Expected the comment objects to be not equal");
    }
}