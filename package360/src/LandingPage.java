import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class LandingPage extends JFrame {

    private SetBoundary Bounds;

    private LandingPage() {
        Bounds = SetBoundary.updateBoundaries(0, 0);
        initUI();
    }

    private void initUI() {
        setTitle("Grade Analytics - Load new File");
        setSize(800, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set boundaries
        JLabel lowerBound = new JLabel("Lower bound");
        JTextField tfLower = new JTextField(50);
        lowerBound.setLabelFor(tfLower);
        setLayout(null);
        add(lowerBound);
        add(tfLower);
        lowerBound.setBounds(20, 100, 120, 30);
        tfLower.setBounds(150, 100, 50, 30);

        JLabel upperBound = new JLabel("Upper bound");
        JTextField tfUpper = new JTextField(50);
        upperBound.setLabelFor(tfUpper);
        add(upperBound);
        add(tfUpper);
        upperBound.setBounds(320, 100, 120, 30);
        tfUpper.setBounds(460, 100, 50, 30);

        // Boundaries set message
        JLabel boundSet = new JLabel("Boundaries Set Successfully.");
        boundSet.setBounds(370, 30, 300, 30);
        add(boundSet);
        boundSet.setVisible(false);

        // Error: Bounds can't be empty
        JLabel emptyBounds = new JLabel("Upper / Lower Bound can't be empty");
        emptyBounds.setBounds(370, 30, 300, 30);
        add(emptyBounds);
        emptyBounds.setVisible(false);

        //set button
        JButton setBoundaries = new JButton("Set Bounds");
        setBoundaries.setBounds(670, 100, 60, 30);
        add(setBoundaries);
        setBoundaries.addActionListener(e -> {
            if (!tfLower.getText().isEmpty() && !tfUpper.getText().isEmpty()) {
                int low = Integer.parseInt(tfLower.getText());
                int high = Integer.parseInt(tfUpper.getText());
                Bounds = new SetBoundary(low, high);
                boundSet.setVisible(true);
                emptyBounds.setVisible(false);
            }
            else{
                emptyBounds.setVisible(true);
                boundSet.setVisible(false);
            }
        });


        //browse button
        JButton browseButton = new JButton("load a file");
        add(browseButton);
        browseButton.setBounds(20, 200, 100, 30);
        browseButton.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Select a csv or txt");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter =
                    new FileNameExtensionFilter("txt or csv files", "csv",
                            "txt");
            jfc.addChoosableFileFilter(filter);
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    var test = new MainPage(jfc.getSelectedFile());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                // Add logic to create dataset from the file data
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var ex = new LandingPage();
            ex.setVisible(true);
        });
    }
}