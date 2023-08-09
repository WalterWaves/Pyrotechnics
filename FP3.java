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
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FP3 implements Listener, Runnable, CommandExecutor {

	public ItemStack fp3 = new ItemStack(Material.TNT);
	public ItemMeta meta = fp3.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof Item) {
				Item item = (Item) entities;
				if (item.getCustomName() != null && item.getCustomName().equals("fp3")) {
					world.spawnParticle(Particle.SMALL_FLAME, item.getLocation().add(0, 0.5, 0), 1, 0, 0, 0, 0);
					world.spawnParticle(Particle.LAVA, item.getLocation().add(0, 0.75, 0), 1, 0, 0.1, 0, 0.1);
					if (item.getTicksLived() >= 60) {
						world.createExplosion(item.getLocation(), 2, true, true);
						world.spawnParticle(Particle.SMOKE_LARGE, item.getLocation(), 25, 0, 0, 0, 0.1);
						world.spawnParticle(Particle.CLOUD, item.getLocation(), 25, 0, 0, 0, 0.1);
						world.spawnParticle(Particle.EXPLOSION_NORMAL, item.getLocation(), 1, 0, 0, 0, 0);
						item.setTicksLived(6000);
						for (Entity e : item.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Player) {
								Player player = (Player) e;
								player.playSound(item.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.75f);
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
		if (label.equalsIgnoreCase("fp3")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "FP3");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Throwable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 2");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Throw");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			fp3.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(fp3);
				}
			} else {
				player.getInventory().addItem(fp3);
			}
		}
		return false;
	}

	@EventHandler
	public void ThrowFP3(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "FP3")) {
			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
					Item fp3 = (Item) world.spawnEntity(player.getEyeLocation(), EntityType.DROPPED_ITEM);
					fp3.setItemStack(new ItemStack(Material.TNT));
					fp3.setCustomName("fp3");
					fp3.setPickupDelay(6000);
					fp3.setGlowing(true);
					fp3.setVelocity(player.getLocation().getDirection());
					fp3.getItemStack().setAmount(64);
					fp3.setInvulnerable(true);
				}
			}
		}
	}
}
