package org.example.collectionManager;
import java.util.Map;
public class KeyToValue {
    public static <K, V> K getKeyFromValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
