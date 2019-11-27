import javax.swing.*;
import java.io.File;
import java.util.List;

class MainPage extends JFrame {

    private File selectedFile;
    private static boolean boundaryFlag = true;

    MainPage(File inputFile) throws Exception {
        selectedFile = inputFile;
        InitMainUI();
    }

    private void InitMainUI() throws Exception {
        setTitle("Grade Analytics - Main Page :" + selectedFile.getName());
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        List<Integer> content = ReadFile.readFileByName(selectedFile.getPath());
        for (Integer i : content) {
            int higher = i.compareTo(SetBoundary.getHigherBound());
            int lower = i.compareTo(SetBoundary.getLowerBound());
            if (higher > 0 || lower < 0) {
                boundaryFlag = false;
                break;
            }
        }
        if (!boundaryFlag) {
            int n = JOptionPane.showOptionDialog(this, "Data in " +
                            "file exceeds set bounds",
                    "Data out of bounds", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, new Object[]{"Yes, Auto-Update", "No-Manual Update"},
                    JOptionPane.YES_OPTION);
        } else {
            setVisible(true);
        }
    }
}
