// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Create a Student class, store and present it as a sortable Linked List.
// Date: 6/29/2021  /  Updated: 7/21/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java

import java.util.*;

public class Registrar{
    public LinkedList<Student> students = new LinkedList<>();

    public Registrar(LinkedList<Student> studentList){
        students = studentList;
    }

    public Registrar getRegistrar(){
        return this;
    }
}
