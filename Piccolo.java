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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Piccolo implements Listener, Runnable, CommandExecutor {

	public ItemStack piccolo = new ItemStack(Material.TNT);
	public ItemMeta meta = piccolo.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof Item) {
				Item item = (Item) entities;
				if (item.getCustomName() != null && item.getCustomName().equals("piccolo")) {
					world.spawnParticle(Particle.SMALL_FLAME, item.getLocation().add(0, 0.75, 0), 1, 0, 0, 0, 0.01);
					if (item.getTicksLived() >= 40) {
						world.createExplosion(item.getLocation(), 1);
						world.spawnParticle(Particle.SMOKE_NORMAL, item.getLocation(), 10, 0, 0, 0, 0.1);
						item.setTicksLived(6000);
						for (Entity e : item.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Player) {
								Player player = (Player) e;
								player.playSound(item.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 3f);
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
		if (label.equalsIgnoreCase("piccolo")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Piccolo");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Throwable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 1");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Throw");
			meta.setLore(lore);
			piccolo.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(piccolo);
				}
			} else {
				player.getInventory().addItem(piccolo);
			}
		}
		return false;
	}

	@EventHandler
	public void ThrowPiccolo(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
				.equals(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Piccolo")) {
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
					Item piccolo = (Item) world.spawnEntity(player.getEyeLocation(), EntityType.DROPPED_ITEM);
					piccolo.setItemStack(new ItemStack(Material.TNT));
					piccolo.setCustomName("piccolo");
					piccolo.setPickupDelay(6000);
					piccolo.setGlowing(true);
					piccolo.setVelocity(player.getLocation().getDirection().multiply(2));
					piccolo.getItemStack().setAmount(64);
					piccolo.setInvulnerable(true);
				}
			}
		}
	}
}
