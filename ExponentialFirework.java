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

public class ExponentialFirework implements Listener, Runnable, CommandExecutor {

	public ItemStack exponentialfirework = new ItemStack(Material.COMPARATOR);
	public ItemMeta meta = exponentialfirework.getItemMeta();
	
	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof Firework) {
				Firework firework = (Firework) entities;
				if (firework.getCustomName() != null && firework.getCustomName().equals("exponentialfirework")) {
					world.spawnParticle(Particle.SOUL_FIRE_FLAME, firework.getLocation().add(0, -0.5, 0), 1, 0, 0, 0, 0);
					if (firework.getTicksLived() >= 31) {
						TNTPrimed tnt1 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt1.setFuseTicks(20);
						tnt1.setVelocity(new Vector(0.5, 1, 0));
						tnt1.setCustomName("exponentialfirework2");
						TNTPrimed tnt2 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt2.setFuseTicks(20);
						tnt2.setVelocity(new Vector(-0.5, 1, 0));
						tnt2.setCustomName("exponentialfirework2");
						TNTPrimed tnt3 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt3.setFuseTicks(20);
						tnt3.setVelocity(new Vector(0, 1, 0.5));
						tnt3.setCustomName("exponentialfirework2");
						TNTPrimed tnt4 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt4.setFuseTicks(20);
						tnt4.setVelocity(new Vector(0, 1, -0.5));
						tnt4.setCustomName("exponentialfirework2");
						firework.detonate();
						world.spawnParticle(Particle.SMOKE_LARGE, firework.getLocation(), 100, 0.5, 0.5, 0.5, 0.25);
						for (Entity e : firework.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Player) {
								Player player = (Player) e;
								player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1f, 1f);
							}
						}
					}
				}
			}
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("exponentialfirework2")) {
					tntprimed.setGlowing(true);
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation().add(0, 1, 0), 10, 0.01, 0.01, 0.01, 0.05);
					if (tntprimed.getTicksLived() == 19) {
						TNTPrimed tnt1 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(-1, 1, 0), EntityType.PRIMED_TNT);
						tnt1.setFuseTicks(70);
						tnt1.setVelocity(tntprimed.getVelocity().multiply(3));
						tnt1.setCustomName("exponentialfirework3");
						TNTPrimed tnt2 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(1, 1, 0), EntityType.PRIMED_TNT);
						tnt2.setFuseTicks(70);
						tnt2.setVelocity(tntprimed.getVelocity().multiply(3));
						tnt2.setCustomName("exponentialfirework3");
						TNTPrimed tnt3 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(0, 1, 1), EntityType.PRIMED_TNT);
						tnt3.setFuseTicks(70);
						tnt3.setVelocity(tntprimed.getVelocity().multiply(3));
						tnt3.setCustomName("exponentialfirework3");
						TNTPrimed tnt4 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(0, 1, -1), EntityType.PRIMED_TNT);
						tnt4.setFuseTicks(70);
						tnt4.setVelocity(tntprimed.getVelocity().multiply(3));
						tnt4.setCustomName("exponentialfirework3");
						TNTPrimed tnt5 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(1, 1, 1), EntityType.PRIMED_TNT);
						tnt5.setFuseTicks(70);
						tnt5.setVelocity(tntprimed.getVelocity().multiply(3));
						tnt5.setCustomName("exponentialfirework3");
						TNTPrimed tnt6 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(-1, 1, -1), EntityType.PRIMED_TNT);
						tnt6.setFuseTicks(70);
						tnt6.setVelocity(tntprimed.getVelocity().multiply(3));
						tnt6.setCustomName("exponentialfirework3");
						TNTPrimed tnt7 = (TNTPrimed) world.spawnEntity(tntprimed.getLocation().add(0, -1, 0), EntityType.PRIMED_TNT);
						tnt7.setFuseTicks(30);
						tnt7.setCustomName("exponentialfirework3");
					}
				}
				
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("exponentialfirework3")) {
					tntprimed.setGlowing(true);
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation().add(0, 1, 0), 10, 0.01, 0.01, 0.01, 0.05);
					if (tntprimed.getTicksLived() == 69) {
						world.spawnParticle(Particle.SMOKE_LARGE, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.CLOUD, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.LAVA, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.FLAME, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.createExplosion(tntprimed.getLocation(), 20);
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("exponentialfirework")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Exponential Firework");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Firework");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 492");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Launch");
			meta.setLore(lore);
			exponentialfirework.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(exponentialfirework);
				}
			} else {
				player.getInventory().addItem(exponentialfirework);
			}
		}
		return false;
	}
	
	@EventHandler
	public void LaunchExponentialFirework(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Exponential Firework")) {
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
					Firework exponentialfirework = (Firework) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 0, 0.5),
							EntityType.FIREWORK);
					exponentialfirework.setCustomName("exponentialfirework");
					FireworkMeta fmeta = exponentialfirework.getFireworkMeta();
					fmeta.setPower(4);
					exponentialfirework.setFireworkMeta(fmeta);
				}
			}
		}
	}

}
