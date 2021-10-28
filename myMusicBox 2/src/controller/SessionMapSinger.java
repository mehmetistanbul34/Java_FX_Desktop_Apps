package controller;

import library.Singer;
import library.Users;

import java.util.HashMap;
import java.util.Map;


public class SessionMapSinger {

    private static Map<String, Singer> sessionMap;

    private SessionMapSinger(){
    }


    public static Map addSession(String key,Singer value){
        if(sessionMap==null){
            sessionMap = new HashMap<>();
        }
        sessionMap.put(key,value);
        return sessionMap;
    }

    public static Singer getSessionMap(String key) {
        return sessionMap.get(key);
    }

    public static void deleteSessionMap(String key){
        sessionMap.remove(key);
    }

    public static void deleteAllSession(){
        sessionMap = null;
    }
}
