package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreBrowse {
    private Integer userId;
    private Integer storeId;
    private Integer storebrowseId;
    private String time;
}
