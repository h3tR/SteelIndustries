package ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock;


import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.HashMap;
import java.util.Objects;


public class BlockPattern {

    public final Block[][][] pattern;
    public final Vec3i offset;
    public final Block centerBlock;

    private BlockPattern(Block[][][] pattern, Vec3i offset, Block centerBlock) {
        this.offset = offset;
        this.pattern = pattern;
        this.centerBlock = centerBlock;
    }

    public boolean match(Level level, BlockPos pos){
        DirectionProperty testProperty = null;
        if(level.getBlockState(pos).getBlock()!=this.centerBlock)
            return false;
        if(level.getBlockState(pos).hasProperty(BlockStateProperties.HORIZONTAL_FACING))
            testProperty = BlockStateProperties.HORIZONTAL_FACING;
      /*  else if(level.getBlockState(pos).hasProperty(BlockStateProperties.FACING))
            testProperty = BlockStateProperties.FACING;*/
        //TODO add support for vertical facing as well
        boolean outcome = false;
        if(testProperty!=null){
            int k =0;
            for (Block[][] x: this.pattern) {
                int j =0;
                for(Block[] z: x){
                    int i =0;
                    for (Block y: z){
                        HorizontaldirectionCoordinateHelper helper = new HorizontaldirectionCoordinateHelper(level.getBlockState(pos).getValue(testProperty));
                        Vec3i reqpos = helper.getDirectionalCoordinates(new Vec3i(k,i,j).offset(this.offset.multiply(-1)));

                        if(level.getBlockState(pos.offset(reqpos)).getBlock() != y){
                            LogUtils.getLogger().debug(level.getBlockState(pos.offset(reqpos)).getBlock().toString());
                            LogUtils.getLogger().debug("test: "+y.toString());
                            return false;}
                        i++;
                    }
                    j++;
                }
                k++;
            }
            return true;
        }else{
            Direction[] dirPossibilities = {Direction.NORTH,Direction.SOUTH,Direction.WEST,Direction.EAST};
            for (Direction d: dirPossibilities ) {
                int k =0;
                boolean localoutcome = true;
                for (Block[][] x: this.pattern) {
                    int j =0;
                    for(Block[] z: x){
                        int i =0;
                        for (Block y: z){
                            HorizontaldirectionCoordinateHelper helper = new HorizontaldirectionCoordinateHelper(d);
                            Vec3i reqpos = helper.getDirectionalCoordinates(new Vec3i(k,i,j).offset(this.offset.multiply(-1)));
                            if(!level.getBlockState(pos.offset(reqpos)).getBlock().equals(y)) {
                                localoutcome = false;
                                break;
                            }
                            i++;
                        }
                        if(!localoutcome)
                            break;
                        j++;
                    }
                    if(!localoutcome)
                        break;
                    k++;
                }
                if(localoutcome){
                    return true;
                }
            }
        }

        return outcome;
    }

    public static class Builder {
        private final String[][] layers;
        private final Block centerBlock;
        private final int SizeX;
        private final int SizeY;
        private final int SizeZ;
        private int currentheight = 0;
        private boolean containsSpace = false;
        private HashMap<Character, Block> key;

        public Builder(Block centerBlock, Vec3i Size) {
            this.centerBlock = centerBlock;
            this.SizeX = Size.getX();
            this.SizeY = Size.getY();
            this.SizeZ = Size.getZ();
            this.layers = new String[SizeZ][SizeY];

        }

        public Builder addLayer(String[] layer) {
            //corrects empty spaces and checks for entries that are too big.
            String[] newlayer = null;
            for (String s : layer) {
                if (s.length() > this.SizeX)
                    throw new IndexOutOfBoundsException("Size of given layer is greater than asked.");
                else if (s.length() < this.SizeX) {
                    StringBuilder sBuilder = new StringBuilder(s);
                    while (sBuilder.length() < this.SizeX) {
                        sBuilder.append(" ");
                        containsSpace = true;
                    }
                    s = sBuilder.toString();
                }
                if (s.contains(" ")) {
                    containsSpace = true;
                }
            }
            if (layer.length > this.SizeZ)
                throw new IndexOutOfBoundsException("Size of given layer is greater than asked.");
            else if (layer.length < this.SizeZ) {
                int llength = 0;
                newlayer = new String[this.SizeZ];
                for (String s : layer) {
                    StringBuilder sBuilder = new StringBuilder(s);
                    while (sBuilder.length() < this.SizeX) {
                        sBuilder.append(" ");
                        containsSpace = true;
                    }
                    newlayer[llength] = layer[llength];
                    llength++;
                }
                while (llength < this.SizeZ) {
                    String s = "";
                    StringBuilder sBuilder = new StringBuilder(s);
                    while (sBuilder.length() < this.SizeX) {
                        sBuilder.append(" ");
                        containsSpace = true;
                    }
                    newlayer[llength] = s;
                    llength++;
                }
                containsSpace = true;
            }
            if (Objects.isNull(newlayer)) {
                newlayer = layer;
            }
            layers[currentheight] = newlayer;
            currentheight++;
            return this;
        }

        public Builder setKey(HashMap<Character, Block> key) {
            //Checks if there is empty space that has no key provided and defaults it to air.
            if (containsSpace && !key.containsKey(' '))
                key.put(' ', Blocks.AIR);
            this.key = key;
            return this;
        }

        public BlockPattern Build() {
            //corrects empty spaces and checks for entries that are too big.
            if (currentheight != this.SizeY) {
                this.addLayer(new String[]{});
                containsSpace = true;
                this.setKey(this.key);
            }
            Block[][][] pattern = new Block[SizeX][SizeZ][SizeY];
            //creates a serialized version of the layers;
            StringBuilder serializedpatternBuilder = new StringBuilder();
            for (String[] layer : layers) {
                for (String row : layer) {
                    serializedpatternBuilder.append(row);
                }
            }
            String serializedPattern = serializedpatternBuilder.toString();
            int[] currentpos = new int[3];
            Vec3i offset = Vec3i.ZERO;
            //fills in the pattern variable using provided key.
            for (char c : serializedPattern.toCharArray()) {

                pattern[currentpos[0]][currentpos[1]][currentpos[2]] = this.key.get(c);
                if (this.key.get(c) == this.centerBlock) {


                    offset =  new Vec3i(currentpos[0],currentpos[1],currentpos[2]);
                    LogUtils.getLogger().debug(offset.toShortString());
                }

                currentpos[0]++;
                if (currentpos[0] >= SizeX) {
                    currentpos[1]++;
                    currentpos[0] = 0;
                }
                if (currentpos[1] >= SizeZ) {
                    currentpos[2]++;
                    currentpos[1] = 0;
                }
            }
            LogUtils.getLogger().debug(String.valueOf(containsSpace));

            return new BlockPattern(pattern,offset,this.centerBlock);

        }
    }
}