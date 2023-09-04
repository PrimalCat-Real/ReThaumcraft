package primalcat.thaumcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ThaumcraftClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> PLAYER_PRIMAL_ASPECTS_COUNT;
    public static final ForgeConfigSpec.ConfigValue<Integer> THAUMOMETER_SCAN_DURATION;


    static {
        BUILDER.push("Configs for Tutorial Mod");

        PLAYER_PRIMAL_ASPECTS_COUNT = BUILDER.comment("Default aspects that gives to player on firth join")
                .defineInRange("Default primal aspects", 6, 0, 1000);
        THAUMOMETER_SCAN_DURATION = BUILDER.comment("Time needed for Thaumometer scan complete")
                .defineInRange("Thaumometer scan duration", 35, 5, 1000);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
