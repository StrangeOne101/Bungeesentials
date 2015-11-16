package net.simplicitee.bungeesentials;

import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener
{
	Bungeesentials plugin;
  
	public PlayerListener(Bungeesentials plugin)
	{
		this.plugin = plugin;
	}
  
	@EventHandler
	public void onServerKickEvent(ServerKickEvent ev)
	{
		ServerInfo kickedFrom = null;
    
		if (ev.getPlayer().getServer() != null)
		{
			kickedFrom = ev.getPlayer().getServer().getInfo();
		}
		else if (this.plugin.getProxy().getReconnectHandler() != null)
		{
			kickedFrom = this.plugin.getProxy().getReconnectHandler().getServer(ev.getPlayer());
		}
		else
		{
			kickedFrom = AbstractReconnectHandler.getForcedHost(ev.getPlayer().getPendingConnection());
			if (kickedFrom == null)
			{
				kickedFrom = ProxyServer.getInstance().getServerInfo(ev.getPlayer().getPendingConnection().getListener().getDefaultServer());
			}
		}
    
		ServerInfo kickTo = this.plugin.getProxy().getServerInfo(Bungeesentials.config.getString("fallback"));
    
		if ((kickedFrom != null) && (kickedFrom.equals(kickTo))) {
			return;
		}
    
		ev.setCancelled(true);
		ev.setCancelServer(kickTo);
		if (Bungeesentials.config.getBoolean("ShowMessage"))
		{
			String msg = Bungeesentials.config.getString("KickMessage");
			msg = ChatColor.translateAlternateColorCodes('&', msg);
			String kmsg = ChatColor.stripColor(BaseComponent.toLegacyText(ev.getKickReasonComponent()));
			msg = msg + kmsg;
			ev.getPlayer().sendMessage(new TextComponent(msg));
		}
	}
}