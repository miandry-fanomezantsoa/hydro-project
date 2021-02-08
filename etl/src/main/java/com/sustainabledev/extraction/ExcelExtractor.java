package com.sustainabledev.extraction;

import com.sustainabledev.utilities.data.Dataset;
import com.sustainabledev.utilities.data.Variable;
import com.sustainabledev.utilities.exceptions.FileFormatException;
import com.sustainabledev.utilities.utils.FileUtils;
import com.sustainabledev.utilities.utils.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.util.LocaleUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ValueRange;
import java.util.*;
import java.util.stream.Collectors;

public class ExcelExtractor implements Extractor {


    @Override
    public Dataset read(Path filePath)
            throws FileFormatException, IllegalAccessException,
            IOException {
        // Check if the path is a file with valid extension
        File file = filePath.toFile();
        if(file.exists()) { // File exists
            if(file.canRead()) { // File is readable
                String extension = FileUtils.getExtension(file);
                Dataset dataset;
                switch (extension) {
                    case "xls":
                        // Extract dataset from Excel file
                        // Create HSSFWorkbook for the xls file
                        LocaleUtil.setUserTimeZone(TimeZone.getTimeZone("GMT")); // Set POI default timezone to GMT
                        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
                        List<Sheet> allSheets = getAllSheets(workbook);

                        // For each sheet, get contained data
                        List<Dataset> datasets = allSheets.stream().map(this::mapToDataset).collect(Collectors.toList());
                        dataset = datasets.get(0);
                        break;
                    case "xlsx":
                        dataset = null;
                        break;
                    default:
                        throw new FileFormatException("Must take a file with Excel format.");
                }

                return dataset;
            } else { // File exists but no permission to read the file
                throw new IllegalAccessException("The file is not accessible.");
            }
        } else { // File not found
            throw new FileNotFoundException("The file does not exist");
        }
    }

    /**
     * Retrieve all sheets contained on a workbook.
     * @param w workbook to be extracted
     * @return a list of all sheets
     */
    private List<Sheet> getAllSheets(Workbook w) {
        List<Sheet> sheets = new ArrayList<>();
        int n = w.getNumberOfSheets();
        for (int i = 0; i < n; i++) {
            sheets.add(w.getSheetAt(i));
        }
        return sheets;
    }

    /**
     * Associate data contained on a sheet to a Dataset. The variable's header
     * is detected in the first row and they should be all string formatted.
     * @param s the sheet containing the data
     * @return a dataset wrapping data.
     */
    private Dataset mapToDataset(Sheet s) {
        Dataset dataset = new Dataset();
        List<Variable> variables = new ArrayList<>();

        // Determining variable's header composing the dataset.
        int firstRowNum = s.getFirstRowNum();
        Row firstRow = s.getRow(firstRowNum);
        int firstCol = firstRow.getFirstCellNum();
        int lastCol = firstRow.getLastCellNum();
        boolean isHeader = true;
        for (int i = firstCol; i <= lastCol; i++) {
            Cell cell = firstRow.getCell(i);
            if(cell != null) {
                if (cell.getCellType() != CellType.STRING) {
                    isHeader = false;
                }
            }
        }
        if(isHeader) { // If first row is the data header
            for (int i = firstCol; i <= lastCol; i++) { // Iterate on columns
                Cell firstRowCurrentCell = firstRow.getCell(i);
                if (firstRowCurrentCell != null) {
                    Variable variable = new Variable(firstRowCurrentCell.getStringCellValue());
                    List<Float> numericData = new ArrayList<>();
                    List<Instant> dateTimesData = new ArrayList<>();
                    boolean isDateTimeFormat = false;
                    int firstIndex = firstRowNum + 1;
                    int lastIndex = s.getLastRowNum();
                    boolean unknownVariableType = true;
                    for (int j = firstIndex; j <= lastIndex; j++) { // Iterate on rows
                        Row row = s.getRow(j);
                        if (row == null) continue;
                        Cell cell = row.getCell(i);

                        if (cell == null || cell.getCellType() == CellType.BLANK) { // Blank cell
                            // Verify all cells in this row are blank
                            boolean blankRow = true;
                            for (int k = firstCol; k <= lastCol; k++) {
                                Cell kCell = row.getCell(k);
                                if (kCell != null && kCell.getCellType() != CellType.BLANK) {
                                    blankRow = false;
                                }
                            }
                            if (blankRow) { // all cells in the row are blank
                                break;
                            } else {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    dateTimesData.add(null);
                                } else {
                                    numericData.add(null);
                                }
                            }
                        } else if (cell.getCellType() == CellType.NUMERIC) { // Numeric data
                            unknownVariableType = false;
                            if (DateUtil.isCellDateFormatted(cell)) {
                                dateTimesData.add(cell.getDateCellValue().toInstant());
                                isDateTimeFormat = true;
                            } else {
                                numericData.add(Float.valueOf(NumberUtils.formatToFloatingPoint(3, cell.getNumericCellValue())));
                            }
                        } else { // Invalid data format
                            if(unknownVariableType) { // The type of the column is still unknown
                                // Cell type detection
                                String cellType = "";
                                for (int t = j + 1; t <= lastIndex; t++) {
                                    if(! unknownVariableType) break;
                                    Row tRow = s.getRow(t);
                                    Cell tCell = tRow.getCell(i);
                                    if (tCell != null) {
                                        if (DateUtil.isCellDateFormatted(tCell)) {
                                            cellType = "datetime";
                                            unknownVariableType = false;
                                        } else if (tCell.getCellType() == CellType.NUMERIC) {
                                            cellType = "number";
                                            unknownVariableType = false;
                                        }
                                    }
                                }

                                switch (cellType) {
                                    case "datetime":
                                        throw new DateTimeParseException("The data format is unrecognized by the formatter", cell.getStringCellValue(), 0);
                                    case "number":
                                        numericData.add(Float.NaN);
                                        break;
                                    default:
                                        break;
                                }
                            } else { // the data type is already known
                                if(isDateTimeFormat) { // The variable is of type Instant
                                    throw new DateTimeParseException("The data format is unrecognized by the formatter", cell.getStringCellValue(), 0);
                                } else { // The variable is of type Float
                                    numericData.add(Float.NaN);
                                }
                            }
                        }
                    }
                    if (isDateTimeFormat) { // The column (so the variable) is datetime
                        variable.setData(dateTimesData);
                    } else { // The column (so the variable) is numeric data
                        variable.setData(numericData);
                    }
                    variables.add(variable);
                }
            }
            dataset.setVariables(variables);
        }

        return dataset;
    }

}
