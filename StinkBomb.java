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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StinkBomb implements Listener, Runnable, CommandExecutor {

	public ItemStack stinkbomb = new ItemStack(Material.INK_SAC);
	public ItemMeta meta = stinkbomb.getItemMeta();
	
	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("stinkbomb")) {
					if (armorstand.getLocation().add(0, -5, 0).getBlock().getType() != Material.AIR) {
						armorstand.teleport(armorstand.getLocation().add(0, 0.1, 0));
					}
					if (armorstand.getTicksLived() >= 400) {
						armorstand.remove();
					}
					if (armorstand.getTicksLived() >= 200) {
						List<Entity> ents = armorstand.getNearbyEntities(15D, 15D, 15D);
						for (Entity e : ents) {
							if (e instanceof LivingEntity) {
								LivingEntity p = (LivingEntity) e;
								p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 0, true));
								p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0, true));
								p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0, true));
								p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40, 2, true));
							}
						}
					}
					
					Location location1 = armorstand.getLocation().add(0, 10, 0);
					Particle.DustOptions dark_green = new Particle.DustOptions(Color.fromRGB(1, 50, 32), 3);
					Particle.DustOptions light_green = new Particle.DustOptions(Color.fromRGB(144, 238, 144), 3);
					for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
						double radius2 = Math.sin(i) * armorstand.getTicksLived() / 20f;
						double y = Math.cos(i) * armorstand.getTicksLived() / 20f * 1.5f;
						for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
							double x = Math.cos(a) * radius2;
							double z = Math.sin(a) * radius2;
							location1.add(x, y, z);
							world.spawnParticle(Particle.REDSTONE, location1, 1, 0, 0, 0, 0, dark_green);
							location1.subtract(x, y, z);
						}
					}
					Location location2 = armorstand.getLocation().add(0, 9.5, 0);
					for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
						double radius2 = Math.sin(i) * armorstand.getTicksLived() / 20f;
						double y = Math.cos(i) * armorstand.getTicksLived() / 20f * 1.5f;
						for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
							double x = Math.cos(a) * radius2;
							double z = Math.sin(a) * radius2;
							location2.add(x, y, z);
							world.spawnParticle(Particle.REDSTONE, location2, 1, 0, 0, 0, 0, light_green);
							location2.subtract(x, y, z);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("stinkbomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Stink Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 0");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			stinkbomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(stinkbomb);
				}
			} else {
				player.getInventory().addItem(stinkbomb);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceStinkBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Stink Bomb")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					for (Entity e : player.getNearbyEntities(5D, 5D, 5D)) {
						if (e instanceof Player) {
							Player players = (Player) e;
							players.playSound(player, Sound.ENTITY_SPLASH_POTION_BREAK, 1f, 1f);
						}
					}
					player.playSound(player, Sound.ENTITY_SPLASH_POTION_BREAK, 1f, 1f);
					ArmorStand stinkbomb1 = (ArmorStand) world
							.spawnEntity(new Location(world, event.getClickedBlock().getLocation().getX() + 5,
									world.getHighestBlockYAt(event.getClickedBlock().getLocation()),
									event.getClickedBlock().getLocation().getZ() + 5), EntityType.ARMOR_STAND);
					stinkbomb1.setCustomName("stinkbomb");
					stinkbomb1.setInvulnerable(true);
					stinkbomb1.setGravity(false);
					stinkbomb1.setTicksLived(100);
					stinkbomb1.setInvisible(true);
					ArmorStand stinkbomb2 = (ArmorStand) world
							.spawnEntity(new Location(world, event.getClickedBlock().getLocation().getX() - 5,
									world.getHighestBlockYAt(event.getClickedBlock().getLocation()),
									event.getClickedBlock().getLocation().getZ() - 5), EntityType.ARMOR_STAND);
					stinkbomb2.setCustomName("stinkbomb");
					stinkbomb2.setInvulnerable(true);
					stinkbomb2.setGravity(false);
					stinkbomb2.setTicksLived(100);
					stinkbomb2.setInvisible(true);
					ArmorStand stinkbomb3 = (ArmorStand) world
							.spawnEntity(new Location(world, event.getClickedBlock().getLocation().getX() + 5,
									world.getHighestBlockYAt(event.getClickedBlock().getLocation()),
									event.getClickedBlock().getLocation().getZ() - 5), EntityType.ARMOR_STAND);
					stinkbomb3.setCustomName("stinkbomb");
					stinkbomb3.setInvulnerable(true);
					stinkbomb3.setGravity(false);
					stinkbomb3.setTicksLived(100);
					stinkbomb3.setInvisible(true);
					ArmorStand stinkbomb4 = (ArmorStand) world
							.spawnEntity(new Location(world, event.getClickedBlock().getLocation().getX() - 5,
									world.getHighestBlockYAt(event.getClickedBlock().getLocation()),
									event.getClickedBlock().getLocation().getZ() + 5), EntityType.ARMOR_STAND);
					stinkbomb4.setCustomName("stinkbomb");
					stinkbomb4.setInvulnerable(true);
					stinkbomb4.setGravity(false);
					stinkbomb4.setTicksLived(100);
					stinkbomb4.setInvisible(true);
				}
			}
		}
	}

}
