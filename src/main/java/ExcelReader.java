
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// search maven repository for apache poi oomxl
// add to pom file inside of <dependencies></dependencies>
// right click project build with dependencies
public class ExcelReader {

    public ArrayList<String> columnToArrayListAsString(String inputFile, int column) throws FileNotFoundException, IOException {
        ArrayList<String> stringList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(inputFile));
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= 50; i++) {
            XSSFRow row = sheet.getRow(i);
            stringList.add(row.getCell(column).getStringCellValue());
        }

        return stringList;
    }

    public ArrayList<Double> columnToArrayListAsDouble(String inputFile, int column) throws FileNotFoundException, IOException {
        ArrayList<Double> doubleList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(inputFile));
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= 50; i++) {
            XSSFRow row = sheet.getRow(i);
            doubleList.add(row.getCell(column).getNumericCellValue());
        }

        return doubleList;
    }

}
