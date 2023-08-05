package primalcat.thaumcraft.api;

import net.minecraft.resources.ResourceLocation;
import primalcat.thaumcraft.init.AspectInit;

import java.util.LinkedHashMap;

public class Aspect implements IAspect{
    private String name;
    private int color;
    private ResourceLocation aspectImage;
    private int blend;
    private Aspect[] components;

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public ResourceLocation getAspectImage() {
        return aspectImage;
    }

    public int getBlend() {
        return blend;
    }

    public Aspect[] getComponents() {
        return components;
    }


    public Aspect(String name, int color, Aspect[] components, ResourceLocation image, int blend){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = image;
        this.blend = blend;
        AspectInit.putAspect(name, this);
    }

    public Aspect(String name, int color, Aspect[] components, int blend){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = new ResourceLocation("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png");
        this.blend = blend;
        AspectInit.putAspect(name, this);
    }
    public Aspect(String name, int color, Aspect[] components){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = new ResourceLocation("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png");
        AspectInit.putAspect(name, this);
    }

    public boolean isPrimal() {
        return (getComponents() == null) || (getComponents().length != 2);
    }
}