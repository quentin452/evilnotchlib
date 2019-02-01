package com.evilnotch.lib.main.eventhandler;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.evilnotch.lib.minecraft.content.tick.TickReg;
import com.evilnotch.lib.minecraft.event.PickEvent;
import com.evilnotch.lib.minecraft.event.TileStackSyncEvent;
import com.evilnotch.lib.minecraft.network.NetWorkHandler;
import com.evilnotch.lib.minecraft.network.packet.PacketUUID;
import com.evilnotch.lib.minecraft.network.packet.PacketYawHead;
import com.evilnotch.lib.minecraft.util.EntityUtil;
import com.evilnotch.lib.util.simple.PointId;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class LibEvents {
	
	public static int mTick = 0;
	public static final List<String> msgs = new ArrayList();
	@SubscribeEvent
	public void mTick(ServerTickEvent e)
	{
		 if(e.phase != Phase.END || msgs.isEmpty())
			 return;
		 Set<String> toRemove = new HashSet();
		 for(String msg : msgs)
		 {
			MinecraftServer mcServer = FMLCommonHandler.instance().getMinecraftServerInstance();
			for(EntityPlayerMP p : mcServer.getPlayerList().getPlayers())
			{
				p.sendMessage(new TextComponentString(msg));
			}
			toRemove.add(msg);
		 }
		 for(String s : toRemove)
			 msgs.remove(s);
	 }
	 
		/**
		 * entity player connection to point id(ticks existed, max tick count,String msg)
		 * the connection is kept even on respawn so there is no glitching this kicker
		 */
		public static HashMap<NetHandlerPlayServer,PointId> kicker = new HashMap();
		public static boolean isKickerIterating = false;
		@SubscribeEvent
		public void kick(TickEvent.ServerTickEvent e)
		{
			if(e.phase != Phase.END || kicker.isEmpty())
				return;

			Iterator<Map.Entry<NetHandlerPlayServer,PointId> > it = kicker.entrySet().iterator();
			while(it.hasNext())
			{
				isKickerIterating = true;
				Map.Entry<NetHandlerPlayServer,PointId> pair = it.next();
				NetHandlerPlayServer connection = pair.getKey();
				PointId point = pair.getValue();
				if(point.getX() >= point.getY())
				{
					it.remove();
					EntityUtil.disconnectPlayer(connection.player,new TextComponentString(point.id));
				}
			}
			isKickerIterating = false;
			
			for(PointId p : kicker.values())
				p.setLocation(p.getX() + 1,p.getY());
		}
	
	/**
	 * Attempt to re-instantiate the entity caches for broken entities when the world is no longer fake
	 */
	 public static boolean cachedEnts = false;
	 @SubscribeEvent
	 public void worldload(WorldEvent.Load e)
	 {
		 World ew = e.getWorld();
		 if(!EntityUtil.cached || ew.isRemote || cachedEnts || EntityUtil.ent_blacklist.isEmpty() && EntityUtil.ent_blacklist_nbt.isEmpty() && EntityUtil.ent_blacklist_nbt.isEmpty())
			 return;
		 cachedEnts = true;
		 World w = e.getWorld();
		 System.out.println("Attempting to Repair Cache From Re-Instantiating Broken Entities. This gives modders debug onWorldLoad if the exception still occurs");
		 EntityUtil.cacheEnts(EntityUtil.ent_blacklist,w);
		 EntityUtil.cacheEnts(EntityUtil.ent_blacklist_nbt,w);
		 EntityUtil.cacheEnts(EntityUtil.ent_blacklist_commandsender,w);
	 }
	 @SubscribeEvent
	 public void tickServer(ServerTickEvent e)
	 {
		 if(e.phase != Phase.END)
			 return;
		 TickReg.tickServer();
	 }
}
