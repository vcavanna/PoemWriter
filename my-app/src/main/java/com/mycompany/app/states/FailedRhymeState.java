package com.mycompany.app.states;

import com.mycompany.app.LineType;
import com.mycompany.app.PoemMaster;

public class FailedRhymeState extends State {

    
    public FailedRhymeState(PoemMaster master) {
        super(master);
        //TODO Auto-generated constructor stub
    }

    // Takes a line, verifies it, and directs the appropriate state.
    @Override
    public void interact() {
        // master.lineToString(-1);
        master.print("The above line doesn't rhyme.");
    }
}