import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ErrorLog class maintains the error log for the program by storing and displaying
 * the various error messages encountered throughout execution.
 *
 * @author Akash Devdhar, Matt Hayes, Henry Pearson, Nicholas Vietri
 * 		   CSE 360 Team Project
 *
 */
public class ErrorLog {

    private static ErrorLog log = null;
    private static int lineNumber;
    private static float invalidVal;

    /**
     * Constructor for the ErrorLog class.
     */
    private ErrorLog()
    {
        System.out.println("Error Log Opened.");
    }

    /**
     * Method to access singleton.
     */
    public static ErrorLog getInstance()
    {
        if(log == null)
        {
            log = new ErrorLog();
        }

        return log;
    }

    List<String> errors = new ArrayList<>(Collections.emptyList());
    String noFile = "File Not Found: The file you tried to load was not found, try again.";
    String invalidBoundaries = "Invalid Boundaries: Adjust the boundaries to fit your data.";
    String invalidData = "Invalid Data: The data that you entered is not an integer or a floating point.";
    String invalidSet = "Invalid Data Set: The file you loaded contains some non-numeric values at line " +
        lineNumber + ", fix these values and re-upload the file.";
    String dataDNE = "Data Value Does Not  Exist: The data value you tried to delete does not exist.";
    String emptyFile = "Empty File: The file you inputted was empty.";


    int i = 0;

    /**
     * Method to translate a given error code to the appropriate error message to be displayed
     * in the program's error log.
     *
     * @param errorCode represents the passed error
     */
    public void addError(int errorCode)
    {
        if(errorCode == 0)
        {
            errors.add(Integer.toString(i+1) + ": " + noFile);
            i++;
            System.out.println("Error 0 logged.");
        } else if(errorCode == 1)
        {
            errors.add(Integer.toString(i+1) + ": " + invalidBoundaries);
            i++;
            invalidBoundaries = "Invalid Boundaries: Adjust the boundaries to fit your data.";
            System.out.println("Error 1 logged.");
        } else if(errorCode == 2)
        {
            errors.add(Integer.toString(i+1) + ": " + invalidData);
            i++;
            System.out.println("Error 2 logged.");
        } else if(errorCode == 3)
        {
            updateInvalidSet();
            errors.add(Integer.toString(i+1) + ": " + invalidSet);
            i++;
            System.out.println("Error 3 logged.");
        } else if(errorCode == 4)
        {
            errors.add(Integer.toString(i+1) + ": " + dataDNE);
            i++;
            System.out.println("Error 4 logged.");
        } else if(errorCode == 5) {
            errors.add(Integer.toString(i+1) + ": " + emptyFile);
            i++;
        }
    }

    /**
     * Method to update the error message for a file with a bad input value.
     */
    private void updateInvalidSet() {
        invalidSet = "Invalid Data Set: The file you loaded contains some non-numeric values at line " +
                lineNumber + ", fix these values and re-upload the file.";
    }

    /**
     * Method to update the error message for a value that is outside of the program's
     * current bounds.
     */
    public void updateOutOfBoundsVal() {
        invalidBoundaries = "Invalid Boundaries: " + invalidVal +
                " is out of bounds. Adjust the boundaries to fit your data.";
    }

    /**
     * Method to set the line number of a value within a file that breaks the program's
     * required input of a numerical value.
     *
     * @param lineNum file line number of the offending value
     */
    public static void setLineNumber(int lineNum) {
        lineNumber = lineNum;
    }

    /**
     * Method to set the value of the number that is out of bounds.
     *
     * @param val value that is out of bounds
     */
    public static void setOutOfBoundsVal(float val) {
        invalidVal = val;
    }

    /**
     * Method to return the error messages of the error log.
     *
     * @return Concatenated string of the various error messages
     */
    public String getReport()
    {
        String print = "";
        for(String i : errors) {
            print += i + "\n\n";
        }
        return print;
    }

    /**
     * Method to check if the error log is empty with no errors.
     *
     * @return boolean that is true if the error log is empty
     */
    public boolean isEmpty()
    {
        for(int i = 0; i < errors.size(); i++)
        {
            if(errors.get(i) != null)
            {
                return false;
            }
        }
        return true;
    }

}
