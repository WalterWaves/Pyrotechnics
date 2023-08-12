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
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class GravityBomb implements Listener, Runnable, CommandExecutor {
	
	public ItemStack gravitybomb = new ItemStack(Material.CLOCK);
	public ItemMeta meta = gravitybomb.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("gravitybomb")) {
					world.spawnParticle(Particle.CLOUD, tntprimed.getLocation(), 10, 0.1, 0.1, 0.1, 0.2);
					if (tntprimed.getTicksLived() == 99) {
						for (Entity e : tntprimed.getNearbyEntities(10D, 10D, 10D)) {
							if (e instanceof LivingEntity) {
								LivingEntity le = (LivingEntity) e;
								le.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 200, 3, true));
							}
						}
						for (Entity e : tntprimed.getNearbyEntities(50D, 50D, 50D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.ENTITY_GENERIC_EXPLODE, 1f, 2f);
							}
						}
						for (int rad = 1; rad <= 10; rad++) {
							Location location1 = tntprimed.getLocation();
							for (double i = 0; i <= Math.PI; i += Math.PI / 50) {
								double radius2 = Math.sin(i) * rad;
								double y = Math.cos(i) * rad;
								for (double a = 0; a < Math.PI * 2; a += Math.PI / 50) {
									double x = Math.cos(a) * radius2;
									double z = Math.sin(a) * radius2;
									location1.add(x, y, z);
									FallingBlock fb = world.spawnFallingBlock(location1, location1.getBlock().getBlockData());
									fb.setGravity(false);
									fb.setCustomName("gravitybombblock");
									fb.setInvulnerable(true);
									location1.getBlock().setType(Material.AIR);									
									location1.subtract(x, y, z);
								}
							}
						}
						tntprimed.remove();
					}
				}
			}
			
			if (entities instanceof FallingBlock) {
				FallingBlock fb = (FallingBlock) entities;
				if (fb.getCustomName() != null && fb.getCustomName().equals("gravitybombblock")) {
					if (fb.getTicksLived() == 200) {
						fb.setGravity(true);
						world.createExplosion(fb.getLocation(), 1, false, false);
					}
					if (fb.getTicksLived() <= 200) {
						fb.setVelocity(new Vector(0, 0.2, 0));
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("gravitybomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Gravity Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 50");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			gravitybomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(gravitybomb);
				}
			} else {
				player.getInventory().addItem(gravitybomb);
			}
		}
		return false;
	}
	
	@EventHandler
	public void PlaceGravityBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Gravity Bomb")) {
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
					TNTPrimed gravitybomb = (TNTPrimed) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					gravitybomb.setCustomName("gravitybomb");
					gravitybomb.setGlowing(true);
					gravitybomb.setFuseTicks(100);
				}
			}
		}
	}

}
