package com.mycompany.app.inputs;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

public class scannerInputTest {
    @Test
    public void TestGetInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("My string".getBytes());
        Scanner scan = new Scanner(in);
        ScannerInput input = new ScannerInput(scan);
        String response = input.getInput();
        assertEquals(response, "My string");
    }
}
