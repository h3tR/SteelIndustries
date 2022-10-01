package ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block;

import com.mojang.logging.LogUtils;
import ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock.HorizontaldirectionCoordinateHelper;
import ml.jozefpeeterslaan72wuustwezel.steelindustries.common.block.blockentity.MultiBlockControllerBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultiBlockController extends HorizontallyFacedBlock implements EntityBlock {
    public MultiBlockController(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new MultiBlockControllerBE(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level p_60504_, BlockPos pos, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        LogUtils.getLogger().debug(new HorizontaldirectionCoordinateHelper(state.getValue(BlockStateProperties.FACING)).getDirectionalCoordinates(pos,new Vec3i(0,0,1)).toString());
        return super.use(state, p_60504_, pos, p_60506_, p_60507_, p_60508_);
    }
}
