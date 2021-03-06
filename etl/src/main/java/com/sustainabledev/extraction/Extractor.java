package com.sustainabledev.extraction;


import com.sustainabledev.hydro.data.Dataset;
import com.sustainabledev.utilities.exceptions.FileFormatException;

import java.io.IOException;
import java.nio.file.Path;

public interface Extractor {
    Dataset read(Path filePath) throws FileFormatException, IllegalAccessException, IOException;
}
