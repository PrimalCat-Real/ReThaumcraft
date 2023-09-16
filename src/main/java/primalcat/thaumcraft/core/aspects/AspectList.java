package primalcat.thaumcraft.core.aspects;

import com.google.gson.*;
import primalcat.thaumcraft.core.registry.AspectRegistry;

import java.lang.reflect.Type;
import java.util.*;

public class AspectList implements JsonSerializer<AspectList>, JsonDeserializer<AspectList> {
    public LinkedHashMap<Aspect,Integer> aspects;


    public AspectList(){
        this.aspects = new LinkedHashMap<Aspect,Integer>();
    }

    public boolean isEmpty() {
        return aspects.isEmpty();
    }

    public AspectList sortByName() {
        List<Map.Entry<Aspect, Integer>> sortedEntries = new ArrayList<>(aspects.entrySet());

        // Sort the entries by the name of Aspect
        Collections.sort(sortedEntries, new Comparator<Map.Entry<Aspect, Integer>>() {
            @Override
            public int compare(Map.Entry<Aspect, Integer> entry1, Map.Entry<Aspect, Integer> entry2) {
                // Compare the names of the aspects in alphabetical order
                return entry1.getKey().getName().compareTo(entry2.getKey().getName());
            }
        });

        // Clear the existing map
        aspects.clear();

        // Populate the aspects map with the sorted entries
        for (Map.Entry<Aspect, Integer> entry : sortedEntries) {
            aspects.put(entry.getKey(), entry.getValue());
        }

        // Return this for chaining
        return this;
    }

    public AspectList add(Aspect aspect, int amount) {
        if (aspect != null && amount > 0 && !aspects.containsKey(aspect)) {
            aspects.put(aspect, amount);
        }
        return this;
    }

    public Map<String, Integer> toMap() {
        Map<String, Integer> resultMap = new HashMap<>();
        for (Aspect aspect : aspects.keySet()) {
            int amount = aspects.get(aspect);
            resultMap.put(aspect.getName(), amount);
        }
        return resultMap;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AspectList [");
        boolean isFirst = true;
        for (Aspect aspect : aspects.keySet()) {
            int amount = aspects.get(aspect);
            if (!isFirst) {
                sb.append(", ");
            }
            sb.append(aspect.getName()).append(": ").append(amount);
            isFirst = false;
        }
        sb.append("]");
        return sb.toString();
    }
    public AspectList merge(AspectList other) {
        AspectList mergedList = new AspectList();

        // Copy the aspects from the current list to the merged list
        for (Map.Entry<Aspect, Integer> entry : this.aspects.entrySet()) {
            Aspect aspect = entry.getKey();
            int amount = entry.getValue();
            mergedList.add(aspect, amount);
        }

        // Merge the aspects from the other list into the merged list
        for (Map.Entry<Aspect, Integer> entry : other.aspects.entrySet()) {
            Aspect aspect = entry.getKey();
            int amount = entry.getValue();

            // If the aspect already exists in the merged list, add the amounts
            if (mergedList.aspects.containsKey(aspect)) {
                int existingAmount = mergedList.aspects.get(aspect);
                mergedList.aspects.put(aspect, existingAmount + amount);
            } else {
                // Otherwise, add the aspect to the merged list
                mergedList.aspects.put(aspect, amount);
            }
        }

        return mergedList;
    }
    public void clear() {
        aspects.clear();
    }

    @Override
    public AspectList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AspectList aspectList = new AspectList();
        JsonObject jsonObject = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String aspectName = entry.getKey();
            int amount = entry.getValue().getAsInt();
            Aspect aspect = AspectRegistry.getAspect(aspectName);
            aspectList.add(aspect, amount);
        }
        return aspectList;
    }


    @Override
    public JsonElement serialize(AspectList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        for (Aspect aspect : src.aspects.keySet()) {
            int amount = src.aspects.get(aspect);
            jsonObject.addProperty(aspect.getName(), amount);
        }
        return jsonObject;
    }
}
