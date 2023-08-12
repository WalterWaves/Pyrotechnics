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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class LightningFirework implements Listener, Runnable, CommandExecutor {

	public ItemStack lightningfirework = new ItemStack(Material.END_ROD);
	public ItemMeta meta = lightningfirework.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof Firework) {
				Firework firework = (Firework) entities;
				if (firework.getCustomName() != null && firework.getCustomName().equals("lightningfirework")) {
					world.spawnParticle(Particle.FIREWORKS_SPARK, firework.getLocation().add(0, -0.5, 0), 5, 0.1, 0.1,
							0.1, 0.1);
					if (firework.getTicksLived() >= 40) {
						TNTPrimed tnt1 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt1.setCustomName("lightningfireworktnt");
						tnt1.setVelocity(new Vector(0.75, 0.75, 0.75));
						tnt1.setFuseTicks(600);
						TNTPrimed tnt2 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt2.setCustomName("lightningfireworktnt");
						tnt2.setVelocity(new Vector(-0.75, 0.75, -0.75));
						tnt2.setFuseTicks(600);
						TNTPrimed tnt3 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt3.setCustomName("lightningfireworktnt");
						tnt3.setVelocity(new Vector(-0.75, 0.75, 0.75));
						tnt3.setFuseTicks(600);
						TNTPrimed tnt4 = (TNTPrimed) world.spawnEntity(firework.getLocation(), EntityType.PRIMED_TNT);
						tnt4.setCustomName("lightningfireworktnt");
						tnt4.setVelocity(new Vector(0.75, 0.75, -0.75));
						tnt4.setFuseTicks(600);
						List<Entity> ents = firework.getNearbyEntities(50D, 50D, 50D);
						for (Entity e : ents) {
							if (e instanceof Player) {
								Player p = (Player) e;
								p.playSound(p, Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR, 1f, 1f);
							}
						}
						firework.detonate();
					}
				}
			}
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("lightningfireworktnt")) {
					if (tntprimed.getTicksLived() == 30) {
						tntprimed.setVelocity(new Vector(0, 0, 0));
						tntprimed.setGravity(false);
						world.strikeLightning(tntprimed.getLocation());
					}
					if (tntprimed.getTicksLived() == 130) {
						tntprimed.setGravity(true);
						tntprimed.setVelocity(new Vector(0, -5, 0));
					}
					if (tntprimed.getLocation().add(0, -0.1, 0).getBlock().getType() != Material.AIR || tntprimed.getTicksLived() >= 170) {
						world.createExplosion(tntprimed.getLocation(), 50, true, true);
						tntprimed.remove();
					}
					world.spawnParticle(Particle.FIREWORKS_SPARK, new Location(world, tntprimed.getLocation().getX(),
							world.getHighestBlockYAt(tntprimed.getLocation()) + 20, tntprimed.getLocation().getZ()), 100,
							0, 10, 0, 0);
					tntprimed.setGlowing(true);
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("lightningfirework")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(
					ChatColor.WHITE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Lightning Firework");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Firework");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 200");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Launch");
			meta.setLore(lore);
			lightningfirework.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(lightningfirework);
				}
			} else {
				player.getInventory().addItem(lightningfirework);
			}
		}
		return false;
	}

	@EventHandler
	public void LaunchLightningFirework(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(
						ChatColor.WHITE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Lightning Firework")) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				event.setCancelled(true);
				if (event.getHand() == EquipmentSlot.HAND) {
					player.getInventory().getItemInMainHand()
							.setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
					Firework lightningfirework = (Firework) world
							.spawnEntity(event.getClickedBlock().getLocation().add(0.5, 0, 0.5), EntityType.FIREWORK);
					lightningfirework.setCustomName("lightningfirework");
					FireworkMeta fmeta = lightningfirework.getFireworkMeta();
					fmeta.setPower(4);
					lightningfirework.setFireworkMeta(fmeta);
				}
			}
		}
	}

}
