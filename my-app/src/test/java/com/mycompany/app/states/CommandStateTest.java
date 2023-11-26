package com.mycompany.app.states;

import org.junit.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;

import com.mycompany.app.PoemMaster;

import static org.junit.Assert.*;



public class CommandStateTest {

    @Test
    public void testMock(){
        PoemMaster mockedMaster = Mockito.mock(PoemMaster.class);

        Mockito.when(mockedMaster.getInput()).thenReturn("My input");
        assertEquals(mockedMaster.getInput(), "My input");
    }
    
    @Test
    public void testGetCommandState(){
        String[] listCommands = {"/ help", "/ rhymesWith", "/ save", "/ export", "/ load", "/ importStyle", "/ rhymeScheme", "/ syllableCount", "/ failedCommand"};
        String[] listExpectedClassName = {"HelpState", "RhymesWithState", "SaveState", "ExportState", "LoadState", "ImportStyleState", "RhymeSchemeState", "SyllableCountState", "FailedCommandState"};
        String s;
        String expectedClassName;
        for (int i = 0; i < listCommands.length; i++) {
            s = listCommands[i];
            PoemMaster master = new PoemMaster();
            expectedClassName = listExpectedClassName[i];
            CommandState commandState = new CommandState(master);

            // Act
            commandState.getCommandState(s);

            // Assert
            State someState = master.getState();
            Class<? extends State> ActualClass = someState.getClass();
            String actualClassName = ActualClass.getSimpleName();
            assertEquals(expectedClassName, actualClassName);
        }
    }
}
