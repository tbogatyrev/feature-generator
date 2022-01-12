package ru.lanit.utils.swagger.map;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapForGherkinTable<K, V> extends TreeMap<K, V> {

    @Override
    public String toString() {
        Iterator<Map.Entry<K, V>> i = entrySet().iterator();
        if (!i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            Map.Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append('|');
            sb.append(key == this ? "(this Map)" : key);
            sb.append('|');
            sb.append(value == this ? "(this Map)" : value.toString().replace("\"", ""));
            if (!i.hasNext())
                return sb.append('|').toString();
            sb.append('|').append('\n');
        }
    }
}
