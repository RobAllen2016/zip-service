package zip.range;

/**
 * POJO representation of a ZIP code exclusion range.
 */
public class ZipRange implements Comparable {

    private int minValue;
    private int maxValue;

    /**
     * Default no-argument constructor; sets minValue to 0 and maxValue to 99999.
     */
    public ZipRange() {
        this.minValue = 0;
        this.maxValue = 99999;
    }

    /**
     * Constructor that sets the values of min and max as specified.
     *
     * @param minValue to be set.
     * @param maxValue to be set.
     */
    public ZipRange(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Getter for minVlue.
     *
     * @return current value of minValue.
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Setter for minValue.
     *
     * @param minValue to be set.
     */
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    /**
     * Getter for maxVlue.
     *
     * @return current value of maxValue.
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Setter for maxValue.
     *
     * @param maxValue to be set.
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Compares this instance to the ZipRange argument to determine whether the two ranges overlap.
     *
     * @param right ZipRange for comparison.
     * @return true if the two ranges overlap.
     */
    public boolean overlaps(ZipRange right) {
        // Given ranges A and B
        // * if A.max >= B.min and A.min <= B.min, then A & B overlap
        // * if B.max >= A.min and B.min <= A.min, then A & B overlap
        return (maxValue >= right.getMinValue() && minValue <= right.getMinValue()) ||
               (right.getMaxValue() >= minValue && right.getMinValue() <= minValue);
    }

    /**
     * Compares this instance's minimum value to that of the ZipRange argument and returns the lower of the two.
     *
     * @param right ZipRange for comparison.
     * @return int lowest value of minValue.
     */
    public int getLowestMin(ZipRange right) {
        return Math.min(minValue, right.getMinValue());
    }

    /**
     * Compares this instance's maximum value to that of the ZipRange argument and returns the higher of the two.
     *
     * @param right ZipRange for comparison.
     * @return int highest value of maxValue.
     */
    public int getHighestMax(ZipRange right) {
        return Math.max(maxValue, right.getMaxValue());
    }

    /**
     * Given two ZipRanges, this method (which may be invoked on either range and the other range passed in as an
     * argument) aggregates the two into a single new range that encompasses both if they overlap, or returns null
     * if they don't.
     *
     * @param right The second ZipRange instance to be aggregated with this.
     * @return A new ZipRange instance representing the aggregate of this and the one passed in, or null if they don't
     * overlap.
     */
    public ZipRange aggregateWithZipRange(ZipRange right) {
        ZipRange returnValue = null;

        if (overlaps(right)) {
            returnValue = new ZipRange(getLowestMin(right), getHighestMax(right));
        }

        return returnValue;
    }

    /**
     * For sorting ZipRange instances, we only care about the minValue.
     *
     * @param o ZipRange instance to compare to.
     * @return Per the Comparable interface, a negative return value means this is lesser than the object we're
     *         comparing to, 0 means equality, and a positive means this is greater than the object we're comparing
     *         to.
     */
    @Override
    public int compareTo(Object o) {
        // If the two references are to the same instance, obviously they're equal.
        if (this == o) return 0;

        // Note that if "o" is not an instance of ZipRange, this method will throw a ClassCastException.
        // For our purposes here, we'll just be careful not to do that.
        ZipRange zipRange = (ZipRange) o;

        // We're going to use this for sorting purposes, and for our algorithm we will sort by minValue.
        return (getMinValue() - zipRange.getMinValue());
    }

    /**
     * Generated equals method.
     *
     * @param o Object reference to a ZipRange instance to compare to.
     * @return true if both minValues and both maxValues are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZipRange zipRange = (ZipRange) o;

        if (getMinValue() != zipRange.getMinValue()) return false;
        return getMaxValue() == zipRange.getMaxValue();
    }

    /**
     * Generated hash code method.
     *
     * @return integer hash code.
     */
    @Override
    public int hashCode() {
        int result = getMinValue();
        result = 31 * result + getMaxValue();
        return result;
    }

    /**
     * Turns the pair of numbers back into a string representation like "[99999,99999]"
     *
     * @return String representation of the range pair.
     */
    @Override
    public String toString() {
        return String.format("[%1$5d,%2$5d]", minValue, maxValue).replace(' ', '0');
    }

}
