package com.mycompany.app.states;

import com.mycompany.app.PoemMaster;

public class GoodLineState extends State {

    
    public GoodLineState(PoemMaster master) {
        super(master);
        //TODO Auto-generated constructor stub
    }

    // Takes an input to either start a new poem or load an old one.
    @Override
    public void interact() {
        master.print("Hi there. Type a line of poetry to begin. Type '/ help' to learn how to work this program.");
        String rv = master.getInput();
        if (rv.contains("/ help")) {
            master.changeState(new FailedCommandState(master));
        }
    }
}