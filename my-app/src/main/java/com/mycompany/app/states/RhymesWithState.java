package com.mycompany.app.states;

import com.mycompany.app.LineType;
import com.mycompany.app.PoemMaster;
import org.apache.commons.lang3.ArrayUtils;

public class RhymesWithState extends State {

    
    public RhymesWithState(PoemMaster master) {
        super(master);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void interact() {
        String line = master.getHistory(0);
        String[] lineArr = line.split(" ");

        String[] rhymeSuggestions = master.getRhymeSuggestions();
        
        master.print("Any of these words are valid options for the line.\n");
        for (int i = 0; i < rhymeSuggestions.length; i++) {
            String output = String.format("%d: %s\n", i, rhymeSuggestions[i]);
            master.print(output);
        }
        master.print("Returning to the editor...\n");

        master.changeState(new EditorState(master));
    }
}