import javax.swing.*;
import java.awt.EventQueue;
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
        JButton browseButton = new JButton("Browse");
        add(browseButton);
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
                System.out.println(selectedFile.getAbsolutePath());
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