package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StreetBrowse {
    private Integer userId;
    private Integer streetId;
    private Integer streetbrowseId;
    private String time;
}
