package app;

import android.content.Context;
import android.content.SharedPreferences;

import bean.UserInfo;

public class UserManager {
    private UserInfo userInfo=new UserInfo();
    private SharedPreferences userSharedPreferences;
    private static final String USER_SETTING = "user_setting";

    private UserManager() {

    }

    private static class HolderUserManager {
        private static final UserManager userManager = new UserManager();
    }

    public static UserManager getInstance() {
        return HolderUserManager.userManager;
    }


    public void saveUserInfo(Context context, UserInfo userInfo) {
        userSharedPreferences = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPreferences.edit();
        editor.putString("userName", userInfo.getUserName());
        editor.putString("phoneNumber", userInfo.getPhoneNumber());
        editor.putString("phoneModel", userInfo.getPhoneModel());
        editor.putInt("loginId", userInfo.getLoginId());
        editor.putString("headImgUrl", userInfo.getHeadImgUrl());
        editor.commit();
    }

    public UserInfo getUserInfo(Context context) {
        userSharedPreferences = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        userInfo.setUserName(userSharedPreferences.getString("userName",""));
        userInfo.setPhoneNumber(userSharedPreferences.getString("phoneNumber",""));
        userInfo.setPhoneModel(userSharedPreferences.getString("phoneModel",""));
        userInfo.setLoginId(userSharedPreferences.getInt("loginId",0));
        userInfo.setHeadImgUrl(userSharedPreferences.getString("headImgUrl",""));
        return userInfo;
    }

    public void loginOut(Context context){
        userSharedPreferences = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPreferences.edit();
        editor.clear();
        editor.commit();

    }
}
