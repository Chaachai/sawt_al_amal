package com.example.sawt_al_amal.facade;

import android.content.Context;

import com.example.sawt_al_amal.dao.CategoryDao;

public class CategoryFacade extends CategoryDao {
    public CategoryFacade(Context context) {
        super(context);
    }
}
