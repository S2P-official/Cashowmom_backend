package com.fictilecore.crm.fictilecoreCRM.entity.tenant;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String line1;      // Building / Plot / Company
    private String line2;      // Area / Landmark
    private String street;     // Street / Road
    private String city;       // City / Taluka
    private String district;   // District
    private String state;      // State / Province
    private String pinCode;    // PIN / ZIP
    private String country;    // Country
}
