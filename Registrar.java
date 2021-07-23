// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Create a Student class, store and present it as a sortable Linked List.
// Date: 6/29/2021  /  Updated: 7/21/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java

import java.util.*;

public class Registrar{
    private Roster402_v2 roster;
    private GUI gui;
    public LinkedList<Student> students = new LinkedList<>();

    public Registrar(GUI g){
        setGUI(g);
        LoadStudentsIntoList();
    }

    public String getLabelName(int labelGridY){
        // set at label creation, used later to update label upon sorting
        return "label" + labelGridY;
    }

    public String getStudentText(Student s){
        // sets text of label to keep presentation uniform
        return "ID: " + s.getId() + " Name: " + s.getFirstName() + " " + s.getLastName();
    }

    // given an id and a list, return the relevant student
    public Student getStudent(int id, LinkedList<Student> list){
        Student currentStudent = list.get(0);
        for (Student s : list){
            if (s.getId() == id){
                currentStudent = s;
                break;
            }
        }
        return currentStudent;
    }

    // removes student from either the roster or the students linkedlist
    // adds them to the opposite linkedlist
    public void swapStudent(LinkedList<Student> list, Student currentStudent){
        for (Student s : list) {
            if (s.getId() == currentStudent.getId()) {
                if (list == students) {
                    roster.roster.add(s);
                    this.students.remove(s);
                    break;
                } else {
                    this.students.add(s);
                    roster.roster.remove(s);
                    break;
                }
            }
        }
    }

    // sorts list provided by student attribute
    public void sortBy(LinkedList<Student> list, String studentAttribute){
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
        if (list == students) {
            gui.updateStudentLabels(students);
        }
        else {
            gui.updateStudentLabels(roster.roster);
        }
    }
    // supporting method to assist with sorting
    private void move(LinkedList<Student> list, int studentIndex) {
// swaps the two entries in Students and returns the result
        Student temp = list.get(studentIndex);
        list.set(studentIndex, list.get(studentIndex + 1));
        list.set(studentIndex + 1, temp);
    }

    // initial load of data into students linkedlist
    private void LoadStudentsIntoList(){
        students.add(new Student("Andre", "Calderon",25));
        students.add(new Student("Micah", "Chapagai", 63));
        students.add(new Student("Chris", "Cutler", 22));
        students.add(new Student("James", "Donahue", 63));
        students.add(new Student("Ryan", "Lambert", 82));
        students.add(new Student("Stephen", "Mays", 49));
        students.add(new Student("Kasey", "Moore", 12));
        students.add(new Student("Timothy", "Moore", 74));
        students.add(new Student("Chaz", "Peterson", 23));
        students.add(new Student("Katrina", "Schaeffer", 31));
        students.add(new Student("Silvia", "Streeter", 13));
    }

    public void setRoster(Roster402_v2 r){
        roster = r;
    }
    public void setGUI(GUI g){
        gui = g;
    }
    public Registrar getRegistrar(){
        return this;
    }
}
