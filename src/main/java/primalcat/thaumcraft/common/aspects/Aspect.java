package primalcat.thaumcraft.common.aspects;

import net.minecraft.util.Identifier;
import primalcat.thaumcraft.api.aspects.IAspect;
import net.minecraft.network.PacketByteBuf;
import primalcat.thaumcraft.common.registry.AspectRegistry;

public class Aspect implements IAspect {

    private String name;
    private int color;
    private Identifier aspectImage;
    private Aspect[] components;
    public Aspect(String name, int color, Aspect[] components, Identifier image){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = image;
        AspectRegistry.putAspect(name, this);
    }

    public Aspect(String name, int color, Aspect[] components){
        this.name = name;
        this.color = color;
        this.components = components;
        this.aspectImage = new Identifier("thaumcraft", "textures/aspects/" + name.toLowerCase() + ".png");
        AspectRegistry.putAspect(name, this);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Identifier getAspectImage() {
        return aspectImage;
    }

    @Override
    public Aspect[] getComponents() {
        return components;
    }

    public static Aspect fromBytes(PacketByteBuf buf) {
        String name = buf.readString();
        int color = buf.readInt();
        Identifier aspectImage = buf.readIdentifier();
        int componentCount = buf.readInt();

        Aspect[] components = new Aspect[componentCount];
        for (int i = 0; i < componentCount; i++) {
            components[i] = fromBytes(buf);
        }

        // Create and return the Aspect instance
        return new Aspect(name, color, components, aspectImage);
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeString(name);
        buf.writeInt(color);
        buf.writeIdentifier(aspectImage);

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

    @Override
    public boolean isPrimal() {
        return (getComponents() == null) || (getComponents().length != 2);
    }
}
