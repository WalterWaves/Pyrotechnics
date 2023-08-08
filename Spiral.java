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

public class Spiral implements Listener, Runnable, CommandExecutor {

	public ItemStack spiral = new ItemStack(Material.CAULDRON);
	public ItemMeta meta = spiral.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof ArmorStand) {
				ArmorStand armorstand = (ArmorStand) entities;
				if (armorstand.getCustomName() != null && armorstand.getCustomName().equals("spiral")) {
					armorstand.teleport(new Location(world, armorstand.getLocation().getX(),
							armorstand.getLocation().getY(), armorstand.getLocation().getZ(),
							armorstand.getLocation().getYaw() + 50, armorstand.getLocation().getPitch()));

					if (armorstand.getLocation().getYaw() % 100 == 0) {
						TNTPrimed tnt = (TNTPrimed) world.spawnEntity(armorstand.getEyeLocation(),
								EntityType.PRIMED_TNT);
						tnt.setVelocity(armorstand.getLocation().getDirection().multiply((armorstand.getLocation().getYaw() + 3000) / 3000));
						tnt.setFuseTicks(40);
						tnt.setCustomName("spiral2");
					}
					if (armorstand.getTicksLived() >= 100) {
						world.createExplosion(armorstand.getLocation(), 2);
						armorstand.remove();
						for (Entity e : armorstand.getNearbyEntities(100D, 100D, 100D)) {
							if (e instanceof Player) {
								Player player = (Player) e;
								player.playSound(armorstand.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1f, 1.75f);
							}
						}
					}
				}
			}
			
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("spiral2")) {
					world.spawnParticle(Particle.SMALL_FLAME, tntprimed.getLocation().add(0, 1, 0), 5, 0.1, 0, 0.1, 0.1);
					world.spawnParticle(Particle.LAVA, tntprimed.getLocation().add(0, 1, 0), 5, 0.1, 0.1, 0.1, 0.1);
					tntprimed.setGlowing(true);
					if (tntprimed.getTicksLived() == 39) {
						world.spawnParticle(Particle.SMOKE_LARGE, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.CLOUD, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.LAVA, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
						world.spawnParticle(Particle.FLAME, tntprimed.getLocation(), 250, 0.5, 0.5, 0.5, 0.25);
					}
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("spiral")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Spiral");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 150");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.setLore(lore);
			spiral.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(spiral);
				}
			} else {
				player.getInventory().addItem(spiral);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceSpiral(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Spiral")) {
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
					ArmorStand spiral = (ArmorStand) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
					spiral.setCustomName("spiral");
					spiral.setInvulnerable(true);
					spiral.setRotation(0, 0);
					spiral.setGravity(false);
					spiral.setInvisible(true);
				}
			}
		}
	}

}
