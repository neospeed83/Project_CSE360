class SetBoundary {

    private static int lowerBound;
    private static int higherBound;
    private static SetBoundary boundaryObject;

    SetBoundary(int low, int high) {
        lowerBound = low;
        higherBound = high;
    }

    static SetBoundary updateBoundaries(int low, int high){
        if (boundaryObject == null)
            boundaryObject = new SetBoundary(low, high);
        return boundaryObject;
    }

    public static int getLowerBound() {
        return lowerBound;
    }

    public static void setLowerBound(int lowerBound) {
        SetBoundary.lowerBound = lowerBound;
    }

    public static int getHigherBound() {
        return higherBound;
    }

    public static void setHigherBound(int higherBound) {
        SetBoundary.higherBound = higherBound;
    }
}
