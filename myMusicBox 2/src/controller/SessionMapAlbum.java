package controller;

import library.Album;
import library.Song;

import java.util.HashMap;
import java.util.Map;


public class SessionMapAlbum {

    private static Map<String, Album> sessionMap;

    private SessionMapAlbum(){
    }


    public static Map addSession(String key,Album value){
        if(sessionMap==null){
            sessionMap = new HashMap<>();
        }
        sessionMap.put(key,value);
        return sessionMap;
    }

    public static Album getSessionMap(String key) {
        return sessionMap.get(key);
    }

    public static void deleteSessionMap(String key){
        sessionMap.remove(key);
    }

    public static void deleteAllSession(){
        sessionMap = null;
    }
}
