package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PoetTest {
    @Test
    public void testGetInstance() {
        // Arrange
        // Act
        Poet poet = Poet.getInstance();

        // Assert
        assertNotEquals(poet, null);
    }
}
