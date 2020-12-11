package net.hashsploit.mediusdiscordbot.util;

import net.hashsploit.mediusdiscordbot.Pair;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.lang.NullPointerException;

public class TimedHashmap <K,V> {
    private HashMap<K, Pair<LocalDateTime,V>> hashmap;
    private int expireSeconds;

    public TimedHashmap(){
        this.expireSeconds = 60*5;
        hashmap = new HashMap<K, Pair<LocalDateTime,V>>();
    }

    public TimedHashmap(int expireSeconds){
        this.expireSeconds = expireSeconds;
        hashmap = new HashMap<K, Pair<LocalDateTime,V>>();
    }

    public void put(K key, V val){
        hashmap.put(key, new Pair<LocalDateTime, V>(LocalDateTime.now(), val));
    }

    public V get(K key) throws NullPointerException{
        if (!containsKey(key)){
            return null;
        }
        return hashmap.get(key).getSecond();
    }

    public boolean containsKey(K key){
        if (!hashmap.containsKey(key)){
            return false;
        }

        LocalDateTime currTime = hashmap.get(key).getFirst();

        return currTime.plusSeconds(this.expireSeconds).isBefore(LocalDateTime.now());
    }
}