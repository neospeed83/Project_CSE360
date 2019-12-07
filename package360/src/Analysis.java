import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * The Analysis class creates and maintains the Analysis functions of the program. Calculations
 * for mean, median, mode, and other analytical statistics and their display are done through
 * this class.
 *
 * @author Akash Devdhar, Matt Hayes, Henry Pearson, Nicholas Vietri
 * 		   CSE 360 Team Project
 *
 */
class Analysis extends JPanel {

    private JTextField count, high, low, mean, median, mode;
    private List<Float> data;

    /**
     * Constructor for the Analysis class, initializes the GUI components of
     * the class.
     */
    Analysis() {
        data = MainPage.getFileData();
        data.sort(null);
        count = new JTextField();
        count.setEditable(false);
        mean = new JTextField();
        mean.setEditable(false);
        median = new JTextField();
        median.setEditable(false);
        mode = new JTextField();
        mode.setEditable(false);
        high = new JTextField();
        high.setEditable(false);
        low = new JTextField();
        low.setEditable(false);
        InitUI();
    }

    /**
     * Method that adds and updates the GUI components of the class.
     */
    private void InitUI() {
        setLayout(null);

        // Tab 3 - Analyse Data
        // Count, Mean Median and Mode fields

        JLabel labelCount = new JLabel("Count");
        labelCount.setLabelFor(count);
        labelCount.setFont(new Font("Serif", Font.BOLD, 14));
        labelCount.setBounds(20, 50, 150, 30);
        add(labelCount);

        JLabel labelHigh = new JLabel("High");
        labelHigh.setLabelFor(high);
        labelHigh.setFont(new Font("Serif", Font.BOLD, 14));
        labelHigh.setBounds(20, 100, 150, 30);
        add(labelHigh);

        JLabel labelLow = new JLabel("Low");
        labelLow.setLabelFor(low);
        labelLow.setFont(new Font("Serif", Font.BOLD, 14));
        labelLow.setBounds(20, 150, 150, 30);
        add(labelLow);


        JLabel labelMean = new JLabel("Mean");
        labelMean.setLabelFor(mean);
        labelMean.setFont(new Font("Serif", Font.BOLD, 14));
        labelMean.setBounds(20, 200, 150, 30);
        add(labelMean);

        JLabel labelMedian = new JLabel("Median");
        labelMedian.setLabelFor(median);
        labelMedian.setFont(new Font("Serif", Font.BOLD, 14));
        labelMedian.setBounds(20, 250, 150, 30);
        add(labelMedian);

        JLabel labelMode = new JLabel("Mode");
        labelMode.setLabelFor(mode);
        labelMode.setFont(new Font("Serif", Font.BOLD, 14));
        labelMode.setBounds(20, 300, 200, 30);
        add(labelMode);


        // TextFields and Calculations
        data = MainPage.getFileData();

        float max = Integer.MIN_VALUE;
        float min = Integer.MAX_VALUE;

        for (Float i : data) {
            if (max < i)
                max = i;
            if (min > i)
                min = i;
        }

        float sum = 0;
        int cnt = 0;
        for (Float i : data) {
            sum = sum + i;
            cnt += 1;
        }


        if(!data.isEmpty())
            count.setText(Integer.toString(cnt));
        else
            count.setText("");
        count.setBounds(120, 50, 200, 30);
        add(count);

        if(!data.isEmpty())
            high.setText(Float.toString(max));
        else
            high.setText("");
        high.setBounds(120, 100, 200, 30);
        add(high);

        if(!data.isEmpty())
            low.setText(Float.toString(min));
        else
            low.setText("");
        low.setBounds(120, 150, 200, 30);
        add(low);

        if(!data.isEmpty())
            mean.setText(String.format("%.2f", sum / cnt));
        else
            mean.setText("");
        mean.setBounds(120, 200, 200, 30);
        add(mean);


        //Median
        if(!data.isEmpty()) {
            int mid = cnt / 2;
            if (cnt % 2 != 0) {
                //Take middle number
                median.setText(String.format("%.2f", data.get(mid)));
            } else {
                //Add middle two numbers and divide by 2
                float medianVal = (data.get(mid) + data.get(mid - 1)) / 2;
                median.setText(String.format("%.2f", medianVal));
            }
        }
        else
            median.setText("");

        median.setBounds(120, 250, 200, 30);
        add(median);

        int result = findMode(data);

        if(!data.isEmpty())
            mode.setText(Integer.toString(result));
        else
            mode.setText("");
        mode.setBounds(120, 300, 200, 30);
        add(mode);
    }

    /**
     * Method that updates the statistical values for the calculations of the class;
     * maximum and minimum value, count of values, mean, median, and mode.
     */
    public void update(){
        data = MainPage.getFileData();

        float max = Integer.MIN_VALUE;
        float min = Integer.MAX_VALUE;

        for (Float i : data) {
            if (max < i)
                max = i;
            if (min > i)
                min = i;
        }

        float sum = 0;
        int cnt = 0;
        for (Float i : data) {
            sum = sum + i;
            cnt += 1;
        }


        if (!data.isEmpty()) {
            count.setText(Integer.toString(cnt));
            high.setText(Float.toString(max));
            low.setText(Float.toString(min));
            mean.setText(String.format("%.2f", sum / cnt));
        }
        else {
            count.setText("");
            high.setText("");
            low.setText("");
            mean.setText("");
            median.setText("");
            mode.setText("");
        }

        //Median, needs to be looked at
        if(!data.isEmpty()) {
            int mid = cnt / 2;
            if (cnt % 2 != 0) {
                //Take middle number
                median.setText(String.format("%.2f", data.get(mid)));
            } else {
                //Add middle two numbers and divide by 2
                float medianVal = (data.get(mid) + data.get(mid - 1)) / 2;
                median.setText(String.format("%.2f", medianVal));
            }
        }

        //Mode
        if(!data.isEmpty()) {
            int result = findMode(data);
            mode.setText(Integer.toString(result));
        }
    }

    /**
     * Method that finds the mode of the data set.
     *
     * @param fileData the data set to find the mode of
     * @return the mode of the data set
     */
    private int findMode(List<Float> fileData) {
        HashMap<Float, Integer> hashMap = new HashMap<>();
        int max = 1;
        float mode = 0;

        if(fileData.size() > 1) {
            for (Float fileDatum : fileData) {
                if (hashMap.get(fileDatum) != null) { // Already found element
                    int count = hashMap.get(fileDatum);
                    count++;
                    hashMap.put(fileDatum, count);

                    if (count > max) {
                        max = count;
                        mode = fileDatum;
                    }
                } else // Newly found element
                    hashMap.put(fileDatum, 1);
            }
        }
        else if(fileData.size() == 1)
            mode = fileData.get(0);
        return (int) mode;
    }
}
