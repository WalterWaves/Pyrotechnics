package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Earthquake implements Listener, Runnable, CommandExecutor {

	public ItemStack earthquake = new ItemStack(Material.COARSE_DIRT);
	public ItemMeta meta = earthquake.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("earthquake")) {
					if (armorstand.getTicksLived() % 60 == 0) {
						for (Entity e : armorstand.getNearbyEntities(30D, 15D, 30D)) {
							if (e instanceof LivingEntity) {
								LivingEntity le = (LivingEntity) e;
								le.setVelocity(new Vector(0, le.getLocation().distance(armorstand.getLocation()) / 10, 0));
							}
						}
						
						Location location1 = armorstand.getLocation().add(0, -1, 0);
						int particles = 500;
						for (float radius = 1; radius <= 30; radius++) {
							for (int i = 0; i < particles; i++) {
								double angle, x, z;
								angle = 2 * Math.PI * i / particles;
								x = Math.cos(angle) * radius;
								z = Math.sin(angle) * radius;
								location1.add(x, 0, z);
								if (location1.getBlock().getType() != Material.AIR) {
									FallingBlock fb = (FallingBlock) world.spawnFallingBlock(location1,
											location1.getBlock().getBlockData());
									fb.setVelocity(new Vector(0, radius / 30, 0));
									location1.getBlock().setType(Material.AIR);
								}
								location1.subtract(x, 0, z);
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
		if (label.equalsIgnoreCase("earthquake")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Earthquake");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 12");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			earthquake.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(earthquake);
				}
			} else {
				player.getInventory().addItem(earthquake);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceEarthquake(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Earthquake")) {
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
					ArmorStand earthquake = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
					earthquake.setCustomName("earthquake");
					earthquake.setInvulnerable(true);
					earthquake.setGravity(false);
					earthquake.setInvisible(true);
				}
			}
		}
	}

}
