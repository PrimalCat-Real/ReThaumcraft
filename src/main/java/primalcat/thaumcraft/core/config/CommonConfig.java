package primalcat.thaumcraft.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.IntValue exampleIntConfigEntry;
    public static ForgeConfigSpec.DoubleValue exampleDoubleConfigEntry;
    public static ForgeConfigSpec.ConfigValue<Double> exampleUnboundedDoubleConfigEntry;
    public static ForgeConfigSpec.LongValue exampleLongConfigEntry;
    public static ForgeConfigSpec.BooleanValue exampleBooleanConfigEntry;
    public static ForgeConfigSpec.ConfigValue<String> exampleStringConfigEntry;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> exampleStringListConfigEntry;

    static {
        BUILDER.push("Configs for Tutorial Mod");

        BUILDER.comment(" This category holds configs that uses numbers.");
        BUILDER.push("Numeric Config Options");

        exampleIntConfigEntry = BUILDER.defineInRange("example_int_config_entry", 5, 2, 50);
        exampleDoubleConfigEntry = BUILDER.defineInRange("example_double_config_entry", 10D, 0D, 100D);

        exampleUnboundedDoubleConfigEntry = BUILDER
                .comment("This comment will be attached to example_unbounded_double_config_entry in the config file.")
                .define("example_unbounded_double_config_entry", 1000D);

        exampleLongConfigEntry = BUILDER.defineInRange("example_long_config_entry", 4L, -900L, 900L);


        BUILDER.comment(" This category holds configs that uses strings.");
        BUILDER.push("String Config Options");

        exampleStringConfigEntry = BUILDER
                .comment("This config holds a single string.")
                .define("example_string_config_entry", "player444");

        BUILDER.comment(" This category will be nested inside the String Config Options category.");
        BUILDER.push("Nested Category");

        exampleStringListConfigEntry = BUILDER
                .comment("This config entry will hold a list of strings.")
                .defineList("example_string_list_config_entry", Arrays.asList("pie", "trains"), entry -> true);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
