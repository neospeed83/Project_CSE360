import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.List;


class MainPage extends JDialog {

    private File selectedFile;
    private static List<Integer> fileData;
    private DefaultCategoryDataset dataset;
    private static DefaultCategoryDataset distributionDataset;
    //Flags
    static boolean boundaryFlag = true;

    MainPage(File inputFile) {
        selectedFile = inputFile;
        dataset = new DefaultCategoryDataset();
        distributionDataset = new DefaultCategoryDataset();
        InitMainUI();
    }

    static List<Integer> getFileData() {
        return fileData;
    }

    public static DefaultCategoryDataset getDistributionDataset() {
        return distributionDataset;
    }

    private void InitMainUI() {
        setLayout(null);

        setTitle("Grade Analytics - Main Page : " + selectedFile.getName());
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        // Load file fileData into -> fileData
        fileData = ReadFile.readFileByName(selectedFile.getPath());
        for (Integer i : fileData) {
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
            // Display Main Page
            setVisible(true);

            // "Add" Section Label
            JLabel addSection = new JLabel("Add:");
            addSection.setFont(
                    new Font("Serif", Font.BOLD, 18));
            addSection.setBounds(20, 20, 120, 30);
            add(addSection);

            // Append button
            JButton appendFile = new JButton("Append another file");
            add(appendFile);
            appendFile.setBounds(20, 50, 150, 30);
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
                            fileData.addAll(append); // Append to fileData
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            //Input label and text field
            JLabel inputLabel = new JLabel("Input");
            JTextField inputData = new JTextField(50);
            inputLabel.setLabelFor(inputData);
            inputData.setBounds(50, 90, 40, 30);
            inputLabel.setBounds(20, 90, 60, 30);
            inputData.setVisible(true);
            add(inputData);
            add(inputLabel);

            //Submitted text field
            JLabel sentValue = new JLabel("Submitted");
            sentValue.setBounds(180,90,60,30);
            sentValue.setVisible(false);
            add(sentValue);

            //empty inpput text field
            JLabel emptyValue = new JLabel("Empty Input");
            emptyValue.setBounds(180,90,80,30);
            emptyValue.setVisible(false);
            add(emptyValue);

                //submit button
            JButton submitValue = new JButton("Submit");
            submitValue.setBounds(90,90,80,30);
            submitValue.setVisible(true);
            add(submitValue);
            submitValue.addActionListener(e -> {
                if (!inputData.getText().isEmpty()) { //catching empty input

                    try {
                        int value = Integer.parseInt(inputData.getText());
                        sentValue.setVisible(true);
                        emptyValue.setVisible(false);
                        fileData.add(value);

                    } catch (NumberFormatException ex5) {
                        JOptionPane.showMessageDialog(this,
                                "Please enter a number",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    emptyValue.setVisible(true);
                    sentValue.setVisible(false);
                }
            });

            //Remove section
            JLabel deleteSection = new JLabel("Remove:");
            deleteSection.setFont(
                    new Font("Serif", Font.BOLD, 18));
            deleteSection.setBounds(200, 20, 120, 30);
            add(deleteSection);

            //Delete label and text field
            JLabel deleteLabel = new JLabel("Delete");
            JTextField deleteData = new JTextField(50);
            deleteLabel.setLabelFor(deleteData);
            deleteData.setBounds(240, 50, 40, 30);
            deleteLabel.setBounds(200, 50, 60, 30);
            deleteData.setVisible(true);
            add(deleteData);
            add(deleteLabel);

            //Submitted text field
            JLabel deletedValue = new JLabel("Submitted");
            deletedValue.setBounds(370,50,60,30);
            deletedValue.setVisible(false);
            add(deletedValue);

            //empty input text field
            JLabel emptyDValue = new JLabel("Empty Input");
            emptyDValue.setBounds(370,50,80,30);
            emptyDValue.setVisible(false);
            add(emptyDValue);

            //submit button
            JButton submitDValue = new JButton("Submit");
            submitDValue.setBounds(280,50,80,30);
            submitDValue.setVisible(true);
            add(submitDValue);
            submitDValue.addActionListener(e -> {
                if (!deleteData.getText().isEmpty()) { //catching empty input

                    try {
                        int value = Integer.parseInt(deleteData.getText());
                        deletedValue.setVisible(true);
                        emptyDValue.setVisible(false);
                        fileData.remove(Integer.valueOf(value));

                    } catch (NumberFormatException ex5) {
                        JOptionPane.showMessageDialog(this,
                                "Please enter a number",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    emptyDValue.setVisible(true);
                    deletedValue.setVisible(false);
                }
            });




            // Display TextArea
            JTextArea displayData = new JTextArea();
            displayData.setEditable(false);
            displayData.setBounds(20, 150, 700, 450);
            displayData.setLineWrap(true);
            displayData.setVisible(false);
            add(displayData);


            // Declaring Tabbed pane here to change active tab on button click
            JTabbedPane tabbedPane = new JTabbedPane();

            // Refresh Section Label
            JLabel refreshSection = new JLabel("Refresh:");
            refreshSection.setFont(
                    new Font("Serif", Font.BOLD, 18));
            refreshSection.setBounds(20, 120, 200, 30);
            add(refreshSection);

            // Display data button
            JButton display = new JButton("Display Data");
            display.setBounds(20, 150, 150, 30);
            add(display);

            display.addActionListener(e -> {
                displayData.setText("");
                fileData.sort(null);
                for (Integer element : fileData) {
                    displayData.append(element + " ");
                }
                displayData.setVisible(true);
                tabbedPane.setSelectedIndex(0);
            });

            // Display Graph button
            JButton displayGraph = new JButton("Display Graph");
            displayGraph.setBounds(20, 200, 150, 30);
            add(displayGraph);

            // Calculate minimum and maximum value of dataset:
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            for (Integer i : fileData) {
                if (max < i) {
                    max = i;
                }
                if (min > i) {
                    min = i;
                }
            }

            int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0,
                    seven = 0, eight = 0, nine = 0, ten = 0;

            for (Integer i : fileData) {
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


            // Created Graph with current data
            JFreeChart graph = ChartFactory.createBarChart(
                    "Data by Percentages",
                    "Percentage Range",
                    "Count",
                    dataset, PlotOrientation.HORIZONTAL,
                    true, false, false);

            // Setting the Y-axis to show only numbers instead of decimals
            CategoryPlot chartPlot = graph.getCategoryPlot();
            ValueAxis yAxis = chartPlot.getRangeAxis();
            yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            ChartPanel chartPanel = new ChartPanel(graph);
            add(chartPanel);

            // Update Graph
            displayGraph.addActionListener(e -> {
                // Calculate minimum and maximum value of dataset:
                int max1 = Integer.MIN_VALUE;
                int min1 = Integer.MAX_VALUE;

                for (Integer i : fileData) {
                    if (max1 < i) {
                        max1 = i;
                    }
                    if (min1 > i) {
                        min1 = i;
                    }
                }

                int first = 0, second = 0, third = 0, fourth = 0, fifth = 0,
                        sixth = 0, seventh = 0, eighth = 0, ninth = 0, tenth = 0;

                for (Integer i : fileData) {
                    if (i <= (min1 + max1) * 0.1)
                        first += 1;
                    else if (i > (min1 + max1) * 0.1 && i <= (min1 + max1) * 0.2)
                        second += 1;
                    else if (i > (min1 + max1) * 0.2 && i <= (min1 + max1) * 0.3)
                        third += 1;
                    else if (i > (min1 + max1) * 0.3 && i <= (min1 + max1) * 0.4)
                        fourth += 1;
                    else if (i > (min1 + max1) * 0.4 && i <= (min1 + max1) * 0.5)
                        fifth += 1;
                    else if (i > (min1 + max1) * 0.5 && i <= (min1 + max1) * 0.6)
                        sixth += 1;
                    else if (i > (min1 + max1) * 0.6 && i <= (min1 + max1) * 0.7)
                        seventh += 1;
                    else if (i > (min1 + max1) * 0.7 && i <= (min1 + max1) * 0.8)
                        eighth += 1;
                    else if (i > (min1 + max1) * 0.8 && i <= (min1 + max1) * 0.9)
                        ninth += 1;
                    else if (i > (min1 + max1) * 0.9)
                        tenth += 1;
                }

                DefaultCategoryDataset dataSet1 = new DefaultCategoryDataset();

                dataSet1.addValue(first, "0-10 %", "0-10 %");
                dataSet1.addValue(second, "11-20 %", "11-20 %");
                dataSet1.addValue(third, "21-30 %", "21-30 %");
                dataSet1.addValue(fourth, "31-40 %", "31-40 %");
                dataSet1.addValue(fifth, "41-50 %", "41-50 %");
                dataSet1.addValue(sixth, "51-60 %", "51-60 %");
                dataSet1.addValue(seventh, "61-70 %", "61-70 %");
                dataSet1.addValue(eighth, "71-80 %", "71-80 %");
                dataSet1.addValue(ninth, "81-90 %", "81-90 %");
                dataSet1.addValue(tenth, "91-100 %", "91-100 %");

                // Set new dataset
                chartPlot.setDataset(dataSet1);
                tabbedPane.setSelectedIndex(1);
            });

            // Display Analysis button
            JButton displayAnalysis = new JButton("Data Analysis");
            displayAnalysis.setBounds(20, 250, 150, 30);
            add(displayAnalysis);

            Analysis analysisPanel = new Analysis();
            displayAnalysis.addActionListener(e -> {
                analysisPanel.update();
                analysisPanel.repaint();
                tabbedPane.setSelectedIndex(2);
            });

            // Display distribution graph button
            JButton displayDistribution = new JButton("Distribution Graph");
            displayDistribution.setBounds(20, 300, 150, 30);
            add(displayDistribution);

            DistributionGraph distributionGraph = new DistributionGraph();
            displayDistribution.addActionListener(e -> {
                distributionGraph.updateGraph();
                distributionGraph.repaint();
                tabbedPane.setSelectedIndex(3);
            });

            tabbedPane.addTab("Display Data", displayData);
            tabbedPane.addTab("Display Graph", chartPanel);
            tabbedPane.addTab("Data Analysis", analysisPanel);
            tabbedPane.addTab("Distribution Graph", distributionGraph);
            tabbedPane.setBounds(200, 150, 1000, 450);
            add(tabbedPane);
            tabbedPane.setVisible(true);

        } // else Display main page
    }// End Init ui
}


