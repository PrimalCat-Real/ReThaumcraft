package thaumcraft.thaumcraft.common.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import thaumcraft.thaumcraft.common.aspects.Aspect;
import thaumcraft.thaumcraft.common.aspects.AspectList;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Map;

public class ScannedAspects {
    private int testValue = 10;
    private ArrayList<Item>  scannedItems= new ArrayList<>();
    private ArrayList<Entity> scannedEntities = new ArrayList<>();
    private AspectList aspectList = new AspectList().setPrimalAspects();

    public int getTestValue() {
        return testValue;
    }

    public AspectList getAspectList() {
        return aspectList;
    }

    public void addTestValue(int addV) {
        this.testValue = testValue + addV;
    }

    public void copyFrom(ScannedAspects source) {
        this.testValue = source.testValue;
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Map.Entry<Aspect, Integer> entry : aspectList.getAspectList().entrySet()) {
            nbt.putInt(entry.getKey().getName(), entry.getValue());
        }
        nbt.putInt("testValue", testValue);
    }

    public void loadNBTData(CompoundTag nbt) {

        testValue = nbt.getInt("testValue");

    }
}
