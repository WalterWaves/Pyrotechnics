package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import org.bukkit.entity.LivingEntity;
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

public class FreezeBomb implements Listener, Runnable, CommandExecutor {

	public ItemStack freezebomb = new ItemStack(Material.SNOWBALL);
	public ItemMeta meta = freezebomb.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("freezebomb")) {
					world.spawnParticle(Particle.SNOWFLAKE, tntprimed.getLocation().add(0, 1, 0), 10, 0.1, 0.1, 0.1,
							0.1);
					world.spawnParticle(Particle.SNOWBALL, tntprimed.getLocation().add(0, 1, 0), 10, 0.1, 0.1, 0.1,
							0.1);
					if (tntprimed.getTicksLived() == 99) {
						for (Entity e : tntprimed.getNearbyEntities(50D, 50D, 50D)) {
							if (e instanceof Player) {
								Player players = (Player) e;
								players.playSound(players, Sound.ENTITY_GENERIC_EXPLODE, 1f, 2f);
							}
						}
						for (Entity e : tntprimed.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof LivingEntity) {
								LivingEntity le = (LivingEntity) e;
								le.setFreezeTicks(400);
							}
						}
						for (int x = -100; x <= 100; x++) {
							for (int z = -100; z <= 100; z++) {
								if (world.getHighestBlockAt(tntprimed.getLocation().add(x, 1, z))
										.getType() != Material.WATER
										&& world.getHighestBlockAt(tntprimed.getLocation().add(x, 1, z))
												.getType() != Material.LAVA) {
									world.getHighestBlockAt(tntprimed.getLocation().add(x, 1, z))
											.setType(Material.SNOW);
								}

								int randomIce = getRandomInt(100);
								if (randomIce == 0) {
									int randomIceHeight = getRandomInt(15);
									for (int h = -20; h <= randomIceHeight + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z)).setType(Material.ICE);
										;
									}
									int randomIceHeight1 = getRandomInt(8);
									for (int h = -20; h <= randomIceHeight1 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x + 1, h, z))
												.setType(Material.ICE);
										;
									}
									int randomIceHeight2 = getRandomInt(8);
									for (int h = -20; h <= randomIceHeight2 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z + 1))
												.setType(Material.ICE);
										;
									}
									int randomIceHeight3 = getRandomInt(8);
									for (int h = -20; h <= randomIceHeight3 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x - 1, h, z))
												.setType(Material.ICE);
										;
									}
									int randomIceHeight4 = getRandomInt(8);
									for (int h = -20; h <= randomIceHeight4 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z - 1))
												.setType(Material.ICE);
										;
									}
								}

								int randomSnow = getRandomInt(100);
								if (randomSnow == 0) {
									int randomSnowHeight = getRandomInt(7);
									for (int h = -20; h <= randomSnowHeight + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight1 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight1 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x + 1, h, z))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight2 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight2 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z + 1))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight3 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight3 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x - 1, h, z))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight4 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight4 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z - 1))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight5 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight5 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x + 1, h, z + 1))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight6 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight6 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x - 1, h, z + 1))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight7 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight7 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x + 1, h, z - 1))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight8 = getRandomInt(3);
									for (int h = -20; h <= randomSnowHeight8 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x - 1, h, z - 1))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight9 = getRandomInt(2);
									for (int h = -20; h <= randomSnowHeight9 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x + 2, h, z))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight10 = getRandomInt(2);
									for (int h = -20; h <= randomSnowHeight10 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x - 2, h, z))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight11 = getRandomInt(2);
									for (int h = -20; h <= randomSnowHeight11 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z + 2))
												.setType(Material.SNOW_BLOCK);
										;
									}
									int randomSnowHeight12 = getRandomInt(2);
									for (int h = -20; h <= randomSnowHeight12 + 1; h++) {
										world.getBlockAt(tntprimed.getLocation().add(x, h, z - 2))
												.setType(Material.SNOW_BLOCK);
										;
									}
								}

								int randomPolarBear = getRandomInt(500);
								if (randomPolarBear == 0) {
									world.spawnEntity(
											world.getHighestBlockAt(tntprimed.getLocation().add(x, 0, z)).getLocation().add(0, 1, 0),
											EntityType.POLAR_BEAR);
								}
								int randomWolf = getRandomInt(500);
								if (randomWolf == 0) {
									world.spawnEntity(
											world.getHighestBlockAt(tntprimed.getLocation().add(x, 0, z)).getLocation().add(0, 1, 0),
											EntityType.WOLF);
								}
							}
						}
						for (int x = -100; x <= 100; x++) {
							for (int y = -100; y <= 100; y++) {
								for (int z = -100; z <= 100; z++) {
									if (tntprimed.getLocation().add(x, y, z).getBlock().getType() == Material.WATER) {
										tntprimed.getLocation().add(x, y, z).getBlock().setType(Material.ICE);
									}
									if (tntprimed.getLocation().add(x, y, z).getBlock().getType() == Material.LAVA) {
										tntprimed.getLocation().add(x, y, z).getBlock().setType(Material.OBSIDIAN);
									}
								}
							}
						}
						tntprimed.remove();
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("freezebomb")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Freeze Bomb");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 0");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			freezebomb.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(freezebomb);
				}
			} else {
				player.getInventory().addItem(freezebomb);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceFreezeBomb(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.AQUA + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Freeze Bomb")) {
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
					TNTPrimed freezebomb = (TNTPrimed) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					freezebomb.setCustomName("freezebomb");
					freezebomb.setGlowing(true);
					freezebomb.setFuseTicks(100);
				}
			}
		}
	}

	public Integer getRandomInt(Integer max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}

}
