package me.WalterWaves.Pyrotechnics;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new FP3(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new FP3(), 0, 1);
		this.getCommand("fp3").setExecutor(new FP3());
		this.getServer().getPluginManager().registerEvents(new Piccolo(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Piccolo(), 0, 1);
		this.getCommand("piccolo").setExecutor(new Piccolo());
		this.getServer().getPluginManager().registerEvents(new TNTRain(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TNTRain(), 0, 1);
		this.getCommand("tntrain").setExecutor(new TNTRain());
		this.getServer().getPluginManager().registerEvents(new TimedBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TimedBomb(), 0, 1);
		this.getCommand("timedbomb").setExecutor(new TimedBomb());
		this.getServer().getPluginManager().registerEvents(new Spiral(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Spiral(), 0, 1);
		this.getCommand("spiral").setExecutor(new Spiral());
		this.getServer().getPluginManager().registerEvents(new TriFirePiccolo(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TriFirePiccolo(), 0, 1);
		this.getCommand("trifirepiccolo").setExecutor(new TriFirePiccolo());
		this.getServer().getPluginManager().registerEvents(new VerticalCannon(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new VerticalCannon(), 0, 1);
		this.getCommand("verticalcannon").setExecutor(new VerticalCannon());
		this.getServer().getPluginManager().registerEvents(new ExponentialFirework(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ExponentialFirework(), 0, 1);
		this.getCommand("exponentialfirework").setExecutor(new ExponentialFirework());
		this.getServer().getPluginManager().registerEvents(new HydrogenBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new HydrogenBomb(), 0, 1);
		this.getCommand("hydrogenbomb").setExecutor(new HydrogenBomb());
		this.getServer().getPluginManager().registerEvents(new BlackHole(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new BlackHole(), 0, 1);
		this.getCommand("blackhole").setExecutor(new BlackHole());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("pyrotechnics")) {
			sender.sendMessage(ChatColor.BOLD + "Available Commands:");
			sender.sendMessage("/blackhole");
			sender.sendMessage("/destroyblackholes");
			sender.sendMessage("/exponentialfirework");
			sender.sendMessage("/fp3");
			sender.sendMessage("/hydrogenbomb");
			sender.sendMessage("/piccolo");
			sender.sendMessage("/spiral");
			sender.sendMessage("/timedbomb");
			sender.sendMessage("/tntrain");
			sender.sendMessage("/trifirepiccolo");
			sender.sendMessage("/verticalcannon");
		}
		
		if (label.equalsIgnoreCase("destroyblackholes")) {
			for (Entity entities : Bukkit.getWorld("world").getEntities()) {
				if (entities instanceof ArmorStand) {
					ArmorStand armorstand = (ArmorStand) entities;
					if (armorstand.getCustomName().equalsIgnoreCase("blackhole")) {
						armorstand.setHealth(0);
					}
				}
			}
		}
		
		return false;
	}

}
