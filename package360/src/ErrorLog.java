import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorLog {

    private static ErrorLog log = null;
    private static int lineNumber;
    private static float invalidVal;

    private ErrorLog()
    {
        System.out.println("Error Log Opened.");
    }

    //Access singleton
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


    int i = 0;
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
        }
    }

    private void updateInvalidSet() {
        invalidSet = "Invalid Data Set: The file you loaded contains some non-numeric values at line " +
                lineNumber + ", fix these values and re-upload the file.";
    }

    public void updateOutOfBoundsVal() {
        invalidBoundaries = "Invalid Boundaries: " + invalidVal +
                " is out of bounds. Adjust the boundaries to fit your data.";
    }

    public static void setLineNumber(int lineNum) {
        lineNumber = lineNum;
    }

    public static void setOutOfBoundsVal(float val) {
        invalidVal = val;
    }

    public String getReport()
    {
        String print = "";
        for(String i : errors) {
            print += i + "\n\n";
        }
        return print;
    }

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
