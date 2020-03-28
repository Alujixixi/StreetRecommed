package com.aluji.controller;

import com.aluji.dao.IBrowseRecordDao;
import com.aluji.entities.Item;
import com.aluji.entities.Store;
import com.aluji.entities.Street;
import com.aluji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    StreetService streetService;

    @Autowired
    StoreService storeService;

    @Autowired
    ItemService itemService;

    @Autowired
    BrowseService browseService;

    @Autowired
    UserService userService;

    @Autowired
    RecommendService recommendService;

    @RequestMapping("/main.html")
    public String list2(Map<String,Object> map){
        Collection<Street> streets = null;
        try {
            streets = streetService.getAllstreet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("streets",streets);
        return "streetlist";
    }

    //进商业街，来到商铺一栏
    @RequestMapping("/tostreet/{streetid}")
    public String tostorelist(@PathVariable("streetid") Integer streetId,
                              Map<String,Object> map,
                                HttpSession session) throws IOException {
        List<Store> stores = storeService.getAllStoreInStreet(streetId);
        map.put("stores",stores);
        Integer userId = userService.getuserIdByuserName((String)session.getAttribute("loginUser"));
        List<Store> recommendStores = recommendService.getRecommendStores(userId);

        map.put("recommendStores",recommendStores);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        browseService.setStreetBrowseRecord(userId,streetId,sdf.format(new Date()));
        Street currentStreet = streetService.getStreetById(streetId);
        session.setAttribute("currentStreet",currentStreet);
        return "storelist";
    }

    //进入商铺，来到商品一栏
    @RequestMapping("/tostore/{storeid}")
    public String toitemlist(@PathVariable("storeid") Integer storeId,
                              Map<String,Object> map,
                              HttpSession session) throws IOException {
        List<Item> items =  itemService.getAllItemInStore(storeId);
        Integer userid = userService.getuserIdByuserName((String)session.getAttribute("loginUser"));
        storeService.getAllStoreInStreet(storeId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        browseService.setStoreBrowseRecord(userid,storeId,sdf.format(new Date()));
        session.setAttribute("items",items);
        Street currentstore = streetService.getStreetById(storeId);
        session.setAttribute("currentStore",currentstore);
        return "itemlist";
    }
}
