package edu.utn.TpCellphone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallDto {
    
    @JsonProperty
    private Date startTime;
    @JsonProperty
    private Date endTime;
    @JsonProperty
    private String number_origin;
    @JsonProperty
    private String number_destination;
}