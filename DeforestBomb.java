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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class DeforestBomb implements Listener, Runnable, CommandExecutor {

	public ItemStack deforestbomb = new ItemStack(Material.OAK_SAPLING);
	public ItemMeta meta = deforestbomb.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("deforestbomb")) {
					world.spawnParticle(Particle.VILLAGER_HAPPY, tntprimed.getLocation(), 10, 1, 1, 1, 0.1);
					world.spawnParticle(Particle.SMOKE_NORMAL, tntprimed.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
					if (tntprimed.getTicksLived() == 99) {
						for (Entity e : tntprimed.getNearbyEntities(50D, 50D, 50D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
							}
						}
						
						List<Material> leaves = new ArrayList<Material>();
						leaves.add(Material.OAK_LEAVES);
						leaves.add(Material.SPRUCE_LEAVES);
						leaves.add(Material.BIRCH_LEAVES);
						leaves.add(Material.JUNGLE_LEAVES);
						leaves.add(Material.ACACIA_LEAVES);
						leaves.add(Material.DARK_OAK_LEAVES);
						leaves.add(Material.MANGROVE_LEAVES);
						leaves.add(Material.CHERRY_LEAVES);
						List<Material> logs = new ArrayList<Material>();
						logs.add(Material.OAK_LOG);
						logs.add(Material.SPRUCE_LOG);
						logs.add(Material.BIRCH_LOG);
						logs.add(Material.JUNGLE_LOG);
						logs.add(Material.ACACIA_LOG);
						logs.add(Material.DARK_OAK_LOG);
						logs.add(Material.MANGROVE_LOG);
						logs.add(Material.CHERRY_LOG);
						for (int x = -100; x <= 100; x++) {
							for (int y = -20; y <= 50; y++) {
								for (int z = -100; z <= 100; z++) {
									if (leaves.contains(tntprimed.getLocation().add(x, y, z).getBlock().getType())) {
										tntprimed.getLocation().add(x, y, z).getBlock().setType(Material.AIR);
									}
									if (logs.contains(tntprimed.getLocation().add(x, y, z).getBlock().getType())) {
										tntprimed.getLocation().add(x, y, z).getBlock().breakNaturally();
									}
								}
							}
						}
						for (Entity e : tntprimed.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Item) {
								Item item = (Item) e;
								if (item.getTicksLived() <= 1) {
									item.setCustomName("deforestbomblog");
									item.setGlowing(true);
								}
							}
						}
						tntprimed.remove();
					}
				}
			}
			
			if (entities instanceof Item) {
				Item item = (Item) entities;
				if (item.getCustomName() != null && item.getCustomName().equals("deforestbomblog")) {
					for (Entity ents : item.getNearbyEntities(20D, 20D, 20D)) {
						if (ents instanceof Player) {
							Player p = (Player) ents;
							drawLine(p.getLocation().add(0, 0.5, 0), item.getLocation().add(0, 0.2, 0), 0.5);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("deforestbomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(
					ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Deforest Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 0");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			deforestbomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(deforestbomb);
				}
			} else {
				player.getInventory().addItem(deforestbomb);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceDeforestBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(
						ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Deforest Bomb")) {
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
					TNTPrimed deforestbomb = (TNTPrimed) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					deforestbomb.setCustomName("deforestbomb");
					deforestbomb.setGlowing(true);
					deforestbomb.setFuseTicks(100);
				}
			}
		}
	}
	
	public void drawLine(Location point1, Location point2, double space) {
		Particle.DustOptions green = new Particle.DustOptions(Color.fromRGB(0, 255, 0), 0.5f);
		World world = point1.getWorld();
		double distance = point1.distance(point2);
		Vector p1 = point1.toVector();
		Vector p2 = point2.toVector();
		Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
		double length = 0;
		for (; length < distance; p1.add(vector)) {
			world.spawnParticle(Particle.REDSTONE, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 100, green);
			length += space;
		}
	}

}
