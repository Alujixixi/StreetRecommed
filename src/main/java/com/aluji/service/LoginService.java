package com.aluji.service;

import com.aluji.dao.IStoreAdminDao;
import com.aluji.dao.IStreetAdminDao;
import com.aluji.dao.IUserDao;
import com.aluji.entities.StoreAdmin;
import com.aluji.entities.StreetAdmin;
import com.aluji.entities.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class LoginService {
    InputStream inputStream = null;
    String resource = null;
    SqlSessionFactory sqlSessionFactory = null;
    SqlSession sqlSession = null;
    //    private void init() throws IOException {
//        resource = "SqlMapConfig.xml";
//        inputStream = Resources.getResourceAsStream(resource);
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//        sqlSession = sqlSessionFactory.openSession();
//
//    }
    {
        resource = "SqlMapConfig.xml";
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    /*
    User Login Info
    login error fail return 0
    user login return 1
    street admin login return 2
    shop admin login return 3
     */
    public int checklogininfo(String username,String password) throws IOException {
        User user = null;
        sqlSession = sqlSessionFactory.openSession();
        try {
            // 操作CRUD，第一个参数：指定statement，规则：命名空间+“.”+statementId
            // 第二个参数：：这指定传入sql的参数里是用户id
            IUserDao iUserDao = sqlSession.getMapper(IUserDao.class);
            user = iUserDao.getUserByName(username);

        } finally {
            sqlSession.close();
        }
        if(user == null || !user.getUserPassword().equals(password))
            return 0;
        else if (user.getUserType() == 1)
            return 1;
        else return 2;
    }

    /*
    * 管理员登录检查。
    * */
    public Integer checkStreetAdminLoginInfo(Integer adminid,String password){
        sqlSession = sqlSessionFactory.openSession();
        IStreetAdminDao iStreetAdminDao = sqlSession.getMapper(IStreetAdminDao.class);
        //商业街管理员

            //商业街管理员id不存在
            StreetAdmin streetAdmin = iStreetAdminDao.getStreetAdminByAdminId(adminid);
               System.out.println(streetAdmin);
            if (streetAdmin != null) {
                //密码正确
                //商业街管理员用户
                if (streetAdmin.getStreetAdminPassword().equals(password)) return 1;
            }

        //不是管理员
        return 0;
    }

    public Integer checkStoreAdminLoginInfo(Integer adminid,String password){
        sqlSession = sqlSessionFactory.openSession();

        IStoreAdminDao iStoreAdminDao = sqlSession.getMapper(IStoreAdminDao.class);
        //商铺管理员
        if(iStoreAdminDao.isStoreAdmin(adminid)) {
            //商铺管理员id不存在
            StoreAdmin storeAdmin = iStoreAdminDao.getStoreAdminByAdminId(adminid);
            if (storeAdmin != null) {
                //密码正确
                //商铺管理员用户
                if (storeAdmin.getStoreAdminPassword().equals(password)) return 1;
            }
        }
        //不是管理员
        return 0;
    }
}
