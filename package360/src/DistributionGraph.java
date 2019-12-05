import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class DistributionGraph extends JPanel {

    private JTextField firstGroup, secondGroup, thirdGroup, fourthGroup, fifthGroup,
        sixthGroup, seventhGroup, eighthGroup, ninthGroup, tenthGroup;

    DistributionGraph() {
        InitUI();
    }

    //Graph needs to resize to fit panel
    private void InitUI() {
        setLayout(null);
        List<Float> fileData = MainPage.getFileData();
        firstGroup = new JTextField(); secondGroup = new JTextField();
        thirdGroup = new JTextField(); fourthGroup = new JTextField();
        fifthGroup = new JTextField(); sixthGroup = new JTextField();
        seventhGroup = new JTextField(); eighthGroup = new JTextField();
        ninthGroup = new JTextField(); tenthGroup = new JTextField();

        // Calculate minimum and maximum value of dataset:
        float max1 = SetBoundary.getHigherBound();
        float min1 = SetBoundary.getLowerBound();

        int first = 0, second = 0, third = 0, fourth = 0, fifth = 0,
                sixth = 0, seventh = 0, eighth = 0, ninth = 0, tenth = 0;

        float sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0, sum6 = 0,
                sum7 = 0, sum8 = 0, sum9 = 0, sum10 = 0;

        float avg1 = 0, avg2 = 0, avg3 = 0, avg4 = 0, avg5 = 0, avg6 = 0,
                avg7 = 0, avg8 = 0, avg9 = 0, avg10 = 0;

        //Find count and total of each percentage group
        for (Float i : fileData) {
            float percentage = (i - min1) / (max1 - min1);
            if (percentage <= (float) 0.1){
                sum1 = sum1 + percentage;
                first += 1;
            } else if (percentage > (float) 0.1 && percentage <= (float) 0.2) {
                sum2 = sum2 + percentage;
                second += 1;
            } else if (percentage > (float) 0.2 && percentage <= (float) 0.3) {
                sum3 = sum3 + percentage;
                third += 1;
            } else if (percentage > (float) 0.3 && percentage <= (float) 0.4) {
                sum4 = sum4 + percentage;
                fourth += 1;
            } else if (percentage > (float) 0.4 && percentage <= (float) 0.5) {
                sum5 = sum5 + percentage;
                fifth += 1;
            } else if (percentage > (float) 0.5 && percentage <= (float) 0.6) {
                sum6 = sum6 + percentage;
                sixth += 1;
            } else if (percentage > (float) 0.6 && percentage <= (float) 0.7) {
                sum7 = sum7 + percentage;
                seventh += 1;
            } else if (percentage > (float) 0.7 && percentage <= (float) 0.8) {
                sum8 = sum8 + percentage;
                eighth += 1;
            } else if (percentage > (float) 0.8 && percentage <= (float) 0.9) {
                sum9 = sum9 + percentage;
                ninth += 1;
            } else if (percentage > (float) 0.9) {
                sum10 = sum10 + percentage;
                tenth += 1;
            }
        }

        DefaultCategoryDataset dataSet1 = MainPage.getDistributionDataset();

        //Need to check for dividing by 0, not every category may be filled
        if(first != 0)
            avg1 = sum1 / first;

        if(second != 0)
            avg2 = sum2 / second;

        if(third != 0)
            avg3 = sum3 / third;

        if(fourth != 0)
            avg4 = sum4 / fourth;

        if(fifth != 0)
            avg5 = sum5 / fifth;

        if(sixth != 0)
            avg6 = sum6 / sixth;

        if(seventh != 0)
            avg7 = sum7 / seventh;

        if(eighth != 0)
            avg8 = sum8 / eighth;

        if(ninth != 0)
            avg9 = sum9 / ninth;

        if(tenth != 0)
            avg10 = sum10 / tenth;

        //Adding labels
        JLabel labelFirst = new JLabel("0-10% Group");
        labelFirst.setLabelFor(firstGroup);
        labelFirst.setFont(new Font("Serif", Font.BOLD, 14));
        labelFirst.setBounds(20, 10, 150, 30);

        JLabel labelSecond = new JLabel("10-20% Group");
        labelSecond.setLabelFor(secondGroup);
        labelSecond.setFont(new Font("Serif", Font.BOLD, 14));
        labelSecond.setBounds(20, 50, 150, 30);

        JLabel labelThird = new JLabel("20-30% Group");
        labelThird.setLabelFor(thirdGroup);
        labelThird.setFont(new Font("Serif", Font.BOLD, 14));
        labelThird.setBounds(20, 90, 150, 30);

        JLabel labelFourth = new JLabel("30-40% Group");
        labelFourth.setLabelFor(fourthGroup);
        labelFourth.setFont(new Font("Serif", Font.BOLD, 14));
        labelFourth.setBounds(20, 130, 150, 30);

        JLabel labelFifth = new JLabel("40-50% Group");
        labelFifth.setLabelFor(fifthGroup);
        labelFifth.setFont(new Font("Serif", Font.BOLD, 14));
        labelFifth.setBounds(20, 170, 150, 30);

        JLabel labelSixth = new JLabel("50-60% Group");
        labelSixth.setLabelFor(sixthGroup);
        labelSixth.setFont(new Font("Serif", Font.BOLD, 14));
        labelSixth.setBounds(20, 210, 150, 30);

        JLabel labelSeventh = new JLabel("60-70% Group");
        labelSeventh.setLabelFor(seventhGroup);
        labelSeventh.setFont(new Font("Serif", Font.BOLD, 14));
        labelSeventh.setBounds(20, 250, 150, 30);

        JLabel labelEighth = new JLabel("70-80% Group");
        labelEighth.setLabelFor(eighthGroup);
        labelEighth.setFont(new Font("Serif", Font.BOLD, 14));
        labelEighth.setBounds(20, 290, 150, 30);

        JLabel labelNinth = new JLabel("80-90% Group");
        labelNinth.setLabelFor(ninthGroup);
        labelNinth.setFont(new Font("Serif", Font.BOLD, 14));
        labelNinth.setBounds(20, 330, 150, 30);

        JLabel labelTenth = new JLabel("90-100% Group");
        labelTenth.setLabelFor(tenthGroup);
        labelTenth.setFont(new Font("Serif", Font.BOLD, 14));
        labelTenth.setBounds(20, 370, 150, 30);

        add(labelFirst); add(labelSecond); add(labelThird); add(labelFourth);
        add(labelFifth); add(labelSixth); add(labelSeventh); add(labelEighth);
        add(labelNinth); add(labelTenth);

        //Update the data in the GUI
        String avg1S = String.format("%.2f", (avg1*100)); String avg2S = String.format("%.2f", (avg2*100));
        String avg3S = String.format("%.2f", (avg3*100)); String avg4S = String.format("%.2f", (avg4*100));
        String avg5S = String.format("%.2f", (avg5*100)); String avg6S = String.format("%.2f", (avg6*100));
        String avg7S = String.format("%.2f", (avg7*100)); String avg8S = String.format("%.2f", (avg8*100));
        String avg9S = String.format("%.2f", (avg9*100)); String avg10S = String.format("%.2f", (avg10*100));

        firstGroup.setText("Count: " + first + "; Average: " + avg1S + "%");
        firstGroup.setBounds(120, 10, 165, 30);
        firstGroup.setEditable(false);

        secondGroup.setText("Count: " + second + "; Average: " + avg2S + "%");
        secondGroup.setBounds(120, 50, 165, 30);
        secondGroup.setEditable(false);

        thirdGroup.setText("Count: " + third + "; Average: " + avg3S + "%");
        thirdGroup.setBounds(120, 90, 165, 30);
        thirdGroup.setEditable(false);

        fourthGroup.setText("Count: " + fourth + "; Average: " + avg4S + "%");
        fourthGroup.setBounds(120, 130, 165, 30);
        fourthGroup.setEditable(false);

        fifthGroup.setText("Count: " + fifth + "; Average: " + avg5S + "%");
        fifthGroup.setBounds(120, 170, 165, 30);
        fifthGroup.setEditable(false);

        sixthGroup.setText("Count: " + sixth + "; Average: " + avg6S + "%");
        sixthGroup.setBounds(120, 210, 165, 30);
        sixthGroup.setEditable(false);

        seventhGroup.setText("Count: " + seventh + "; Average: " + avg7S + "%");
        seventhGroup.setBounds(120, 250, 165, 30);
        seventhGroup.setEditable(false);

        eighthGroup.setText("Count: " + eighth + "; Average: " + avg8S + "%");
        eighthGroup.setBounds(120, 290, 165, 30);
        eighthGroup.setEditable(false);

        ninthGroup.setText("Count: " + ninth + "; Average: " + avg9S + "%");
        ninthGroup.setBounds(120, 330, 165, 30);
        ninthGroup.setEditable(false);

        tenthGroup.setText("Count: " + tenth + "; Average: " + avg10S + "%");
        tenthGroup.setBounds(120, 370, 165, 30);
        tenthGroup.setEditable(false);

        add(firstGroup); add(secondGroup); add(thirdGroup); add(fourthGroup);
        add(fifthGroup); add(sixthGroup); add(seventhGroup); add(eighthGroup);
        add(ninthGroup); add(tenthGroup);

        // Set new dataset
    }

    public void update() {
        List<Float> fileData = MainPage.getFileData();

        float max1 = SetBoundary.getHigherBound();
        float min1 = SetBoundary.getLowerBound();

        int first = 0, second = 0, third = 0, fourth = 0, fifth = 0,
                sixth = 0, seventh = 0, eighth = 0, ninth = 0, tenth = 0;

        float sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0, sum6 = 0,
                sum7 = 0, sum8 = 0, sum9 = 0, sum10 = 0;

        float avg1 = 0, avg2 = 0, avg3 = 0, avg4 = 0, avg5 = 0, avg6 = 0,
                avg7 = 0, avg8 = 0, avg9 = 0, avg10 = 0;

        //Find count and total of each percentage group
        for (Float i : fileData) {
            float percentage = (i - min1) / (max1 - min1);
            if (percentage <= (float) 0.1){
                sum1 = sum1 + percentage;
                first += 1;
            } else if (percentage > (float) 0.1 && percentage <= (float) 0.2) {
                sum2 = sum2 + percentage;
                second += 1;
            } else if (percentage > (float) 0.2 && percentage <= (float) 0.3) {
                sum3 = sum3 + percentage;
                third += 1;
            } else if (percentage > (float) 0.3 && percentage <= (float) 0.4) {
                sum4 = sum4 + percentage;
                fourth += 1;
            } else if (percentage > (float) 0.4 && percentage <= (float) 0.5) {
                sum5 = sum5 + percentage;
                fifth += 1;
            } else if (percentage > (float) 0.5 && percentage <= (float) 0.6) {
                sum6 = sum6 + percentage;
                sixth += 1;
            } else if (percentage > (float) 0.6 && percentage <= (float) 0.7) {
                sum7 = sum7 + percentage;
                seventh += 1;
            } else if (percentage > (float) 0.7 && percentage <= (float) 0.8) {
                sum8 = sum8 + percentage;
                eighth += 1;
            } else if (percentage > (float) 0.8 && percentage <= (float) 0.9) {
                sum9 = sum9 + percentage;
                ninth += 1;
            } else if (percentage > (float) 0.9) {
                sum10 = sum10 + percentage;
                tenth += 1;
            }
        }

        //Need to check for dividing by 0, not every category may be filled
        if(first != 0)
            avg1 = sum1 / first;

        if(second != 0)
            avg2 = sum2 / second;

        if(third != 0)
            avg3 = sum3 / third;

        if(fourth != 0)
            avg4 = sum4 / fourth;

        if(fifth != 0)
            avg5 = sum5 / fifth;

        if(sixth != 0)
            avg6 = sum6 / sixth;

        if(seventh != 0)
            avg7 = sum7 / seventh;

        if(eighth != 0)
            avg8 = sum8 / eighth;

        if(ninth != 0)
            avg9 = sum9 / ninth;

        if(tenth != 0)
            avg10 = sum10 / tenth;

        //Update the data in the GUI
        String avg1S = String.format("%.2f", (avg1*100)); String avg2S = String.format("%.2f", (avg2*100));
        String avg3S = String.format("%.2f", (avg3*100)); String avg4S = String.format("%.2f", (avg4*100));
        String avg5S = String.format("%.2f", (avg5*100)); String avg6S = String.format("%.2f", (avg6*100));
        String avg7S = String.format("%.2f", (avg7*100)); String avg8S = String.format("%.2f", (avg8*100));
        String avg9S = String.format("%.2f", (avg9*100)); String avg10S = String.format("%.2f", (avg10*100));

        firstGroup.setText("Count: " + first + "; Average: " + avg1S + "%");
        secondGroup.setText("Count: " + second + "; Average: " + avg2S + "%");
        thirdGroup.setText("Count: " + third + "; Average: " + avg3S + "%");
        fourthGroup.setText("Count: " + fourth + "; Average: " + avg4S + "%");
        fifthGroup.setText("Count: " + fifth + "; Average: " + avg5S + "%");
        sixthGroup.setText("Count: " + sixth + "; Average: " + avg6S + "%");
        seventhGroup.setText("Count: " + seventh + "; Average: " + avg7S + "%");
        eighthGroup.setText("Count: " + eighth + "; Average: " + avg8S + "%");
        ninthGroup.setText("Count: " + ninth + "; Average: " + avg9S + "%");
        tenthGroup.setText("Count: " + tenth + "; Average: " + avg10S + "%");
        
    }
}
