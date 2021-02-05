package com.sustainabledev.extraction;

import com.sustainabledev.utilities.data.Dataset;
import com.sustainabledev.utilities.exceptions.FileFormatException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface Extractor {
    Dataset read(Path filePath) throws FileFormatException, IllegalAccessException, IOException;
}
