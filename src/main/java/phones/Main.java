package phones;

import java.time.LocalDate;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        PhoneDAO phoneDAO = PhoneDAO.getInstance();
//        List<PhoneDTO> phones = phonesDAO.getPhones();
//        phones.forEach(phone -> System.out.println(phone.toString()));

        PhoneDTO iphone6S = PhoneDTO.builder().name("Iphone 6S").brand("Apple").touchScreen(true)
                .date(LocalDate.of(2015, 10, 12)).cardStandard("Nano SIM")
                .ram(3072d).build();
        PhoneDTO siemensC55 = PhoneDTO.builder().name("C55").brand("Siemens").touchScreen(false)
                .date(LocalDate.of(2003, 03, 12)).cardStandard("Standard SIM")
                .weight(88d).build();
        PhoneDTO samsungS9 = PhoneDTO.builder().name("S9").brand("Samsung").touchScreen(true)
                .date(LocalDate.of(2018, 05, 12))
                .cardStandard("Nano SIM").weight(147d).ram(4096d).build();

        phoneDAO.insertPhones(Arrays.asList(iphone6S, siemensC55, samsungS9));

        phoneDAO.getPhones().forEach(phone -> System.out.println(phone.toString()));

        phoneDAO.deletePhone(iphone6S);
        phoneDAO.getPhones().forEach(phone -> System.out.println(phone.toString()));

        System.out.println("Get Siemens C55: ");
        siemensC55 = phoneDAO.getPhoneByName("C55");
        System.out.println(siemensC55);


        siemensC55.setName("CD431");
        siemensC55.setBrand("Motorola");

        phoneDAO.updatePhone(siemensC55);
        siemensC55 = phoneDAO.getPhoneByName("CD431");
        System.out.println(siemensC55);

    }
}
