package controller;

import library.Singer;
import library.Song;

import java.util.HashMap;
import java.util.Map;


public class SessionMapSong {

    private static Map<String, Song> sessionMap;

    private SessionMapSong(){
    }


    public static Map addSession(String key,Song value){
        if(sessionMap==null){
            sessionMap = new HashMap<>();
        }
        sessionMap.put(key,value);
        return sessionMap;
    }

    public static Song getSessionMap(String key) {
        return sessionMap.get(key);
    }

    public static void deleteSessionMap(String key){
        sessionMap.remove(key);
    }

    public static void deleteAllSession(){
        sessionMap = null;
    }
}
