package net.simplicitee.bungeesentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Bungeesentials extends Plugin
{
	public static Configuration config;
	public static ConfigurationProvider cProvider;
	public static File file;
  
	public void onEnable()  
	{
		getLogger().info("Bungeesentials enabled! Developed by StrangeOne101 and Simplicitee");
		getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
		
		loadConfig();
	}
  
	public void onDisable()
	{
		//Removed shit cause it was useless
	}
	
	public void loadConfig()
	{
		file = new File(getDataFolder(), "config.yml");
		
		try
		{
			if (!file.exists()) 
			{
				Files.copy(getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
			}
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		}
		catch (IOException e) {e.printStackTrace();}
	}
}