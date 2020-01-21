package com.example.sawt_al_amal.facade;

import android.content.Context;

import com.example.sawt_al_amal.dao.UserDao;

public class UserFacade extends UserDao {

    public UserFacade(Context context) {
        super(context);
    }
}
