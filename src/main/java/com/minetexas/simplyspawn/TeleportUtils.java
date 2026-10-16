package com.minetexas.simplyspawn;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.minetexas.simplyspawn.util.SSSettings;

public class TeleportUtils implements Listener {
	
	public static void randomSpawn(Player player) {
		if (!player.isSleeping()) {
			player.teleport(randomLocation(player.getWorld()));
		} else {
			player.sendMessage(ChatColor.RED + "Get up you lazy bum!");
		}
	}
	
	public static void spawn(Player player) {
		if (SSSettings.spawnCommandIsRandom && SSSettings.isValidWorld(player)) {
			randomSpawn(player);
			return;
		}
		
		// fix the position to the center of a block
		Location spawn = player.getWorld().getSpawnLocation();

		int modifier = SSSettings.spawnRadius;
		if (modifier > 0) {
			int x1 = (int) Math.round(modifier * -1);
			int x2 = (int) Math.round(modifier);
			int randomX = TeleportUtils.generateInt(x1, x2);
			int randomY = TeleportUtils.generateInt(x1, x2);
			spawn.setX(spawn.getBlockX() + 0.5 + randomX);
			spawn.setY(spawn.getBlockY());
			spawn.setZ(spawn.getBlockZ() + 0.5 + randomY);
		} else {
			spawn.setX(spawn.getBlockX() + 0.5);
			spawn.setZ(spawn.getBlockZ() + 0.5);
		}
		if (!player.isSleeping()) {
			player.teleport(spawn);
		} else {
			player.sendMessage(ChatColor.RED + "Get up you lazy bum!");
		}
	}
	
	public static Location randomLocation(World w) {
		double playerCount = w.getPlayers().size();
		double modifier = playerCount * SSSettings.spawnMultiplier;
		int x1 = (int) Math.round(modifier * -1);
		int x2 = (int) Math.round(modifier);
		int x = generateInt(x1, SSSettings.randomSpawnRadius + x2);
		int z = generateInt(x1, SSSettings.randomSpawnRadius + x2);
		int y = 0;
		
		Location loc = new Location(w, x + 0.5, y, z + 0.5);
		Chunk chunk = loc.getChunk();
		
		
		if(!chunk.isLoaded()) {
			chunk.load(true);
		}
		
		loc.setY(w.getHighestBlockYAt(loc) + 1);
		
		Block under = w.getBlockAt(loc);
		
		if(under.getType() == Material.LAVA) {
			under.setType(Material.STONE);
			loc.setY(loc.getY() + 1);
		}
		
		return loc;
	}
	
	public static int generateInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}