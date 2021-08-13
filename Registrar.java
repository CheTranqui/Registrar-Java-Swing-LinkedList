// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Create a Student class, store and present it as a sortable Linked List.
// Date: 6/29/2021  /  Updated: 7/21/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java

import java.util.*;

public class Registrar{
    public LinkedList<Student> students = new LinkedList<>();

    public Registrar(){
        LoadStudentsIntoList();
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

    public Registrar getRegistrar(){
        return this;
    }
}
