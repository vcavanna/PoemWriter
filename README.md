# DS2020
What to Include in your README
<h1>Poetry Text Editor: A command line poetry writing assistant </h1>

<h2>Project Description</h2>
This project makes poetry writing easier by making rhyme suggestions, setting rhyme schemes, and saving poetry to text.

Features:
- A command-line based writing portal, especially 
- help          - Creates this menu. Invoked by putting '/ help' in the line.
- rhymesWith    - Finds something that rhymes with another word. Invoked by putting '/ rhymesWith' and then the word that you want to find a rhyme for.
                The program will give a variety of options for words that rhyme with the given word. You can choose which of those words you'd like to use by typing the chosen word,
                In which case that word will be added to the line, or you can type '/' to exit without picking an option.
- save          - Will save the program as a file so that you can edit it later with all poetry details such as rhyme scheme, etc. included. Doesn't save as a txt file, but instead as something easily loaded by PoemWriter. Invoked by typing '/ save'.     
- export        - Saves just the lines as a txt file. Does not keep poetry details. Invoked by typing '/ export'.
- load          - Loads a previously saved file. Invoked by typing '/ load' and then selecting the file.
- import        - Imports a poem and adopts its style to the poetry, so you can model a new poem off of its style of poetry.        
- rhymeScheme   - Sets the rhyme scheme for future editing.
- syllableCount - Sets the number of syllables for future editing.

Planned Improvements:
1. Refactoring. PoemWriter needs to be split into multiple files, although I'm also open to replacing the substitute Java datatypes that we made (preceded by "DS", i.e. DSArrayList) with the standard datatypes. I would follow the principles of the Clean Code book to better understand what would simplify this project.

Possible Next Steps:
1. Saving written poems to a cloud database instead of simply locally, implementing a rudimentary sign-in process to make writing poetry simpler.
2. Scraping a poetry database for poem samples and parsing them for rhyme schemas using this library that I've developed.

<h2>How to install and use this project</h2>
1. Ensure that you have Java installed already.
2. clone this project to your local folder.
3. If in VScode, you can simply select the poemwriter.java file and press the play button at the top right
4. If not in VScode:
  javac PoemWriter.java
  java PoemWriter
5. You can begin by typing "/ help" -> "/ commands" to learn which commands can power the project.
6. I'll provide examples of how you can use this project soon.

<h2>Credits</h2>
Collaborated with the class for almost all of this project, except for my work in the PoemWriter class. A very big thank you to Dr. Hochberg for teaching the class and introducing me to this.

Also, if you followed tutorials or referenced a certain material that might help the user to build that particular project, include links to those here as well.

This is just a way to show your appreciation and also to help others get a first hand copy of the project.
