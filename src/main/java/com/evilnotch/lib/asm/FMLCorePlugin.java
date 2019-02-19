package com.evilnotch.lib.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@IFMLLoadingPlugin.Name("evilnotchlib-transformer_fixes")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.MCVersion("1.12.2")
@TransformerExclusions("com.evilnotch.lib.asm.")
public class FMLCorePlugin implements IFMLLoadingPlugin
{
	public static boolean isObf;
    public static volatile boolean configGenerated = false;
    @Override
    public String[] getASMTransformerClass()
    {
    	if(!configGenerated)
    	{
    		ConfigCore.load();
    		configGenerated = true;
    	}
        return new String[] {
        		"com.evilnotch.lib.asm.transformer.Transformer",
        		"com.evilnotch.lib.asm.transformer.EntityTransformer",
        		"com.evilnotch.iitemrender.asm.RenderTransformer",
        		"com.evilnotch.menulib.asm.MenuLibTransformer"
        };
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
       return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
    	isObf = (Boolean) data.get("runtimeDeobfuscationEnabled");
    	
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
