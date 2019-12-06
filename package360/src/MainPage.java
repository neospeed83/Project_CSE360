import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


class MainPage extends JDialog {

    private File selectedFile;
    private static List<Float> fileData;
    private DefaultCategoryDataset dataset;
    private static DefaultCategoryDataset distributionDataset;
    //Flags
    static boolean boundaryFlag = true;
    static boolean reportFlag = false;
    static boolean refreshSwitch = false;
    static boolean initialFileCreation = true;
    static boolean errorFlag = false;

    private static String reportContent = "";
    private static int fileNumber = 1;

    MainPage(File inputFile) {
        selectedFile = inputFile;
        dataset = new DefaultCategoryDataset();
        distributionDataset = new DefaultCategoryDataset();
        InitMainUI();
    }

    static List<Float> getFileData() {
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

        //create error log
        ErrorLog log = ErrorLog.getInstance();

        //Error Log - Henry
        //Display Error Log Button
        JButton errorLog = new JButton("Error Log");
        add(errorLog);
        errorLog.setBounds(1075, 100, 125, 30);
        errorLog.addActionListener(e -> {
            if(log.isEmpty())
            {
                JOptionPane.showMessageDialog(this,"Error Log is empty.",
                        "Error Log", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String errors = (log.getReport());
                JTextArea errorArea = new JTextArea(errors, 6, 50);
                errorArea.setWrapStyleWord(true);
                errorArea.setLineWrap(true);
                errorArea.setEditable(false);
                JScrollPane sp = new JScrollPane(errorArea);
                JOptionPane.showMessageDialog(this, sp,
                        "Error Log", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Load file fileData into -> fileData
        fileData = ReadFile.readFileByName(selectedFile.getPath());
        for (Float i : fileData) {
            int higher = i.compareTo(SetBoundary.getHigherBound());
            int lower = i.compareTo(SetBoundary.getLowerBound());
            if (higher > 0 || lower < 0) {
                boundaryFlag = false;
                log.setOutOfBoundsVal(i);
                log.updateOutOfBoundsVal();
                break;
            }
        }

        // handle out of bounds data error and other errors OR ELSE show Main Screen
        if(errorFlag) {
            errorFlag = false;
        }
        else if (!boundaryFlag) {
            JOptionPane.showMessageDialog(this,
                    "The file contains out of bounds data!",
                    "Data Boundary Error",
                    JOptionPane.ERROR_MESSAGE);
            log.addError(1);
            MainPage.updateReport("Unsuccessfully loaded program with the file: " + selectedFile + "\n");
        } else {
            // Display Main Page
            setVisible(true);
            MainPage.updateReport("New Data Set - Successfully loaded program with the file: "
                    + selectedFile + "\n");

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
                        List<Float> append =
                                ReadFile.readFileByName(jfc.getSelectedFile().getPath());
                        for (Float i : append) {
                            float higher = i.compareTo(SetBoundary.getHigherBound());
                            float lower = i.compareTo(SetBoundary.getLowerBound());
                            if (higher > 0 || lower < 0) {
                                log.setOutOfBoundsVal(i);
                                log.updateOutOfBoundsVal();
                                boundaryFlag = false;
                                break;
                            }
                        }
                        if(errorFlag)
                            errorFlag = false;
                        else if (!boundaryFlag) {
                            MainPage.updateReport("Unsuccessfully appended program with the file: "
                                    + jfc.getSelectedFile() + "\n");
                            JOptionPane.showMessageDialog(jfc,
                                    "The file contains out of bounds data!",
                                    "Data Boundary Error",
                                    JOptionPane.ERROR_MESSAGE);
                            log.addError(1);
                        } else {
                            fileData.addAll(append); // Append to fileData
                            MainPage.updateReport("Successfully appended program with the file: "
                                    + jfc.getSelectedFile() + "\n");

                            JOptionPane.showMessageDialog(this,
                                    "Successfully appended to file, refresh or view data.",
                                    "Appended File",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Load button
            JButton loadFile = new JButton("Load another file");
            add(loadFile);
            loadFile.setBounds(20, 90, 150, 30);
            loadFile.addActionListener(e -> {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Select a csv or txt to load");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter =
                        new FileNameExtensionFilter("txt or csv files", "csv",
                                "txt");
                jfc.addChoosableFileFilter(filter);
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    try {
                        List<Float> load =
                                ReadFile.readFileByName(jfc.getSelectedFile().getPath());
                        for (Float i : load) {
                            float higher = i.compareTo(SetBoundary.getHigherBound());
                            float lower = i.compareTo(SetBoundary.getLowerBound());
                            if (higher > 0 || lower < 0) {
                                log.setOutOfBoundsVal(i);
                                log.updateOutOfBoundsVal();
                                boundaryFlag = false;
                                break;
                            }
                        }
                        if(errorFlag)
                            errorFlag = false;
                        else if (!boundaryFlag) {
                            JOptionPane.showMessageDialog(jfc,
                                    "The file contains out of bounds data!",
                                    "Data Boundary Error",
                                    JOptionPane.ERROR_MESSAGE);
                            log.addError(1);
                            MainPage.updateReport("Unsuccessfully loaded program with the file: "
                                    + jfc.getSelectedFile() + "\n");
                        } else {
                            reportContent = "";
                            reportContent ="Bounds are: " + SetBoundary.getLowerBound() + ", " +
                                    SetBoundary.getHigherBound();
                            initialFileCreation = true;
                            fileData = load; // Load to
                            setTitle("Grade Analytics - Main Page : " + jfc.getSelectedFile().getName());
                            MainPage.updateReport("\nNew Data Set - Successfully loaded program with the file: "
                                    + jfc.getSelectedFile() + "\n");

                            JOptionPane.showMessageDialog(this,
                                    "Successfully loaded file, refresh or view data.",
                                    "Loaded File",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            Analysis analysisPanel = new Analysis();
            DistributionGraph distribution = new DistributionGraph();

            //set boundaries for the main page
            JLabel lowerBound = new JLabel("Lower bound");
            JTextField tfLower = new JTextField(50);
            lowerBound.setLabelFor(tfLower);
            setLayout(null);
            tfLower.setText(Float.toString(SetBoundary.getLowerBound()));
            add(lowerBound);
            add(tfLower);
            lowerBound.setBounds(500, 20, 120, 30);
            tfLower.setBounds(500, 50, 50, 30);

            JLabel upperBound = new JLabel("Upper bound");
            JTextField tfUpper = new JTextField(50);
            upperBound.setLabelFor(tfUpper);
            tfUpper.setText(Float.toString(SetBoundary.getHigherBound()));
            add(upperBound);
            add(tfUpper);
            upperBound.setBounds(600, 20, 120, 30);
            tfUpper.setBounds(600, 50, 50, 30);

            // Error: Bounds can't be empty
            JLabel emptyBounds = new JLabel("Upper / Lower Bound can't be empty");
            emptyBounds.setBounds(370, 30, 300, 30);
            add(emptyBounds);
            emptyBounds.setVisible(false);

            //set button for the main page
            JButton setBoundaries = new JButton("Set Bounds");
            setBoundaries.setBounds(700, 50, 120, 30);
            add(setBoundaries);
            setBoundaries.addActionListener(e -> {
                if (!tfLower.getText().isEmpty() && !tfUpper.getText().isEmpty()) {
                    try {
                        boolean canUpdateBounds = true;
                        float low = Float.parseFloat(tfLower.getText());
                        float high = Float.parseFloat(tfUpper.getText());

                        for (Float i : fileData) {
                            if (canUpdateBounds && (i < low || i > high)) {
                                canUpdateBounds = false;
                                boundaryFlag = false;
                                log.setOutOfBoundsVal(i);
                                log.updateOutOfBoundsVal();
                                JOptionPane.showMessageDialog(this,
                                        "The file contains out of bounds data!",
                                        "Data Boundary Error",
                                        JOptionPane.ERROR_MESSAGE);
                                log.addError(1);

                                updateReport("Unsuccessfully set bounds to: " + low + ", " + high + "\n");
                            }
                        }
                        if(canUpdateBounds) {
                            SetBoundary.updateBoundaries(low, high);
                            distribution.update();
                            analysisPanel.update();
                            boundaryFlag = true;
                            JOptionPane.showMessageDialog(this,
                                    "Bounds set successfully",
                                    "Boundaries Set",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        boundaryFlag = true; //Revert flag for next load or boundary check
                    } catch (NumberFormatException ex5) {
                        JOptionPane.showMessageDialog(this,
                                "Please enter a number",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                        log.addError(2);

                        updateReport("Unsuccessfully set bounds \n");
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Upper / lower bounds can't be empty",
                            "Empty Bounds",
                            JOptionPane.ERROR_MESSAGE);
                    log.addError(1);
                    updateReport("Unsuccessfully set bounds \n");
                }
            });

            //Create Report Button
            JButton createReport = new JButton("Create Report");
            add(createReport);
            createReport.setBounds(1075, 50, 125, 30);
            createReport.addActionListener(e -> {
                   createReportFile();

                   JOptionPane.showMessageDialog(this,
                            "Report created successfully",
                            "Report Created",
                            JOptionPane.INFORMATION_MESSAGE);
            });

            //Add button - Nick
            //Input label and text field
            JLabel inputLabel = new JLabel("Input");
            JTextField inputData = new JTextField(50);
            inputLabel.setLabelFor(inputData);
            inputData.setBounds(240, 90, 40, 30);
            inputLabel.setBounds(200, 90, 60, 30);
            inputData.setVisible(true);
            add(inputData);
            add(inputLabel);

            //Submitted text field
            JLabel sentValue = new JLabel("Inserted");
            sentValue.setBounds(370,90,60,30);
            sentValue.setVisible(false);
            add(sentValue);

            //empty input text field
            JLabel emptyValue = new JLabel("Empty Input");
            emptyValue.setBounds(370,90,80,30);
            emptyValue.setVisible(false);
            add(emptyValue);

            //submit button
            JButton submitValue = new JButton("Submit");
            submitValue.setBounds(280,90,80,30);
            submitValue.setVisible(true);
            add(submitValue);
            submitValue.addActionListener(e -> {
                emptyValue.setVisible(false);
                sentValue.setVisible(false);
                if (!inputData.getText().isEmpty()) { //catching empty input
                    try {
                        float value = Float.parseFloat(inputData.getText());
                        float hB = SetBoundary.getHigherBound();
                        float lB = SetBoundary.getLowerBound();
                        emptyValue.setVisible(false);

                        if(value >= lB && value <= hB) {
                            fileData.add(value);
                            sentValue.setVisible(true);
                            updateReport("Successfully added keyboard input: " + value + "\n");
                            JOptionPane.showMessageDialog(this,
                                    "Successfully added keyboard input, refresh or view data.",
                                    "Added Keyboard Input",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            updateReport("Unsuccessfully added keyboard input: " + value + "\n");
                            log.setOutOfBoundsVal(value);
                            log.updateOutOfBoundsVal();
                            JOptionPane.showMessageDialog(this,
                                    "Value is out of bounds",
                                    "Out of Bounds",
                                    JOptionPane.ERROR_MESSAGE);
                            log.addError(1);
                            sentValue.setVisible(false);
                        }

                    } catch (NumberFormatException ex5) {
                        JOptionPane.showMessageDialog(this,
                                "Please enter a number",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                        updateReport("Unsuccessfully added keyboard input\n");
                        log.addError(2);
                    }
                } else {
                    emptyValue.setVisible(true);
                    sentValue.setVisible(false);
                    updateReport("Unsuccessfully added keyboard input\n");
                }
            });

            //Delete Button - Nick
            //Remove section
            JLabel deleteSection = new JLabel("Keyboard Operations:");
            deleteSection.setFont(
                    new Font("Serif", Font.BOLD, 18));
            deleteSection.setBounds(200, 20, 200, 30);
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
            JLabel deletedValue = new JLabel("Deleted");
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
                emptyDValue.setVisible(false);
                deletedValue.setVisible(false);
                if (!deleteData.getText().isEmpty()) { //catching empty input
                    try {
                        float value = Float.parseFloat(deleteData.getText());
                        if(fileData.remove(Float.valueOf(value))) {
                            updateReport("Successfully deleted keyboard input: " + value + "\n");
                            deletedValue.setVisible(true);
                            emptyDValue.setVisible(false);
                            JOptionPane.showMessageDialog(this,
                                    "Successfully deleted keyboard input, refresh or view data.",
                                    "Deleted Keyboard Input",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                            updateReport("Unsuccessfully deleted keyboard input: " + value + "\n");
                            JOptionPane.showMessageDialog(this,
                                    "Number is not present",
                                    "Invalid input detected",
                                    JOptionPane.ERROR_MESSAGE);
                            log.addError(4);
                        }

                    } catch (NumberFormatException ex5) {
                        JOptionPane.showMessageDialog(this,
                                "Please enter a number",
                                "Invalid input detected",
                                JOptionPane.ERROR_MESSAGE);
                        updateReport("Unsuccessfully deleted keyboard input\n");
                        log.addError(2);
                    }
                } else {
                    emptyDValue.setVisible(true);
                    deletedValue.setVisible(false);
                    updateReport("Unsuccessfully deleted keyboard input\n");
                }
            });

            // Display TextArea
            JTextArea displayData = new JTextArea();
            displayData.setEditable(false);
            displayData.setBounds(20, 150, 700, 450);
            displayData.setLineWrap(true);
            displayData.setVisible(false);
            displayData.setText("");
            fileData.sort(null);
            updateDisplay(displayData);
            displayData.setVisible(true);
            JScrollPane displayScrollPane = new JScrollPane(displayData);
            add(displayScrollPane);


            // Declaring Tabbed pane here to change active tab on button click
            JTabbedPane tabbedPane = new JTabbedPane();


            // Refresh Section Label
            JLabel refreshSection = new JLabel("Refresh:");
            refreshSection.setFont(
                    new Font("Serif", Font.BOLD, 18));
            refreshSection.setBounds(20, 160, 200, 30);
            add(refreshSection);

            // Display data button
            JButton display = new JButton("Display Data");
            display.setBounds(20, 190, 150, 30);
            add(display);

            //Needs to display in 4 columns with descending order
            display.addActionListener(e -> {
                updateDisplay(displayData);
                refreshSwitch = true;
                displayData.setVisible(true);
                tabbedPane.setSelectedIndex(0);
                updateReport("Refreshed and viewed displayed data\n");
            });

            // Display Graph button
            JButton displayGraph = new JButton("Display Graph");
            displayGraph.setBounds(20, 240, 150, 30);
            add(displayGraph);

            // Calculate minimum and maximum value of dataset:
            float max = SetBoundary.getHigherBound();
            float min = SetBoundary.getLowerBound();

            int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0,
                    seven = 0, eight = 0, nine = 0, ten = 0;

            for (Float i : fileData) {
                float percentage = (i - min) / (max - min);
                if (percentage <= (float) 0.1)
                    one += 1;
                else if (percentage > (float) 0.1 && percentage <= (float) 0.2)
                    two += 1;
                else if (percentage > (float) 0.2 && percentage <=  (float) 0.3)
                    three += 1;
                else if (percentage > (float) 0.3 && percentage <= (float) 0.4)
                    four += 1;
                else if (percentage > (float) 0.4 && percentage <= (float) 0.5)
                    five += 1;
                else if (percentage > (float) 0.5 && percentage <= (float) 0.6)
                    six += 1;
                else if (percentage > (float) 0.6 && percentage <= (float) 0.7)
                    seven += 1;
                else if (percentage > (float) 0.7 && percentage <= (float) 0.8)
                    eight += 1;
                else if (percentage > (float) 0.8 && percentage <= (float) 0.9)
                    nine += 1;
                else if (percentage > (float) 0.9)
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
                refreshSwitch = true;
                updateDisplayGraph(chartPlot);
                tabbedPane.setSelectedIndex(1);
                updateReport("Refreshed and viewed data bar graph\n");
            });

            // Display Analysis button
            JButton displayAnalysis = new JButton("Data Analysis");
            displayAnalysis.setBounds(20, 290, 150, 30);
            add(displayAnalysis);

            //Analysis analysisPanel = new Analysis();
            displayAnalysis.addActionListener(e -> {
                refreshSwitch = true;
                analysisPanel.update();
                analysisPanel.repaint();
                tabbedPane.setSelectedIndex(2);
                updateReport("Refreshed and viewed data analysis\n");
            });

            // Display distribution graph button
            JButton displayDistribution = new JButton("Distribution");
            displayDistribution.setBounds(20, 340, 150, 30);
            add(displayDistribution);

            //DistributionGraph distribution = new DistributionGraph();
            displayDistribution.addActionListener(e -> {
                refreshSwitch = true;
                distribution.update();
                distribution.repaint();
                tabbedPane.setSelectedIndex(3);
                updateReport("Refreshed and viewed data distribution\n");
            });

            tabbedPane.addTab("Display Data", displayScrollPane);
            tabbedPane.addTab("Display Graph", chartPanel);
            tabbedPane.addTab("Data Analysis", analysisPanel);
            tabbedPane.addTab("Distribution", distribution);
            tabbedPane.setBounds(200, 150, 1000, 450);

            //Change listener to update data on tab switch
            ChangeListener changeListener = new ChangeListener() {
                public void stateChanged(ChangeEvent change) {
                    JTabbedPane source = (JTabbedPane) change.getSource();
                    int index = source.getSelectedIndex();
                    if (index == 0) {
                        updateDisplay(displayData);
                        if(!refreshSwitch)
                            updateReport("Viewed displayed data\n");
                        refreshSwitch = false;
                    } else if (index == 1) {
                        updateDisplayGraph(chartPlot);
                        if(!refreshSwitch)
                            updateReport("Viewed data bar graph\n");
                        refreshSwitch = false;
                    }
                    else if (index == 2) {
                        analysisPanel.update();
                        analysisPanel.repaint();
                        if(!refreshSwitch)
                            updateReport("Viewed data analysis\n");
                        refreshSwitch = false;
                    } else if (index == 3) {
                        distribution.update();
                        distribution.repaint();
                        if(!refreshSwitch)
                            updateReport("Viewed data distribution\n");
                        refreshSwitch = false;
                    }
                }
            };

            tabbedPane.addChangeListener(changeListener);
            add(tabbedPane);
            tabbedPane.setVisible(true);

        } // else Display main page
    }// End Init ui

    //Function for updating display graph
    private void updateDisplayGraph(CategoryPlot catPlot) {
        float max = SetBoundary.getHigherBound();
        float min = SetBoundary.getLowerBound();

        int one = 0, two = 0, three = 0, four = 0, five = 0,
                six = 0, seven = 0, eight = 0, nine = 0, ten = 0;

        for (Float i : fileData) {
            float percentage = (i - min) / (max - min);
            if (percentage <= (float) 0.1)
                one += 1;
            else if (percentage > (float) 0.1 && percentage <= (float) 0.2)
                two += 1;
            else if (percentage > (float) 0.2 && percentage <=  (float) 0.3)
                three += 1;
            else if (percentage > (float) 0.3 && percentage <= (float) 0.4)
                four += 1;
            else if (percentage > (float) 0.4 && percentage <= (float) 0.5)
                five += 1;
            else if (percentage > (float) 0.5 && percentage <= (float) 0.6)
                six += 1;
            else if (percentage > (float) 0.6 && percentage <= (float) 0.7)
                seven += 1;
            else if (percentage > (float) 0.7 && percentage <= (float) 0.8)
                eight += 1;
            else if (percentage > (float) 0.8 && percentage <= (float) 0.9)
                nine += 1;
            else if (percentage > (float) 0.9)
                ten += 1;
        }

        DefaultCategoryDataset dataSet1 = new DefaultCategoryDataset();

        dataSet1.addValue(one, "0-10 %", "0-10 %");
        dataSet1.addValue(two, "11-20 %", "11-20 %");
        dataSet1.addValue(three, "21-30 %", "21-30 %");
        dataSet1.addValue(four, "31-40 %", "31-40 %");
        dataSet1.addValue(five, "41-50 %", "41-50 %");
        dataSet1.addValue(six, "51-60 %", "51-60 %");
        dataSet1.addValue(seven, "61-70 %", "61-70 %");
        dataSet1.addValue(eight, "71-80 %", "71-80 %");
        dataSet1.addValue(nine, "81-90 %", "81-90 %");
        dataSet1.addValue(ten, "91-100 %", "91-100 %");

        // Set new dataset
        catPlot.setDataset(dataSet1);
    }

    //Function for updating display data
    private void updateDisplay(JTextArea displayData) {
        //Wipe the previous text data
        displayData.setText("");
        fileData.sort(null);

        //Making buckets to hold "column" data in descending order
        int count = 0; for(Float element : fileData) { count++; }
        int bucketCapacity = count / 4;
        int bucketOverflow = count % 4;
        int bucketLevel1 = 0, bucketLevel2 = 0, bucketLevel3 = 0;
        String bucket1 = "", bucket2 = "", bucket3 = "", bucket4 = "";

        //Fill buckets to their capacity, any overflow goes into the first bucket
        for (int i = count-1; i >= 0; i--) {
            if(bucketLevel1 < (bucketCapacity+bucketOverflow)) {
                bucket1 += (fileData.get(i) + ",");
                bucketLevel1++;
            }
            else if(bucketLevel2 < bucketCapacity) {
                bucket2 += (fileData.get(i) + ",");
                bucketLevel2++;
            }
            else if(bucketLevel3 < bucketCapacity) {
                bucket3 += (fileData.get(i) +",");
                bucketLevel3++;
            }
            else {
                bucket4 += (fileData.get(i) +",");
            }
        }

        //Empty buckets into arrays so the individual strings can be printed
        String emptiedBucket1[] = bucket1.split(",");
        String emptiedBucket2[] = bucket2.split(",");
        String emptiedBucket3[] = bucket3.split(",");
        String emptiedBucket4[] = bucket4.split(",");

        //Print the bucket contents in descending order with 4 columns
        for(int i = 0; i < (bucketCapacity+bucketOverflow); i++) {
            String padded = "";
            if(i < emptiedBucket1.length) {
                padded = String.format("%-30s", emptiedBucket1[i]);
                displayData.append(padded);
            }
            if(i < emptiedBucket2.length) {
                padded = String.format("%-30s", emptiedBucket2[i]);
                displayData.append(padded);
            }
            if(i < emptiedBucket3.length) {
                padded = String.format("%-30s", emptiedBucket3[i]);
                displayData.append(padded);
            }
            if(i < emptiedBucket4.length) {
                padded = String.format("%-30s", emptiedBucket4[i]);
                displayData.append(padded);
            }

            displayData.append("\n");
        }
        displayData.setVisible(true);
    }

    public static void updateReport(String content) {
        reportContent += content;
    }

    private static void createReportFile() {
        File report = new File("report" + fileNumber + ".txt");
        while(initialFileCreation && report.exists()) {
            fileNumber++;
            report = new File("report" + fileNumber + ".txt");
        }
        try {
            if (initialFileCreation) {
                initialFileCreation = false;
                FileWriter fw = new FileWriter(report, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("Created Report:\n\n");
                bw.write(reportContent);
                reportContent = "";
                bw.close();
                fw.close();

                reportFlag = true;
            } else {
                FileWriter fw = new FileWriter(report, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(reportContent);
                reportContent = "";
                bw.close();
                fw.close();

                reportFlag = true;
            }
        }
        catch(IOException e) {}
    }
}
