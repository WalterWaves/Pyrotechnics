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

public class IncendiaryTNT implements Listener, Runnable, CommandExecutor {
	
	public ItemStack incendiarytnt = new ItemStack(Material.SPECTRAL_ARROW);
	public ItemMeta meta = incendiarytnt.getItemMeta();
	
	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("incendiarytnt")) {
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
					world.spawnParticle(Particle.LAVA, tntprimed.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
					world.spawnParticle(Particle.FLAME, tntprimed.getLocation(), 10, 0.1, 0.1, 0.1, 0.1);
					if (tntprimed.getTicksLived() == 99) {
						for (int x = -80; x <= 80; x += 20) {
							for (int z = -80; z <= 80; z += 20) {
								world.createExplosion(tntprimed.getLocation().add(x, 0, z), 50, true, false);
							}
						}
						world.spawnParticle(Particle.FLAME, tntprimed.getLocation(), 1000, 10, 10, 10, 0.5);
						world.spawnParticle(Particle.SMOKE_NORMAL, tntprimed.getLocation(), 1000, 10, 10, 10, 0.5);
						world.spawnParticle(Particle.LAVA, tntprimed.getLocation(), 1000, 10, 10, 10, 0.5);
						tntprimed.remove();
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("incendiarytnt")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Incendiary TNT");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 0-500");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			incendiarytnt.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(incendiarytnt);
				}
			} else {
				player.getInventory().addItem(incendiarytnt);
			}
		}
		return false;
	}
	
	@EventHandler
	public void PlaceIncendiaryTNT(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.GOLD + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Incendiary TNT")) {
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
					TNTPrimed incendiarytnt = (TNTPrimed) world.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					incendiarytnt.setCustomName("incendiarytnt");
					incendiarytnt.setGlowing(true);
					incendiarytnt.setFuseTicks(100);
				}
			}
		}
	}

}
