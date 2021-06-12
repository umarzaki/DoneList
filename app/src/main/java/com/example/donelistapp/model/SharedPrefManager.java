package com.example.donelistapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_DONELIST = "spDoneList";
    public static final String SP_ID = "spId";
    public static final String SP_NAMA = "spNama";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_IS_ACTIVE = "spIsActive";


    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_DONELIST, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void clearAll(){
        spEditor.clear();
        spEditor.commit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPId(){
        return sp.getString(SP_ID, "");
    }

    public Boolean getSPIsActive(){
        return sp.getBoolean(SP_IS_ACTIVE, false);
    }


}
