package phones;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PhonesDAO phonesDAO = PhonesDAO.getInstance();
        List<PhoneDTO> phones = phonesDAO.getPhones();
//        phones.forEach(phone -> System.out.println(phone.toString()));

        PhoneDTO siemensC55 = phonesDAO.getPhoneByName("C55");
        System.out.println(siemensC55.toString());

        PhoneDTO honor8 = PhoneDTO.builder()
                .name("8")
                .brand("Honor")
                .touchScreen(true)
                .ram(4096.0)
                .build();

        phonesDAO.insertPhone(honor8);
    }
}
