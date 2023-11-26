package com.mycompany.app.outputs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.mycompany.app.outputs.Output;

public class OutputTest {
    @Test
    public void testPrint() {
        String valToPrint = "Abc";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        Output printer = new Output(printStream);
        printer.print(valToPrint);
        String output = new String(baos.toByteArray());
        assertTrue(valToPrint.equals(output));
    }

    @Test
    public void testPrintln() {
        String valToPrint = "Abc";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        Output printer = new Output(printStream);
        printer.println(valToPrint);
        String output = new String(baos.toByteArray());
        assertEquals(valToPrint.concat(System.lineSeparator()), output);
    }
}
