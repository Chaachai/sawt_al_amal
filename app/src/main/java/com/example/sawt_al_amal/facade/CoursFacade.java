package com.example.sawt_al_amal.facade;

import android.content.Context;

import com.example.sawt_al_amal.dao.CoursDao;

public class CoursFacade extends CoursDao {
    public CoursFacade(Context context) {
        super(context);
    }
}
