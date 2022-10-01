package ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static ml.jozefpeeterslaan72wuustwezel.steelindustries.SteelIndustriesMod.MODID;
import static ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.BlockInit.BLOCKS;
import static ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.BlockInit.MULTIBLOCK_CONTROLLER;

public class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIESTYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES,MODID);

    public static final RegistryObject<BlockEntityType<MultiBlockControllerBE>> MULTIBLOCK_CONTROLLER_ENTITY = BLOCKENTITIESTYPES.register("multiblock_controller_be",
            ()->BlockEntityType.Builder.of(
                    MultiBlockControllerBE::new,
                    MULTIBLOCK_CONTROLLER.get()
            ).build(null));
}
