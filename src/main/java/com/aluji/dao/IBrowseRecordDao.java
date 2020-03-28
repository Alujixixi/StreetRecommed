package com.aluji.dao;

import com.aluji.entities.Store;
import com.aluji.entities.StoreBrowse;

import java.util.List;
import java.util.Map;

public interface IBrowseRecordDao {

    public void insertstreetBrowseRecord(Map<String, Object> parm);

    public void insertstoreBrowseRecord(Map<String, Object> parm);

    public List<StoreBrowse> getAllstoreBrowseRecordBystreetId(Integer streetid);

    public List<StoreBrowse> getAllStoreBrowse();
}
