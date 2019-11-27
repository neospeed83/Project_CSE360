import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
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
        setLayout(null);
        setTitle("Grade Analytics - Main Page :" + selectedFile.getName());
        setSize(800, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Load file dataset into -> content
        List<Integer> content = ReadFile.readFileByName(selectedFile.getPath());
        for (Integer i : content) {
            int higher = i.compareTo(SetBoundary.getHigherBound());
            int lower = i.compareTo(SetBoundary.getLowerBound());
            if (higher > 0 || lower < 0) {
                boundaryFlag = false;
                break;
            }
        }
        // handle out of bounds data error OR ELSE show Main Screen
        if (!boundaryFlag) {
            JOptionPane.showMessageDialog(this,
                    "The file contains out of bounds data!",
                    "Data Boundary Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            setVisible(true);
            //browse button
            JButton appendFile = new JButton("Append a file");
            add(appendFile);
            appendFile.setBounds(20, 40, 120, 30);
            appendFile.addActionListener(e -> {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Select a csv or txt to append");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter =
                        new FileNameExtensionFilter("txt or csv files", "csv",
                                "txt");
                jfc.addChoosableFileFilter(filter);
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        List<Integer> append =
                                ReadFile.readFileByName(jfc.getSelectedFile().getPath());
                        for (Integer i : append) {
                            int higher = i.compareTo(SetBoundary.getHigherBound());
                            int lower = i.compareTo(SetBoundary.getLowerBound());
                            if (higher > 0 || lower < 0) {
                                boundaryFlag = false;
                                break;
                            }
                        }
                        if (!boundaryFlag) {
                            JOptionPane.showMessageDialog(this,
                                    "The file contains out of bounds data!",
                                    "Data Boundary Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            content.addAll(append); // Append to dataset
                            System.out.println(content);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
}