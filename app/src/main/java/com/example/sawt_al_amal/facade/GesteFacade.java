package com.example.sawt_al_amal.facade;

import android.content.Context;

import com.example.sawt_al_amal.bean.Geste;
import com.example.sawt_al_amal.dao.GesteDao;

import java.util.List;

public class GesteFacade extends GesteDao {
    public GesteFacade(Context context) {
        super(context);
    }

    public Geste findGesteByCours(int cours_id) {
        List<Geste> gestes = findAll();
        for (Geste geste : gestes) {
            if (geste.getCours().getId() == cours_id) {
                return geste;
            }
        }
        return null;
    }
}
