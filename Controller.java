// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Allow user to add a particular number of students to a roster.
//      Allow them to view the roster and close the program at will.
// Date: 7/21/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java / Controller.java

//  Allow the user to control the number of students added to the roster.
//  Ask if the user would like to see their new roster to confirm additions.
//  If yes, then display contents of the file, if no, end the program.

import java.util.LinkedList;

public class Controller {
    private static Registrar myRegistrar;
    private static Roster402_v2 myRoster;
    private static GUI myGUI;

    public static void main(String[] args) {
        setRoster(new Roster402_v2("CIS 402"));
        setRegistrar(new Registrar());
        setGUI(new GUI(myRoster, myRegistrar));
    }

    public static String getStudentText(Student s){
        // sets text of label to keep presentation uniform
        return "ID: " + s.getId() + " Name: " + s.getFirstName() + " " + s.getLastName();
    }

    // given an id and a list, return the relevant student
    public static Student getStudent(int id, LinkedList<Student> list){
        Student currentStudent = list.get(0);
        for (Student s : list){
            if (s.getId() == id){
                currentStudent = s;
                break;
            }
        }
        return currentStudent;
    }

    public static String getLabelName(int labelGridY){
        // set at label creation, used later to update label upon sorting
        return "label" + labelGridY;
    }

    // removes student from either the roster or the students linkedlist
    // adds them to the opposite linkedlist
    public static void swapStudent(LinkedList<Student> list, Student currentStudent){
        for (Student s : list) {
            if (s.getId() == currentStudent.getId()) {
                if (list == myRegistrar.students) {
                    myRoster.roster.add(s);
                    myRegistrar.students.remove(s);
                    break;
                } else {
                    myRegistrar.students.add(s);
                    myRoster.roster.remove(s);
                    break;
                }
            }
        }
    }

    // sorts list provided by student attribute
    public static void sortBy(LinkedList<Student> list, String studentAttribute){
        int totalIndexes = list.size()-1;
        for (int i = 0; i < totalIndexes; i++){
            for (int j = 0; j < totalIndexes; j++){
                switch (studentAttribute) {
// each case gets a slightly different comparison
                    case "Student ID":
                        if (list.get(j).getId() > list.get(j + 1).getId()) {
// if a move must happen, it is done in the 'move' method to make the switch more legible
                            move(list, j);
                        }
                        break;
                    case "First Name":
                        if (list.get(j).getFirstName().compareTo(list.get(j+1).getFirstName()) > 0) {
                            move(list, j);
                        }
                        break;
                    case "Last Name":
                        if (list.get(j).getLastName().compareTo(list.get(j+1).getLastName()) > 0) {
                            move(list, j);
                        }
                        break;
                }
            }
        }
        if (list == myRegistrar.students) {
            myGUI.updateStudentLabels(myRegistrar.students);
        }
        else {
            myGUI.updateStudentLabels(myRoster.roster);
        }
    }
    // supporting method to assist with sorting
    private static void move(LinkedList<Student> list, int studentIndex) {
// swaps the two entries in Students and returns the result
        Student temp = list.get(studentIndex);
        list.set(studentIndex, list.get(studentIndex + 1));
        list.set(studentIndex + 1, temp);
    }

    private static void setRegistrar(Registrar r){
        myRegistrar = r;
    }

    public Registrar getRegistrar(){
        return myRegistrar;
    }

    private static void setRoster(Roster402_v2 r){
        myRoster = r;
    }

    public Roster402_v2 getRoster(){
        return myRoster;
    }

    private static void setGUI(GUI g){
        myGUI = g;
    }
}
