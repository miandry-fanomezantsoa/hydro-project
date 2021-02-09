package com.sustainabledev.hydro.data;

import com.sustainabledev.hydro.analysis.DescriptiveAnalysis;
import com.sustainabledev.hydro.analysis.SimpleDescriptiveAnalysis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DATASET")
class DatasetTest {

    @Nested
    @DisplayName("Fill all null values")
    class FillNullValues {

        @Test
        @DisplayName("by zeros")
        void byZeros() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("28/01/2001 12:00").toInstant(),
                        format.parse("28/01/2001 13:00").toInstant(),
                        format.parse("28/01/2001 14:00").toInstant(),
                        format.parse("28/01/2001 15:00").toInstant(),
                        format.parse("28/01/2001 16:00").toInstant(),
                        format.parse("28/01/2001 17:00").toInstant(),
                        format.parse("28/01/2001 18:00").toInstant(),
                        format.parse("28/01/2001 19:00").toInstant()
                ));
                Variable<Float> rainfall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(4.419f,3.92f,3.986f,3.999f,4.06f,4.27f,4.24f,4.23f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(238.971f,null,null,null,229.946f,223.936f,220.952f,219.961f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainfall, level, flow));

                Variable<Float> expectedFlow = new Variable("Débit", Arrays.asList(238.971f,0.0f,0.0f,0.0f,229.946f,223.936f,220.952f,219.961f));
                Dataset expectedDataset = new Dataset(Arrays.asList(time, rainfall, level, expectedFlow));

                assertEquals(expectedDataset, dataset.fillNullValues(d -> {
                    List<Variable> variables = d.getVariables().stream().map(v -> {
                        List<Object> data = (List<Object>) v.getData().stream().map(o -> (o == null) ? 0.0f : o).collect(Collectors.toList());
                        v.setData(data);
                        return v;
                    }).collect(Collectors.toList());
                    return variables;
                }));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("by a specific value")
        void byGivenValue() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("28/01/2001 12:00").toInstant(),
                        format.parse("28/01/2001 13:00").toInstant(),
                        format.parse("28/01/2001 14:00").toInstant(),
                        format.parse("28/01/2001 15:00").toInstant(),
                        format.parse("28/01/2001 16:00").toInstant(),
                        format.parse("28/01/2001 17:00").toInstant(),
                        format.parse("28/01/2001 18:00").toInstant(),
                        format.parse("28/01/2001 19:00").toInstant()
                ));
                Variable<Float> rainfall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(4.419f,3.92f,3.986f,3.999f,4.06f,4.27f,4.24f,4.23f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(238.971f,null,null,null,229.946f,223.936f,220.952f,219.961f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainfall, level, flow));

                Variable<Float> expectedFlow = new Variable("Débit", Arrays.asList(238.971f,22.0f,22.0f,22.0f,229.946f,223.936f,220.952f,219.961f));
                Dataset expectedDataset = new Dataset(Arrays.asList(time, rainfall, level, expectedFlow));

                assertEquals(expectedDataset, dataset.fillNullValues(d -> {
                    List<Variable> variables = d.getVariables().stream().map(v -> {
                        List<Object> data = (List<Object>) v.getData().stream().map(o -> (o == null) ? 22.0f : o).collect(Collectors.toList());
                        v.setData(data);
                        return v;
                    }).collect(Collectors.toList());
                    return variables;
                }));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("by the mean value")
        void byMeanValue() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("28/01/2001 12:00").toInstant(),
                        format.parse("28/01/2001 13:00").toInstant(),
                        format.parse("28/01/2001 14:00").toInstant(),
                        format.parse("28/01/2001 15:00").toInstant(),
                        format.parse("28/01/2001 16:00").toInstant(),
                        format.parse("28/01/2001 17:00").toInstant(),
                        format.parse("28/01/2001 18:00").toInstant(),
                        format.parse("28/01/2001 19:00").toInstant()
                ));
                Variable<Float> rainfall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(4.419f,3.92f,3.986f,3.999f,4.06f,4.27f,4.24f,4.23f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(238.971f,null,null,null,229.946f,223.936f,220.952f,219.961f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainfall, level, flow));

                Variable<Float> expectedFlow = new Variable("Débit", Arrays.asList(238.971f,226.753f,226.753f,226.753f,229.946f,223.936f,220.952f,219.961f));
                Dataset expectedDataset = new Dataset(Arrays.asList(time, rainfall, level, expectedFlow));

                assertEquals(expectedDataset, dataset.fillNullValues(d -> {
                    List<Variable> variables = d.getVariables().stream().map(v -> {
                        // Detecting the data type
                        String dataType = v.getDataType();
                        /*for (Object o : v.getData()) {
                            if (o == null) continue;
                            try {
                                Instant instant = (Instant) o;
                                dataType = "datetime";
                            } catch (ClassCastException e) {

                            }
                        }*/
                        if (!dataType.equals("datetime") && v.getData().contains(null)) {
                            DescriptiveAnalysis analysis = new SimpleDescriptiveAnalysis(
                                    (List<? extends Number>) v.getData().parallelStream().filter(e -> e != null).collect(Collectors.toList())
                            );
                            Float mean = analysis.getMean();
                            List<Object> data = (List<Object>) v.getData().stream().map(o -> (o == null) ? mean : o).collect(Collectors.toList());
                            v.setData(data);
                        }
                        return v;
                    }).collect(Collectors.toList());
                    return variables;
                }));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    @Nested
    @DisplayName("Correct all invalid formatted data")
    class ValidData {

        @Test
        @DisplayName("with zeros")
        void correctWithZeros() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("10/01/2002 15:00").toInstant(),
                        format.parse("10/01/2002 16:00").toInstant(),
                        format.parse("10/01/2002 17:00").toInstant(),
                        format.parse("10/01/2002 18:00").toInstant(),
                        format.parse("10/01/2002 19:00").toInstant()
                ));
                Variable<Float> rainfall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(1.31f,1.3f,1.29f,1.3f,1.36f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(Float.NaN,0.354f,Float.NaN,Float.NaN,2.943f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainfall, level, flow));

                Variable<Float> expectedFlow = new Variable("Débit", Arrays.asList(0.0f,0.354f,0.0f,0.0f,2.943f));
                Dataset expectedDataset = new Dataset(Arrays.asList(time, rainfall, level, expectedFlow));

                assertEquals(expectedDataset, dataset.correctInvalidData(d -> {
                    List<Variable> variables = d.getVariables().stream().map(v -> {
                        String dataType = v.getDataType();
                        if(dataType.equals("numeric")) {
                            List<Object> data = (List<Object>) v.getData().stream().map(o -> (((Float)o).equals(Float.NaN)) ? 0.0f : o).collect(Collectors.toList());
                            v.setData(data);
                        }
                        return v;
                    }).collect(Collectors.toList());
                    return variables;
                }));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("with specific value")
        void correctWithGivenValue() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("10/01/2002 15:00").toInstant(),
                        format.parse("10/01/2002 16:00").toInstant(),
                        format.parse("10/01/2002 17:00").toInstant(),
                        format.parse("10/01/2002 18:00").toInstant(),
                        format.parse("10/01/2002 19:00").toInstant()
                ));
                Variable<Float> rainfall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(1.31f,1.3f,1.29f,1.3f,1.36f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(Float.NaN,0.354f,Float.NaN,Float.NaN,2.943f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainfall, level, flow));

                Variable<Float> expectedFlow = new Variable("Débit", Arrays.asList(22.0f,0.354f,22.0f,22.0f,2.943f));
                Dataset expectedDataset = new Dataset(Arrays.asList(time, rainfall, level, expectedFlow));

                assertEquals(expectedDataset, dataset.correctInvalidData(d -> {
                    List<Variable> variables = d.getVariables().stream().map(v -> {
                        String dataType = v.getDataType();
                        if(dataType.equals("numeric")) {
                            List<Object> data = (List<Object>) v.getData().stream().map(o -> (((Float)o).equals(Float.NaN)) ? 22.0f : o).collect(Collectors.toList());
                            v.setData(data);
                        }
                        return v;
                    }).collect(Collectors.toList());
                    return variables;
                }));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("with mean value of a variable data")
        void correctWithMeanValue() {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("10/01/2002 15:00").toInstant(),
                        format.parse("10/01/2002 16:00").toInstant(),
                        format.parse("10/01/2002 17:00").toInstant(),
                        format.parse("10/01/2002 18:00").toInstant(),
                        format.parse("10/01/2002 19:00").toInstant()
                ));
                Variable<Float> rainfall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(1.31f,1.3f,1.29f,1.3f,1.36f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(Float.NaN,0.354f,Float.NaN,Float.NaN,2.943f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainfall, level, flow));

                Variable<Float> expectedFlow = new Variable("Débit", Arrays.asList(1.648f,0.354f,1.648f,1.648f,2.943f));
                Dataset expectedDataset = new Dataset(Arrays.asList(time, rainfall, level, expectedFlow));

                assertEquals(expectedDataset, dataset.correctInvalidData(d -> {
                    List<Variable> variables = d.getVariables().stream().map(v -> {
                        String dataType = v.getDataType();
                        if(dataType.equals("numeric")) {
                            DescriptiveAnalysis analysis = new SimpleDescriptiveAnalysis(
                                    (List<? extends Number>) v.getData().stream().
                                            filter(e -> !((Float)e).equals(Float.NaN)).
                                            collect(Collectors.toList())
                            );
                            Float mean = analysis.getMean();
                            List<Object> data = (List<Object>) v.getData().stream().
                                    map(o -> (((Float)o).equals(Float.NaN)) ? mean : o).collect(Collectors.toList());
                            v.setData(data);
                        }
                        return v;
                    }).collect(Collectors.toList());
                    return variables;
                }));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}