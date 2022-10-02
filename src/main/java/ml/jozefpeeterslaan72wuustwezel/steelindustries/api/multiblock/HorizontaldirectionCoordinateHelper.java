package ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock;


import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

public class HorizontaldirectionCoordinateHelper {
    private final Direction direction;

    public HorizontaldirectionCoordinateHelper(Direction direction){
        this.direction = direction;
    }

    public Vec3i getDirectionalCoordinates(Vec3i offset){
        Vec3i realOffset;
        switch (this.direction){
            case SOUTH -> realOffset = new Vec3i(offset.getX(),offset.getY(),-offset.getZ());
            case EAST -> realOffset = new Vec3i(-offset.getZ(),offset.getY(),offset.getX());
            case WEST -> realOffset = new Vec3i(offset.getZ(),offset.getY(),offset.getX());
            default ->  realOffset = new Vec3i(offset.getX(),offset.getY(),offset.getZ());
        }
        return realOffset;
    }

}
