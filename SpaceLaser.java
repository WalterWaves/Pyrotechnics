package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpaceLaser implements Listener, Runnable, CommandExecutor {

	public ItemStack spacelaser = new ItemStack(Material.BLAZE_ROD);
	public ItemMeta meta = spacelaser.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("spacelaser")) {
					Location location1 = armorstand.getLocation().add(0, 10, 0);
					Particle.DustOptions red = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 2);
					int particles = 100;
					float radius = 5;
					if (armorstand.getTicksLived() <= 100) {
						for (Entity e : armorstand.getNearbyEntities(50D, 50D, 50D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 0.5f);
							}
						}
						radius = armorstand.getTicksLived() / 20f;
					}
					for (int i = 0; i < particles; i++) {
						double angle, x, z;
						angle = 2 * Math.PI * i / particles;
						x = Math.cos(angle) * radius;
						z = Math.sin(angle) * radius;
						location1.add(x, 0, z);
						world.spawnParticle(Particle.REDSTONE, location1, 10, 0, 10, 0, 0, red);
						location1.subtract(x, 0, z);
					}
					if (armorstand.getTicksLived() > 100 && armorstand.getTicksLived() < 200) {
						for (Entity e : armorstand.getNearbyEntities(50D, 50D, 50D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, armorstand.getTicksLived() / 100f);
							}
						}
					}
					if (armorstand.getTicksLived() >= 200) {
						world.createExplosion(armorstand.getLocation(), 50, true, true);
						for (int y = -63; y <= 319; y++) {
							for (int rad = 1; rad <= 5; rad++) {
								for (int i = 0; i < 360; i++) {
									double x = armorstand.getLocation().getBlock().getX() + Math.sin((double) i) * rad;
									double z = armorstand.getLocation().getBlock().getZ() + Math.cos((double) i) * rad;
									Location loc = new Location(world, x, y, z);
									Block b = loc.getBlock();
									if (b != null) {
										b.setType(Material.AIR);
									}
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
		if (label.equalsIgnoreCase("spacelaser")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Space Laser");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 60");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			spacelaser.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(spacelaser);
				}
			} else {
				player.getInventory().addItem(spacelaser);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceSpaceLaser(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Space Laser")) {
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
					ArmorStand spacelaser = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
					spacelaser.setCustomName("spacelaser");
					spacelaser.setInvulnerable(true);
					spacelaser.setGravity(false);
					spacelaser.setInvisible(true);
				}
			}
		}
	}

}
