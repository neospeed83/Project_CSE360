import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class LandingPage extends JFrame {

    private static boolean errorFlag = false;

    private LandingPage() {
        //set default bounds 0, 100
        SetBoundary.updateBoundaries(0, 100);
        initUI();
    }


    static void setErrorFlag() {
        LandingPage.errorFlag = true;
    }

    private void initUI() {
        setTitle("Grade Analytics - Load new File");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //create error log
        ErrorLog log = ErrorLog.getInstance();

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
        boundSet.setBounds(370, 30, 300, 30);
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

                    if(low <= high)
                        SetBoundary.updateBoundaries(low, high);
                    else {
                        JOptionPane.showMessageDialog(this,
                                "Lower bound must be less than or equal to upper bound.",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    MainPage.boundaryFlag = true;
                    boundSet.setVisible(true);

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
                        //setEnabled(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LandingPage ex = new LandingPage();
            ex.setVisible(true);
        });
    }
}