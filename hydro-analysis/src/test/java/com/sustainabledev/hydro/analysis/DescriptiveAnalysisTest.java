package com.sustainabledev.hydro.analysis;

import static org.junit.jupiter.api.Assertions.*;

import com.sustainabledev.utilities.exceptions.IllegalOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@DisplayName("Descriptive Analysis")
public class DescriptiveAnalysisTest {

    @Test
    @DisplayName("Should throw InvalidOperationException when dataset is empty")
    void shouldThrowExceptionWithoutDataSet() {
        DescriptiveAnalysis analysis = new SimpleDescriptiveAnalysis(null);
        assertAll(
                () -> assertThrows(IllegalOperationException.class,
                        () -> analysis.getMax()),
                () -> assertThrows(IllegalOperationException.class,
                        () -> analysis.getMin()),
                () -> assertThrows(IllegalOperationException.class,
                        () -> analysis.getSum()),
                () -> assertThrows(IllegalOperationException.class,
                        () -> analysis.getMean()),
                () -> assertThrows(IllegalOperationException.class,
                        () -> analysis.getVar()),
                () -> assertThrows(IllegalOperationException.class,
                        () -> analysis.getStd())
        );
    }

    @Test
    @DisplayName("Aggregation operation give single value")
    void maxValueOfASerieIsUnique() {
        List<Number> data = Arrays.asList(10.5f, 124.2f, 30f, 11.4f, 124.2f);

        DescriptiveAnalysis analysis = new SimpleDescriptiveAnalysis(data);
        List<Float> maxValues = List.of(analysis.getMax());
        List<Float> minValues = List.of(analysis.getMin());
        List<Float> sumValues = List.of(analysis.getSum());
        List<Float> meanValues = List.of(analysis.getMean());
        List<Float> varValues = List.of(analysis.getVar());
        List<Float> stdValues = List.of(analysis.getStd());

        assertAll(
                () -> assertEquals(1, maxValues.size()),
                () -> assertEquals(1, minValues.size()),
                () -> assertEquals(1, sumValues.size()),
                () -> assertEquals(1, meanValues.size()),
                () -> assertEquals(1, varValues.size()),
                () -> assertEquals(1, stdValues.size())
        );
    }

    @Test
    @DisplayName("Finding max value return maximum value of series of data")
    void maxValueOfASerie() {
        // Input data
        List<Number> data1 = Arrays.asList(1.5f, 3.6f, 3.61f, 0.12f);
        List<Number> data2 = Arrays.asList(10.5f, 124.2f, 30f, 11.4f, 124.2f);

        DescriptiveAnalysis analysis1 = new SimpleDescriptiveAnalysis(data1);
        DescriptiveAnalysis analysis2 = new SimpleDescriptiveAnalysis(data2);

        assertAll(
                () -> assertEquals(3.610f, analysis1.getMax()),
                () -> assertEquals(124.200f, analysis2.getMax())
        );
    }

    @Test
    @DisplayName("Finding min value return minimum value of series of data")
    void minValueOfASerie() {
        // Input data
        List<Number> data1 = Arrays.asList(1.5f, 3.6f, 3.61f, 0.12f);
        List<Number> data2 = Arrays.asList(10.5f, 124.2f, 30f, 11.4f, 124.2f);

        DescriptiveAnalysis analysis1 = new SimpleDescriptiveAnalysis(data1);
        DescriptiveAnalysis analysis2 = new SimpleDescriptiveAnalysis(data2);

        assertAll(
                () -> assertEquals(0.120f, analysis1.getMin()),
                () -> assertEquals(10.500f, analysis2.getMin())
        );
    }

    @Test
    @DisplayName("Computing sum value return the sum value of series of data")
    void sumValueOfASerie() {
        // Input data
        List<Number> data1 = Arrays.asList(1.5f, 3.6f, 3.61f, 0.12f);
        List<Number> data2 = Arrays.asList(10.516f, 124.234f, 30f, 11.489f, 124.2f);

        DescriptiveAnalysis analysis1 = new SimpleDescriptiveAnalysis(data1);
        DescriptiveAnalysis analysis2 = new SimpleDescriptiveAnalysis(data2);

        assertAll(
                () -> assertEquals(8.830F, analysis1.getSum()),
                () -> assertEquals(300.439F, analysis2.getSum())
        );
    }

    @Test
    @DisplayName("Computing mean value return the mean value of series of data")
    void meanValueOfASerie() {
        // Input data
        List<Number> data1 = Arrays.asList(1.5f, 3.6f, 3.61f, 0.12f);
        List<Number> data2 = Arrays.asList(10.516f, 124.234f, 30f, 11.489f, 124.2f);

        DescriptiveAnalysis analysis1 = new SimpleDescriptiveAnalysis(data1);
        DescriptiveAnalysis analysis2 = new SimpleDescriptiveAnalysis(data2);

        assertAll(
                () -> assertEquals(2.207F, analysis1.getMean()),
                () -> assertEquals(60.088F, analysis2.getMean())
        );
    }

    @Test
    @DisplayName("Computing variance value return the variance " +
            "value of series of data")
    void varianceValueOfASerie() {
        // Input data
        List<Number> data1 = Arrays.asList(1.5f, 3.6f, 3.61f, 0.12f);
        List<Number> data2 = Arrays.asList(10.516f, 124.234f, 30f, 11.489f, 124.2f);

        DescriptiveAnalysis analysis1 = new SimpleDescriptiveAnalysis(data1);
        DescriptiveAnalysis analysis2 = new SimpleDescriptiveAnalysis(data2);

        assertAll(
                () -> assertEquals(2.191F, analysis1.getVar()),
                () -> assertEquals(2789.918F, analysis2.getVar())
        );
    }

    @Test
    @DisplayName("Computing standard deviation value return the " +
            "standard deviation value of series of data")
    void stdValueOfASerie() {
        // Input data
        List<Number> data1 = Arrays.asList(1.5f, 3.6f, 3.61f, 0.12f);
        List<Number> data2 = Arrays.asList(10.516f, 124.234f, 30f, 11.489f, 124.2f);

        DescriptiveAnalysis analysis1 = new SimpleDescriptiveAnalysis(data1);
        DescriptiveAnalysis analysis2 = new SimpleDescriptiveAnalysis(data2);

        assertAll(
                () -> assertEquals(1.480F, analysis1.getStd()),
                () -> assertEquals(52.820F, analysis2.getStd())
        );
    }
}
