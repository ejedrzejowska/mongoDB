package phones;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PhoneDTO {
    private String id;
    private String name;
    private String brand;
    private Double ram;
    private Boolean touchScreen;
    private Date date;
    private Double weight;
    private String operatingSystem;
    private String displayProtection;
    private String cardStandard;
}
