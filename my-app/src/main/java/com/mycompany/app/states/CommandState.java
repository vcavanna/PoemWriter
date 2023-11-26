package com.mycompany.app.states;

import com.mycompany.app.PoemMaster;

public class CommandState extends State {
    public CommandState(PoemMaster master) {
        super(master);
    }

    @Override
    public void interact() {

    }

    // Takes a given line with a command and switches Master to the next state
    public void getCommandState(String s) {
        // TODO: Put PoemMaster typifyCommand logic in here, newState = a state
        Command command = Command.valueOf(findCommandText(s));
        State newState;
        newState = extracted(command);

        master.changeState(newState);
    }

    private State extracted(Command command) {
        switch(command) {
            case help:
                return new HelpState(master);
            case rhymesWith:
                return new RhymesWithState(master);
            case save:
                return new SaveState(master);
            case export:
                return new ExportState(master);
            case load:
                return new LoadState(master);
            case importStyle:
                return new ImportStyleState(master);
            case rhymeScheme:
                return new RhymeSchemeState(master);
            case syllableCount:
                return new SyllableCountState(master);
            case failedCommand:
                return new FailedCommandState(master);
            default:
                return new FailedCommandState(master);
        }
    }

    private enum Command {
        help, rhymesWith, save, export, load, importStyle, rhymeScheme, syllableCount, failedCommand
    }

    private static String findCommandText(String lineStr) {
        String[] wordsInInput = lineStr.split(" ");
        boolean getNext = false;
        for (String word : wordsInInput) {
            if (getNext) {
                return word;
            } else if(word.equals("/")) {
                getNext = true;
            }
        }
        if (getNext == false) {
            return "failedCommand";
        }
        return null;
    }
}
