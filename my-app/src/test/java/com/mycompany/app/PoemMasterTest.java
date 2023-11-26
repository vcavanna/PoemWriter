package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.mycompany.app.inputs.ScannerInput;
import com.mycompany.app.outputs.Output;
import com.mycompany.app.states.*;
import com.mycompany.app.states.EditorState;
import com.mycompany.app.Line;

public class PoemMasterTest {
    @Test
    public void testPrint() {
        // Arrange
        String valToPrint = "Some value";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        Output printer = new Output(printStream);
        PoemMaster master = new PoemMaster(printer);

        // Act
        master.print(valToPrint);

        // Assert
        String output = new String(baos.toByteArray());
        assertEquals(valToPrint, output);
    }

    @Test
    public void testGetInputString() {
        ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
        Scanner scan = new Scanner(in);
        ScannerInput input = new ScannerInput(scan);
        PoemMaster master = new PoemMaster(input);
        String response = master.getInput();
        assertEquals(response, "My string");
    }

    @Test
    public void testGetInputStringWithPrompt() {
        // Arrange
        ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
        Scanner scan = new Scanner(in);
        ScannerInput input = new ScannerInput(scan);
        String valToPrint = "Give me my string back";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        Output printer = new Output(printStream);
        PoemMaster master = new PoemMaster(printer, input);

        // Act
        String response = master.getInput(valToPrint);

        // Assert
        assertEquals(response, "My string");
        String output = new String(baos.toByteArray());
        assertEquals(valToPrint, output);
    }

    @Test
    public void testProcessLineToState(){
        // Arrange
        PoemMaster master = new PoemMaster();
        Poem poem = Mockito.mock(Poem.class);
        master.setPoem(poem);

        // Arrange answer arrays
        Boolean[] RhymeCheckAnswers = {false,true,false,false};
        Boolean[] SyllableCheckAnswers = {false,false,true,false};
        String[] expectedLines = {
            "A valid line of poetry",
            "A line that fails the rhyme check",
            "A line that fails the syllable check",
            "A line that triggers the / help command"
        };
        String[] expectedClassNames = {
            "EditorState",
            "FailedRhymeState",
            "FailedSyllableState",
            "CommandState"
        };

        // Test each use case
        for (int i = 0; i < expectedLines.length; i++) {
            Mockito.when(poem.failsRhymeCheck(any(Line.class))).thenReturn(RhymeCheckAnswers[i]);
            Mockito.when(poem.failsSyllableCount(any(Line.class))).thenReturn(SyllableCheckAnswers[i]);
            String expectedLine = expectedLines[i];
            ArgumentCaptor<Line> actual = ArgumentCaptor.forClass(Line.class);
            doNothing().when(poem).addLine(actual.capture());
            
            // Act
            State state = master.processLineToState(expectedLine);

            // Assert
            if (i == 0) { // 0 is a actual line of poetry
                assertEquals(expectedLine, actual.getValue().text);
            }
            assertEquals(state.getClass().getSimpleName(), expectedClassNames[i]);
        }
    }
}
