package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    public Integer storeId;
    public String storeName;
    public String storeAddress;
    public String storeType;
    public Integer avgPrice;
    public Integer streetId;
}
