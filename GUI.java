// Name:  Chaz Peterson
// Purpose:  Assignment 9.1 - Allow user to add a particular number of students to a roster.
//      Allow them to view the roster and close the program at will.
// Date: 7/21/2021
// File name: Registrar.java / Student.java / Roster402_v2.java / GUI.java

//  GUI for Roster402_v2 / Registrar

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class GUI {
    private JPanel panel = new JPanel();
    private Dimension idealDimensions = new Dimension(750, 450); //frame dimensions
    GridBagConstraints c = new GridBagConstraints();
    public static Color labelFontColor = new Color(250, 230, 230);
    public static Color panelBackgroundColor = new Color(0, 30, 50);
    public static Color labelHighlightColor = new Color(30, 70, 90);
    public static Font verdanaPlain = new Font("Verdana", Font.PLAIN, 12);
    public static Font verdanaBold = new Font("Verdana", Font.BOLD, 12);
    public Registrar registrar;
    public Roster402_v2 roster;

    public GUI(Roster402_v2 myRoster, Registrar myRegistrar) {
        setRegistrar(myRegistrar);
        setRoster(myRoster);

        JFrame frame = createFrame();
        panel = createPanel();
        c.fill = GridBagConstraints.NORTH;
        frame.add(panel, BorderLayout.NORTH);

        // two bold main labels for columns:
        createTitleLabel("Sort By:", 0,0, true);
        createTitleLabel("Available Students:", 1,0, true);

        // dropdown for sorting purposes:
        String[] choicesForDropDown = {"Student ID", "First Name", "Last Name"};
        createDropDown(choicesForDropDown, 0,1);

        // 3rd column title label:
        createTitleLabel(roster.getRosterName(),2,0,true);
        // instructions label for managing a roster:
        createRosterInstructionsLabel(roster.getRosterInstructions());

        // button to add students to roster
        createAddToRosterButton();
        // button to print current roster
        createPrintRosterButton();

        // iterate through the list and create relevant labels for each column
        createStudentLabels(registrar.students,1,1);
        createStudentLabels(roster.roster,2,1);

        frame.pack();
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame();
        frame.setSize(idealDimensions);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Student Roster");
        return frame;
    }

    private JPanel createPanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setPreferredSize(idealDimensions);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(panelBackgroundColor);
        return panel;
    }

    public void createTitleLabel(String labelText, int gridX, int gridY, boolean bold){
        JLabel label = new JLabel(labelText);
        label.setForeground(labelFontColor);
        label.setFont(verdanaPlain);
        if (bold){
            label.setFont(verdanaBold);
        }
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = gridX;
        c.gridy = gridY;
        panel.add(label,c);
    }

    public void createDropDown(String[] choices, int gridX, int gridY){
        JComboBox<String> dropDown = new JComboBox<String>(choices);
        dropDown.setForeground(labelFontColor);
        dropDown.setBackground(labelHighlightColor);
        dropDown.setFont(verdanaBold);
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
        dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        dropDown.setRenderer(dlcr);
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.gridx = gridX;
        c.gridy = gridY;
        c.insets = new Insets(5,0,0,0); // spacing between labels

        // since starting sort is by last name, this makes default selection and data presentation match
        dropDown.setSelectedIndex(2);

        dropDown.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // onselect, sort both lists
                Controller.sortBy(registrar.students, (String) dropDown.getSelectedItem());
                Controller.sortBy(roster.roster, (String) dropDown.getSelectedItem());
        }});
        panel.add(dropDown, c);
    }

    public void createStudentLabels(LinkedList<Student> list, int columnGridX, int startingGridY){
        // all labels will be in the same GridX position (i.e. same column)
        int gridY = startingGridY;
        for (Student s : list){
            createStudentLabel(list, s, columnGridX, gridY);
            gridY++;
        }
    }

    private void createStudentLabel(LinkedList<Student> list, Student s, int gridX, int labelGridY){
        String studentText = Controller.getStudentText(s);
        JLabel label = new JLabel(studentText);
        label.setBackground(panelBackgroundColor);
        label.setForeground(labelFontColor);
        label.setFont(verdanaPlain);
        label.setOpaque(true); // showing background so highlighting can be seen on hover
        String labelName; // allows us to access label and update it later
        if (list == registrar.students){
            labelName = "students" + Controller.getLabelName(labelGridY);
        }
        else{
            labelName = "roster" + Controller.getLabelName(labelGridY);
        }

        label.setName(labelName);
        // when label is clicked, swap that student from one linkedlist to the other
        // and update the two GUI lists / show end result as sorted how user has chosen
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = Integer.parseInt((label.getText().split(" "))[1]);
                Student myStudent = Controller.getStudent(id, list);
                Controller.swapStudent(list, myStudent);
                reCreateStudentLabels();
                String sortingAttribute = (String)((JComboBox) panel.getComponent(2)).getSelectedItem();
                Controller.sortBy(registrar.students, sortingAttribute);
                Controller.sortBy(roster.roster, sortingAttribute);
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

        c.insets = new Insets(1,15,1,0); // spacing between labels
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = gridX;
        c.gridy = labelGridY;  // used in label name, but also to set current position on grid
        panel.add(label, c);
        panel.revalidate();
        panel.repaint();
    }

    // on click will print current Roster to the console
    public void createPrintRosterButton() {
        JButton printRoster = new JButton("Print Roster");
        printRoster.setBackground(labelHighlightColor);
        printRoster.setForeground(labelFontColor);
        printRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("\nCurrent Roster for " + roster.getRosterName() + ":\n");
                if (roster.roster.size() == 0) {
                    System.out.println("\tNo students are currently signed up for " + roster.getRosterName() + ".");
                } else {
                    int count = 1;
                    for (Student s : roster.roster) {
                        System.out.println("\t" + count + ".  " + Controller.getStudentText(s));
                        count++;
                    }
                }
            }
        });
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        panel.add(printRoster, c);
        // buttons don't seem to be added to the panel without a repaint
        panel.revalidate();
        panel.repaint();
    }

    // removes only the student labels.
    private void removeLabels(){
        Component[] p = panel.getComponents();  // array containing all panel components
        for (int l = p.length-1; l > 0; l--){
            p = panel.getComponents();  // array containing all panel components
            for (int m = p.length-1; m > 0; m--) {
                if (p[m].getName() != null &&
                        (p[m].getName().equals("studentslabel" + l)
                        || p[m].getName().equals("rosterlabel" + l))) {  // locate this label in particular
                    panel.remove(m);
                }
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    public void reCreateStudentLabels(){
        removeLabels();
        createStudentLabels(registrar.students, 1,1);
        createStudentLabels(roster.roster,2,1);
    }

    // upon sort, update student labels - no need to remove and recreate since we have the same
    // quantity of labels once sort is completed.
    public void updateStudentLabels(LinkedList<Student> list){
        // determine which list is being updated
        String listName;
        if (list == registrar.students){
            listName = "students";
        }
        else{
            listName = "roster";
        }
        // iterates through students in same manner as original creation
        int labelGridY = 0;  // represents the GridY position of the resulting label
        for (Student s : list){
            labelGridY++;
            updateLabel(s, listName + Controller.getLabelName(labelGridY));
        }
    }
    // supports "updateStudentLabels"
    private void updateLabel(Student s, String labelName){
        Component[] p = panel.getComponents();  // array containing all panel components
        for (int l = 0; l < p.length; l++){
            if (p[l].getName() != null && p[l].getName().equals(labelName)){  // locate this label in particular
                ((JLabel) p[l]).setText(Controller.getStudentText(s));  // update its text
            }
        }
    }

    public void createRosterInstructionsLabel(String instructions){
        JLabel rosterInstructionLabel = new JLabel(instructions);
        rosterInstructionLabel.setForeground(new Color(220, 200, 200));
        rosterInstructionLabel.setFont(verdanaPlain);
        rosterInstructionLabel.setSize(new Dimension(60,150));
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 5;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(rosterInstructionLabel, c);

    }

    public void createAddToRosterButton(){
        JButton addToRoster = new JButton("Add to Roster");
        addToRoster.setBackground(labelHighlightColor);
        addToRoster.setForeground(labelFontColor);
        addToRoster.addActionListener(new ActionListener() {
            // when clicked, open input dialog to capture quantity to move
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean validInput = false;
                String errorMessage = "";
                do {
                    String input = JOptionPane.showInputDialog(null, errorMessage + "How many students would you" +
                            " like to add to the roster? (0 - " + registrar.students.size() + ")");
                    // allow for cancel/x/no input
                    if (input == null){
                        validInput = true;
                    }
                    // if input, make sure it's a proper amount and an actual integer
                    else {
                        try {
                            int quantity = Integer.parseInt(input);
                            if (quantity >= 0 && quantity <= registrar.students.size()) {
                                addToRoster(quantity);
                                validInput = true;
                            }
                        } catch (Exception exception) {
                            // if not between proper numbers, remind user of requirement
                            // and show prompt again.
                            errorMessage = "Input must be a number between 0 and " + registrar.students.size() + ".\n";
                        }
                    }
                }while (!validInput);
            }
        });
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 7;
        panel.add(addToRoster, c);
    }

    private void addToRoster(int quantity){
        // swap a random index from reg.students to roster.roster quantity number of times
        for (int i = 0; i < quantity; i++) {
            int index = (int) (Math.floor(Math.random() * registrar.students.size()));
            Controller.swapStudent(registrar.students, registrar.students.get(index));
        }
        reCreateStudentLabels();
        String sortingAttribute = (String)((JComboBox) panel.getComponent(2)).getSelectedItem();
        Controller.sortBy(registrar.students, sortingAttribute);
        Controller.sortBy(roster.roster, sortingAttribute);
    }

    public GUI getGUI(){
        return this;
    }

    public void setRegistrar(Registrar r){
        registrar = r;
    }
    public void setRoster(Roster402_v2 r){
        roster = r;
    }
}
