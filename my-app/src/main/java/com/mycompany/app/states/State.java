package com.mycompany.app.states;

import com.mycompany.app.Poem;
import com.mycompany.app.PoemMaster;

public abstract class State {
    PoemMaster master;

    State(PoemMaster master) {
        this.master = master;
    }

    public abstract void interact();
}