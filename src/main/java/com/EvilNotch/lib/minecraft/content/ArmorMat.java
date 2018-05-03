package com.EvilNotch.lib.minecraft.content;

import java.util.HashMap;

import com.EvilNotch.lib.Api.FieldAcess;
import com.EvilNotch.lib.minecraft.Util;
import com.EvilNotch.lib.util.IEnumContainer;
import com.EvilNotch.lib.util.JavaUtil;
import com.EvilNotch.lib.util.Line.ArrEntry;
import com.EvilNotch.lib.util.Line.LineEnhanced;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ArmorMat implements IEnumContainer{
	
	 /**
	  * A hashmap between enum string name and armor material ENUM NAMES MUST BE UNIQUE
	  * Recomended to use modid but, replace all ":" with "_"
	  */
	 public static HashMap<String,ArmorMaterial> armorenums = new HashMap();
	 /**
	  * Cache for retrieving pre-configured ArmorMat
	  * Don't add them yourselves it's auto added by calling tool constructors
	  */
	 public static HashMap<String,ArmorMat> armormats = new HashMap();
	
	 public String textureName;
     /**
      * how many hit points before armor breaks
      */
     public int durability;
     /**
      * Holds the damage reduction (each 1 points is half a shield on gui) of each piece of armor (helmet, plate,
      * legs and boots)
      */
     public int[] damageReductionAmountArray;
     /** Return the enchantability factor of the material */
     public int enchantability;
     public SoundEvent soundEvent;
     public float toughness;
     //Added by forge for custom Armor materials.
     public ItemStack repairMaterial = ItemStack.EMPTY;
	 public String enumName;
	
	/**
	 * Point of this class is to create a material and then enumify it later
	*/
	public ArmorMat(ResourceLocation enumName,ResourceLocation textureName, int durability, int[] damageReductionAmountArrayIn, int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn)
    {
		 this.enumName = enumName.toString().replaceAll(":", "_");
         this.textureName = textureName.toString();
         this.durability = durability;
         this.damageReductionAmountArray = damageReductionAmountArrayIn;
         this.enchantability = enchantabilityIn;
         this.soundEvent = soundEventIn;
         this.toughness = toughnessIn;
    }
	public ArmorMat(ResourceLocation enumName,ResourceLocation nameIn, int maxDamageFactorIn, int[] damageReductionAmountArrayIn, int enchantabilityIn, ResourceLocation soundEventIN, float toughnessIn)
    {
         this(enumName,nameIn,maxDamageFactorIn,damageReductionAmountArrayIn, enchantabilityIn, Util.getSoundEvent(soundEventIN), toughnessIn);
    }
	public ArmorMat(LineEnhanced line)
	{
		this.enumName = line.getModPath();
		this.textureName = (String) line.heads.get(0);
		this.durability = line.getInt(1);
		 
		int[] damageReduce = new int[4];
		ArrEntry arr = line.getStaticArray(2);
		damageReduce[0] = line.getInt(arr, 0);
		damageReduce[1] = line.getInt(arr, 1);
		damageReduce[2] = line.getInt(arr, 2);
		damageReduce[3] = line.getInt(arr, 3);
		this.damageReductionAmountArray = damageReduce;
		
		this.enchantability = line.getInt(3);
		this.soundEvent = Util.getSoundEvent(new ResourceLocation((String) line.heads.get(4)));
		this.toughness = line.getFloat(5);
	}
	 
	@Override
	public ArmorMaterial getEnum()
	{
		if(!armorenums.containsKey(this.enumName))
		{
			ArmorMaterial mat = EnumHelper.addArmorMaterial(this.enumName, this.textureName,this.durability,this.damageReductionAmountArray,this.enchantability,this.soundEvent,this.toughness);
			armorenums.put(this.enumName, mat);
			return mat;
		}
		return armorenums.get(this.enumName);
	 }
	 
	 @Override
	 public String toString(){
		 return "\"" + this.enumName + "\" = [\"" + this.textureName + "\"," + this.durability + ",[" + JavaUtil.getIntsAsString(this.damageReductionAmountArray) + "]," + this.enchantability + ",\"" + FieldAcess.getSoundEventAsLoc(this.soundEvent) + "\"," + this.toughness + "f]";
	 }

}
