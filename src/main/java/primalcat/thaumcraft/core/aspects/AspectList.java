package primalcat.thaumcraft.core.aspects;

import com.google.gson.*;
import primalcat.thaumcraft.core.registry.AspectRegistry;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AspectList implements JsonSerializer<AspectList>, JsonDeserializer<AspectList> {
    public LinkedHashMap<Aspect,Integer> aspects;


    public AspectList(){
        this.aspects = new LinkedHashMap<Aspect,Integer>();
    }

    public boolean isEmpty() {
        return aspects.isEmpty();
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
