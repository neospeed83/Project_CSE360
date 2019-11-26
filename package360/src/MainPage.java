import javax.swing.*;
import java.util.ArrayList;

class MainPage extends JFrame {

    MainPage() {
        InitMainUI();
    }

    private void InitMainUI(){
        setTitle("Grade Analytics - Main Page");
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
