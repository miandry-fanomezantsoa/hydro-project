package com.sustainabledev.hydro.analysis;

import com.sustainabledev.utilities.exceptions.IllegalOperationException;
import com.sustainabledev.utilities.utils.NumberUtils;

import static java.lang.Math.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class SimpleDescriptiveAnalysis implements DescriptiveAnalysis {

    private List<? extends Number> data;
    private static final DecimalFormat HYDRO_FORMAT =
            new DecimalFormat("0.000");

    public SimpleDescriptiveAnalysis(List<? extends Number> d) {
        this.data = d;
    }

    public void of(List<? extends Number> data) {
        this.data = data;
    }

    public List<? extends Number> getData() {
        return data;
    }

    public void setData(List<? extends Number> data) {
        this.data = data;
    }

    private int floatComparator(Number n1, Number n2) {
        if (n1.floatValue() < n2.floatValue()) {
            return -1;
        } else if (n1.floatValue() == n2.floatValue()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Float getMax() throws IllegalOperationException {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        return Float.valueOf(NumberUtils.formatToFloatingPoint(3,
                data.stream().max(this::floatComparator).get()));
    }

    @Override
    public Float getMin() throws IllegalOperationException {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        return Float.valueOf(NumberUtils.formatToFloatingPoint(3,
                data.stream().min(this::floatComparator).get()));
    }

    @Override
    public Float getMean() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        Stream<? extends  Number> stream = data.stream();
        return Float.valueOf(NumberUtils.formatToFloatingPoint(3,
                getSum() / Integer.valueOf(this.data.size()).floatValue()));
    }

    @Override
    public Float getSum() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        Stream<? extends Number> stream = data.stream();
        return Float.valueOf(NumberUtils.formatToFloatingPoint(3,
                stream.mapToDouble(Number::doubleValue).sum()));
    }

    @Override
    public Float getVar() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        final Float mean = getMean();
        Number se = data.stream().map(e -> pow(((Float)e - mean), 2)).
                mapToDouble(Number::doubleValue).sum() / this.data.size();
        return Float.valueOf(NumberUtils.formatToFloatingPoint(3, se));
    }

    @Override
    public Float getStd() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        return Float.valueOf(NumberUtils.formatToFloatingPoint(3, sqrt(getVar())));
    }
}
