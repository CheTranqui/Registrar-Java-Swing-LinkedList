
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
    public Registrar reg;
    public Roster402_v2 roster;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        JFrame frame = createFrame();
        panel = createPanel();
        c.fill = GridBagConstraints.NORTH;
        frame.add(panel, BorderLayout.NORTH);

        new Registrar(this);
        roster = new Roster402_v2(this);
        reg.setRoster(roster);

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
                reg.sortBy(reg.students, (String) dropDown.getSelectedItem());
                reg.sortBy(roster.roster, (String) dropDown.getSelectedItem());
        }});
        panel.add(dropDown, c);
    }

    public void createStudentLabels(LinkedList<Student> list, int columnGridX, int startingGridY){
        // all labels will be in GridX position 1.
        int gridY = startingGridY;
        for (Student s : list){
            createStudentLabel(list, s, columnGridX, gridY);
            gridY++;
        }
    }

    private void createStudentLabel(LinkedList<Student> list, Student s, int gridX, int labelGridY){
        String studentText = reg.getStudentText(s);
        JLabel label = new JLabel(studentText);
        label.setBackground(panelBackgroundColor);
        label.setForeground(labelFontColor);
        label.setFont(verdanaPlain);
        label.setOpaque(true); // showing background so highlighting can be seen on hover
        String labelName; // allows us to access label and update it later
        if (list == reg.students){
            labelName = "students" + reg.getLabelName(labelGridY);
        }
        else{
            labelName = "roster" + reg.getLabelName(labelGridY);
        }

        label.setName(labelName);
        // when label is clicked, swap that student from one linkedlist to the other
        // and update the two GUI lists / show end result as sorted how user has chosen
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = Integer.parseInt((label.getText().split(" "))[1]);
                Student myStudent = reg.getStudent(id, list);
                reg.swapStudent(list, myStudent);
                reCreateStudentLabels();
                String sortingAttribute = (String)((JComboBox) panel.getComponent(2)).getSelectedItem();
                reg.sortBy(reg.students, sortingAttribute);
                reg.sortBy(roster.roster, sortingAttribute);
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
                System.out.println("\nCurrent Roster for CIS 402:\n");
                if (roster.roster.size() == 0) {
                    System.out.println("\tNo students are currently signed up for CIS 402.");
                } else {
                    int count = 1;
                    for (Student s : roster.roster) {
                        System.out.println("\t" + count + ".  " + reg.getStudentText(s));
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
        createStudentLabels(reg.students, 1,1);
        createStudentLabels(roster.roster,2,1);
    }

    // upon sort, update student labels - no need to remove and recreate since we have the same
    // quantity of labels once sort is completed.
    public void updateStudentLabels(LinkedList<Student> list){
        // determine which list is being updated
        String listName;
        if (list == reg.students){
            listName = "students";
        }
        else{
            listName = "roster";
        }
        // iterates through students in same manner as original creation
        int labelGridY = 0;  // represents the GridY position of the resulting label
        for (Student s : list){
            labelGridY++;
            updateLabel(s, listName + reg.getLabelName(labelGridY));
        }
    }
    // supports "updateStudentLabels"
    private void updateLabel(Student s, String labelName){
        Component[] p = panel.getComponents();  // array containing all panel components
        for (int l = 0; l < p.length; l++){
            if (p[l].getName() != null && p[l].getName().equals(labelName)){  // locate this label in particular
                ((JLabel) p[l]).setText(reg.getStudentText(s));  // update its text
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
                            " like to add to the roster? (0 - " + reg.students.size() + ")");
                    // allow for cancel/x/no input
                    if (input == null){
                        validInput = true;
                    }
                    // if input, make sure it's a proper amount and an actual integer
                    else {
                        try {
                            int quantity = Integer.parseInt(input);
                            if (quantity >= 0 && quantity <= reg.students.size()) {
                                addToRoster(quantity);
                                validInput = true;
                            }
                        } catch (Exception exception) {
                            // if not between proper numbers, remind user of requirement
                            // and show prompt again.
                            errorMessage = "Input must be a number between 0 and " + reg.students.size() + ".\n";
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
            int index = (int) (Math.floor(Math.random() * reg.students.size()));
            reg.swapStudent(reg.students, reg.students.get(index));
        }
        reCreateStudentLabels();
        String sortingAttribute = (String)((JComboBox) panel.getComponent(2)).getSelectedItem();
        reg.sortBy(reg.students, sortingAttribute);
        reg.sortBy(roster.roster, sortingAttribute);
    }

    public void setRegistrar(Registrar r){
        reg = r;
    }
    public void setRoster(Roster402_v2 r){
        roster = r;
    }
}
