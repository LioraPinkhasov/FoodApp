//package com.example.foodapp.notifications;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class SavingToken {
//
//    private static final String SHARED_PREFERENCES_NAMES = "FCMSharedPreferences";
//    private static final String KEY_ACCESS_TOKEN = "token";
//
//    private static Context context;
//    private static SavingToken instance;
//
//    public SavingToken(Context context){
//        this.context = context;
//    }
//
//    public static synchronized SavingToken getInstance(Context context){
//        if(instance == null)
//            instance = new SavingToken(context);
//        return instance;
//    }
//
//    public boolean storeToken(String token){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAMES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(KEY_ACCESS_TOKEN, token);
//        editor.apply();
//        return true;
//    }
//
//    public String getToken(){
//        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAMES, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_ACCESS_TOKEN,null);
//    }
//
//
//}
