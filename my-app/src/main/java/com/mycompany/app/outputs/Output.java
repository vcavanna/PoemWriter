package com.mycompany.app.outputs;

import java.io.PrintStream;

public class Output {
    PrintStream out;
    public Output(PrintStream out){
        this.out = out;
    };
    public void println(String str){
        out.println(str);
    };
    public void print(String str){
        out.print(str);
    };
}
