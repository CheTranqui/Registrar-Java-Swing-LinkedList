// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Allow user to add a particular number of students to a roster.
//      Allow them to view the roster and close the program at will.
// Date: 7/19/2021
// Company: Bellevue University
// File name: Registrar.java / Student.java / Roster402_v2.java

//  Allow the user to control the number of students added to the roster.
//  Ask if the user would like to see their new roster to confirm additions.
//  If yes, then display contents of the file, if no, end the program.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Roster402_v2 {
    private Registrar reg;
    private GUI gui;

    public LinkedList<Student> roster = new LinkedList<>();

    public Roster402_v2(GUI g){
        setGUI(g);
        gui.setRoster(this);
        setRegistrar(gui.reg);

        gui.createTitleLabel("Roster: CIS 402",2,0,true);
        // populating roster with labels
        gui.createStudentLabels(roster,2,1);

        // provide instructions on how to add/remove students to/from roster
        String rosterInstructions = "<html><div style='text-align: center'>" +
            "Click on a student<br>" +
            "to assign/unassign<br>" +
            "to/from the roster<br>" +
            "or click below to<br>" +
            "add a precise number<br>" +
            "of students instead.</div></html>";

        // add instructions to label and place it
        gui.createRosterInstructionsLabel(rosterInstructions);

        gui.createAddToRosterButton();

        gui.createPrintRosterButton();
    }


    public Roster402_v2 getRoster(){
        return this;
    }
    public void setRegistrar(Registrar r){
        reg = r;
    }
    public void setGUI(GUI g){
        gui = g;
    }
}
