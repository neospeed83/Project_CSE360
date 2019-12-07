/**
 * Class that sets and maintains the programs data boundaries
 *
 * @author Akash Devdhar, Matt Hayes, Henry Pearson, Nicholas Vietri
 * 		   CSE 360 Team Project
 */
class SetBoundary {

    private static float lowerBound;
    private static float higherBound;
    private static SetBoundary boundaryObject;

    /**
     * Constructor method to set the program's initial boundaries.
     *
     * @param low lower bound
     * @param high upper bound
     */
    private SetBoundary(float low, float high) {
        lowerBound = low;
        higherBound = high;
    }

    /**
     * Method to make subsequent updates to the program's bounds by changing the class variables
     * of an instance of the class.
     *
     * @param low lower bound
     * @param high upper bound
     */
    static void updateBoundaries(float low, float high) {
        if (boundaryObject == null) {
            boundaryObject = new SetBoundary(low, high);
        } else {
            SetBoundary.setLowerBound(low);
            SetBoundary.setHigherBound(high);
        }

        MainPage.updateReport("Set bounds to: " + low + ", " + high + "\n");
    }

    /**
     * Getter method to return the lower bound of the program.
     *
     * @return lower bound
     */
    static float getLowerBound() {
        return lowerBound;
    }


    /**
     * Setter method to set the lower bound of the program.
     *
     * @param lowerBound lower bound to set
     */
    private static void setLowerBound(float lowerBound) {
        SetBoundary.lowerBound = lowerBound;
    }

    /**
     * Getter method to return the upper bound of the program.
     *
     * @return upper bound
     */
    static float getHigherBound() {
        return higherBound;
    }

    /**
     * Setter method to set the upper bound of the program.
     *
     * @param higherBound upper bound to set
     */
    private static void setHigherBound(float higherBound) {
        SetBoundary.higherBound = higherBound;
    }
}
