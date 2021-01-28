package com.sustainabledev.hydro.analysis;

import com.sustainabledev.utilities.exceptions.IllegalOperationException;

import static java.lang.Math.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

public class SimpleDescriptiveAnalysis implements DescriptiveAnalysis {

    private Float[] data;
    private static final DecimalFormat HYDRO_FORMAT =
            new DecimalFormat("0.000");

    public SimpleDescriptiveAnalysis(Float[] d) {
        this.data = d;
    }

    public void of(Float[] data) {
        this.data = data;
    }

    public Float[] getData() {
        return data;
    }

    public void setData(Float[] data) {
        this.data = data;
    }

    private String formatToFloatingPoint(int p, Number n) {
        String format = "%." + p + "f";

        // Get default locale
        Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(new Locale("en", "EN"));

        String result = String.format(format, n);

        // Restore the default locale to the original after getting the result
        Locale.setDefault(defaultLocale);

        return result;
    }

    @Override
    public Float getMax() throws IllegalOperationException {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        return Float.valueOf(formatToFloatingPoint(3,
                Arrays.stream(data).max(Float::compare).get()));
    }

    @Override
    public Float getMin() throws IllegalOperationException {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        return Float.valueOf(formatToFloatingPoint(3,
                Arrays.stream(data).min(Float::compare).get()));
    }

    @Override
    public Float getMean() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        Stream<Float> stream = Arrays.stream(data);
        return Float.valueOf(formatToFloatingPoint(3,
                getSum() / Integer.valueOf(this.data.length).floatValue()));
    }

    @Override
    public Float getSum() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        Stream<Float> stream = Arrays.stream(data);
        return Float.valueOf(formatToFloatingPoint(3,
                stream.mapToDouble(Number::doubleValue).sum()));
    }

    @Override
    public Float getVar() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        final Float mean = getMean();
        Number se = Arrays.stream(this.data).map(e -> pow((e - mean), 2)).
                mapToDouble(Number::doubleValue).sum() / this.data.length;
        return Float.valueOf(formatToFloatingPoint(3, se));
    }

    @Override
    public Float getStd() {
        if(data == null) {
            throw new IllegalOperationException("The dataset is empty");
        }
        return Float.valueOf(formatToFloatingPoint(3, sqrt(getVar())));
    }
}
