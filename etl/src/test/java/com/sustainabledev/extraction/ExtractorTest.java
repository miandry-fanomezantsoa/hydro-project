package com.sustainabledev.extraction;

import static org.junit.jupiter.api.Assertions.*;

import com.sustainabledev.utilities.data.Dataset;
import com.sustainabledev.utilities.data.Variable;
import com.sustainabledev.utilities.exceptions.FileFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;

@DisplayName("Data extraction")
class ExtractorTest {

    @Test
    @DisplayName("collect series of data contained in source files")
    void readFiles() {
        // Load the test file from resources folder
        String fileName = "extraction/reading-test.xls";
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Path filePath = Path.of(classLoader.getResource(fileName).toURI());

            Extractor extractor = new ExcelExtractor();

            Variable<Float> time = new Variable("Date", Arrays.asList(36892.0f, 36892.043f, 36892.082f,
                    36892.125f, 36892.168f, 36892.207f, 36892.25f, 36892.293f, 36892.332f, 36892.375f, 36892.418f));
            Variable<Float> rainFall = new Variable("Pluie", Arrays.asList(0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f));
            Variable<Float> level = new Variable("Hauteur", Arrays.asList(1.921f,1.928f,1.941f,1.953f,1.951f,1.95f,
                    1.939f,1.918f,1.902f,1.891f,1.873f));
            Variable<Float> flow = new Variable("Débit", Arrays.asList(34.792f,35.222f,36.023f,36.764f,36.640f,36.578f,
                    35.899f,34.608f,33.630f,32.959f,31.866f));
            Dataset dataset = new Dataset(Arrays.asList(time, rainFall, level, flow));

            assertEquals(dataset, extractor.read(filePath));

        } catch (URISyntaxException | IllegalAccessException | FileFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}