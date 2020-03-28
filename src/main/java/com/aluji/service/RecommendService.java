package com.aluji.service;

import com.aluji.dao.IBrowseRecordDao;
import com.aluji.entities.Item;
import com.aluji.entities.Store;
import com.aluji.entities.StoreBrowse;
import com.aluji.entities.User;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RecommendService {

    @Autowired
    UserService userService;

    @Autowired
    StoreService storeService;

    @Autowired
    BrowseService browseService;

    public static int RECOMMEND_NUM = 6;


    public List<Store> getRecommendStores(Integer recommendUserId) throws IOException {

//        List<Integer> recommendStoreId = getOnlineRecommendStoreId(recommendUserId);
        List<Integer> recommendStoreId = storeService.getRecommendStoreIdByUserId(recommendUserId);
        List<Store> recommendStore = new ArrayList<>();
        for(int i = 0; i < Math.min(RECOMMEND_NUM,recommendStoreId.size()); i++) {
            System.out.println(recommendStoreId.get(i));
            Store store = storeService.getStoreById(recommendStoreId.get(i));
            System.out.println(store.getStoreName());
            recommendStore.add(store);
        }
        return recommendStore;
    }

    /**
     *  在线计算推荐列表
     * @param recommendUserId 被推荐的用户的Id
     * @return 返回推荐给该用户的Store的storeId列表
     */
    public List<Integer> getOnlineRecommendStoreId(Integer recommendUserId) {
        //userId的集合
        Set<Integer> userIds = new HashSet<>();
        //storeId的集合
        Set<Integer> storeIds = new HashSet<>();
        //获取所有浏览记录
        List<StoreBrowse> storeBrowses = browseService.getAllStoreBrowse();

        //storeId对应的store上发生浏览行为的所有用户Id
        // storeId,List<userId>
        Map<Integer,Set<Integer>> storeToUsers = new HashMap<>();
        //userId对应的用户所有有点击记录的商铺Id
        //userId,List<storeId>
        Map<Integer,Set<Integer>> userToStores = new HashMap<>();
        //遍历storeBrowse,填充上面的两个map
        for(StoreBrowse storeBrowse:storeBrowses) {
            if(storeToUsers.containsKey(storeBrowse.getStoreId())) {
                    storeToUsers.get(storeBrowse.getStoreId()).add(storeBrowse.getUserId());
            } else {
                storeToUsers.put(storeBrowse.getStoreId(),new HashSet<>(storeBrowse.getUserId()));
            }
            if(userToStores.containsKey(storeBrowse.getUserId())) {
                userToStores.get(storeBrowse.getUserId()).add(storeBrowse.getStoreId());
            } else {
                userToStores.put(storeBrowse.getUserId(),new HashSet<>(storeBrowse.getStoreId()));
            }
            if(!userIds.contains(storeBrowse.getUserId())) userIds.add(storeBrowse.getUserId());
            if(!storeIds.contains(storeBrowse.getStoreId())) storeIds.add(storeBrowse.getStoreId());
        }

        //建立矩阵用户Id与用户Id的矩阵，矩阵中的值为用户i与用户j共同浏览过的商铺数

        Map<Integer,Integer> userIdToIndex = new HashMap<>();
        Map<Integer,Integer> indexToUserId = new HashMap<>();

        int idx = 0;
        List<Integer> allUserId = userService.getAllUserId();
        for(Integer i: allUserId) {
            userIdToIndex.put(i,idx);
            indexToUserId.put(idx,i);
            idx++;
        }
        int[][] Matrix = new int[idx+1][idx+1];
        Set<Map.Entry<Integer, Set<Integer>>> entrySet = storeToUsers.entrySet();
        Iterator<Map.Entry<Integer, Set<Integer>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<Integer> commonUsers = iterator.next().getValue();
            for (Integer user_u : commonUsers) {
                for (Integer user_v : commonUsers) {
                    if(user_u == user_v){
                        continue;
                    }
                    Matrix[userIdToIndex.get(user_u)][userIdToIndex.get(user_v)] += 1;//计算用户u与用户v都有正反馈的物品总数
                }
            }
        }

        //计算指定用户recommendUser的物品推荐度
        List<Pair<Integer,Double>> recommendDegree = new ArrayList<>();
        for(Integer storeId : storeIds){//遍历每一个商铺
            System.out.println("storeId:" + storeId);
            Set<Integer> users = storeToUsers.get(storeId);//得到浏览过当前正在遍历的商铺的所有用户集合
            if(!users.contains(recommendUserId)){//如果被推荐用户没有浏览过当前商铺，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                //对每一个浏览过当前正在遍历的商铺的用户，计算该用户与被推荐用户的相似度。
                //计算方法为矩阵中recommendUserId与userId所对应的值除以两个用户各自总浏览次数乘积的开平方。
                for(Integer userId: users){
                    itemRecommendDegree += Matrix[userIdToIndex.get(recommendUserId)][userIdToIndex.get(userId)]
                            /Math.sqrt(userToStores.get(recommendUserId).size()*userToStores.get(userId).size());//推荐度计算
                }
                recommendDegree.add(new Pair<Integer, Double>(storeId, itemRecommendDegree));
                System.out.println("The item "+storeId+" for "+recommendUserId +"'s recommended degree:"+itemRecommendDegree);
            }
        }
        //推荐度进行排序
        recommendDegree.sort(new Comparator<Pair<Integer, Double>>() {
            @Override
            public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
                if(o1.getValue() - o2.getValue() < 0) return 1;
                else if(o1.getValue() - o2.getValue() == 0) return 0;
                return -1;
            }
        });
        List<Integer> ans = new ArrayList<>();
        for(int i = 0; i < Math.min(RECOMMEND_NUM,recommendDegree.size()); i++) {
            ans.add(recommendDegree.get(i).getKey());
        }
        return ans;
    }

}
