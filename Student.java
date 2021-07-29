// Name:  Chaz Peterson
// Purpose:  Assignment 6.1 - Create a Student class, store and present it as a sortable Linked List.
// Date: 6/29/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java



public class Student {
    // class has only 3 properties
    private String firstName;
    private String lastName;
    private int Id;

    // constructor requires definition of all three properties
    // Id is NOT auto-incremented, but a pre-determined value declared upon instantiation
    public Student(String firstName,String lastName,int Id){
        setFirstName(firstName);
        setLastName(lastName);
        setId(Id);
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return Id;
    }

    private void setId(int id) {
        Id = id;
    }
}
