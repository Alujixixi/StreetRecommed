package com.aluji.controller;

import com.aluji.entities.Store;
import com.aluji.entities.StoreBrowse;
import com.aluji.entities.StreetAdmin;
import com.aluji.service.BrowseService;
import com.aluji.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    StoreService storeService;

    @Autowired
    BrowseService browseService;

    //去增加页
    @RequestMapping(value = "/admin/toaddstore")
    public String toaddstore(){
        return "addstore";
    }

    //增加商铺
    @PostMapping("/admin/addstore")
    public String addstreet(Store store,
                            HttpSession session) throws IOException {
        StreetAdmin streetAdmin = (StreetAdmin)session.getAttribute("currentstreetadmin");
        Integer streetid = streetAdmin.getStreetId();
        System.out.println(streetid);
        store.setStreetId(streetid);
        storeService.addStore(store);
        List<Store> allStoreInStreet = storeService.getAllStoreInStreet(streetid);
        session.setAttribute("currentstores",allStoreInStreet);
        return "streetadmin";
    }

    //去修改商铺页
    @RequestMapping(value = "/admin/toalterstore/{storeid}")
    public String toalterstore(@PathVariable("storeid")Integer storeid,
                               HttpSession session) throws IOException {
        Store alteringstore = storeService.getStoreById(storeid);
        session.setAttribute("alteringstore",alteringstore);
        return "alterstore";
    }

    //修改商铺
    @PostMapping("/admin/alterstore/{storeid}")
    public String alterstore(@PathVariable("storeid") Integer storeid, Store store,
                            HttpSession session) throws IOException {
        StreetAdmin streetAdmin = (StreetAdmin)session.getAttribute("currentstreetadmin");
        Integer streetid = streetAdmin.getStreetId();
        store.setStoreId(storeid);
        storeService.alterStore(store);
        List<Store> allStoreInStreet = storeService.getAllStoreInStreet(streetid);
        session.setAttribute("currentstores",allStoreInStreet);
        return "streetadmin";
    }

    @RequestMapping("/admin/deletestore/{storeid}")
    public String deletestore(@PathVariable("storeid")Integer storeid,HttpSession session) throws IOException {
        storeService.deleteStore(storeid);
        StreetAdmin streetAdmin = (StreetAdmin)session.getAttribute("currentstreetadmin");
        Integer streetid = streetAdmin.getStreetId();
        List<Store> allStoreInStreet = storeService.getAllStoreInStreet(streetid);
        session.setAttribute("currentstores",allStoreInStreet);
        return "streetadmin";
}

    @RequestMapping("/streetmanage")
    public String streetadminmain(){
        return "streetadmin";
    }
    @RequestMapping("streetbrowselog")
    public String streetbrowselog(HttpSession session){
        StreetAdmin streetAdmin = (StreetAdmin)session.getAttribute("currentstreetadmin");
        List<StoreBrowse> list = browseService.getAllstoreBrowseBystreetId(streetAdmin.getStreetId());
        session.setAttribute("currentstorevbrowserecords",list);
        return "streetbrowselog";
    }


}
