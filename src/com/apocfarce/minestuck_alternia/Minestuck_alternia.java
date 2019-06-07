package com.apocfarce.minestuck_alternia;
import com.apocfarce.minestuck_alternia.proxy.ClientProxy;
import com.apocfarce.minestuck_alternia.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Minestuck_alternia.modId, name = Minestuck_alternia.name+Reference.MOD_VERSION, version = Minestuck_alternia.version, acceptedMinecraftVersions = "["+Minestuck_alternia.version+"]")
public class Minestuck_alternia {

	public static final String modId = Reference.MOD_ID;
	public static final String name = Reference.NAME;
	public static final String version = Reference.MINECRAFT_VERSION;

	@Mod.Instance(modId)
	public static Minestuck_alternia instance;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		if(event.getSide().isClient())
		{
			ClientProxy.registerRenderers();
		}
	}
	

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;

}