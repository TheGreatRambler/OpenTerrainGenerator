package com.pg85.otg.customobjects.bo3.bo3function;

import java.util.Random;

import com.pg85.otg.common.LocalWorldGenRegion;
import com.pg85.otg.customobjects.bo3.BO3Config;
import com.pg85.otg.customobjects.bofunctions.BlockFunction;
import com.pg85.otg.util.ChunkCoordinate;

/**
 * Represents a block in a BO3.
 */
public class BO3BlockFunction extends BlockFunction<BO3Config>
{
	public BO3BlockFunction() { }
	
    public BO3BlockFunction(BO3Config holder)
    {
    	this.holder = holder;
    }
	
    public BO3BlockFunction rotate()
    {
        BO3BlockFunction rotatedBlock = new BO3BlockFunction();
        rotatedBlock.x = z;
        rotatedBlock.y = y;
        rotatedBlock.z = -x;
        rotatedBlock.material = material.rotate();
        rotatedBlock.metaDataTag = metaDataTag;
        rotatedBlock.metaDataName = metaDataName;

        return rotatedBlock;
    }

    @Override
    public void spawn(LocalWorldGenRegion worldGenRegion, Random random, int x, int y, int z, ChunkCoordinate chunkBeingPopulated, boolean replaceBlock)
    {
    	worldGenRegion.setBlock(x, y, z, material, metaDataTag, chunkBeingPopulated, replaceBlock);
    }
    
    @Override
    public Class<BO3Config> getHolderType()
    {
        return BO3Config.class;
    }
}