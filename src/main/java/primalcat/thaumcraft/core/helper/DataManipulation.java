package primalcat.thaumcraft.core.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManipulation {
    public static <T> void mergeLists(List<T> firstList, List<T> secondList) {
        for (T element : secondList) {
            if (!firstList.contains(element)) {
                firstList.add(element);
            }
        }
    }
    public static Map<String, Integer> subtractMaps(Map<String, Integer> originalMap, Map<String, Integer> subtractMap) {
        // Create a new map to hold the result
        Map<String, Integer> resultMap = new HashMap<>(originalMap);

        for (Map.Entry<String, Integer> entry : subtractMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            if (resultMap.containsKey(key)) {
                int currentValue = resultMap.get(key);
                int newValue = currentValue - value;

                // Make sure the new value is not negative
                if (newValue >= 0) {
                    resultMap.put(key, newValue);
                } else {
                    resultMap.put(key, 0); // Set the value to zero if it would be negative
                }
            }
            // No else branch here, so if the key is not present, it's just skipped
        }

        return resultMap;
    }
}
