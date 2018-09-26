package com.evilnotch.lib.minecraft.content.world;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class FakeSaveHandler implements ISaveHandler{
	
	public FakeSaveHandler()
	{
		
	}

	@Override
	public WorldInfo loadWorldInfo() 
	{
		return FakeWorld.world_info;
	}

	@Override
	public void checkSessionLock() throws MinecraftException {
	}

	@Override
	public IChunkLoader getChunkLoader(WorldProvider provider) {
		return getChunkLoader();
	}
	public static IChunkLoader loader = null;
	public static IChunkLoader getChunkLoader() {
		if(loader == null)
			loader = new FakeChunkLoader();
		return loader;
	}

	@Override
	public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
	}

	@Override
	public void saveWorldInfo(WorldInfo worldInformation) {
	}

	@Override
	public IPlayerFileData getPlayerNBTManager() {
		return getPlayerManager();
	}
	public static IPlayerFileData manager = null;
	public static IPlayerFileData getPlayerManager() {
		if(manager == null)
			manager = new FakePlayerFileData();
		return manager;
	}

	@Override
	public void flush() {
	}

	@Override
	public File getWorldDirectory() {
		return null;
	}

	@Override
	public File getMapFileFromName(String mapName) {
		return null;
	}
	/**
	 * null is allowed here
	 */
	@Override
	public TemplateManager getStructureTemplateManager() {
		return null;
	}

}
