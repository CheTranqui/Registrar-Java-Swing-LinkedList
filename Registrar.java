// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Create a Student class, store and present it as a sortable Linked List.
// Date: 6/29/2021  /  Updated: 7/19/2021
// Company: Bellevue University
// File name: Registrar.java / Student.java / myRoster.java


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class Registrar implements ActionListener {

    //class variables to be used in multiple places
    public LinkedList<Student> students = new LinkedList<>();
    public static Font verdanaPlain = new Font("Verdana", Font.PLAIN, 12);
    public static Font verdanaBold = new Font("Verdana", Font.BOLD, 12);
    public JPanel panel = new JPanel();
    public static Color labelFontColor = new Color(250,230,230);
    public static Color panelBackgroundColor = new Color(0,30,50);
    public static Color labelHighlightColor = new Color(30, 70,90);
    private Roster402_v2 myRoster;

    public Registrar(){
        LoadStudentsIntoList();
        // creating a GUI for the roster display:
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(750,400));
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(panelBackgroundColor);
        // using GridBagLayout to organize rows/columns
        GridBagConstraints c = new GridBagConstraints();

    // intro label - gives a title to the dropdown - top left
        JLabel sortByLabel = new JLabel("Sort By:");
        sortByLabel.setForeground(labelFontColor);
        sortByLabel.setFont(verdanaBold);
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(sortByLabel, c);

        // intro label for students' column - top right
        JLabel studentsLabel = new JLabel("Available Students:");
        studentsLabel.setForeground(labelFontColor);
        studentsLabel.setFont(verdanaBold);
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(studentsLabel, c);

// provide dropdown with options to sort by - under top left by 1
        String[] choicesForDropDown = {"Student ID", "First Name", "Last Name"};
        JComboBox<String> sortByDropDown = new JComboBox<String>(choicesForDropDown);
        sortByDropDown.setForeground(labelFontColor);
        sortByDropDown.setBackground(labelHighlightColor);
        sortByDropDown.setFont(verdanaBold);
        c.weightx = 0.0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(5,0,0,0); // spacing between labels
        // set starting position to Last Name (index 2 in the array)
        // since starting sort is by last name, this makes default selection and data presentation match
        sortByDropDown.setSelectedIndex(2);
        // when changed, will have to sort by choice
        sortByDropDown.addActionListener(this);
        panel.add(sortByDropDown, c);

        createStudentLabels();

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Student Roster");
        frame.pack();
        frame.setVisible(true);
    }

    public void createStudentLabels(){
        // create labels to display current sorted roster
        int labelGridY = 0;  // represents the GridY position of the resulting label
        // all labels will be in GridX position 1.
        GridBagConstraints c = new GridBagConstraints();
        for (Student s : students){
            labelGridY++;
            createOutputLabel(s, labelGridY, c);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // cast source to JComboBox, sort via the text of the choice
        JComboBox cb = (JComboBox)e.getSource();
        sortBy(students, (String) cb.getSelectedItem());
        sortBy(myRoster.roster, (String) cb.getSelectedItem());
    }

    public String getLabelName(int labelGridY){
        // set at label creation, used later to update label upon sorting
        return "label" + labelGridY;
    }

    public String getStudentText(Student s){
        // sets text of label to keep presentation uniform
        return "ID: " + s.getId() + " Name: " + s.getFirstName() + " " + s.getLastName();
    }

    private void createOutputLabel(Student s, int labelGridY, GridBagConstraints c){
        String studentText = getStudentText(s);
        JLabel label = new JLabel(studentText);
        // dark mode!
        label.setBackground(panelBackgroundColor);
        label.setForeground(labelFontColor);
        label.setOpaque(true);

        String labelName = getLabelName(labelGridY);  // allows us to access label and update it later
        label.setName(labelName);
        label.setFont(verdanaPlain);

        // when label is clicked, swap that student from one linkedlist to the other
        // and update the two GUI lists / show end result as sorted how user has chosen
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = Integer.parseInt((label.getText().split(" "))[1]);
                Student myStudent = getStudent(id, students);
                removeLabels("");
                swapStudent(students, myStudent);
                createStudentLabels();
                myRoster.recreateRosterLabels();
                String sortingAttribute = (String)((JComboBox) panel.getComponent(2)).getSelectedItem();
                sortBy(students, sortingAttribute);
                sortBy(myRoster.roster, sortingAttribute);
            }
            // highlight label on mouseover
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(labelHighlightColor);
            }
            // remove highlight on mouse exit
            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(panelBackgroundColor);
            }
        });

        c.insets = new Insets(5,15,5,0); // spacing between labels
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = labelGridY;  // used in label name, but also to set current position on grid
        panel.add(label, c);
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
                    myRoster.roster.add(s);
                    this.students.remove(s);
                    break;
                } else {
                    this.students.add(s);
                    myRoster.roster.remove(s);
                    break;
                }
            }
        }
    }

    // removes any (and only!) student labels from the GUI
    public void removeLabels(String type){
        Component[] p = panel.getComponents();  // array containing all panel components
        for (int l = p.length-1; l > 0; l--){
            p = panel.getComponents();  // array containing all panel components
            for (int m = p.length-1; m > 0; m--) {
                if (p[m].getName() != null && p[m].getName().equals(type + "label" + l)) {  // locate this label in particular
                    panel.remove(m);
                }
            }
        }
        panel.revalidate();
        panel.repaint();
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
            updateStudentLabels();
        }
        else {
            myRoster.updateRosterLabels();
        }
    }
    // supporting method to assist with sorting
    private void move(LinkedList<Student> list, int studentIndex) {
// swaps the two entries in Students and returns the result
        Student temp = list.get(studentIndex);
        list.set(studentIndex, list.get(studentIndex + 1));
        list.set(studentIndex + 1, temp);
    }

    // updates label info after sorting, rather than removing and recreating labels
    public void updateStudentLabels(){
        // iterates through students in same manner as original creation
        int labelGridY = 0;  // represents the GridY position of the resulting label
        for (Student s : students){
            labelGridY++;
            updateLabel(s, getLabelName(labelGridY));
        }
    }
    // supports "updateStudentLabels"
    private void updateLabel(Student s, String labelName){
        Component[] p = panel.getComponents();  // array containing all panel components
        for (int l = 0; l < p.length; l++){
            if (p[l].getName() != null && p[l].getName().equals(labelName)){  // locate this label in particular
                ((JLabel) p[l]).setText(getStudentText(s));  // update its text
            }
        }
    }

    // initial load of data into students linkedlist
    private void LoadStudentsIntoList(){
        students.add(new Student("Jonathan", "Calderon",25));
        students.add(new Student("Ram", "Chapagai", 63));
        students.add(new Student("Thane", "Cutler", 22));
        students.add(new Student("Lucas", "Donahue", 63));
        students.add(new Student("Andrew", "Lambert", 82));
        students.add(new Student("Anthony", "Mays", 49));
        students.add(new Student("David", "Moore", 12));
        students.add(new Student("ShiQuise", "Moore", 74));
        students.add(new Student("Chaz", "Peterson", 23));
        students.add(new Student("Amy", "Schaeffer", 31));
        students.add(new Student("Troy", "Streeter", 13));
    }

    public void setRoster(Roster402_v2 r){
        myRoster = r;
    }
}
