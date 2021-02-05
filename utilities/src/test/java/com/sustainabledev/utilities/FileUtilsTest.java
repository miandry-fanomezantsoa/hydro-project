package com.sustainabledev.utilities;

import com.sustainabledev.utilities.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Files utilies operations")
class FileUtilsTest {

    @Test
    @DisplayName("get file extension")
    void getFileExtension() {
        try {
            File file1 = new File(getClass().getClassLoader().getResource("test.xls").toURI());
            File file2 = new File(getClass().getClassLoader().getResource("test.xlsx").toURI());
            assertAll(
                    () -> assertEquals("xls", FileUtils.getExtension(file1)),
                    () -> assertEquals("xlsx", FileUtils.getExtension(file2))
            );

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}