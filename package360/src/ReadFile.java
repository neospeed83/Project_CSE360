// Java program to illustrate reading data from file
// using nio.File

import javax.swing.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

/**
 * The ReadFile class reads the inputted file for the program.
 *
 * @author Akash Devdhar, Matt Hayes, Henry Pearson, Nicholas Vietri
 * 		   CSE 360 Team Project
 *
 */
class ReadFile {

    /**
     * Method that reads a file and catches accompanying errors for the data set of the program.
     * If no errors are caught, the method returns the data set for the program.
     *
     * @param fileName file to read
     * @return a list of data values
     */
    static List<Float> readFileByName(String fileName) {

        //create error log
        ErrorLog log = ErrorLog.getInstance();

        List<String> lines;
        int lineNumber = 0;
        String[] elements = null;
        List<Float> result = new ArrayList<>(Collections.emptyList());
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);

            for (String s : lines) {
                lineNumber++;
                if (!s.isBlank())
                    if (!s.contains(",")) {
                        result.add(Float.parseFloat(s));
                    } else {
                        elements = s.split(",");
                        for (String i : elements) {
                            if (!i.isBlank())
                                result.add(Float.parseFloat(i));
                        }
                        elements = null;
                    }
            }

            if(result.isEmpty()) {
                MainPage.errorFlag = true;
                MainPage.emptyFileFlag = true;
                LandingPage.emptyFileFlag = true;
            }

        } catch (NoSuchFileException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please check your file name",
                    "File Does not exist!",
                    JOptionPane.ERROR_MESSAGE);
            MainPage.errorFlag = true;
            LandingPage.setErrorFlag();
            log.addError(0);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please check your Input file",
                    "Invalid file data!",
                    JOptionPane.ERROR_MESSAGE);
            LandingPage.setErrorFlag();
            MainPage.errorFlag = true;
            log.setLineNumber(lineNumber);
            log.addError(3);
        } catch (IOException e) {
            LandingPage.setErrorFlag();
        }

        return result;
    }
}
