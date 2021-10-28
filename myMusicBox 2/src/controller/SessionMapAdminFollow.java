package controller;

import java.util.HashMap;
import java.util.Map;


public class SessionMapAdminFollow {

    private static Map<String, Integer> sessionMap;

    private SessionMapAdminFollow(){
    }


    public static Map addSession(String key,Integer value){
        if(sessionMap==null){
            sessionMap = new HashMap<>();
        }
        sessionMap.put(key,value);
        return sessionMap;
    }

    public static Integer getSessionMap(String key) {
        return sessionMap.get(key);
    }

    public static void deleteSessionMap(String key){
        sessionMap.remove(key);
    }

    public static void deleteAllSession(){
        sessionMap = null;
    }
}
