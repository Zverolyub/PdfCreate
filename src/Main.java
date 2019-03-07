import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD;


public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        String filename = "./DewExcelFile.xls";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("FirstSheet");

        printHeader(sheet);
        Integer startRow = 1;

        List<Person> persons = requestPersons();
        for (int i = 0; i < persons.size(); i++) {
            printRow(sheet, startRow + i, persons.get(i));
        }

        FileOutputStream fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
        String fullpath = new File(filename).getCanonicalPath();
        String banner = String.format("Файл создан. Путь: %s", fullpath);
        System.out.println(banner);

    }

    private static List<Person> requestPersons() throws URISyntaxException, IOException {


            int personCount = ThreadLocalRandom.current().nextInt(0, 30);
            HttpClient client = HttpClientBuilder.create().build();
            // Создаем билдер для URI
            String url = String.format("https://randomuser.me/api/?results=%s", personCount);
            // Создаем инстанс HttpGet
            HttpGet request = new HttpGet(url);
            // Выполняем запрос
            HttpResponse response = client.execute(request);

            // Получаем тело ответа
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));


            String line = "";
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);

            }

            List<Person> personList = new ArrayList<Person>();


            for (int i = 0; i < personCount; i++) {
                JSONObject obj = new JSONObject(result.toString());
                JSONObject personObject = obj
                        .getJSONArray("results")
                        .getJSONObject(i);
                String personFirstName = personObject
                        .getJSONObject("name")
                        .getString("first");
                String personSurname = personObject
                        .getJSONObject("name")
                        .getString("last");
                Integer personAge = personObject
                        .getJSONObject("dob")
                        .getInt("age");
                String birthday = personObject
                        .getJSONObject("dob")
                        .getString("date");
                String personRegion = personObject
                        .getJSONObject("location")
                        .getString("state");
                String personStreet = personObject
                        .getJSONObject("location")
                        .getString("street");
                String personCity = personObject
                        .getJSONObject("location")
                        .getString("city");
                String personSex = personObject
                        .getJSONObject("name")
                        .getString("title");

                Person person = new Person();
                person.Name = personFirstName;
                person.Surname = personSurname;
                person.Age = personAge;
                person.Birthday = LocalDate.parse(birthday.substring(0, 10));
                person.Region = personRegion;
                person.City = personCity;
                person.Street = personStreet;
                person.House = ThreadLocalRandom.current().nextInt(0, 150);
                person.Apartment = ThreadLocalRandom.current().nextInt(0, 409);
                person.ZipCode = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 200000));
                person.Country = new PersonGenerator().Generate().Country;
                person.INN = new PersonGenerator().Generate().INN;
                person.MiddleName = "-";
                person.Sex = personSex;

                personList.add(person);

            }
        return personList;
        }

    public static void savePdf() throws IOException {
        try {
            String filename = "./DewPDFFile.pdf";
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDFont font = HELVETICA_BOLD;
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(font, 12);
            //contentStream.moveTo(100, 700);
            contentStream.drawString("Через две недели я доделаю эту часть");
            contentStream.endText();

            contentStream.close();
            document.save(filename);
            document.close();

            String fullpath = new File(filename).getCanonicalPath();
            String banner = String.format("Файл создан. Путь: %s", fullpath);
            System.out.println(banner);
            System.out.println("PDF created");
        } catch (Exception ex) {
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

