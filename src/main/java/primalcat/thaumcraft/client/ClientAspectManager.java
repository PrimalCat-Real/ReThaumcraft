package primalcat.thaumcraft.client;

import primalcat.thaumcraft.aspects.Aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientAspectManager {
    private static List<String> targetsList = new ArrayList<>();

    private static Map<String, Integer> aspectMap = new HashMap<>();
    public List<String> getTargetsList() {
        return targetsList;
    }

    public static void setTargetsList(List<String> targetsList) {
        ClientAspectManager.targetsList = targetsList;
    }

    public static Map<String, Integer> getAspectMap() {
        return aspectMap;
    }

    public static void setAspectMap(Map<String, Integer> aspectMap) {
        ClientAspectManager.aspectMap = aspectMap;
    }

    public static void addTarget(String value) {
        if (!targetsList.contains(value)) {
            targetsList.add(value);
        }
    }

    public static boolean checkTarget(String targetName){
        return targetsList.contains(targetName);
    }
//    public static void updateTargetsList(List<String> newTargetsList) {
//        targetsList.clear();
//        targetsList.addAll(newTargetsList);
//    }
    public static boolean checkAspect(Aspect aspect){
        return aspectMap.containsKey(aspect);
    }
}
