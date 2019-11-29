import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

class Analysis extends JPanel {

    private JTextField count, high, low, mean, median, mode;
    private List<Integer> data;

    Analysis(List<Integer> inputData) {
        data = inputData;
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

    private void InitUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        // Tab 3 - Analyse Data
        // Count, Mean Median and Mode fields
        JLabel labelCount = new JLabel("Count");
        labelCount.setLabelFor(count);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;

        //labelCount.setFont(new Font("Serif",Font.BOLD, 14));
        //labelCount.setBounds(400,250,200,30);
        add(labelCount, constraints);

        JLabel labelHigh = new JLabel("High");
        labelHigh.setLabelFor(high);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 2;
        //labelMode.setFont(new Font("Serif",Font.BOLD, 14));
        //labelMode.setBounds(400,400,200,30);
        add(labelHigh, constraints);

        JLabel labelLow = new JLabel("Low");
        labelLow.setLabelFor(low);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 3;
        //labelMode.setFont(new Font("Serif",Font.BOLD, 14));
        //labelMode.setBounds(400,400,200,30);
        add(labelLow, constraints);


        JLabel labelMean = new JLabel("Mean");
        labelMean.setLabelFor(mean);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 4;
        //labelMean.setFont(new Font("Serif",Font.BOLD, 14));
        //labelMean.setBounds(400,3000,200,30);
        add(labelMean, constraints);

        JLabel labelMedian = new JLabel("Median");
        labelMedian.setLabelFor(median);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 5;
        //labelMedian.setFont(new Font("Serif",Font.BOLD, 14));
        //labelMedian.setBounds(400,350,200,30);
        add(labelMedian, constraints);

        JLabel labelMode = new JLabel("Mode");
        labelMode.setLabelFor(mode);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 6;
        //labelMode.setFont(new Font("Serif",Font.BOLD, 14));
        //labelMode.setBounds(400,400,200,30);
        add(labelMode, constraints);



        // TextFields and Calculations

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(Integer i : data){
            if(max < i)
                max = i;
            if(min > i)
                min = i;
        }

        int sum = 0;
        int cnt = 0;
        for (Integer i : data) {
            sum = sum + i;
            cnt += 1;
        }


        count.setText(Integer.toString(cnt));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 1;
        //mean.setBounds(500,650,200, 30);
        add(count, constraints);

        high.setText(Integer.toString(max));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 2;
        //mean.setBounds(500,650,200, 30);
        add(high, constraints);

        low.setText(Integer.toString(min));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 3;
        //mean.setBounds(500,650,200, 30);
        add(low, constraints);

        mean.setText(Integer.toString(sum / cnt));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 4;
        //mean.setBounds(500,550,200, 30);
        add(mean, constraints);


        //Median
        if(cnt % 2 != 0){
            median.setText(Integer.toString(data.get(cnt/2)));
        }else{
            double med =
                    ((double)data.get((cnt)/2) + (double)data.get((cnt/2)+1)) / 2;
            median.setText(Double.toString(med));
        }

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 5;
        //mean.setBounds(500,550,200, 30);
        add(median, constraints);

        int result = findMode(data);

        mode.setText(Integer.toString(result));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 6;
        //mean.setBounds(500,550,200, 30);
        add(mode, constraints);
    }

    private int findMode(List<Integer> fileData)
    {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        int max  = 1;
        int mode = 0;

        for (Integer fileDatum : fileData) {
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
        return mode;
    }
}
