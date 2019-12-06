public class ErrorLog {

    private static ErrorLog log = null;
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

    String[] errors = new String[100];
    String noFile = "File Not Found: The file you tried to load was not found, try again.";
    String invalidBoundaries = "Invalid Boundaries: Adjust the boundaries to fit your data.";
    String invalidData = "Invalid Data: The data that you entered is not an integer or a floating point.";
    String invalidSet = "Invalid Data Set: The file you loaded contains some non-numeric values, fix these " +
            "values and re-upload the file.";
    String dataDNE = "Data Value Does Not  Exist: The data value you tried to delete does not exist.";


    int i = 0;
    public void addError(int errorCode)
    {
        if(errorCode == 0)
        {
            errors[i] = Integer.toString(i+1) + ": " + noFile;
            i++;
            System.out.println("Error 0 logged.");
        } else if(errorCode == 1)
        {
            errors[i] = Integer.toString(i+1) + ": " + invalidBoundaries;
            i++;
            System.out.println("Error 1 logged.");
        } else if(errorCode == 2)
        {
            errors[i] = Integer.toString(i+1) + ": " + invalidData;
            i++;
            System.out.println("Error 2 logged.");
        } else if(errorCode == 3)
        {
            errors[i] = Integer.toString(i+1) + ": " + invalidSet;
            i++;
            System.out.println("Error 3 logged.");
        } else if(errorCode == 4)
        {
            errors[i] = Integer.toString(i+1) + ": " + dataDNE;
            i++;
            System.out.println("Error 4 logged.");
        }
    }

    public String[] getReport()
    {
        return errors;
    }

    public boolean isEmpty()
    {
        for(int i = 0; i < errors.length; i++)
        {
            if(errors[i] != null)
            {
                return false;
            }
        }
        return true;
    }

}
