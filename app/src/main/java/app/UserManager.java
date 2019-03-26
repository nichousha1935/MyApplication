package app;

import android.content.Context;
import android.content.SharedPreferences;

import bean.UserInfo;

public class UserManager {
    private UserInfo userInfo=new UserInfo();
    private SharedPreferences userSharedPreferenced;
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
        userSharedPreferenced = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPreferenced.edit();
        editor.putString("userName", userInfo.getUserName());
        editor.putString("phoneNumber", userInfo.getPhoneNumber());
        editor.putString("phoneModel", userInfo.getPhoneModel());
        editor.putInt("loginId", userInfo.getLoginId());
        editor.putString("headImgUrl", userInfo.getHeadImgUrl());
        editor.commit();
    }

    public UserInfo getUserInfo(Context context) {
        userSharedPreferenced = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        userInfo.setUserName(userSharedPreferenced.getString("userName",""));
        userInfo.setPhoneNumber(userSharedPreferenced.getString("phoneNumber",""));
        userInfo.setPhoneModel(userSharedPreferenced.getString("phoneModel",""));
        userInfo.setLoginId(userSharedPreferenced.getInt("loginId",0));
        userInfo.setHeadImgUrl(userSharedPreferenced.getString("headImgUrl",""));
        return userInfo;
    }

    public void loginOut(Context context){
        userSharedPreferenced = context.getSharedPreferences(USER_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSharedPreferenced.edit();
        editor.clear();
        editor.commit();

    }
}
