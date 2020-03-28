package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreetAdmin {
    private Integer streetAdminId;
    private String streetAdminPassword;
    private String streetAdminTel;
    private String streetAdminAddress;
    private Integer streetId;
}
