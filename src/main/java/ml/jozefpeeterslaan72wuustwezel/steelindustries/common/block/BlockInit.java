package ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static ml.jozefpeeterslaan72wuustwezel.steelindustries.SteelIndustriesMod.MODID;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);


    public static final RegistryObject<Block> MULTIBLOCK_CONTROLLER = BLOCKS.register("multiblock_controller", () -> new MultiBlockController(BlockBehaviour.Properties.of(Material.STONE)));


}
