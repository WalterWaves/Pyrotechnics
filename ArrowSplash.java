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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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

public class ArrowSplash implements Listener, Runnable, CommandExecutor {

	public ItemStack arrowsplash = new ItemStack(Material.ARROW);
	public ItemMeta meta = arrowsplash.getItemMeta();
	
	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("arrowsplash")) {
					world.spawnParticle(Particle.SMOKE_NORMAL, tntprimed.getLocation(), 10, 0.5, 0.5, 0.5, 0.1);
					if (tntprimed.getFuseTicks() % 5 == 0) {
						for (Entity e : tntprimed.getNearbyEntities(50D, 50D, 50D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.ENTITY_ARROW_HIT, 1f, 1f);
							}
						}
					}
					if (tntprimed.getTicksLived() == 99) {
						Location location1 = tntprimed.getLocation();
						for (double i = 0; i <= Math.PI / 2; i += Math.PI / 50) {
							double radius2 = Math.sin(i);
							double y = Math.cos(i);
							for (double a = 0; a < Math.PI * 2; a += Math.PI / 50) {
								double x = Math.cos(a) * radius2;
								double z = Math.sin(a) * radius2;
								location1.add(x, y, z);
								world.spawnEntity(location1, EntityType.ARROW);
								location1.subtract(x, y, z);
							}
						}
						world.createExplosion(tntprimed.getLocation(), 100, false, false);
						tntprimed.remove();
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("arrowsplash")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Arrow Splash");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 0");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			arrowsplash.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(arrowsplash);
				}
			} else {
				player.getInventory().addItem(arrowsplash);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceArrowSplash(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Arrow Splash")) {
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
					TNTPrimed arrowsplash = (TNTPrimed) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					arrowsplash.setCustomName("arrowsplash");
					arrowsplash.setGlowing(true);
					arrowsplash.setFuseTicks(100);
				}
			}
		}
	}

}
