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
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Load file dataset into -> dataset
        List<Integer> dataset = ReadFile.readFileByName(selectedFile.getPath());
        for (Integer i : dataset) {
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

            setVisible(true); // Display Main Page

            //append button
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
                            JOptionPane.showMessageDialog(jfc,
                                    "The file contains out of bounds data!",
                                    "Data Boundary Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            dataset.addAll(append); // Append to dataset
                            System.out.println(dataset);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Display data button
            JButton display = new JButton("Display");
            display.setBounds(20, 150, 100, 30);
            add(display);

            // Display TextArea
            JTextArea displayData = new JTextArea();
            displayData.setEditable(false);
            displayData.setBounds(20, 250, 400, 300);
            displayData.setLineWrap(true);
            displayData.setVisible(false);
            add(displayData);
            display.addActionListener(e -> {
                displayData.setText("");
                dataset.sort(null);
                for (Integer element : dataset) {
                    displayData.append(element + " ");
                }
                displayData.setVisible(true);
            });

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Display Data", displayData);
            tabbedPane.addTab("Display Graph", new JPanel());
            tabbedPane.addTab("Analyse Data", new JPanel());
            tabbedPane.addTab("Distribution Graph", new JPanel());
            tabbedPane.setBounds(200, 200, 600, 300);
            add(tabbedPane);
            tabbedPane.setVisible(true);

        } // else Display main page
    }
}