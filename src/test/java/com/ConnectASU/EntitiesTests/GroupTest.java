package com.ConnectASU.EntitiesTests;

import com.ConnectASU.entities.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetName() {
        String expectedName = "Group A";
        String actualName = group.getName();
        assertEquals(expectedName, actualName);
    }
}