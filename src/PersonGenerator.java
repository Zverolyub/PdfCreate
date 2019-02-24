import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PersonGenerator {
    private LocalDate currentDate;
    private List<String> femaleNames;
    private List<String> femaleSurnames;
    private List<String> femaleMiddleNames;
    private List<String> maleNames;
    private List<String> maleSurnames;
    private List<String> maleMiddleNames;
    private List<String> country;
    private List<String> region;
    private List<String> city;
    private List<String> street;


    private List<String> getLines(String resourceName) throws IOException {
        ClassLoader classLoader = new Main().getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        BufferedReader bf = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<String>();

        while (true) {
            String line = bf.readLine();
            if (line == null) break;
            lines.add(line);
        }
        return lines;
    }

    private String getRandom(List<String> lines) {
        Integer lineId = (int) (Math.random() * lines.size());
        String line = lines.get(lineId);
        return line;
    }

    public PersonGenerator() throws IOException {
        currentDate = LocalDate.now();
        femaleNames = getLines("resources/NameF.txt");
        femaleSurnames = getLines("resources/SurnameF.txt");
        maleNames = getLines("resources/NameM.txt");
        maleSurnames = getLines("resources/SurnameM.txt");
        maleMiddleNames = getLines("resources/MiddleNameM.txt");
        femaleMiddleNames = getLines("resources/MiddleNameF.txt");
        country = getLines("resources/Country.txt");
        region = getLines("resources/Region.txt");
        city = getLines("resources/City.txt");
        street = getLines("resources/Street.txt");

    }

    private static List<String> Sexes = new ArrayList<String>() {{
        add("М");
        add("Ж");
    }};

    public Person Generate() {
        Person person = new Person();
        String sex = getRandom(Sexes);
        if (sex == "Ж") {
            person.Name = getRandom(femaleNames);
            person.Surname = getRandom(femaleSurnames);
            person.MiddleName = getRandom(femaleMiddleNames);
        } else {
            person.Name = getRandom(maleNames);
            person.Surname = getRandom(maleSurnames);
            person.MiddleName = getRandom(maleMiddleNames);
        }
        person.Sex = sex;
        LocalDate birthday = randomDateInPast(1950, 2000);
        person.Birthday = birthday;
        person.Age = (int) Duration.between(birthday.atStartOfDay(), currentDate.atStartOfDay()).toDays() / 365;
        person.INN = generateInn();
        person.Country = getRandom(country);
        person.Region = getRandom(region);
        person.City = getRandom(city);
        person.Street = getRandom(street);
        person.ZipCode = generateZipCode();
        person.House = generateHouse();
        person.Apartment = generateApartment();
        return person;
    }

    private Integer generateNumber(Integer from, Integer to) {
       Integer result = from + (int) Math.round(Math.random() * (to - from));
        return result;
    }

    private LocalDate randomDateInPast(Integer fromYear, Integer toYear) {
        Integer lowerBound = (fromYear - 1970) * 365;
        Integer upperBound = (toYear - 1970) * 365;
        Integer number = generateNumber(lowerBound, upperBound);
        LocalDate randomDate = LocalDate.ofEpochDay(number);
        return randomDate;
    }

    private String generateInn() {
        int[] inn = {7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] weights1 = {7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};
        int[] weights2 = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0};

        for (int i = 2; i < 10; i++) {
            inn[i] = generateNumber(0, 9);
        }

        int controlSum1 = 0;
        for (int i = 0; i < weights1.length; i++) {
            controlSum1 += weights1[i] * inn[i];
        }
        int number1 = controlSum1 % 11;
        if (number1 > 9) {
            number1 = number1 % 10;
        }
        inn[10] = number1;
        int controlSum2 = 0;
        for (int i = 0; i < weights1.length; i++) {
            controlSum2 += weights2[i] * inn[i];
        }
        int number2 = controlSum2 % 11;
        if (number2 > 9) {
            number2 = number2 % 10;
        }
        inn[11] = number2;

        String result = "";
        for (int i = 0; i < inn.length; i++) {
            result += inn[i];
        }
        return result;
    }

    private String generateZipCode() {
        int[] zipCode = {1, 0, 0, 0, 0, 0};

        for (int i = 1; i < 6; i++) {
            zipCode[i] = generateNumber(0, 9);
        }
        String result = "";
        for (int i = 0; i < zipCode.length; i++) {
            result += zipCode[i];
        }
        return result;
    }
    private Integer generateHouse() {
        int House = (int)(Math.random() * 100) + 1;
        return House;
    }

    private Integer generateApartment() {
        int Apartment = (int)(Math.random() * 200) + 1;
        return Apartment;
    }

    }




