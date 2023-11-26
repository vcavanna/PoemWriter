package com.mycompany.app.states;

import com.mycompany.app.PoemMaster;

public class FailedCommandState extends State {
    public FailedCommandState(PoemMaster master) {
        super(master);
    }

    @Override
    public void interact() {
        master.print("The above line is not a command.");
        master.print("Try typing '/' followed by a command.");
        master.print("'/ help' is a great place to start.");
        master.changeState(new EditorState(master));
    }
}
