package phones;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PhonesDAO {

    private static PhonesDAO instance;
    private static final MongoClient client = new MongoClient("127.0.0.1", 27017);

    private PhonesDAO() {

    }

    public static PhonesDAO getInstance() {
        if (instance == null) {
            synchronized (PhonesDAO.class) {
                if (instance == null) {
                    instance = new PhonesDAO();
                }
            }
        }
        return instance;
    }

    public List<PhoneDTO> getPhones() {
        FindIterable<Document> documents = getPhonesCollection().find();
        List<PhoneDTO> phones = new ArrayList<>();
        for (Document document : documents) {
            PhoneDTO phoneDTO = buildPhoneDTO(document);
            phones.add(phoneDTO);
        }
        return phones;
    }

    public PhoneDTO getPhoneByName(String name) {
        Document document = getPhonesCollection().find(Filters.eq("name", name)).first();

        PhoneDTO phoneDTO = buildPhoneDTO(document);

        return phoneDTO;
    }

    public void insertPhone(PhoneDTO phoneDTO) {
        Document document = new Document();
        document.append("name", phoneDTO.getName())
                .append("brand", phoneDTO.getBrand())
                .append("touchscreen", phoneDTO.getTouchScreen())
                .append("ram", phoneDTO.getRam())
                .append("weight", phoneDTO.getWeight())
                .append("operatingSystem", phoneDTO.getOperatingSystem())
                .append("displayProtection", phoneDTO.getDisplayProtection())
                .append("cardStandard", phoneDTO.getCardStandard())
                .append("releaseDate", phoneDTO.getDate());
        getPhonesCollection().insertOne(document);
    }

    private PhoneDTO buildPhoneDTO(Document document){
        PhoneDTO phoneDTO = PhoneDTO.builder()
                .id(document.getObjectId("_id").toString())
                .name(document.getString("name"))
                .brand(document.getString("brand"))
                .ram(document.getDouble("ram"))
                .touchScreen(document.getBoolean("touchscreen"))
                .cardStandard(document.getString("cardStandard"))
                .date(document.getDate("releaseDate"))
                .operatingSystem(document.getString("operatingSystem"))
                .weight(document.getDouble("weight"))
                .displayProtection(document.getString("displayProtection"))
                .build();
        return phoneDTO;
    }

    public MongoCollection<Document> getPhonesCollection() {
        return client.getDatabase("phonesDB").getCollection("phones");

    }
}
