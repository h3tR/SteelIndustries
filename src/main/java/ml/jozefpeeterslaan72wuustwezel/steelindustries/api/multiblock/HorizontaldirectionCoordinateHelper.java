package ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

import java.util.HashMap;

public class HorizontaldirectionCoordinateHelper {
    private final Direction direction;

    public HorizontaldirectionCoordinateHelper(Direction direction){
        this.direction = direction;
    }

    public BlockPos getDirectionalCoordinates(BlockPos pos, Vec3i offset){
        Vec3i realOffset = Vec3i.ZERO;
        switch (this.direction){
            case NORTH -> realOffset = new Vec3i(offset.getX(),offset.getY(),-offset.getZ());
            case SOUTH -> realOffset = new Vec3i(offset.getX(),offset.getY(),offset.getZ());
            case EAST -> realOffset = new Vec3i(offset.getZ(),offset.getY(),offset.getX());
            case WEST -> realOffset = new Vec3i(-offset.getZ(),offset.getY(),offset.getX());
        }
        return pos.offset(realOffset);
    }

}
