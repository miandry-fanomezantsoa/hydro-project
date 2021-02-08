package com.sustainabledev.extraction;

import static org.junit.jupiter.api.Assertions.*;

import com.sustainabledev.utilities.data.Dataset;
import com.sustainabledev.utilities.data.Variable;
import com.sustainabledev.utilities.exceptions.FileFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.TimeZone;

@DisplayName("EXTRACTION")
class ExtractorTest {

    @Nested
    @DisplayName("EXCEL Data Extraction")
    class ExcelDataExtraction {
        @Test
        @DisplayName("collect series of data contained in source files")
        void readFiles() throws ParseException {
            // Load the test file from resources folder
            String fileName = "extraction/reading-test.xls";
            ClassLoader classLoader = getClass().getClassLoader();
            try {
                Path filePath = Path.of(classLoader.getResource(fileName).toURI());

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));

                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("01/01/2001 00:00").toInstant(),
                        format.parse("01/01/2001 01:00").toInstant(),
                        format.parse("01/01/2001 02:00").toInstant(),
                        format.parse("01/01/2001 03:00").toInstant(),
                        format.parse("01/01/2001 04:00").toInstant(),
                        format.parse("01/01/2001 05:00").toInstant(),
                        format.parse("01/01/2001 06:00").toInstant(),
                        format.parse("01/01/2001 07:00").toInstant(),
                        format.parse("01/01/2001 08:00").toInstant(),
                        format.parse("01/01/2001 09:00").toInstant(),
                        format.parse("01/01/2001 10:00").toInstant()
                ));
                Variable<Float> rainFall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(1.921f,1.928f,1.941f,1.953f,1.951f,1.95f,
                        1.939f,1.918f,1.902f,1.891f,1.873f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(34.792f,35.222f,36.023f,36.764f,36.640f,36.578f,
                        35.899f,34.608f,33.630f,32.959f,31.866f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainFall, level, flow));

                Extractor extractor = new ExcelExtractor();
                assertEquals(dataset, extractor.read(filePath));

            } catch (URISyntaxException | IllegalAccessException | FileFormatException | IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("detect null values inside dataset")
        void nullValuesDetected() {
            // Load the test file from resources folder
            String fileName = "extraction/reading-null-values.xls";
            ClassLoader classLoader = getClass().getClassLoader();
            try {
                Path filePath = Path.of(classLoader.getResource(fileName).toURI());

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
                Variable<Float> rainFall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(4.419f,3.92f,3.986f,3.999f,4.06f,4.27f,4.24f,4.23f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(238.971f,null,null,null,229.946f,223.936f,220.952f,219.961f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainFall, level, flow));

                Extractor extractor = new ExcelExtractor();

                assertEquals(dataset, extractor.read(filePath));
            } catch (URISyntaxException | ParseException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (FileFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Test
        @DisplayName("detect invalid data format")
        void invalidFormattedDataDetected() {
            // Load the test file from resources folder
            String fileName = "extraction/invalid-format.xls",
                    invalidDate = "extraction/invalid-datetime-format.xls";
            ClassLoader classLoader = getClass().getClassLoader();
            try {
                Path filePath = Path.of(classLoader.getResource(fileName).toURI()),
                        invalidDatePath = Path.of(classLoader.getResource(invalidDate).toURI());

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));

                Variable<Instant> time = new Variable("Date", Arrays.asList(
                        format.parse("10/01/2002 15:00").toInstant(),
                        format.parse("10/01/2002 16:00").toInstant(),
                        format.parse("10/01/2002 17:00").toInstant(),
                        format.parse("10/01/2002 18:00").toInstant(),
                        format.parse("10/01/2002 19:00").toInstant()
                ));
                Variable<Float> rainFall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f));
                Variable<Float> level = new Variable("Hauteur", Arrays.asList(1.31f,1.3f,1.29f,1.3f,1.36f));
                Variable<Float> flow = new Variable("Débit", Arrays.asList(Float.NaN,0.354f,Float.NaN,Float.NaN,2.943f));
                Dataset dataset = new Dataset(Arrays.asList(time, rainFall, level, flow));

                Extractor extractor = new ExcelExtractor();

                assertAll(
                        () -> assertEquals(dataset, extractor.read(filePath)),
                        () -> assertThrows(DateTimeParseException.class, () -> extractor.read(invalidDatePath))
                );
            } catch (URISyntaxException | ParseException e) {
                e.printStackTrace();
            }
        }
    }


}