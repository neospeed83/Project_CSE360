import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

class MainPage extends JFrame {

    private File selectedFile;
    private SetBoundaries boundaries;

    MainPage(File inputFile, SetBoundaries bounds) {
        selectedFile = inputFile;
        boundaries = bounds;
        InitMainUI();
    }

    private void InitMainUI(){
        setTitle("Grade Analytics - Main Page :" + selectedFile.getName());
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        List<Integer> content = ReadFile.readFileByName(selectedFile.getPath());
        System.out.println("Result" + content);
    }
}
