package com.Bryan.Server;

public class User {
    public volatile static String inputUsername, inputPassword;
    private volatile static String Username, Password, Type, Region;

    // Set and Get
    public static void setUsername(String username) {
        Username = username;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setPassword(String password) {
        Password = password;
    }

    public static String getPassword() {
        return Password;
    }

    public static void setType(String type){
        Type = type;
    }

    public static String getType(){
        return Type;
    }

    public static void setRegion(String region) {Region = region;}

    public static String getRegion (){ return Region;}

    // Logic
    public static boolean loginLogic() { //returns the result if the input password and email matches the values in database (called from method)
        return inputUsername.equals(Username) && inputPassword.equals(Password);
    }

}

