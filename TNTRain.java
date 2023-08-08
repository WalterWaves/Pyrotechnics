package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class TNTRain implements Listener, Runnable, CommandExecutor {

	public ItemStack tntrain = new ItemStack(Material.FIREWORK_ROCKET);
	public ItemMeta meta = tntrain.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof Firework) {
				Firework firework = (Firework) entities;
				if (firework.getCustomName() != null && firework.getCustomName().equals("tntrain")) {
					world.spawnParticle(Particle.SMALL_FLAME, firework.getLocation().add(0, -0.5, 0), 1, 0, 0, 0, 0);
					world.spawnParticle(Particle.LAVA, firework.getLocation().add(0, -0.75, 0), 1, 0, 0.1, 0, 0.1);
					if (firework.getTicksLived() >= 30) {
						TNTPrimed tnt1 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt1.setFuseTicks(100);
						tnt1.setVelocity(new Vector(0.2, 2, 0));
						tnt1.setCustomName("tntrain2");
						TNTPrimed tnt2 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt2.setFuseTicks(100);
						tnt2.setVelocity(new Vector(-0.2, 2, 0));
						tnt2.setCustomName("tntrain2");
						TNTPrimed tnt3 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt3.setFuseTicks(100);
						tnt3.setVelocity(new Vector(0, 2, 0.2));
						tnt3.setCustomName("tntrain2");
						TNTPrimed tnt4 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt4.setFuseTicks(100);
						tnt4.setVelocity(new Vector(0, 2, -0.2));
						tnt4.setCustomName("tntrain2");
						TNTPrimed tnt5 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt5.setFuseTicks(100);
						tnt5.setVelocity(new Vector(0.2, 2, 0.2));
						tnt5.setCustomName("tntrain2");
						TNTPrimed tnt6 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt6.setFuseTicks(100);
						tnt6.setVelocity(new Vector(-0.2, 2, 0.2));
						tnt6.setCustomName("tntrain2");
						TNTPrimed tnt7 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt7.setFuseTicks(100);
						tnt7.setVelocity(new Vector(0.2, 2, -0.2));
						tnt7.setCustomName("tntrain2");
						TNTPrimed tnt8 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt8.setFuseTicks(100);
						tnt8.setVelocity(new Vector(-0.2, 2, -0.2));
						tnt8.setCustomName("tntrain2");
						TNTPrimed tnt9 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt9.setFuseTicks(100);
						tnt9.setVelocity(new Vector(0, 2, 0));
						tnt9.setCustomName("tntrain2");
						firework.detonate();
						world.spawnParticle(Particle.SMOKE_LARGE, firework.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.CLOUD, firework.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						for (Entity e : firework.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Player) {
								Player player = (Player) e;
								player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1f, 1f);
								player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
							}
						}
					}
				}
			}
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("tntrain2")) {
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation().add(0, 1, 0), 5, 0.1, 0, 0.1, 0.1);
					world.spawnParticle(Particle.LAVA, tntprimed.getLocation().add(0, 1, 0), 5, 0.1, 0.1, 0.1, 0.1);
					tntprimed.setGlowing(true);
					if (tntprimed.getTicksLived() == 99) {
						world.spawnParticle(Particle.SMOKE_LARGE, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.CLOUD, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.LAVA, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.FLAME, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("tntrain")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "TNTRain");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Firework");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 27");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Launch");
			meta.setLore(lore);
			tntrain.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(tntrain);
				}
			} else {
				player.getInventory().addItem(tntrain);
			}
		}
		return false;
	}

	@EventHandler
	public void LaunchTNTRain(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "TNTRain")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					for (Entity e : player.getNearbyEntities(5D, 5D, 5D)) {
						if (e instanceof Player) {
							Player players = (Player) e;
							players.playSound(player, Sound.ENTITY_TNT_PRIMED, 1f, 1f);
						}
					}
					player.playSound(player, Sound.ENTITY_TNT_PRIMED, 1f, 1f);
					Firework tntrain = (Firework) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 0, 0.5),
							EntityType.FIREWORK);
					tntrain.setCustomName("tntrain");
					FireworkMeta fmeta = tntrain.getFireworkMeta();
					fmeta.setPower(4);
					tntrain.setFireworkMeta(fmeta);
				}
			}
		}
	}
}
