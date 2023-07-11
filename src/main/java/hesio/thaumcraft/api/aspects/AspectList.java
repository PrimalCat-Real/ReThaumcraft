package hesio.thaumcraft.api.aspects;

import com.google.gson.*;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AspectList implements JsonSerializer<AspectList>, JsonDeserializer<AspectList> {
    public LinkedHashMap<Aspect,Integer> aspects;


    public AspectList(){
        this.aspects = new LinkedHashMap<Aspect,Integer>();
    }


    public AspectList add(Aspect aspect, int amount) {
        if (aspect != null && amount > 0 && !aspects.containsKey(aspect)) {
            aspects.put(aspect, amount);
        }
        return this;
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

    @Override
    public AspectList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AspectList aspectList = new AspectList();
        JsonObject jsonObject = json.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String aspectName = entry.getKey();
            int amount = entry.getValue().getAsInt();
            Aspect aspect = Aspect.getAspect(aspectName);
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
