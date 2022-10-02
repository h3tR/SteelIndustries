package ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block;

import ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock.BlockPattern;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.TickingBlockEntity;

public abstract class MultiBlockController extends HorizontallyFacedBlock implements EntityBlock, TickingBlockEntity {
    protected final BlockPattern blockPattern;
    public MultiBlockController(Properties properties, BlockPattern blockPattern) {
        super(properties);

        this.blockPattern = blockPattern;
    }
}
