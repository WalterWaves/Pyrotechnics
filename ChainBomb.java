package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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
import org.bukkit.util.Vector;

public class ChainBomb implements Listener, Runnable, CommandExecutor {

	public ItemStack chainbomb = new ItemStack(Material.CHAIN);
	public ItemMeta meta = chainbomb.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("chainbomb")) {
					tntprimed.setVelocity(new Vector(0, -20, 0));
					Particle.DustOptions red = new Particle.DustOptions(Color.fromRGB(255, 0, 0), 1);
					world.spawnParticle(Particle.REDSTONE, tntprimed.getLocation(), 10, 1, 1, 1, 0.5, red);
					if (tntprimed.getTicksLived() == 100) {
						world.createExplosion(tntprimed.getLocation().add(5, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(-5, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, 5), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, -5), 5);
					}
					if (tntprimed.getTicksLived() == 110) {
						world.createExplosion(tntprimed.getLocation().add(10, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(-10, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, 10), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, -10), 5);
					}
					if (tntprimed.getTicksLived() == 120) {
						world.createExplosion(tntprimed.getLocation().add(15, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(-15, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, 15), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, -15), 5);
					}
					if (tntprimed.getTicksLived() == 130) {
						world.createExplosion(tntprimed.getLocation().add(20, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(-20, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, 20), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, -20), 5);
					}
					if (tntprimed.getTicksLived() == 140) {
						world.createExplosion(tntprimed.getLocation().add(25, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(-25, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, 25), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, -25), 5);
					}
					if (tntprimed.getTicksLived() == 150) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, 0), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, 30), 5);
						world.createExplosion(tntprimed.getLocation().add(0, 0, -30), 5);
					}
					if (tntprimed.getTicksLived() == 160) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 5), 5);
						world.createExplosion(tntprimed.getLocation().add(-5, 0, 30), 5);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, -5), 5);
						world.createExplosion(tntprimed.getLocation().add(5, 0, -30), 5);
					}
					if (tntprimed.getTicksLived() == 170) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 10), 5);
						world.createExplosion(tntprimed.getLocation().add(-10, 0, 30), 5);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, -10), 5);
						world.createExplosion(tntprimed.getLocation().add(10, 0, -30), 5);
					}
					if (tntprimed.getTicksLived() == 180) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 15), 5);
						world.createExplosion(tntprimed.getLocation().add(-15, 0, 30), 5);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, -15), 5);
						world.createExplosion(tntprimed.getLocation().add(15, 0, -30), 5);
					}
					if (tntprimed.getTicksLived() == 190) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 20), 5);
						world.createExplosion(tntprimed.getLocation().add(-20, 0, 30), 5);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, -20), 5);
						world.createExplosion(tntprimed.getLocation().add(20, 0, -30), 5);
					}
					if (tntprimed.getTicksLived() == 200) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 25), 5);
						world.createExplosion(tntprimed.getLocation().add(-25, 0, 30), 5);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, -25), 5);
						world.createExplosion(tntprimed.getLocation().add(25, 0, -30), 5);
					}
					if (tntprimed.getTicksLived() == 210) {
						world.createExplosion(tntprimed.getLocation().add(30, 0, 30), 3);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, 30), 3);
						world.createExplosion(tntprimed.getLocation().add(-30, 0, -30), 3);
						world.createExplosion(tntprimed.getLocation().add(30, 0, -30), 3);
						world.createExplosion(tntprimed.getLocation(), 5);
						tntprimed.remove();
					}
				}
			}
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("chainbomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Chain Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 245");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			chainbomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(chainbomb);
				}
			} else {
				player.getInventory().addItem(chainbomb);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceChainBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Chain Bomb")) {
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
					TNTPrimed chainbomb = (TNTPrimed) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					chainbomb.setCustomName("chainbomb");
					chainbomb.setGlowing(true);
					chainbomb.setFuseTicks(211);
				}
			}
		}
	}

}
