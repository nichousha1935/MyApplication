package com.example.ind4.myapplication;

public class UserManager {
    private UserManager(){

    }
    private static class  HolderUserManager{
        private static  final UserManager userManager=new UserManager();
    }

    public static UserManager getInstance(){
        return HolderUserManager.userManager;
}
}
