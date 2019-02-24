package com.evilnotch.lib.minecraft.basicmc.item.tool;

import java.util.ArrayList;

import com.evilnotch.lib.main.loader.LoaderItems;
import com.evilnotch.lib.minecraft.basicmc.auto.lang.LangEntry;
import com.evilnotch.lib.minecraft.basicmc.auto.lang.LangRegistry;
import com.evilnotch.lib.minecraft.basicmc.item.BasicItem;
import com.evilnotch.lib.minecraft.basicmc.item.IBasicItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;
import net.minecraft.util.ResourceLocation;

public class ItemBasicHoe extends ItemHoe implements IBasicItem{
	
	public boolean hasregister = false;
	public boolean hasmodel = false;
	public boolean haslang = false;
	public boolean hasconfig = false;
	
	public ItemBasicHoe(ToolMat mat,ResourceLocation id,LangEntry... langlist){
		this(mat,id,null,langlist);
	}
	public ItemBasicHoe(ToolMat mat, ResourceLocation id, CreativeTabs tab,LangEntry... langlist) {
		this(mat,id,tab,true,true,true,true,langlist);
	}

	public ItemBasicHoe(ToolMat material,ResourceLocation id,CreativeTabs tab,boolean model,boolean register,boolean lang, boolean config,LangEntry... langlist) {
		super(BasicItem.getMat(material,config));
		this.setRegistryName(id);
		String unlocalname = id.toString().replaceAll(":", ".");
		this.setUnlocalizedName(unlocalname);
		this.setCreativeTab(tab);
		
		this.hasregister = register;
		this.hasmodel = model;
		this.haslang = lang;
		this.hasconfig = config;
		
		//autofill
		this.populateLang(id, langlist);
		
		LoaderItems.items.add(this);
	}
	public void populateLang(ResourceLocation id, LangEntry... langs)
	{
		if(!this.useLangRegistry())
			return;
		LangRegistry.registerLang(this, id, langs);
	}
	
	@Override
	public boolean register() {
		return this.hasregister;
	}
	@Override
	public boolean registerModel() {
		return this.hasmodel;
	}
	@Override
	public boolean useLangRegistry() {
		return this.haslang;
	}
	@Override
	public boolean useConfigPropterties() {
		return this.hasconfig;
	}
}
