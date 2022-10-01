package ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Objects;

public class BlockPattern {

    private Block[][][] pattern;
    private int[] offset;

    private BlockPattern(Block[][][] pattern,int[] offset){
        this.offset = verify3DCoordinates(offset);
        this.pattern = pattern;
    }

    public Block getBlockAt(int[] coords){
        coords = verify3DCoordinates(coords);
        return pattern[coords[0]][coords[2]][coords[1]];
    }



    public static class Builder{
        private final String[][] layers;
        private int currentheight = 0;
        private boolean containsSpace = false;
        private HashMap<Character,Block> key;
        private final Block centerBlock;
        private final int SizeX;
        private final int SizeY;
        private final int SizeZ;

        public Builder(Block centerBlock,int[] Size){
            int[] realSize = verify3DCoordinates(Size);
            this.centerBlock = centerBlock;
            this.SizeX = realSize[0];
            this.SizeY = realSize[1];
            this.SizeZ = realSize[2];
            this.layers = new String[SizeZ][SizeY];

        }

        public Builder addLayer(String[] layer){
            //corrects empty spaces and checks for entries that are too big.
            String[] newlayer = null;
            for (String s: layer) {
                if(s.length()>this.SizeX)
                    throw new IndexOutOfBoundsException("Size of given layer is greater than asked.");
                else if (s.length()<this.SizeX) {
                    StringBuilder sBuilder = new StringBuilder(s);
                    while (sBuilder.length()<this.SizeX){
                        sBuilder.append(" ");
                        containsSpace = true;
                    }
                    s = sBuilder.toString();
                }
                if(s.contains(" ")){
                    containsSpace = true;
                }
            }
            if(layer.length>this.SizeZ)
                throw new IndexOutOfBoundsException("Size of given layer is greater than asked.");
            else if (layer.length<this.SizeZ) {
                int llength = 0;
                newlayer = new String[this.SizeZ];
                for (String s: layer){
                    StringBuilder sBuilder = new StringBuilder(s);
                    while (sBuilder.length()<this.SizeX){
                        sBuilder.append(" ");
                        containsSpace = true;
                    }
                    newlayer[llength] = layer[llength];
                    llength++;
                }
                while (llength<this.SizeZ){
                    String s = "";
                    StringBuilder sBuilder = new StringBuilder(s);
                    while (sBuilder.length()<this.SizeX){
                        sBuilder.append(" ");
                        containsSpace = true;
                    }
                    newlayer[llength] = s;
                    llength++;
                }
                containsSpace = true;
            }
            if(Objects.isNull(newlayer)){
                newlayer = layer;
            }
            layers[currentheight] = newlayer;
            currentheight++;
            return this;
        }

        public Builder setKey(HashMap<Character,Block> key){
            //Checks if there is empty space that has no key provided and defaults it to air.
            if(containsSpace&&!key.containsKey(' '))
                key.put(' ', Blocks.AIR);
            this.key = key;
            return this;
        }

        public BlockPattern Build(){
            //corrects empty spaces and checks for entries that are too big.
            if(currentheight!=this.SizeY){
                this.addLayer(new String[]{});
                containsSpace = true;
                this.setKey(this.key);
            }
            Block[][][] pattern = new Block[SizeX][SizeZ][SizeY];
            //creates a serialized version of the layers;
            StringBuilder serializedpatternBuilder = new StringBuilder();
            for (String[] layer: layers) {
                for (String row: layer) {
                    serializedpatternBuilder.append(row);
                }
            }
            String serializedPattern = serializedpatternBuilder.toString();
            int[] currentpos = new int[3];
            int[] offset = new int[3];
            //fills in the pattern variable using provided key.
            for(char c: serializedPattern.toCharArray()){

                pattern[currentpos[0]][currentpos[1]][currentpos[2]] = this.key.get(c);
                if(this.key.get(c)==this.centerBlock){
                    offset = currentpos;
                }

                currentpos[0]++;
                if(currentpos[0]>=SizeX) {
                    currentpos[1]++;
                    currentpos[0]=0;
                }
                if(currentpos[1]>=SizeZ) {
                    currentpos[2]++;
                    currentpos[1]=0;
                }
            }
            return new BlockPattern(pattern,offset);

        }
    }

    public static int[] verify3DCoordinates(int[] coords){
        int[] verifiedCoords = new int[3];
        if(coords.length>3)
            throw new IndexOutOfBoundsException("Size must have 3 values");
        else if (coords.length<3) {
            int llength = 0;
            for (int i: coords) {
                verifiedCoords[llength] = i;
                llength++;
            }
            while (llength<3){
                verifiedCoords[llength] = 0;
                llength++;
            }
        }else{
            verifiedCoords = coords;
        }
        return verifiedCoords;
    }
}