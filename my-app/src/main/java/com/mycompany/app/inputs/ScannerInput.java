package com.mycompany.app.inputs;

import java.util.Scanner;

public class ScannerInput extends Input {
    public ScannerInput() {
        this.scan = new Scanner(System.in);
    }

    public ScannerInput(Scanner scan) {
        this.scan = scan;
    }

    public String getInput(){
        return scan.nextLine();
    }
}
