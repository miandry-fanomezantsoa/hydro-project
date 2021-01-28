package com.sustainabledev.hydro.analysis;

/**
 * Classic interface to describe all statistical descriptive analysis of series
 * of data.
 */
public interface DescriptiveAnalysis {
    /**
     * Get max value given series of data.
     * @return the numeric single maximum value
     */
    Float getMax();

    /**
     * Get min value given series of data.
     * @return the numeric single minimum value
     */
    Float getMin();

    /**
     * Compute the mean value of series of data using arithmetic algorithmic :
     * by dividing the sum value by number of series items.
     * @return the numeric average value
     */
    Float getMean();

    /**
     * Compute the sum value of a series of data by adding the value of each
     * series item.
     * @return the numeric sum value
     */
    Float getSum();
    Float getVar();
    Float getStd();
}
