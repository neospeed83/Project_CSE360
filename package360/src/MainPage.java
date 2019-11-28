import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
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
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Load file filedata into -> filedata
        List<Integer> filedata = ReadFile.readFileByName(selectedFile.getPath());
        for (Integer i : filedata) {
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

            // Add Section Label
            JLabel addSection = new JLabel("Add:");
            addSection.setFont(
                    new Font("Serif",Font.BOLD,18));
            addSection.setBounds(20, 20, 120, 30);
            add(addSection);

            //append button
            JButton appendFile = new JButton("Add a file");
            add(appendFile);
            appendFile.setBounds(20, 50, 120, 30);
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
                            filedata.addAll(append); // Append to filedata
                            System.out.println(filedata);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Refresh Section Label
            JLabel refreshSection = new JLabel("Refresh:");
            refreshSection.setFont(
                    new Font("Serif",Font.BOLD,18));
            refreshSection.setBounds(20, 120, 200, 30);
            add(refreshSection);

            // Display data button
            JButton display = new JButton("Display Data");
            display.setBounds(20, 150, 150, 30);
            add(display);

            // Display Graph button
            JButton displayGraph = new JButton("Display Graph");
            displayGraph.setBounds(20, 200, 150, 30);
            add(displayGraph);

            // Display Analysis button
            JButton displayAnalysis = new JButton("Data Analysis");
            displayAnalysis.setBounds(20, 250, 150, 30);
            add(displayAnalysis);

            // Display distribution graph button
            JButton displayDistribution = new JButton("Distribution Graph");
            displayDistribution.setBounds(20, 300, 150, 30);
            add(displayDistribution);


            // Display TextArea
            JTextArea displayData = new JTextArea();
            displayData.setEditable(false);
            displayData.setBounds(20, 150, 700, 450);
            displayData.setLineWrap(true);
            displayData.setVisible(false);
            add(displayData);
            display.addActionListener(e -> {
                displayData.setText("");
                filedata.sort(null);
                for (Integer element : filedata) {
                    displayData.append(element + " ");
                }
                displayData.setVisible(true);
            });

            ChartPanel cp = createGraph(filedata);
            add(cp);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Display Data", displayData);
            tabbedPane.addTab("Display Graph", cp);
            tabbedPane.addTab("Data Analysis", new JPanel());
            tabbedPane.addTab("Distribution Graph", new JPanel());
            tabbedPane.setBounds(200, 150, 1000, 450);
            add(tabbedPane);
            tabbedPane.setVisible(true);


        } // else Display main page


    }

    private ChartPanel createGraph(List<Integer> data) {
        // Calculate minimum and maximum value of dataset:
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (Integer i : data) {
            if (max < i) {
                max = i;
            }
            if (min > i) {
                min = i;
            }
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0,
                seven = 0, eight = 0, nine = 0, ten = 0;
        for (Integer i : data) {
            if (i <= (min + max) * 0.1)
                one += 1;
            else if (i > (min + max) * 0.1 && i <= (min + max) * 0.2)
                two += 1;
            else if (i > (min + max) * 0.2 && i <= (min + max) * 0.3)
                three += 1;
            else if (i > (min + max) * 0.3 && i <= (min + max) * 0.4)
                four += 1;
            else if (i > (min + max) * 0.4 && i <= (min + max) * 0.5)
                five += 1;
            else if (i > (min + max) * 0.5 && i <= (min + max) * 0.6)
                six += 1;
            else if (i > (min + max) * 0.6 && i <= (min + max) * 0.7)
                seven += 1;
            else if (i > (min + max) * 0.7 && i <= (min + max) * 0.8)
                eight += 1;
            else if (i > (min + max) * 0.8 && i <= (min + max) * 0.9)
                nine += 1;
            else if (i > (min + max) * 0.9)
                ten += 1;
        }

        dataset.addValue(one, "0-10 %", "0-10 %");
        dataset.addValue(two, "11-20 %", "11-20 %");
        dataset.addValue(three, "21-30 %", "21-30 %");
        dataset.addValue(four, "31-40 %", "31-40 %");
        dataset.addValue(five, "41-50 %", "41-50 %");
        dataset.addValue(six, "51-60 %", "51-60 %");
        dataset.addValue(seven, "61-70 %", "61-70 %");
        dataset.addValue(eight, "71-80 %", "71-80 %");
        dataset.addValue(nine, "81-90 %", "81-90 %");
        dataset.addValue(ten, "91-100 %", "91-100 %");

        JFreeChart graph = ChartFactory.createBarChart(
                "Data by Percentages",
                "Percentage Range",
                "Count",
                dataset, PlotOrientation.HORIZONTAL,
                true,false, false);

        // Setting the Y-axis to show only numbers instead of decimals
        CategoryPlot chartPlot = graph.getCategoryPlot();
        ValueAxis yAxis = chartPlot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return new ChartPanel(graph);
    }
}
