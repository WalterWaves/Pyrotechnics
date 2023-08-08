package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class BlackHole implements Listener, Runnable, CommandExecutor {

	public ItemStack blackhole = new ItemStack(Material.ENDER_EYE);
	public ItemMeta meta = blackhole.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("blackhole")) {
					Location location1 = armorstand.getLocation().add(0, 1, 0);
					Particle.DustOptions black = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 10);
					for (double i = 0; i <= Math.PI; i += Math.PI / 40) {
						double radius2 = Math.sin(i) * armorstand.getTicksLived() / 20f * 1.2f;
						double y = Math.cos(i) * armorstand.getTicksLived() / 20f * 1.2f;
						for (double a = 0; a < Math.PI * 2; a += Math.PI / 40) {
							double x = Math.cos(a) * radius2;
							double z = Math.sin(a) * radius2;
							location1.add(x, y, z);
							world.spawnParticle(Particle.REDSTONE, location1, 1, 0, 0, 0, 0, black);
							location1.subtract(x, y, z);
						}
					}
					if (armorstand.getTicksLived() >= 200) {

						for (float r = 3; r >= 1.1; r -= 0.1f) {
							for (double i = 0; i <= Math.PI; i += Math.PI / 50) {
								double radius2 = Math.sin(i) * armorstand.getTicksLived() / 20f * r;
								double y = Math.cos(i) * armorstand.getTicksLived() / 20f * r;
								for (double a = 0; a < Math.PI * 2; a += Math.PI / 50) {
									double x = Math.cos(a) * radius2;
									double z = Math.sin(a) * radius2;
									location1.add(x, y, z);
									if (location1.getBlock().getType() != Material.AIR) {
										world.spawnFallingBlock(location1, location1.getBlock().getBlockData());
										location1.getBlock().setType(Material.AIR);
									}
									location1.subtract(x, y, z);
								}
							}
						}

						List<Entity> ent = armorstand.getNearbyEntities(300D, 300D, 300D);
						for (Entity e : ent) {
							if (e.getLocation().distance(armorstand.getLocation()) <= 300
									&& e.getLocation().distance(armorstand.getLocation()) > 200) {
								e.setVelocity(getDifferentialVector(e.getLocation(), armorstand.getLocation())
										.multiply(0.0001));
							}
							if (e.getLocation().distance(armorstand.getLocation()) <= 200
									&& e.getLocation().distance(armorstand.getLocation()) > 100) {
								e.setVelocity(getDifferentialVector(e.getLocation(), armorstand.getLocation())
										.multiply(0.0005));
							}
							if (e.getLocation().distance(armorstand.getLocation()) <= 100
									&& e.getLocation().distance(armorstand.getLocation()) > 50) {
								e.setVelocity(getDifferentialVector(e.getLocation(), armorstand.getLocation())
										.multiply(0.001));
							}
							if (e.getLocation().distance(armorstand.getLocation()) <= 50
									&& e.getLocation().distance(armorstand.getLocation()) > 25) {
								e.setVelocity(getDifferentialVector(e.getLocation(), armorstand.getLocation())
										.multiply(0.005));
							}
							if (e.getLocation().distance(armorstand.getLocation()) <= 25) {
								e.setVelocity(getDifferentialVector(e.getLocation(), armorstand.getLocation())
										.multiply(0.05));
							}
							if (e.getLocation().distance(armorstand.getLocation()) <= 15) {
								if (!(e instanceof Player)) {
									e.remove();
								} else {
									Player player = (Player) e;
									if (player.getGameMode() == GameMode.SURVIVAL) {
										player.teleport(new Location(player.getWorld(), player.getLocation().getX(),
												-100, player.getLocation().getZ(), player.getLocation().getYaw(), 90));
										world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
										player.setHealth(0);
										Bukkit.broadcastMessage(player.getName() + " was eaten by a black hole");
										world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, true);
									}
								}
							}
						}

						for (double i = 0; i <= Math.PI; i += Math.PI / 100) {
							double radius2 = Math.sin(i) * armorstand.getTicksLived() / 20f;
							double y = Math.cos(i) * armorstand.getTicksLived() / 20f;
							for (double a = 0; a < Math.PI * 2; a += Math.PI / 100) {
								double x = Math.cos(a) * radius2;
								double z = Math.sin(a) * radius2;
								location1.add(x, y, z);
								location1.getBlock().setType(Material.BLACK_CONCRETE);
								location1.subtract(x, y, z);
							}
						}
						armorstand.setTicksLived(200);
					}
				}

				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("blackholedeath")) {
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(0, 20, 0), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(20, 20, 0), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(0, -20, 20), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(20, 20, 20), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(-20, 20, 20), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(20, -20, -20), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(-20, -20, 0), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(0, 20, -20), 1);
					drawLine(armorstand.getLocation(), armorstand.getLocation().add(-20, -20, -20), 1);
					if (armorstand.getTicksLived() >= 200) {
						for (int x = -10; x <= 10; x += 10) {
							for (int y = -50; y <= 10; y += 10) {
								for (int z = -10; z <= 10; z += 10) {
									world.createExplosion(armorstand.getLocation().add(x, y, z), 50);
								}
							}
						}
						armorstand.remove();
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("blackhole")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.BLACK + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Black Hole");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 0-600");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			blackhole.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(blackhole);
				}
			} else {
				player.getInventory().addItem(blackhole);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceBlackHole(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.BLACK + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Black Hole")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					ArmorStand blackhole = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
					blackhole.setCustomName("blackhole");
					blackhole.setInvulnerable(true);
					blackhole.setGravity(false);
					blackhole.setInvisible(true);
				}
			}
			if (event.getAction() == Action.RIGHT_CLICK_AIR) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					ArmorStand blackhole = (ArmorStand) world.spawnEntity(event.getPlayer().getLocation(),
							EntityType.ARMOR_STAND);
					blackhole.setCustomName("blackhole");
					blackhole.setInvulnerable(true);
					blackhole.setGravity(false);
					blackhole.setInvisible(true);
				}
			}
		}
	}

	@EventHandler
	public void DestroyBlackHole(EntityDeathEvent event) {
		if (event.getEntity() instanceof ArmorStand) {
			ArmorStand armorstand = (ArmorStand) event.getEntity();
			if (armorstand.getCustomName().equals("blackhole")) {
				ArmorStand blackholedeath = (ArmorStand) armorstand.getWorld().spawnEntity(armorstand.getLocation(),
						EntityType.ARMOR_STAND);
				blackholedeath.setCustomName("blackholedeath");
				blackholedeath.setInvulnerable(true);
				blackholedeath.setGravity(false);
				blackholedeath.setInvisible(true);
				for (Player players : Bukkit.getOnlinePlayers()) {
					players.playSound(players, Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f);
				}
			}
		}
	}

	public void drawLine(Location point1, Location point2, double space) {
		World world = point1.getWorld();
		Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
		double distance = point1.distance(point2);
		Vector p1 = point1.toVector();
		Vector p2 = point2.toVector();
		Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
		double length = 0;
		for (; length < distance; p1.add(vector)) {
			world.spawnParticle(Particle.END_ROD, p1.getX(), p1.getY(), p1.getZ(), 50, 0.5, 0.5, 0.5, 0.5);
			world.spawnParticle(Particle.CLOUD, p1.getX(), p1.getY(), p1.getZ(), 50, 0.1, 0.1, 0.1, 0.1);
			world.spawnParticle(Particle.FLASH, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 0.1);
			length += space;
		}
	}

	public Vector getDifferentialVector(Location from, Location to) {
		return new Vector((to.getX() - from.getX()), (to.getY() - from.getY()), (to.getZ() - from.getZ()));
	}
}
