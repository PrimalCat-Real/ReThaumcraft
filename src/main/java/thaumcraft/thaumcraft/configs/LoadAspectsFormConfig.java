package thaumcraft.thaumcraft.configs;

import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import thaumcraft.thaumcraft.Thaumcraft;

import java.io.File;
import java.nio.file.Path;

public class LoadAspectsFormConfig {
    private Path pathToC;
    public LoadAspectsFormConfig() {
        this.pathToC = FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath());

    }
    public void getJsonFile(){
        File jsonFile = new File(pathToC + "" , "File.json");
        System.out.println(jsonFile);
    }
}
