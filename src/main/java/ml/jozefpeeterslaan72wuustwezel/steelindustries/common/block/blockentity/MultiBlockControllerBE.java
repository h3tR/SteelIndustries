package ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.blockentity;

import ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.HorizontallyFacedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class MultiBlockControllerBE extends BlockEntity implements BlockEntityTicker {
    public MultiBlockControllerBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockEntity entity) {
        if(level.getBlockState(pos).getBlock() instanceof HorizontallyFacedBlock){
            level.getBlockState(pos).getValue(BlockStateProperties.FACING);
        }else {
            throw new IllegalStateException("Block has no Directional properties");
        }
    }
}
