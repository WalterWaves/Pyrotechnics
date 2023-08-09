package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class AirStrike implements Listener, Runnable, CommandExecutor {

	public ItemStack airstrike = new ItemStack(Material.REDSTONE_TORCH);
	public ItemMeta meta = airstrike.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("airstrike")) {
					Location location1 = armorstand.getLocation();
					Particle.DustOptions red = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 2);
					int particles = 100;
					float radius = 7;
					for (int i = 0; i < particles; i++) {
						double angle, x, z;
						angle = 2 * Math.PI * i / particles;
						x = Math.cos(angle) * radius;
						z = Math.sin(angle) * radius;
						location1.add(x, 0, z);
						world.spawnParticle(Particle.REDSTONE, location1, 1, 0, 0, 0, 0, red);
						location1.subtract(x, 0, z);
					}

					drawLine(armorstand.getLocation().add(5, 0, 5), armorstand.getLocation().add(-5, 0, -5), 0.2);
					drawLine(armorstand.getLocation().add(5, 0, -5), armorstand.getLocation().add(-5, 0, 5), 0.2);
					if (armorstand.getTicksLived() >= 199) {
						armorstand.remove();
					}
				}

				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("airstrikeplane")) {
					List<Entity> ent = armorstand.getNearbyEntities(200D, 200D, 200D);
					for (Entity e : ent) {
						if (e instanceof Player) {
							Player p = (Player) e;
							p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 0.5f);
						}
					}
					armorstand.teleport(armorstand.getLocation().add(-1, 0, -1));
					for (int x = 0; x <= 8; x++) {
						for (int z = 0; z <= 8; z++) {
							armorstand.getLocation().add(x, 0, z).getBlock().setType(Material.BLACK_CONCRETE);
						}
					}
					for (int x = 0; x <= 8; x++) {
						for (int z = 0; z <= 8; z++) {
							armorstand.getLocation().add(x + 4, 0, z + 4).getBlock().setType(Material.AIR);
						}
					}

					if (armorstand.getTicksLived() == 113) {
						TNTPrimed tnt = (TNTPrimed) world.spawnEntity(armorstand.getLocation().add(0, -1, 0),
								EntityType.PRIMED_TNT);
						tnt.setFuseTicks(200);
						tnt.setCustomName("airstriketnt");
						tnt.setVelocity(new Vector(-1.01, 0, -1.01));
					}

					if (armorstand.getTicksLived() >= 300) {
						for (int x = 0; x <= 15; x++) {
							for (int z = 0; z <= 15; z++) {
								armorstand.getLocation().add(x, 0, z).getBlock().setType(Material.AIR);
							}
						}
						armorstand.remove();
					}
				}
			}
			if (entities instanceof TNTPrimed) {
				TNTPrimed tnt = (TNTPrimed) entities;
				if (tnt.getCustomName() != null && tnt.getCustomName().equals("airstriketnt")) {
					
					world.spawnParticle(Particle.FLAME, tnt.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
					
					Location location1 = tnt.getLocation().add(0, 1, 0);
					Particle.DustOptions black = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
					for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
						double radius2 = Math.sin(i);
						double y = Math.cos(i) * 2;
						for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
							double x = Math.cos(a) * radius2;
							double z = Math.sin(a) * radius2;
							location1.add(x, y, z);
							world.spawnParticle(Particle.REDSTONE, location1, 1, 0, 0, 0, 0, black);
							location1.subtract(x, y, z);
						}
					}
					
					if (tnt.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR) {
						List<Entity> ent = tnt.getNearbyEntities(1D, 1D, 1D);
						for (Entity e : ent) {
							if (e instanceof ArmorStand) {
								ArmorStand armorstand = (ArmorStand) e;
								if (armorstand.getCustomName().equals("airstrike")) {
									armorstand.remove();
								}
							}
						}
						List<Entity> pls = tnt.getNearbyEntities(50D, 50D, 50D);
						for (Entity e : pls) {
							if (e instanceof Player) {
								Player p = (Player) e;
								p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
								p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 1f, 0.8f);
								p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 1f, 0.6f);
								p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.2f);
								p.playSound(p, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.4f);
							}
						}
						for (int x = -10; x <= 10; x += 10) {
							for (int z = -10; z <= 10; z += 10) {
								world.createExplosion(tnt.getLocation().add(x, 0, z), 50, true, true);
							}
						}
						tnt.remove();
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("airstrike")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Air Strike");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 200");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			airstrike.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(airstrike);
				}
			} else {
				player.getInventory().addItem(airstrike);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceAirStrike(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Air Strike")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					for (Entity e : player.getNearbyEntities(5D, 5D, 5D)) {
						if (e instanceof Player) {
							Player players = (Player) e;
							players.playSound(player, Sound.ITEM_ELYTRA_FLYING, 1f, 1f);
							players.playSound(player, Sound.ENTITY_TNT_PRIMED, 1f, 1f);
						}
					}
					player.playSound(player, Sound.ITEM_ELYTRA_FLYING, 1f, 1f);
					player.playSound(player, Sound.ENTITY_TNT_PRIMED, 1f, 1f);
					ArmorStand airstrike = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
					airstrike.setCustomName("airstrike");
					airstrike.setInvulnerable(true);
					airstrike.setGravity(false);
					airstrike.setInvisible(true);

					ArmorStand plane = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(150, 50, 150), EntityType.ARMOR_STAND);
					plane.setCustomName("airstrikeplane");
					plane.setInvisible(true);
					plane.setInvulnerable(true);
					plane.setGravity(false);
				}
			}
		}
	}

	public void drawLine(Location point1, Location point2, double space) {
		World world = point1.getWorld();
		Particle.DustOptions red = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 2);
		Validate.isTrue(point2.getWorld().equals(world), "Lines cannot be in different worlds!");
		double distance = point1.distance(point2);
		Vector p1 = point1.toVector();
		Vector p2 = point2.toVector();
		Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
		double length = 0;
		for (; length < distance; p1.add(vector)) {
			world.spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 0, red);
			length += space;
		}
	}
}
