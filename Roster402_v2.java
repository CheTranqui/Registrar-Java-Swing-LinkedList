// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Allow user to add a particular number of students to a roster.
//      Allow them to view the roster and close the program at will.
// Date: 7/21/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java

//  Allow the user to control the number of students added to the roster.
//  Ask if the user would like to see their new roster to confirm additions.
//  If yes, then display contents of the file, if no, end the program.

import java.util.LinkedList;

public class Roster402_v2 {
    private String rosterName;
    public LinkedList<Student> roster = new LinkedList<>();

    public static void main(String[] args) {
        new GUI();
    }

    public Roster402_v2(GUI g){
        setRosterName("Roster: CIS 402");
    }

    public String getRosterName(){
        return rosterName;
    }

    private void setRosterName(String nameOfRoster){
        this.rosterName = nameOfRoster;
    }

    public String getRosterInstructions(){
        return "<html><div style='text-align: center'>" +
                "Click on a student<br>" +
                "to assign/unassign<br>" +
                "to/from the roster<br>" +
                "or click below to<br>" +
                "add a precise number<br>" +
                "of students instead.</div></html>";
    }
}
