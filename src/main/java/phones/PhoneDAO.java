package phones;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PhoneDAO {

    private static PhoneDAO instance;
    private static final MongoClient client = new MongoClient("127.0.0.1", 27017);
    private static final String PHONE_COLLECTION = "phones";

    private PhoneDAO() {

    }

    public static PhoneDAO getInstance() {
        if (instance == null) {
            synchronized (PhoneDAO.class) {
                if (instance == null) {
                    instance = new PhoneDAO();
                }
            }
        }
        return instance;
    }

    private static void init() {
        MongoDatabase database = client.getDatabase("phonesDB");
        database.getCollection(PHONE_COLLECTION).drop();
        database.createCollection(PHONE_COLLECTION);
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

    public void insertPhones(List<PhoneDTO> phones) {
        List<Document> documents = new ArrayList<>();
        for (PhoneDTO phoneDto : phones) {
            Document document = new Document();
            document.append("name", phoneDto.getName())
                    .append("brand", phoneDto.getBrand())
                    .append("touchscreen", phoneDto.getTouchScreen())
                    .append("releaseDate", phoneDto.getDate())
                    .append("weight", phoneDto.getWeight())
                    .append("ram", phoneDto.getRam())
                    .append("cardStandard", phoneDto.getCardStandard())
                    .append("operatingSystem", phoneDto.getOperatingSystem())
                    .append("displayProtection", phoneDto.getDisplayProtection());

            documents.add(document);
        }
        getPhonesCollection().insertMany(documents);
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

    private PhoneDTO buildPhoneDTO(Document document) {
        PhoneDTO phoneDTO = PhoneDTO.builder()
                .id(document.getObjectId("_id").toString())
                .name(document.getString("name"))
                .brand(document.getString("brand"))
                .ram(document.getDouble("ram"))
                .touchScreen(document.getBoolean("touchscreen"))
                .cardStandard(document.getString("cardStandard"))
                .operatingSystem(document.getString("operatingSystem"))
                .weight(document.getDouble("weight"))
                .displayProtection(document.getString("displayProtection"))
                .build();

        Optional.ofNullable(document.getDate("releaseDate")).ifPresent(elem -> phoneDTO
                .setDate(elem.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        return phoneDTO;
    }

    public void updatePhone(PhoneDTO phoneDto){
        BasicDBObject updateFields = new BasicDBObject();
        updateFields.put("name",phoneDto.getName());
        updateFields.put("brand",phoneDto.getBrand());

        BasicDBObject setQuery = new BasicDBObject();
        setQuery.put("$set", updateFields);

        BasicDBObject searchQuery = new BasicDBObject("_id",new ObjectId(phoneDto.getId()));
        getPhonesCollection().updateOne(searchQuery,setQuery);

    }

    public void deletePhone(PhoneDTO phoneDto) {
        BasicDBObject basicDBObject = new BasicDBObject("name",phoneDto.getName());
        DeleteResult deleteResult = getPhonesCollection().deleteOne(basicDBObject);
        if(deleteResult.getDeletedCount()!=0) {
            System.out.println(phoneDto.getName() + " phone has been deleted.");
        }
    }

    public MongoCollection<Document> getPhonesCollection() {
        return client.getDatabase("phonesDB").getCollection(PHONE_COLLECTION);

    }
}
