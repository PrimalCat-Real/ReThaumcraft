package thaumcraft.thaumcraft.common.aspects;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Set;

public class AspectList {
    private HashMap<Aspect, Integer> aspectList = new HashMap<Aspect, Integer>();
    public AspectList(Aspect aspect, Integer amount) {
        this.aspectList.put(aspect, amount);
    }
    public Set getKeys(){
        return aspectList.keySet();
    }

    public int getSize(){
        return aspectList.size();
    }


}
