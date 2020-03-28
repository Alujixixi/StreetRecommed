package com.aluji.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private  String userName;
    private String userPassword;
    private String userGender;
    private String userTel;
    private String userAddress;
    private Date userBirthday;
    private String userEmail;
    //普通用户1，游客用户2
    private int userType;

}
