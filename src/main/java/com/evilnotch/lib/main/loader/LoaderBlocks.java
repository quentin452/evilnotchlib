package com.evilnotch.lib.main.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.evilnotch.lib.main.Config;
import com.evilnotch.lib.minecraft.basicmc.auto.BlockWrapper;
import com.evilnotch.lib.util.line.config.ConfigBase;
import com.evilnotch.lib.util.line.config.ConfigNonMeta;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class LoaderBlocks {
	
	public static List<BlockWrapper> blocks = new ArrayList();
	public static ConfigBase cfgBlockProps = null;
	 
	public static void loadpreinit()
	{
		cfgBlockProps = new ConfigNonMeta(new File(Config.cfg.getParent(),"config/blockprops.cfg"));
		cfgBlockProps.loadConfig();
	}

	public static void registerBlocks() 
	{
		for(BlockWrapper basic : LoaderBlocks.blocks)
		{
			Block b = (Block) basic.b;
			ItemBlock itemblock = basic.itemblock;
			ForgeRegistries.BLOCKS.register(b);
			if(itemblock != null)
			{
				ForgeRegistries.ITEMS.register(itemblock);
			}
	    }
		blocks.clear();
	}

	public static void loadpostinit() 
	{
		if(!LoaderMain.isDeObfuscated)
		{
			LoaderBlocks.cfgBlockProps.saveConfig(true, false, true);
		}
		clearConfig();
	}

	private static void clearConfig() 
	{
		cfgBlockProps = null;
	}

}
