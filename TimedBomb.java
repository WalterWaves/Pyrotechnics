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

public class TimedBomb implements Listener, Runnable, CommandExecutor {

	public ItemStack timedbomb = new ItemStack(Material.FIRE_CHARGE);
	public ItemMeta meta = timedbomb.getItemMeta();
	
	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().contains("Timed Bomb")) {
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation().add(0, 1, 0), 1, 0, 0, 0, 0);
					world.spawnParticle(Particle.LAVA, tntprimed.getLocation().add(0, 1, 0), 1, 0, 0.1, 0, 0.1);
					tntprimed.setCustomName(ChatColor.GRAY + "Timed Bomb" + ChatColor.RED + " " + tntprimed.getFuseTicks() / 20);
					if (tntprimed.getFuseTicks() % 20 == 0) {
						for (Entity e : tntprimed.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.BLOCK_NOTE_BLOCK_GUITAR, 1f, tntprimed.getFuseTicks() / 250f);
							}
						}
					}
					if (tntprimed.getTicksLived() == 399) {
						world.createExplosion(tntprimed.getLocation(), 30);
						world.spawnParticle(Particle.SMOKE_LARGE, tntprimed.getLocation(), 500, 1, 1, 1, 0.5);
						world.spawnParticle(Particle.CLOUD, tntprimed.getLocation(), 500, 1, 1, 1, 0.5);
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("timedbomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Timed Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 30");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			timedbomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(timedbomb);
				}
			} else {
				player.getInventory().addItem(timedbomb);
			}
		}
		return false;
	}
	
	@EventHandler
	public void PlaceTimedBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Timed Bomb")) {
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
					TNTPrimed timedbomb = (TNTPrimed) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					timedbomb.setCustomName(ChatColor.GRAY + "Timed Bomb" + ChatColor.RED + " 20");
					timedbomb.setCustomNameVisible(true);
					timedbomb.setGlowing(true);
					timedbomb.setFuseTicks(400);
				}
			}
		}
	}

}
