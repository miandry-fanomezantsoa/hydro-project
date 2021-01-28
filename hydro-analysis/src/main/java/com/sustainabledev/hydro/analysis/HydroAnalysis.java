package com.sustainabledev.hydro.analysis;

/**
 * Interface to describe hydrological basis analysis of series of data.
 */
public interface HydroAnalysis {
    /**
     * Compute the return period of a hydrological variable value from a series
     * of data.
     * @return a float value of the calculated return period
     */
    Float getReturnPeriod(Float v);

    /**
     * Compute the value of a hydrological variable having a given return period
     * from a series of data.
     * @return a float value of the calculated return period
     */
    Float getValue(Float r);
}
