package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreAdmin {
    private Integer storeAdminId;
    private String storeAdminPassword;
    private String storeAdminTel;
    private String storeAdminAddress;
    private Integer storeId;
}
