import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class LandingPage extends JFrame {
    private LandingPage() {
        initUI();
    }

    private void initUI() {
        setTitle("Grade Analytics");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //set boundaries
        JLabel lowerBound = new JLabel("select low bound");
        JTextField tfLower = new JTextField(50);
        lowerBound.setLabelFor(tfLower);
        setLayout(null);
        add(lowerBound);
        add(tfLower);
        lowerBound.setBounds(90, 200, 100, 30);
        tfLower.setBounds(200, 200, 100, 30);

        JLabel upperBound = new JLabel("select upper bound");
        JTextField tfUpper = new JTextField(50);
        upperBound.setLabelFor(tfUpper);
        add(upperBound);
        add(tfUpper);
        upperBound.setBounds(390, 200, 120, 30);
        tfUpper.setBounds(530, 200, 100, 30);


        //browse button
        JButton browseButton = new JButton("load a file");
        setLayout(null);
        add(browseButton);
        browseButton.setBounds(20, 20, 100, 30);
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
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath()); // Add
                // logic to create dataset from the file data
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