package com.mycompany.app;

import com.mycompany.app.inputs.Input;
import com.mycompany.app.outputs.Output;
import com.mycompany.app.states.*;

import java.util.ArrayList;

import com.mycompany.app.Line;

public class PoemMaster {
    private State state;
    private Input input;
    private Output output;
    private Poem poem;
    private String cursorStr = "";
    private ArrayList<String> history;

    public PoemMaster() {}

    public PoemMaster(Output output) {
        this.output = output;
        this.history = new ArrayList<String>();
    }

    public PoemMaster(Output output, Input input) {
        this.output = output;
        this.input = input;
    }

    public PoemMaster(Input input) {
        this.input = input;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public String getInput() {
        String rv = this.input.getInput();
        history.add(rv);
        return rv;
    }

    public String getInput(String prompt) {
        this.print(prompt);
        return getInput();
    }

    public String getCursorStr() {
        return cursorStr;
    }

    public void setCursorStr(String s) {
        cursorStr = s;
    }

    public void setPoem(Poem poem) {
        this.poem = poem;
    }

    public void print(String valToPrint) {
        this.output.print(valToPrint);
    }

    public State processLineToState(String lineStr) {
        Line line = new Line(lineStr);
        if (line.text.contains("/")) {
            return new CommandState(this);
        } else if (poem.failsRhymeCheck(line)) {
            return new FailedRhymeState(this);
        } else if (poem.failsSyllableCount(line)) {
            return new FailedSyllableState(this);
        } else {
            poem.addLine(line);
            return new EditorState(this);
        }
    }

    /**
     * @param i The index you want to get from history, starting from most recent
     * @return The string of input from history
     */
    public String getHistory(int i) {
        if (history.size() >= i) {
            return null;
        } else {
            return history.get(history.size() - i);
        }
    }

    /**
     * @return The list of words that fit the poem's rhymescheme needs, or an empty array.
     */
    public String[] getRhymeSuggestions() {
        return null;
    }
}
