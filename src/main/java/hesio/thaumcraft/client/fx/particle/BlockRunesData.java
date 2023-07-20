package hesio.thaumcraft.client.fx.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import hesio.thaumcraft.inits.ParticlesInit;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class BlockRunesData implements ParticleOptions {
    private final Vec3 direction;

    public BlockRunesData(Vec3 direction) {
        this.direction = direction;
    }

    public Vec3 getDirection() {
        return direction;
    }


    @Override
    public ParticleType<?> getType() {
        return ParticlesInit.BLOCKRUNE_PARTICLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat((float) direction.x);
        buffer.writeFloat((float) direction.y);
        buffer.writeFloat((float) direction.z);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f",
                BuiltInRegistries.PARTICLE_TYPE.getId(this.getType()),
                this.getDirection().x, this.getDirection().y, this.getDirection().z);
    }

    public static class Deserializer implements ParticleOptions.Deserializer<BlockRunesData> {
        @Nullable
        @Override
        public BlockRunesData fromCommand(ParticleType<BlockRunesData> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double x = reader.readDouble();
            reader.expect(' ');
            double y = reader.readDouble();
            reader.expect(' ');
            double z = reader.readDouble();
            return new BlockRunesData(new Vec3(x, y, z));
        }

        @Override
        public BlockRunesData fromNetwork(ParticleType<BlockRunesData> particleType, FriendlyByteBuf buffer) {
            double x = buffer.readDouble();
            double y = buffer.readDouble();
            double z = buffer.readDouble();
            return new BlockRunesData(new Vec3(x, y, z));
        }


    }

}
