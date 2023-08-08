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
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HydrogenBomb implements Listener, Runnable, CommandExecutor {

	public ItemStack hydrogenbomb = new ItemStack(Material.DRAGON_EGG);
	public ItemMeta meta = hydrogenbomb.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().contains("⚠ Explosion Imminent ⚠")) {
					tntprimed.setCustomName(ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "⚠ Explosion Imminent ⚠"
							+ ChatColor.DARK_RED + " " + ChatColor.UNDERLINE + "" + ChatColor.BOLD
							+ tntprimed.getFuseTicks() / 20 + " seconds left.");
					world.spawnParticle(Particle.SOUL_FIRE_FLAME, tntprimed.getLocation(), 10, 1, 1, 1, 0.25);
					world.spawnParticle(Particle.END_ROD, tntprimed.getLocation(), 10, 1, 1, 1, 0.25);
					world.spawnParticle(Particle.FLASH, tntprimed.getLocation(), 5, 1, 1, 1, 0.25);
					Location location1 = tntprimed.getLocation().add(0, 0.1, 0);
					int particles = 100;
					float radius = 3f;
					for (int i = 0; i < particles; i++) {
						double angle, x, z;
						angle = 2 * Math.PI * i / particles;
						x = Math.cos(angle) * radius;
						z = Math.sin(angle) * radius;
						location1.add(x, 0, z);
						world.spawnParticle(Particle.SMOKE_NORMAL, location1, 1, 0, 0, 0, 0);
						location1.subtract(x, 0, z);
					}
					Location location2 = tntprimed.getLocation().add(0, 0.5, 0);
					for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
						double radius2 = Math.sin(i) * 3;
						double y = Math.cos(i) * 3;
						for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
							double x = Math.cos(a) * radius2;
							double z = Math.sin(a) * radius2;
							location2.add(x, y, z);
							world.spawnParticle(Particle.SOUL_FIRE_FLAME, location2, 1, 0, 0, 0, 0);
							location2.subtract(x, y, z);
						}
					}

					if (tntprimed.getTicksLived() % 10 == 0) {
						for (Player players : Bukkit.getOnlinePlayers()) {
							players.playSound(players, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1f, 1.5f);
						}
					}
					if (tntprimed.getTicksLived() % 20 == 0) {
						for (Player players : Bukkit.getOnlinePlayers()) {
							players.playSound(players, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1f, 0.5f);
						}
					}

					for (Player players : Bukkit.getOnlinePlayers()) {
						players.playSound(players, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 0.25f);
					}

					if (tntprimed.getTicksLived() == 1199) {

						for (int x = -120; x <= 120; x++) {
							for (int y = -64; y <= 100; y++) {
								for (int z = -120; z <= 120; z++) {
									if (tntprimed.getLocation().add(x, y, z).getBlock().getType() == Material.WATER) {
										tntprimed.getLocation().add(x, y, z).getBlock().setType(Material.AIR);
									}
									if (tntprimed.getLocation().add(x, y, z).getBlock().getType() == Material.LAVA) {
										tntprimed.getLocation().add(x, y, z).getBlock().setType(Material.AIR);
									}
								}
							}
						}

						for (int x = -75; x <= 75; x += 15) {
							for (int z = -75; z <= 75; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, 15, z), 50, true, true);
							}
						}
						for (int x = -50; x <= 50; x += 15) {
							for (int z = -50; z <= 50; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, 30, z), 50, true, true);
							}
						}
						for (int x = -25; x <= 25; x += 15) {
							for (int z = -25; z <= 25; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, 45, z), 50, true, true);
							}
						}

						for (int x = -100; x <= 100; x += 15) {
							for (int z = -100; z <= 100; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, 0, z), 50, true, true);
							}
						}
						for (int x = -75; x <= 75; x += 15) {
							for (int z = -75; z <= 75; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -15, z), 50, true, true);
							}
						}
						for (int x = -75; x <= 75; x += 15) {
							for (int z = -75; z <= 75; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -30, z), 50, true, true);
							}
						}
						for (int x = -75; x <= 75; x += 15) {
							for (int z = -75; z <= 75; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -45, z), 50, true, true);
							}
						}
						for (int x = -50; x <= 50; x += 15) {
							for (int z = -50; z <= 50; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -60, z), 50, true, true);
							}
						}
						for (int x = -50; x <= 50; x += 15) {
							for (int z = -50; z <= 50; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -75, z), 50, true, true);
							}
						}
						for (int x = -50; x <= 50; x += 15) {
							for (int z = -50; z <= 50; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -90, z), 50, true, true);
							}
						}
						for (int x = -25; x <= 25; x += 15) {
							for (int z = -25; z <= 25; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -105, z), 50, true, true);
							}
						}
						for (int x = -25; x <= 25; x += 15) {
							for (int z = -25; z <= 25; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -120, z), 50, true, true);
							}
						}
						for (int x = -25; x <= 25; x += 15) {
							for (int z = -25; z <= 25; z += 15) {
								world.createExplosion(tntprimed.getLocation().add(x, -135, z), 50, true, true);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("hydrogenbomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Hydrogen Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 2650");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			hydrogenbomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(hydrogenbomb);
				}
			} else {
				player.getInventory().addItem(hydrogenbomb);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceHydrogenBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(
						ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Hydrogen Bomb")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					for (Player players : Bukkit.getOnlinePlayers()) {
						players.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f);
					}
					TNTPrimed hydrogenbomb = (TNTPrimed) world
							.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.PRIMED_TNT);
					hydrogenbomb.setCustomName(
							ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "⚠ Explosion Imminent ⚠" + ChatColor.DARK_RED
									+ " " + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "60 seconds left.");
					hydrogenbomb.setCustomNameVisible(true);
					hydrogenbomb.setGlowing(true);
					hydrogenbomb.setInvulnerable(true);
					hydrogenbomb.setFuseTicks(1200);
				}
			}
		}
	}

}
