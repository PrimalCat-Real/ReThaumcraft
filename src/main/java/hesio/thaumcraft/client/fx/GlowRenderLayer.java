package hesio.thaumcraft.client.fx;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;

public class GlowRenderLayer extends RenderType {
    public GlowRenderLayer(String name, VertexFormat vertexFormat, VertexFormat.Mode mode, int bufferSize, boolean useDelegate, boolean renderStates, Runnable setupTask, Runnable clearTask) {
        super(name, vertexFormat, mode, bufferSize, useDelegate, renderStates, setupTask, clearTask);
    }
}
