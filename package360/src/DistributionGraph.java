import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.List;

class DistributionGraph extends JPanel {

    DistributionGraph() {
        InitUI();
    }

    private void InitUI() {

        List<Integer> fileData = MainPage.getFileData();

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

        int sum1 = 0, sum2 = 0, sum3 = 0, sum4 = 0, sum5 = 0, sum6 = 0,
                sum7 = 0, sum8 = 0, sum9 = 0, sum10 = 0;

        for (Integer i : fileData) {
            if (i <= (min1 + max1) * 0.1) {
                sum1 = sum1 + i;
                first += 1;
            } else if (i > (min1 + max1) * 0.1 && i <= (min1 + max1) * 0.2) {
                sum2 = sum2 + i;
                second += 1;
            } else if (i > (min1 + max1) * 0.2 && i <= (min1 + max1) * 0.3) {
                sum3 = sum3 + i;
                third += 1;
            } else if (i > (min1 + max1) * 0.3 && i <= (min1 + max1) * 0.4) {
                sum4 = sum4 + i;
                fourth += 1;
            } else if (i > (min1 + max1) * 0.4 && i <= (min1 + max1) * 0.5) {
                sum5 = sum5 + i;
                fifth += 1;
            } else if (i > (min1 + max1) * 0.5 && i <= (min1 + max1) * 0.6) {
                sum6 = sum6 + i;
                sixth += 1;
            } else if (i > (min1 + max1) * 0.6 && i <= (min1 + max1) * 0.7) {
                sum7 = sum7 + i;
                seventh += 1;
            } else if (i > (min1 + max1) * 0.7 && i <= (min1 + max1) * 0.8) {
                sum8 = sum8 + i;
                eighth += 1;
            } else if (i > (min1 + max1) * 0.8 && i <= (min1 + max1) * 0.9) {
                sum9 = sum9 + i;
                ninth += 1;
            } else if (i > (min1 + max1) * 0.9) {
                sum10 = sum10 + i;
                tenth += 1;
            }
        }

        DefaultCategoryDataset dataSet1 = MainPage.getDistributionDataset();

        dataSet1.addValue(first, "", "0-10 % avg " + (Integer) (sum1 / first));
        dataSet1.addValue(second, "", "11-20 % avg " + (Integer) (sum2 / second));
        dataSet1.addValue(third, "", "21-30 % avg " + (Integer) (sum3 / third));
        dataSet1.addValue(fourth, "", "31-40 % avg " + (Integer) (sum4 / fourth));
        dataSet1.addValue(fifth, "", "41-50 % avg " + (Integer) (sum5 / fifth));
        dataSet1.addValue(sixth, "", "51-60 % avg " + (Integer) (sum6 / sixth));
        dataSet1.addValue(seventh, "", "61-70 % avg " + (Integer) (sum7 / seventh));
        dataSet1.addValue(eighth, "", "71-80 % avg " + (Integer) (sum8 / eighth));
        dataSet1.addValue(ninth, "", "81-90 % avg " + (Integer) (sum9 / ninth));
        dataSet1.addValue(tenth, "", "91-100 % avg " + (Integer) (sum10 / tenth));

        // Created Graph with current data
        JFreeChart graph = ChartFactory.createBarChart(
                "Data Distribution and Average",
                "Percentage Range",
                "Count",
                dataSet1, PlotOrientation.HORIZONTAL,
                true, false, false);

        // Setting the Y-axis to show only numbers instead of decimals
        CategoryPlot chartPlot = graph.getCategoryPlot();
        ValueAxis yAxis = chartPlot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        ChartPanel chartPanel = new ChartPanel(graph);
        add(chartPanel);
        // Set new dataset
    }

    void updateGraph() {

    }
}
