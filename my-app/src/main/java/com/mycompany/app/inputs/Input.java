package com.mycompany.app.inputs;

import java.util.Scanner;

public abstract class Input {
    Scanner scan;
    Input(){};
    abstract public String getInput();
}
