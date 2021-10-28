package controller;

import library.Admin;
import library.Users;

import java.util.HashMap;
import java.util.Map;


public class SessionMapAdmin {

    private static Map<String, Users> sessionMap;

    private SessionMapAdmin(){
    }


    public static Map addSession(String key, Users value){
        if(sessionMap==null){
            sessionMap = new HashMap<>();
        }
        sessionMap.put(key,value);
        return sessionMap;
    }

    public static Users getSessionMap(String key) {
        return sessionMap.get(key);
    }

    public static void deleteSessionMap(String key){
        sessionMap.remove(key);
    }

    public static void deleteAllSession(){
        sessionMap = null;
    }
}
