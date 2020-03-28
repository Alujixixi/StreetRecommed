package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer itemId;
    private String itemName;
    private String itemType;
    private Double itemPrice;
    private String itemDiscribe;
    private Integer storeId;
}
