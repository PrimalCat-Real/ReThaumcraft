package primalcat.thaumcraft.common.aspects;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import primalcat.thaumcraft.api.Aspect;
import primalcat.thaumcraft.init.AspectInit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerAspects {
    private final Map<String, Integer> aspectMap = new HashMap<>();
    private final List<String> stringList = new ArrayList<>();

    public List<String> getTargetsList() {
        return stringList;
    }

    public Map<String, Integer> getAspects() {
        return aspectMap;
    }
    public void saveNBTData(CompoundTag nbt) {
        CompoundTag aspectsTag = new CompoundTag();
        for (Map.Entry<String, Integer> entry : aspectMap.entrySet()) {
            CompoundTag aspectData = new CompoundTag();

            aspectData.putString("AspectName", entry.getKey()); // Store Aspect name as String
            aspectData.putInt("Value", entry.getValue());

            aspectsTag.put(entry.getKey(), aspectData);
        }
        nbt.put("Aspects", aspectsTag);

        CompoundTag scannedTag = new CompoundTag();
        for (int i = 0; i < stringList.size(); i++) {
            scannedTag.putString(""+i, stringList.get(i));
        }
        nbt.put("Scanned", scannedTag);
    }

    public void loadNBTData(CompoundTag nbt) {
        CompoundTag aspectsTag = nbt.getCompound("Aspects");
        for (String aspectName : aspectsTag.getAllKeys()) {
            CompoundTag aspectData = aspectsTag.getCompound(aspectName);

            String aspectNameString = aspectData.getString("AspectName"); // Retrieve Aspect name as String
            Aspect aspect = AspectInit.getAspect(aspectNameString); // Get Aspect using the name
            if (aspect != null) {
                int value = aspectData.getInt("Value");
                aspectMap.put(aspect.getName(), value);
            }
        }
        CompoundTag scannedTag = nbt.getCompound("Scanned"); // Corrected line
        for (String key : scannedTag.getAllKeys()) {
            stringList.add(scannedTag.getString(key));
        }
    }


    public void addAspect(String aspectName, int valueToAdd) {
        if (aspectMap.containsKey(aspectName)) {
            int currentValue = aspectMap.get(aspectName);
            aspectMap.put(aspectName, currentValue + valueToAdd);
        } else {
            aspectMap.put(aspectName, valueToAdd);
        }
    }

    public void putAll(Map<String, Integer> map){
        aspectMap.putAll(map);
    }
    public void mergeMaps(Map<String, Integer> targetMap) {
        for (Map.Entry<String, Integer> entry : targetMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            if (aspectMap.containsKey(key)) {
                aspectMap.put(key, aspectMap.get(key) + value);
            } else {
                aspectMap.put(key, value);
            }
        }
    }

    public void addTarget(String value) {
        if (!stringList.contains(value)) {
            stringList.add(value);
        }
    }

    public void setAspects(ArrayList<String> stringList, Map<String, Integer> aspectMap) {
        this.aspectMap.clear();
        this.aspectMap.putAll(aspectMap);
    }

    public void copyFrom(PlayerAspects source) {
        this.aspectMap.clear();
        this.aspectMap.putAll(source.aspectMap);
        this.stringList.clear();
        this.stringList.addAll(source.stringList);
    }

}
