package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LineTest {
    @Test
    public void test() {
        assertEquals(2, 1+1);
    }

    @Test
    public void testLineConstructorWithString() {
        // Arrange
        Line expectedLine = new Line();
        String text = "s";
        expectedLine.text = text;

        // Act
        Line actualLine = new Line(text);

        assertEquals(expectedLine.text, actualLine.text);
    }
}
