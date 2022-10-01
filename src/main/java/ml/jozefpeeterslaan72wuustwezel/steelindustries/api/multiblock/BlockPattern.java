package ml.jozefpeeterslaan72wuustwezel.steelindustries.api.multiblock;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Objects;

public class BlockPattern {

    private Block[][][] Pattern;
    private BlockPattern(){

    }



    public static class Builder{
        private String[][] layers;
        private int currentheight = 0;
        private boolean containsSpace = false;
        private HashMap<Character,Block> key;
        private Block centerBlock;
        private final int SizeX;
        private final int SizeY;
        private final int SizeZ;

        public Builder(Block centerBlock,int SizeX, int SizeY, int SizeZ){
            this.centerBlock = centerBlock;
            this.SizeX = SizeX;
            this.SizeY = SizeY;
            this.SizeZ = SizeZ;
            layers = new String[SizeZ][SizeY];

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
            if(currentheight!=this.SizeY){
                this.addLayer(new String[]{});
                containsSpace = true;
                this.setKey(this.key);
            }

            return new BlockPattern();

        }
    }
}
