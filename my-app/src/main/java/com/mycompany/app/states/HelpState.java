package com.mycompany.app.states;

import com.mycompany.app.PoemMaster;

public class HelpState extends State {
    public HelpState(PoemMaster master) {
        super(master);
        //TODO Auto-generated constructor stub
    }

    static String[] welcome = {
        "Welcome to the help menu. Type any of the following to access that page.\n",
        "0 - quit help menu\n",
        "1 - general\n",
        "2 - commands\n",
    };

    static String[] general = {
        "This program helps you write poetry according to a rhyme scheme you can set yourself.\n",
        "To begin, you can type any line you like, or start by setting the rhymeScheme and syllable count.\n",
        "     You can do this by typing '/ rhymeScheme' or '/ syllableCount'\n",
        "Alternatively, try typing '/ importStyle' to paste a poem and use its rhyme settings.\n"
    };

    static String[] commands = {
        "Here are all of the commands.\n",
        "help          - Creates this menu. Invoked by putting '/ help' in the line.\n",
        "rhymesWith    - Finds something that rhymes with another word. Invoked by putting '/ rhymesWith' and then the word that you want to find a rhyme for.\n",
        "                The program will give a variety of options for words that rhyme with the given word. You can choose which of those words you'd like to use by typing the chosen word,\n",
        "                In which case that word will be added to the line, or you can type '/' to exit without picking an option.\n",
        "save          - Will save the program as a file so that you can edit it later with all poetry details such as rhyme scheme, etc. included.\n",
        "                Doesn't save as a txt file, but instead as something easily loaded by PoemWriter. Invoked by typing '/ save'.\n",
        "export        - Saves just the lines as a txt file. Does not keep poetry details. Invoked by typing '/ export'.\n",
        "load          - Loads a previously saved file. Invoked by typing '/ load' and then selecting the file.\n",
        "importStyle   - Imports a poem and adopts its style to the poetry, so you can model a new poem off of its style of poetry.\n",
        "rhymeScheme   - Sets the rhyme scheme for future editing.\n",
        "syllableCount - Sets the number of syllables for future editing.\n",
        "Press any key to go back to the help menu.\n"
    };

    @Override
    public void interact() {
        printAll(welcome);
        while (true) {
            String selection = master.getInput("Type 0, 1, or 2 to select\n");
            if (selection.equals("0")) {
                break;
            } else if (selection.equals("1")) {
                printAll(general);
            } else if (selection.equals("2")) {
                printAll(commands);
            } else {
                master.print("Not an output. Try again.");
            }
        }

        // On break, return to editor
        master.changeState(new EditorState(master));
    }

    private void printAll(String[] strArr) {
        for (String string : strArr) {
            master.print(string);
        }
    }
}
