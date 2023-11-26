package com.mycompany.app.states;

import com.mycompany.app.LineType;
import com.mycompany.app.PoemMaster;

public class EditorState extends State {

    
    public EditorState(PoemMaster master) {
        super(master);
        //TODO Auto-generated constructor stub
    }

    // Takes a line, verifies it, and directs the appropriate state.
    @Override
    public void interact() {
        String line = master.getInput();
        
        State newState = master.processLineToState(line);
        master.changeState(newState);
    }
}