package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Street {
    private Integer streetId;
    private String streetName;
    private String streetAddress;
    private String streetType;
}
