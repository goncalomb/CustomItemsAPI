/*
 * Copyright (C) 2013 - Gonçalo Baltazar <http://goncalomb.com>
 *
 * This file is part of CustomItemsAPI.
 *
 * CustomItemsAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CustomItemsAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CustomItemsAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.goncalomb.bukkit.customitemsapi.items;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public final class AntiMatterBomb extends GenericBomb {
	
	private boolean _cleanup;
	
	public AntiMatterBomb() {
		super("anti-matter-bomb", ChatColor.GREEN + "Anti-Matter Bomb", new MaterialData(Material.ENDER_PEARL));
		setLore("§c§k----- §c§lCaution! §r§c§k-----");
		setDefaultConfig("enabled", false);
		setDefaultConfig("fuse", 60);
		setDefaultConfig("cleanup", true);
	}
	
	@Override
	public void applyConfig(ConfigurationSection section) {
		super.applyConfig(section);
		_cleanup = section.getBoolean("cleanup", true);
	}
	
	@Override
	public void onExplode(Item item, Location location) {
		World world = location.getWorld();
		Random rand = new Random();
		int radiusSquared = 25;
		final ArrayList<FallingBlock> fallingBlocks = new ArrayList<FallingBlock>();
		for (int x = -5; x < 5; ++x) {
			for (int y = -5; y < 5; ++y) {
				for (int z = -5; z < 5; ++z) {
					Vector v = new Vector(x, y, z);
					if (v.lengthSquared() <= radiusSquared) {
						Location loc = location.clone().add(v);
						Block block = loc.getBlock();
						if (!block.isEmpty() && !block.isLiquid() && block.getType() != Material.BEDROCK) {
							Vector vel = location.toVector().subtract(loc.toVector()).normalize();
							vel.add(new Vector(rand.nextFloat() - 0.5, 0, rand.nextFloat() - 0.5)).normalize();
							vel.multiply(1 + rand.nextFloat());
							FallingBlock fallingBlock = world.spawnFallingBlock(loc, block.getTypeId(), block.getData());
							fallingBlock.setDropItem(false);
							fallingBlock.setVelocity(vel);
							fallingBlocks.add(fallingBlock);
							block.setType(Material.AIR);
						}
					}
				}
			}
		}
		if (fallingBlocks.size() > 0) {
			world.playSound(location, Sound.BAT_TAKEOFF, 5, 0.1f);
			if (_cleanup) {
				Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
					@Override
					public void run() {
						for (FallingBlock fallingBlock : fallingBlocks) {
							Block b = fallingBlock.getLocation().getBlock();
							if (b.getTypeId() == fallingBlock.getBlockId() && b.getData() == fallingBlock.getBlockData()) {
								b.setType(Material.AIR);
							}
							fallingBlock.remove();
						}
					}
				}, 5*20);
			}
		}
	}
	
}
