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
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class VerticalCannon implements Listener, Runnable, CommandExecutor {

	public ItemStack verticalcannon = new ItemStack(Material.BREWING_STAND);
	public ItemMeta meta = verticalcannon.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("verticalcannon")) {
					armorstand.getLocation().getBlock().setType(Material.BREWING_STAND);
					if (armorstand.getTicksLived() >= 41) {
						if (armorstand.getTicksLived() % 10 == 0) {
							TNTPrimed tnt = (TNTPrimed) world.spawnEntity(armorstand.getLocation().add(0, 0.5, 0), EntityType.PRIMED_TNT);
							tnt.setCustomName("verticalcannon2");
							tnt.setGlowing(true);
							tnt.setFuseTicks(100);
							int randomY = getRandomInt(100);
							int randomX = getRandomInt(10);
							int randomZ = getRandomInt(10);
							int positive = getRandomInt(2);
							if (positive == 0) {
								tnt.setVelocity(new Vector(randomX / 100f, 1.5 + randomY / 100f, randomZ / 100f));
							} else {
								tnt.setVelocity(new Vector(-randomX / 100f, 1.5 + randomY / 100f, -randomZ / 100f));
							}
							for (Entity e : armorstand.getNearbyEntities(50D, 50D, 50D)) {
								if (e instanceof Player) {
									Player player = (Player) e;
									player.playSound(armorstand.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1f,
											1f);
								}
							}
						}
					}
					if (armorstand.getTicksLived() >= 141) {
						armorstand.getLocation().getBlock().setType(Material.AIR);
						world.createExplosion(armorstand.getLocation(), 0);
						world.spawnParticle(Particle.SMOKE_NORMAL, armorstand.getLocation(), 50, 0, 0, 0, 0.1);
						armorstand.remove();
					}
				}
			}
			if (entities instanceof TNTPrimed) {
				TNTPrimed tnt = (TNTPrimed) entities;
				if (tnt.getCustomName() != null && tnt.getCustomName().equals("verticalcannon2")) {
					world.spawnParticle(Particle.FLAME, tnt.getLocation().add(0, 1, 0), 2, 0.1, 0.3, 0.1, 0.01);
					if (tnt.getVelocity().getY() > 0) {
						world.spawnParticle(Particle.FIREWORKS_SPARK, tnt.getLocation().add(0, -1, 0), 10, 0.05, -0.5, 0.05, 0.05);
					}
					if (tnt.getTicksLived() == 99) {
						world.spawnParticle(Particle.SMOKE_LARGE, tnt.getLocation(), 50, 0.1, 0.1, 0.1, 0.1);
						world.spawnParticle(Particle.CLOUD, tnt.getLocation(), 50, 0.1, 0.1, 0.1, 0.1);
						world.spawnParticle(Particle.LAVA, tnt.getLocation(), 50, 0.1, 0.1, 0.1, 0.1);
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("verticalcannon")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Vertical Cannon");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 30");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			verticalcannon.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(verticalcannon);
				}
			} else {
				player.getInventory().addItem(verticalcannon);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceVerticalCannon(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.GRAY + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Vertical Cannon")) {
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
					ArmorStand verticalcannon = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
					verticalcannon.setCustomName("verticalcannon");
					verticalcannon.setInvulnerable(true);
					verticalcannon.setGravity(false);
					verticalcannon.setInvisible(true);
				}
			}
		}
	}
	
	public static Integer getRandomInt(Integer max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}

}
