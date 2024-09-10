package com.minetexas.simplyspawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.minetexas.simplyspawn.RespawnListener;


public class RespawnListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if (player.getBedSpawnLocation() == null) {
			if (!world.getName().contains("nether") && !world.getName().contains("end")) {
				Location location = TeleportUtils.randomLocation(player.getWorld());
				event.setRespawnLocation(location);
			}
		}
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(!e.getPlayer().hasPlayedBefore()) {
			TeleportUtils.randomSpawn(e.getPlayer());
		}
	}
}
