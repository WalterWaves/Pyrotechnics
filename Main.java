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
		this.getServer().getPluginManager().registerEvents(new AirStrike(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AirStrike(), 0, 1);
		this.getCommand("airstrike").setExecutor(new AirStrike());
		this.getServer().getPluginManager().registerEvents(new SphereFirework(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SphereFirework(), 0, 1);
		this.getCommand("spherefirework").setExecutor(new SphereFirework());
		this.getServer().getPluginManager().registerEvents(new IncendiaryTNT(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new IncendiaryTNT(), 0, 1);
		this.getCommand("incendiarytnt").setExecutor(new IncendiaryTNT());
		this.getServer().getPluginManager().registerEvents(new LightningFirework(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new LightningFirework(), 0, 1);
		this.getCommand("lightningfirework").setExecutor(new LightningFirework());
		this.getServer().getPluginManager().registerEvents(new SpaceLaser(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new SpaceLaser(), 0, 1);
		this.getCommand("spacelaser").setExecutor(new SpaceLaser());
		this.getServer().getPluginManager().registerEvents(new ArrowSplash(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ArrowSplash(), 0, 1);
		this.getCommand("arrowsplash").setExecutor(new ArrowSplash());
		this.getServer().getPluginManager().registerEvents(new StinkBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new StinkBomb(), 0, 1);
		this.getCommand("stinkbomb").setExecutor(new StinkBomb());
		this.getServer().getPluginManager().registerEvents(new GravityBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new GravityBomb(), 0, 1);
		this.getCommand("gravitybomb").setExecutor(new GravityBomb());
		this.getServer().getPluginManager().registerEvents(new FreezeBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new FreezeBomb(), 0, 1);
		this.getCommand("freezebomb").setExecutor(new FreezeBomb());
		this.getServer().getPluginManager().registerEvents(new DeforestBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new DeforestBomb(), 0, 1);
		this.getCommand("deforestbomb").setExecutor(new DeforestBomb());
		this.getServer().getPluginManager().registerEvents(new ChunkTNT(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ChunkTNT(), 0, 1);
		this.getCommand("chunktnt").setExecutor(new ChunkTNT());
		this.getServer().getPluginManager().registerEvents(new ChainBomb(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ChainBomb(), 0, 1);
		this.getCommand("chainbomb").setExecutor(new ChainBomb());
		this.getServer().getPluginManager().registerEvents(new Earthquake(), this);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Earthquake(), 0, 1);
		this.getCommand("earthquake").setExecutor(new Earthquake());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("pyrotechnics")) {
			sender.sendMessage(ChatColor.BOLD + "Available Commands:");
			sender.sendMessage("/airstrike");
			sender.sendMessage("/arrowsplash");
			sender.sendMessage("/blackhole");
			sender.sendMessage("/chainbomb");
			sender.sendMessage("/chunktnt");
			sender.sendMessage("/deforestbomb");
			sender.sendMessage("/earthquake");
			sender.sendMessage("/destroyblackholes");
			sender.sendMessage("/destroyearthquakes");
			sender.sendMessage("/exponentialfirework");
			sender.sendMessage("/fp3");
			sender.sendMessage("/freezebomb");
			sender.sendMessage("/gravitybomb");
			sender.sendMessage("/hydrogenbomb");
			sender.sendMessage("/incendiarytnt");
			sender.sendMessage("/lightningfirework");
			sender.sendMessage("/piccolo");
			sender.sendMessage("/spacelaser");
			sender.sendMessage("/spherefirework");
			sender.sendMessage("/spiral");
			sender.sendMessage("/stinkbomb");
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
		
		if (label.equalsIgnoreCase("destroyearthquakes")) {
			for (Entity entities : Bukkit.getWorld("world").getEntities()) {
				if (entities instanceof ArmorStand) {
					ArmorStand armorstand = (ArmorStand) entities;
					if (armorstand.getCustomName().equalsIgnoreCase("earthquake")) {
						armorstand.setHealth(0);
					}
				}
			}
		}
		
		return false;
	}

}
