package ml.jozefpeeterslaan72wuustwezel.steelindustries.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static ml.jozefpeeterslaan72wuustwezel.steelindustries.SteelIndustriesMod.MODID;
import static ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.BlockInit.MULTIBLOCK_CONTROLLER;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> MULTIBLOCK_CONTROLLER_ITEM = ITEMS.register("example_block", () ->  new BlockItem(MULTIBLOCK_CONTROLLER.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("steel_ingot", () -> new Item( new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
