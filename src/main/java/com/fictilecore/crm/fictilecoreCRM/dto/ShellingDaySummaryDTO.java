package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Data;
import java.util.List;

@Data
public class ShellingDaySummaryDTO {

    private String date;
    private Integer totalWholes;
    private Integer totalBrokens;
    private List<String> lots;
    private List<String> origins;

}
