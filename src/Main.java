import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) {
        try {
            String filename = "./DewExcelFile.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");

            printHeader(sheet);
            PersonGenerator personGenerator = new PersonGenerator();
            Integer rowsCount = (int)(Math.random() * 30) + 1;
            Integer startRow = 1;
            for (int i = 0; i < rowsCount; i++) {
                Person person = personGenerator.Generate();
                printRow(sheet, startRow + i, person);
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            String fullpath = new File(filename).getCanonicalPath();
            String banner = String.format("Файл создан. Путь: %s", fullpath);
            System.out.println(banner);

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
    }

    private static void printHeader(HSSFSheet sheet) {
        HSSFRow rowhead = sheet.createRow(0);
        rowhead.createCell(0).setCellValue("Имя");
        rowhead.createCell(1).setCellValue("Фамилия");
        rowhead.createCell(2).setCellValue("Отчество");
        rowhead.createCell(3).setCellValue("Возраст");
        rowhead.createCell(4).setCellValue("Пол");
        rowhead.createCell(5).setCellValue("Дата рождения");
        rowhead.createCell(6).setCellValue("ИНН");
        rowhead.createCell(7).setCellValue("Индекс");
        rowhead.createCell(8).setCellValue("Страна");
        rowhead.createCell(9).setCellValue("Область");
        rowhead.createCell(10).setCellValue("Город");
        rowhead.createCell(11).setCellValue("Улица");
        rowhead.createCell(12).setCellValue("Дом");
        rowhead.createCell(13).setCellValue("Квартира");
    }

    private static void printRow(HSSFSheet sheet, Integer rowNumber, Person person) {
        HSSFRow row = sheet.createRow(rowNumber);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

        row.createCell(0).setCellValue(person.Name);
        row.createCell(1).setCellValue(person.Surname);
        row.createCell(2).setCellValue(person.MiddleName);
        row.createCell(3).setCellValue(person.Age);
        row.createCell(4).setCellValue(person.Sex);
        row.createCell(5).setCellValue(person.Birthday.format(formatter));
        row.createCell(6).setCellValue(person.INN);
        row.createCell(7).setCellValue(person.ZipCode);
        row.createCell(8).setCellValue(person.Country);
        row.createCell(9).setCellValue(person.Region);
        row.createCell(10).setCellValue(person.City);
        row.createCell(11).setCellValue(person.Street);
        row.createCell(12).setCellValue(person.House);
        row.createCell(13).setCellValue(person.Apartment);


    }
}
