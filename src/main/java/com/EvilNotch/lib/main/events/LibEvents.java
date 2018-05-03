package com.EvilNotch.lib.main.events;

import java.io.File;

import com.EvilNotch.lib.main.Config;
import com.EvilNotch.lib.main.MainJava;
import com.EvilNotch.lib.minecraft.EntityUtil;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LibEvents {
	public static boolean cachedEnts = false;
	public static File worlDir = null;
	public static File playerDataNames = null;
	public static File playerDataDir = null;
	
	/**
	 * Attempt to re-instantiate the entity caches for broken entities when the world is no longer fake
	 */
	 @SubscribeEvent
	 public void worldload(WorldEvent.Load e)
	 {
		 World w = e.getWorld();
		 if(!w.isRemote)
		 {
			 //instantiate directories for core minecraft bug fixes
			 worlDir = w.getSaveHandler().getWorldDirectory();
			 playerDataNames = new File(worlDir,"playerdata/names");
			 playerDataNames.mkdirs();
			 playerDataDir = playerDataNames.getParentFile();
		 }
		 
		 if(cachedEnts)
			 return;
		 MainJava.worldServer = w;
		 
		 System.out.println("Attempting to Repair Cache From Re-Instantiating Broken Entities");
		 EntityUtil.cacheEnts(EntityUtil.ent_blacklist,w);
		 EntityUtil.cacheEnts(EntityUtil.ent_blacklist_nbt,w);
		 EntityUtil.cacheEnts(EntityUtil.ent_blacklist_commandsender,w);
		 
		 System.out.println("blacklistEnt:" + EntityUtil.ent_blacklist);
		 System.out.println("blacklistNBTEnt:" + EntityUtil.ent_blacklist_nbt);
		 System.out.println("blacklistCMDEnt:" + EntityUtil.ent_blacklist_commandsender);
		 cachedEnts = true;
	 }
}
