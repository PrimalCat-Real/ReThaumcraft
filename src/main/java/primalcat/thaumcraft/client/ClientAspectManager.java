package primalcat.thaumcraft.client;

import java.util.ArrayList;
import java.util.List;

public class ClientAspectManager {
    private static List<String> targetsList = new ArrayList<>();
    public List<String> getTargetsList() {
        return targetsList;
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
}
