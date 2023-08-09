package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

public class SphereFirework implements Listener, Runnable, CommandExecutor {

	public ItemStack spherefirework = new ItemStack(Material.ENDER_PEARL);
	public ItemMeta meta = spherefirework.getItemMeta();
	
	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof Firework) {
				Firework firework = (Firework) entities;
				if (firework.getCustomName() != null && firework.getCustomName().equals("spherefirework")) {
					world.spawnParticle(Particle.SOUL_FIRE_FLAME, firework.getLocation().add(0, -0.5, 0), 5, 0.1, 0.1, 0.1, 0.1);
					if (firework.getTicksLived() >= 50) {
						Location location1 = firework.getLocation();
						for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
							double radius2 = Math.sin(i);
							double y = Math.cos(i);
							for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
								double x = Math.cos(a) * radius2;
								double z = Math.sin(a) * radius2;
								location1.add(x, y, z);
								TNTPrimed tnt = (TNTPrimed) world.spawnEntity(location1, EntityType.PRIMED_TNT);
								tnt.setFuseTicks(120);
								tnt.setCustomName("spherefireworktnt");
								location1.subtract(x, y, z);
							}
						}
						world.createExplosion(firework.getLocation(), 3);
						firework.detonate();
					}
				}
			}
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("spherefireworktnt")) {
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation().add(0, 1, 0), 5, 0.1, 0, 0.1, 0.1);
					world.spawnParticle(Particle.LAVA, tntprimed.getLocation().add(0, 1, 0), 5, 0.1, 0.1, 0.1, 0.1);
					tntprimed.setGlowing(true);
					if (tntprimed.getTicksLived() == 119) {
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
		if (label.equalsIgnoreCase("spherefirework")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Sphere Firework");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Firework");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 300");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Launch");
			meta.setLore(lore);
			spherefirework.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(spherefirework);
				}
			} else {
				player.getInventory().addItem(spherefirework);
			}
		}
		return false;
	}
	
	@EventHandler
	public void LaunchSphereFirework(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Sphere Firework")) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				event.setCancelled(true);
			}
			
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
					Firework spherefirework = (Firework) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 0, 0.5),
							EntityType.FIREWORK);
					spherefirework.setCustomName("spherefirework");
					FireworkMeta fmeta = spherefirework.getFireworkMeta();
					fmeta.setPower(4);
					spherefirework.setFireworkMeta(fmeta);
				}
			}
		}
	}

}
