package primalcat.thaumcraft.aspects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import primalcat.thaumcraft.init.AspectInit;

public class Aspect implements IAspect{
    private String name;
    private int color;
    private ResourceLocation aspectImage;
    private int blend;
    private Aspect[] components;

    public void setComponents(Aspect[] components) {
        this.components = components;
    }

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

    public static Aspect fromBytes(FriendlyByteBuf buf) {
        String name = buf.readUtf();
        int color = buf.readInt();
        ResourceLocation aspectImage = buf.readResourceLocation();
        int blend = buf.readInt();
        int componentCount = buf.readInt();

        Aspect[] components = new Aspect[componentCount];
        for (int i = 0; i < componentCount; i++) {
            components[i] = fromBytes(buf);
        }

        // Create and return the Aspect instance
        return new Aspect(name, color, components, aspectImage, blend);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(name);
        buf.writeInt(color);
        buf.writeResourceLocation(aspectImage);
        buf.writeInt(blend);

        // Serialize the component count
        if (components != null) {
            buf.writeInt(components.length);
            for (Aspect component : components) {
                // Recursively serialize each component
                component.toBytes(buf);
            }
        } else {
            buf.writeInt(0); // No components, write zero
        }
    }

    public boolean isPrimal() {
        return (getComponents() == null) || (getComponents().length != 2);
    }
}