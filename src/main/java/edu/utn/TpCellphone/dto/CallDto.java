package edu.utn.TpCellphone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallDto {
    @JsonProperty
    private String numberOrigin;
    @JsonProperty
    private String numberDestination;
    @JsonProperty
    private int duration;
}
