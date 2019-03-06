/*import com.sun.javafx.collections.MappingChange;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.json.JSONPropertyIgnore;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

//@Getter
//@Setter
//@JSONPropertyIgnore

public class PersonGeneratorFromApi extends Person {
    private String gender;
    private Map<String,String> name;
    private Map<Object,Object> location;
    private Map<String,String> dob;

    public Person toPerson() throws ParseException {
        SimpleDateFormat dataFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataFormat.parse(dob.get("data")));
        return Person.createBuilder()
                .setName(name.get("first"))
                .setMiddlename("-")
                .setSurname(name.get("last"))
                .setSex(gender == "female" ? false : true)
                .setBirthday(calendar)
                .setZipCode(String.valueOf(location.get("postcode")))
                .setRegion((String) location.get("state"))
                .setCity((String) location.get("city"))
                .setStreet((String) location.get("street"))
                .setHouse(ThreadLocalRandom.current().nextInt(0, 150))
                .setApartment(ThreadLocalRandom.current().nextInt(0, 400))
                .build();
    }

    public void close() {
        gender = null;
        name = null:
        location = null;
        dob = null;
    }

    private List<Person> mappingUsers(String jsonPerson) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonHuman).findValue("results");
        List<Person> randomUsers = new ArrayList<>();
        for (int indexArray = 0; indexArray < rootNode.size();indexArray++) {
            try (PersonGeneratorFromApi personRandomUser = mapper.readValue(rootNode.get(indexArray).toString(), PersonGeneratorFromApi.class)) {
                randomUsers.add(personRandomUser.toPerson())
            }

        }
        return randomUsers;
    }
}
*/