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
    public Registrar reg1 = new Registrar();
    public LinkedList<Student> roster = new LinkedList<>();

    public static void main(String[] args) {
        new Roster402_v2();
    }

    public Roster402_v2(){
        reg1.setRoster(this);
        GridBagConstraints c = new GridBagConstraints();
        // making label for new column: Roster
        JLabel rosterLabel = new JLabel("Roster: CIS 402");
        rosterLabel.setForeground(Registrar.labelFontColor);
        rosterLabel.setFont(Registrar.verdanaBold);
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 0;
        reg1.panel.add(rosterLabel, c);

        // populating roster with labels
        createRosterLabels();

        // provide instructions on how to add/remove students to/from roster
        String rosterInstructions = "<html><div style='text-align: center'>" +
            "Click on a student" + "<br>" +
            "to assign/unassign" + "<br>" +
            "to/from the roster"  + "<br>" +
            "or click below to"  + "<br>" +
            "add a precise number"  + "<br>" +
            "of students instead." + "</div></html>";

        // add instructions to label and place it
        JLabel rosterInstructionLabel = new JLabel(rosterInstructions);
        rosterInstructionLabel.setForeground(Registrar.labelFontColor);
        rosterInstructionLabel.setFont(Registrar.verdanaPlain);
        rosterInstructionLabel.setForeground(new Color(220, 200, 200));
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 5;
        c.gridx = 0;
        c.gridy = 2;
        reg1.panel.add(rosterInstructionLabel, c);

        // create button that allows user to add a particular number of students
        JButton addToRoster = new JButton("Add to Roster");
        addToRoster.setBackground(Registrar.labelHighlightColor);
        addToRoster.setForeground(Registrar.labelFontColor);
        addToRoster.addActionListener(new ActionListener() {
            // when clicked, open input dialog to capture quantity to move
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validInput = false;
                do {
                    String input = JOptionPane.showInputDialog(null, "How many students would you" +
                            " like to add to the roster? (0 - " + reg1.students.size() + ")");
                    // allow for cancel/x/no input
                    if (input == null){
                        validInput = true;
                    }
                    // if input, make sure it's a proper amount and an actual integer
                    else {
                        try {
                            int quantity = Integer.parseInt(input);
                            if (quantity >= 0 && quantity <= reg1.students.size()) {
                                addToRoster(quantity);
                                validInput = true;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }while (!validInput);
            }
        });
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 8;
        reg1.panel.add(addToRoster, c);

        // add a button to allow user to print the roster
        JButton printRoster = new JButton("Print Roster");
        printRoster.setBackground(Registrar.labelHighlightColor);
        printRoster.setForeground(Registrar.labelFontColor);
        printRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\nCurrent Roster for CIS 402:\n");
                if (roster.size() == 0){
                    System.out.println("\tNo students are currently signed up for CIS 402.");
                }
                else {
                    int count = 1;
                    for (Student s : roster) {
                        System.out.println("\t" + count + ".  " + reg1.getStudentText(s));
                        count++;
                    }
                }
            }
        });
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        reg1.panel.add(printRoster, c);
        // buttons don't seem to be added to the panel without a repaint
        reg1.panel.revalidate();
        reg1.panel.repaint();
    }

    private void createRosterLabels(){
        GridBagConstraints c = new GridBagConstraints();
        int rosterLabelGridY = 0;  // represents the GridY position of the resulting label
        // all student and roster labels will be in GridX position 1.
        for (Student s : roster){
            rosterLabelGridY++;
            createOutputLabel(s, rosterLabelGridY, c);
        }
    }

    private void createOutputLabel(Student s, int labelGridY, GridBagConstraints c){
        // studentText is an organized string output with id/first/last
        String studentText = reg1.getStudentText(s);
        JLabel label = new JLabel(studentText);
        // dark mode!
        label.setBackground(Registrar.panelBackgroundColor);
        label.setForeground(Registrar.labelFontColor);
        label.setOpaque(true);

        String labelName = "roster" + reg1.getLabelName(labelGridY);  // allows us to access label and update it later
        label.setName(labelName);
        label.setFont(Registrar.verdanaPlain);

        // if label is clicked, swap student from one list to the other with:
        // the relevant linkedList  /  re-sort  /  update both GUI labels
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = Integer.parseInt((label.getText().split(" "))[1]);
                Student myStudent = reg1.getStudent(id, roster);
                reg1.removeLabels("");
                reg1.removeLabels("roster");
                reg1.swapStudent(roster, myStudent);
                reg1.createStudentLabels();
                String sortingAttribute = (String)((JComboBox) reg1.panel.getComponent(2)).getSelectedItem();
                reg1.sortBy(reg1.students, sortingAttribute);
                reg1.sortBy(roster, sortingAttribute);
                createRosterLabels();
            }

            // show highlighting when mouse enters the label's area
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(Registrar.labelHighlightColor);
            }
            // remove highlighting when it leaves the label's area
            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(Registrar.panelBackgroundColor);
            }
        });

        c.insets = new Insets(5,15,5,0); // spacing between labels
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = labelGridY;  // used in label name, but also to set current position on grid
        reg1.panel.add(label, c);
    }

    public void recreateRosterLabels(){
        reg1.removeLabels("roster");
        createLabels();
    }

    private void createLabels(){
            // create labels to display current sorted roster
        int labelGridY = 0;  // represents the GridY position of the resulting label
        // all labels will be in GridX position 1.
        GridBagConstraints c = new GridBagConstraints();
        for (Student s : roster){
            labelGridY++;
            createOutputLabel(s, labelGridY, c);
        }
    }

    public void updateRosterLabels(){
        // iterates through students in same manner as original creation
        int labelGridY = 0;  // represents the GridY position of the resulting label
        for (Student s : roster){
            labelGridY++;
            updateRosterLabel(s, "roster" + reg1.getLabelName(labelGridY));
        }
    }

    private void updateRosterLabel(Student s, String labelName) {
        Component[] p = reg1.panel.getComponents();  // array containing all panel components
        for (int l = 0; l < p.length; l++){
            if (p[l].getName() != null && p[l].getName().equals(labelName)){  // locate this label in particular
                ((JLabel) p[l]).setText(reg1.getStudentText(s));  // update its text
            }
        }
    }

    private void addToRoster(int quantity){
        reg1.removeLabels("");
        reg1.removeLabels("roster");
        for (int i = 0; i < quantity; i++) {
            int index = (int) (Math.floor(Math.random() * reg1.students.size()));
            reg1.swapStudent(reg1.students, reg1.students.get(index));
        }
        reg1.createStudentLabels();
        recreateRosterLabels();
        String sortingAttribute = (String)((JComboBox) reg1.panel.getComponent(2)).getSelectedItem();
        reg1.sortBy(reg1.students, sortingAttribute);
        reg1.sortBy(roster, sortingAttribute);
    }

    public Roster402_v2 getRoster(){
        return this;
    }
}
