package primalcat.thaumcraft.common.capability.aspects;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import primalcat.thaumcraft.aspects.Aspect;
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

//    public void setTargetsList(List<String> list){
//        this.stringList = list;
//    }
    public Map<String, Integer> getAspects() {
        return aspectMap;
    }
    public Integer getAspectCount(String aspectName){
        if(aspectMap.containsKey(aspectName)){
            return aspectMap.get(aspectName);
        }else {
            return 0;
        }

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
        if(!map.isEmpty()){
            aspectMap.putAll(map);
        }
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

    public void subtractMaps(Map<String, Integer> targetMap) {
        for (Map.Entry<String, Integer> entry : targetMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            if (aspectMap.containsKey(key)) {
                int currentValue = aspectMap.get(key);
                int newValue = currentValue - value;
                // Make sure the new value is not negative
                if (newValue >= 0) {
                    aspectMap.put(key, newValue);
                } else {
                    aspectMap.put(key, 0); // Set the value to zero if it would be negative
                }
            }
            // No else branch here, so if the key is not present, it's just skipped
        }
    }

    public void addTarget(String value) {
        if (!stringList.contains(value)) {
            stringList.add(value);
        }
    }

    public void mergeTargets(List<String> valueList){
        for (String value : valueList) {
            if (!stringList.contains(value)) {
                stringList.add(value);
            }
        }
    }

    public void clearTargets() {
        this.stringList.clear();
    }
    public void clearAspects() {
        this.aspectMap.clear();
    }

    public void copyFrom(PlayerAspects source) {
        this.aspectMap.clear();
        this.aspectMap.putAll(source.aspectMap);
        this.stringList.clear();
        this.stringList.addAll(source.stringList);
    }

}
