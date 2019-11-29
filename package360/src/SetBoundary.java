class SetBoundary {

    private static int lowerBound;
    private static int higherBound;
    private static SetBoundary boundaryObject;

    private SetBoundary(int low, int high) {
        lowerBound = low;
        higherBound = high;
    }

    static void updateBoundaries(int low, int high) {
        if (boundaryObject == null) {
            boundaryObject = new SetBoundary(low, high);
        } else {
            SetBoundary.setLowerBound(low);
            SetBoundary.setHigherBound(high);
        }
    }

    static int getLowerBound() {
        return lowerBound;
    }


    private static void setLowerBound(int lowerBound) {
        SetBoundary.lowerBound = lowerBound;
    }

    static int getHigherBound() {
        return higherBound;
    }

    private static void setHigherBound(int higherBound) {
        SetBoundary.higherBound = higherBound;
    }
}
