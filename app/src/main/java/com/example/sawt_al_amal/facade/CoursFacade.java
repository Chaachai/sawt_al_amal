package com.example.sawt_al_amal.facade;

import android.content.Context;

import com.example.sawt_al_amal.bean.Cours;
import com.example.sawt_al_amal.dao.CoursDao;
import java.util.ArrayList;
import java.util.List;

public class CoursFacade extends CoursDao {
    public CoursFacade(Context context) {
        super(context);
    }

    public List<Cours> findCoursByLvl(int lvl_id) {
        List<Cours> list = findAll();
        List<Cours> res = new ArrayList<>();
        for (Cours cours : list) {
            if (cours.getNiveau().getId() == lvl_id) {
                res.add(cours);
            }
        }
        return res;
    }
}
