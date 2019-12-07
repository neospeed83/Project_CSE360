import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * The LandingPage class contains the main method for the program which allows for the
 * initialization of the program itself. Initial file loading and bound setting is handled
 * through this class.
 *
 * @author Akash Devdhar, Matt Hayes, Henry Pearson, Nicholas Vietri
 * 		   CSE 360 Team Project
 *
 */
public class LandingPage extends JFrame {

    private static boolean errorFlag = false;
    static boolean emptyFileFlag = false;

    /**
     * Constructor for the LandingPage class that initializes the starting GUI screen
     * while also setting bounds to the default values of 0 and 100.
     */
    private LandingPage() {
        //set default bounds 0, 100
        SetBoundary.updateBoundaries(0, 100);
        initUI();
    }

    /**
     * Method to set the class errorFlag value for subsequent error handling.
     */
    static void setErrorFlag() {
        LandingPage.errorFlag = true;
    }

    /**
     * Method that initializes the GUI and allows for the loading of a file and the setting
     * of the initial bounds.
     */
    private void initUI() {
        setTitle("Grade Analytics - Load new File");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create error log
        ErrorLog log = ErrorLog.getInstance();

        //Error Log - Henry
        //Display Error Log Button
        JButton errorLog = new JButton("Error Log");
        add(errorLog);
        errorLog.setBounds(650, 20, 120, 30);
        errorLog.addActionListener(e -> {
            if(log.isEmpty())
            {
                JOptionPane.showMessageDialog(this,"Error Log is empty.",
                        "Error Log", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String errors = (log.getReport());
                JTextArea errorArea = new JTextArea(errors, 6, 50);
                errorArea.setWrapStyleWord(true);
                errorArea.setLineWrap(true);
                errorArea.setEditable(false);
                JScrollPane sp = new JScrollPane(errorArea);
                JOptionPane.showMessageDialog(this, sp,
                        "Error Log", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //set boundaries
        JLabel lowerBound = new JLabel("Lower bound");
        JTextField tfLower = new JTextField(50);
        lowerBound.setLabelFor(tfLower);
        setLayout(null);
        tfLower.setText(Float.toString(SetBoundary.getLowerBound()));
        add(lowerBound);
        add(tfLower);
        lowerBound.setBounds(20, 100, 120, 30);
        tfLower.setBounds(150, 100, 50, 30);

        JLabel upperBound = new JLabel("Upper bound");
        JTextField tfUpper = new JTextField(50);
        upperBound.setLabelFor(tfUpper);
        tfUpper.setText(Float.toString(SetBoundary.getHigherBound()));
        add(upperBound);
        add(tfUpper);
        upperBound.setBounds(320, 100, 120, 30);
        tfUpper.setBounds(460, 100, 50, 30);

        // Boundaries set message
        JLabel boundSet = new JLabel("Boundaries Set Successfully.");
        boundSet.setBounds(250, 30, 300, 30);
        add(boundSet);
        boundSet.setVisible(false);

        //set button
        JButton setBoundaries = new JButton("Set Bounds");
        setBoundaries.setBounds(600, 100, 120, 30);
        add(setBoundaries);
        setBoundaries.addActionListener(e -> {
            if (!tfLower.getText().isEmpty() && !tfUpper.getText().isEmpty()) {

                try {
                    float low = Float.parseFloat(tfLower.getText());
                    float high = Float.parseFloat(tfUpper.getText());

                    if(low <= high) {
                        SetBoundary.updateBoundaries(low, high);
                        MainPage.boundaryFlag = true;
                        boundSet.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Lower bound must be less than or equal to upper bound.",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                        log.addError(1);
                    }

                } catch (NumberFormatException ex5) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a number",
                            "Invalid input detected",
                            JOptionPane.ERROR_MESSAGE);
                    MainPage.updateReport("Unsuccessfully set bounds \n");
                    log.addError(2);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Upper / lower bounds can't be empty",
                        "Empty Bounds",
                        JOptionPane.ERROR_MESSAGE);
                MainPage.updateReport("Unsuccessfully set bounds \n");
                log.addError(1);
            }
        });


        //browse button
        JButton browseButton = new JButton("Load a file");
        add(browseButton);
        browseButton.setBounds(20, 200, 100, 30);
        browseButton.addActionListener(e -> {
            boundSet.setVisible(false);
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Select a csv or txt");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("txt or csv files", "csv",
                            "txt");
            jfc.addChoosableFileFilter(filter);
            int returnValue = jfc.showOpenDialog(this);

            if (jfc.getSelectedFile() != null) {
                if (!jfc.getSelectedFile().exists()) {
                    errorFlag = true;
                    JOptionPane.showMessageDialog(this,
                            "Please check your file name",
                            "File Does not exist!",
                            JOptionPane.ERROR_MESSAGE);
                    log.addError(0);
                }

                if (jfc.getSelectedFile().exists() && returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        MainPage.boundaryFlag = true;
                        new MainPage(jfc.getSelectedFile());
                        if(emptyFileFlag) {
                            log.addError(5);
                            JOptionPane.showMessageDialog(this,
                                    "The file inputted is empty",
                                    "Empty File",
                                    JOptionPane.ERROR_MESSAGE);
                            emptyFileFlag = false;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    errorFlag = false;
            }
        });
    }

    /**
     * Main method, begins the program by creating an instance of the LandingPage class.
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LandingPage ex = new LandingPage();
            ex.setVisible(true);
        });
    }
}