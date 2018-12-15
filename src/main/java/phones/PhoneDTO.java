package phones;

import lombok.*;

import java.time.LocalDate;

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
    private LocalDate date;
    private Double weight;
    private String operatingSystem;
    private String displayProtection;
    private String cardStandard;
}
