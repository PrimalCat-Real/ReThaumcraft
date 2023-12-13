package primalcat.thaumcraft.common.init

import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import primalcat.thaumcraft.Thaumcraft
import primalcat.thaumcraft.common.item.ItemBase
import primalcat.thaumcraft.common.item.misc.*
import primalcat.thaumcraft.common.item.tools.Thaumometer
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ThaumcraftItems {
    val REGISTRY: DeferredRegister.Items = DeferredRegister.createItems(Thaumcraft.MODID)

    // If you get an "overload resolution ambiguity" error, include the arrow at the start of the closure.
    val THAUMOMETER by REGISTRY.register("thaumometer") { ->
        Thaumometer ()
    }
    val BOTTLE_TAINT by REGISTRY.register("bottle_taint") { ->
        BottleTaint ()
    }

    // curios
    val AMULET_FANCY by REGISTRY.register("amulet_fancy") { ->
        ItemBase (Item.Properties())
    }
    val AMULET_MUNDANE by REGISTRY.register("amulet_mundane") { ->
        ItemBase (Item.Properties())
    }
    val AMULET_VIS by REGISTRY.register("amulet_vis") { ->
        ItemBase (Item.Properties())
    }
    val BATH_SALT by REGISTRY.register("bath_salt") { ->
        ItemBase (Item.Properties())
    }
    val BUCKETDEATH by REGISTRY.register("bucket_death") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_TIN by REGISTRY.register("cluster_tin") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_SILVER by REGISTRY.register("cluster_silver") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_IRON by REGISTRY.register("cluster_iron") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_GOLD by REGISTRY.register("cluster_gold") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_COPPER by REGISTRY.register("cluster_copper") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_LEAD by REGISTRY.register("cluster_lead") { ->
        ItemBase (Item.Properties())
    }
    val CLUSTER_QUARTZ by REGISTRY.register("cluster_quartz") { ->
        ItemBase (Item.Properties())
    }
    val BRAIN by REGISTRY.register("brain") { ->
        Brain ()
    }
    val CRYSTAL_ESSENCE by REGISTRY.register("crystal_essence") { ->
        CrystalEssence ()
    }
    val CRIMSON_RITES by REGISTRY.register("crimson_rites") { ->
        CrimsonRites ()
    }
    val FLUX_SPONGE by REGISTRY.register("flux_sponge") { ->
        FluxSponge ()
    }
    val FOCUS_POUCH by REGISTRY.register("focus_pouch") { ->
        FocusPouch ()
    }
}