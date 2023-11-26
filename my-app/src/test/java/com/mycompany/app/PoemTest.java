package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PoemTest {
    @Test
    public void test() {
        assertEquals(2, 1+1);
    }

    @Test
    public void testAddStringLineNoChecks() {
        Poem poem = new Poem();
        assert(poem.lines.size() == 0);
        poem.checkingRhyme = false;
        poem.checkingSyllables = false;
        poem.addLine("Some kind of line");
        assert(poem.lines.size()==1);
    }

    @Test
    public void testTypifyLine() {
        
    }
}
