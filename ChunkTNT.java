package me.WalterWaves.Pyrotechnics;

import java.util.ArrayList;
import java.util.Collection;
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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
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

public class ChunkTNT implements Listener, Runnable, CommandExecutor {

	public ItemStack chunktnt = new ItemStack(Material.CRYING_OBSIDIAN);
	public ItemMeta meta = chunktnt.getItemMeta();

	@Override
	public void run() {
		World world = (World) Bukkit.getWorld("world");
		for (Entity entities : world.getEntities()) {
			if (entities instanceof TNTPrimed) {
				TNTPrimed tntprimed = (TNTPrimed) entities;
				if (tntprimed.getCustomName() != null && tntprimed.getCustomName().equals("chunktnt")) {
					world.spawnParticle(Particle.CRIT_MAGIC, tntprimed.getLocation().add(0, -1, 0), 10, 1, 1, 1, 0.1);
					if (tntprimed.getTicksLived() >= 100) {
						drawLine(tntprimed.getLocation(), tntprimed.getLocation().add(0, -250, 0), 0.5);
						if (tntprimed.getTicksLived() % 10 == 0) {
							for (Entity e : tntprimed.getNearbyEntities(50D, 50D, 50D)) {
								if (e instanceof Player) {
									Player players = (Player) e;
									players.playSound(players, Sound.BLOCK_STONE_BREAK, 1f, 1f);
								}
							}

							for (int x = -8; x <= 8; x++) {
								for (int z = -8; z <= 8; z++) {
									for (int y = 0; y <= 200; y++) {
										if (tntprimed.getLocation().add(x, -y, z).getBlock()
												.getType() != Material.AIR) {
											if (tntprimed.getLocation().add(x, -y, z).getBlock()
													.getType() != Material.BEDROCK) {
												Collection<ItemStack> drops = tntprimed.getLocation().add(x, -y, z)
														.getBlock().getDrops();
												if (drops != null) {
													for (ItemStack i : drops) {
														Item item = (Item) world.spawnEntity(tntprimed.getLocation(),
																EntityType.DROPPED_ITEM);
														item.setItemStack(i);
														item.setGlowing(true);
														item.setVelocity(new Vector(1, 0.5, 0));
													}
												}
												FallingBlock fb = (FallingBlock) world.spawnFallingBlock(
														tntprimed.getLocation().add(x, -y, z), tntprimed.getLocation()
																.add(x, -y, z).getBlock().getBlockData());
												fb.setVelocity(new Vector(0, 100, 0));
												fb.setGravity(false);
												fb.setDropItem(false);
												tntprimed.getLocation().add(x, -y, z).getBlock().setType(Material.AIR);
												break;
											} else {
												world.spawnParticle(Particle.SOUL_FIRE_FLAME, tntprimed.getLocation(),
														1000, 5, 10, 5, 0.05);
												for (Entity e : tntprimed.getNearbyEntities(50D, 50D, 50D)) {
													if (e instanceof Player) {
														Player players = (Player) e;
														players.playSound(players, Sound.UI_TOAST_CHALLENGE_COMPLETE,
																1f, 1f);
													}
												}
												tntprimed.remove();
											}
										}
									}
								}
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
		if (label.equalsIgnoreCase("chunktnt")) {
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Chunk TNT");
			lore.add("");
			lore.add(ChatColor.DARK_GRAY + "TYPE" + ChatColor.GRAY + " Placeable");
			lore.add(ChatColor.DARK_GRAY + "POWER" + ChatColor.GRAY + " 80");
			lore.add(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "Right-Click To Ignite And Place");
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.setLore(lore);
			chunktnt.setItemMeta(meta);
			player.playSound(player, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
			if (args.length == 1) {
				for (int i = 1; i <= Integer.parseInt(args[0]); i++) {
					player.getInventory().addItem(chunktnt);
				}
			} else {
				player.getInventory().addItem(chunktnt);
			}
		}
		return false;
	}

	@EventHandler
	public void PlaceChunkTNT(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		World world = (World) player.getWorld();
		if (player.getInventory().getItemInMainHand().getItemMeta() != null
				&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()
						.equals(ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "" + ChatColor.UNDERLINE + "Chunk TNT")) {
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
					TNTPrimed chunktnt = (TNTPrimed) world.spawnEntity(
							event.getClickedBlock().getLocation().add(0.5, 1.5, 0.5), EntityType.PRIMED_TNT);
					chunktnt.setCustomName("chunktnt");
					chunktnt.setGlowing(true);
					chunktnt.setGravity(false);
					chunktnt.setVelocity(new Vector(0, 0.1, 0));
					chunktnt.setFuseTicks(5000);
				}
			}
		}
	}

	public void drawLine(Location point1, Location point2, double space) {
		World world = point1.getWorld();
		double distance = point1.distance(point2);
		Vector p1 = point1.toVector();
		Vector p2 = point2.toVector();
		Vector vector = p2.clone().subtract(p1).normalize().multiply(space);
		double length = 0;
		for (; length < distance; p1.add(vector)) {
			world.spawnParticle(Particle.END_ROD, p1.getX(), p1.getY(), p1.getZ(), 1, 0, 0, 0, 0);
			length += space;
		}
	}

}
