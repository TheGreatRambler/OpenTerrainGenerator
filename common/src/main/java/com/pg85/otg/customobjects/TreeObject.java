package com.pg85.otg.customobjects;

import com.pg85.otg.common.LocalWorldGenRegion;
import com.pg85.otg.config.standard.PluginStandardValues;
import com.pg85.otg.customobjects.structures.CustomStructureCache;
import com.pg85.otg.util.ChunkCoordinate;
import com.pg85.otg.util.bo3.Rotation;
import com.pg85.otg.util.minecraft.defaults.TreeType;

import java.util.Random;

/**
 * A Minecraft tree, viewed as a custom object.
 *
 * <p>For historical reasons, TreeObject implements {@link CustomObject} instead
 * of just {@link SpawnableObject}. We can probably refactor the Tree resource
 * to accept {@link SpawnableObject}s instead of {@link CustomObject}s, so that
 * all the extra methods are no longer needed.
 */
public class TreeObject implements CustomObject
{
    private TreeType type;
    public int minHeight = PluginStandardValues.WORLD_DEPTH;
    public int maxHeight = PluginStandardValues.WORLD_HEIGHT - 1;

    TreeObject(TreeType type)
    {
        this.type = type;
    }

    @Override
    public boolean onEnable()
    {
    	return true;
    }

    @Override
    public String getName()
    {
        return type.name();
    }

    @Override
    public boolean canSpawnAsTree()
    {
        return true;
    }
    
    // Called during population.
    @Override
    public boolean process(CustomStructureCache structureCache, LocalWorldGenRegion worldGenRegion, Random random, ChunkCoordinate chunkCoord)
    {
        // A tree has no frequency or rarity, so spawn it once in the chunk
    	// Make sure we stay within population bounds.
        int x = chunkCoord.getBlockXCenter() + random.nextInt(ChunkCoordinate.CHUNK_SIZE);
        int z = chunkCoord.getBlockZCenter() + random.nextInt(ChunkCoordinate.CHUNK_SIZE);
                
        int y = worldGenRegion.getHighestBlockAboveYAt(x, z, chunkCoord);        
        if (y < minHeight || y > maxHeight)
        {
            return false;
        }
        
        return spawnForced(structureCache, worldGenRegion, random, Rotation.NORTH, x, y, z);
    }
    
    @Override
    public boolean spawnFromSapling(CustomStructureCache structureCache, LocalWorldGenRegion worldGenRegion, Random random, Rotation rotation, int x, int y, int z)
    {
        return worldGenRegion.placeTree(type, random, x, y, z);
    }
    
    @Override
    public boolean spawnForced(CustomStructureCache structureCache, LocalWorldGenRegion worldGenRegion, Random random, Rotation rotation, int x, int y, int z)
    {
        return worldGenRegion.placeTree(type, random, x, y, z);
    }
    
    @Override
    public boolean spawnAsTree(CustomStructureCache structureCache, LocalWorldGenRegion worldGenRegion, Random random, int x, int z, int minY, int maxY, ChunkCoordinate chunkBeingPopulated)
    {
        int y = worldGenRegion.getHighestBlockAboveYAt(x, z, chunkBeingPopulated);
        Rotation rotation = Rotation.getRandomRotation(random);

        if(!(minY == -1 && maxY == -1))
        {
	        if (y < minY || y > maxY)
	        {
	            return false;
	        }
        }
        
        if (y < minHeight || y > maxHeight)
        {
            return false;
        }
        
        return spawnForced(structureCache, worldGenRegion, random, rotation, x, y, z);
    }
    
    @Override
    public boolean canRotateRandomly()
    {
        // Trees cannot be rotated
        return false;
    }

	@Override
	public boolean loadChecks()
	{
		return true;
	}

	@Override
	public boolean doReplaceBlocks()
	{
		return false;
	}
}
