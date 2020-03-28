package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreClicks {
    private Integer userId;
    private Integer storeId;
    private Integer clicks;

}
