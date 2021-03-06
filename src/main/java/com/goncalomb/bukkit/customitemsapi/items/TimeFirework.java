/*
 * Copyright (C) 2013, 2014 - Gonçalo Baltazar <http://goncalomb.com>
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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import com.goncalomb.bukkit.customitemsapi.api.CustomFirework;
import com.goncalomb.bukkit.customitemsapi.api.DispenserDetails;
import com.goncalomb.bukkit.customitemsapi.api.FireworkPlayerDetails;

public class TimeFirework extends CustomFirework {
	
	private static boolean _changing = false;
	private long _finalTime;
	
	protected TimeFirework(String slug, String name, long time) {
		super(slug, name);
		_finalTime = time%24000;
		_finalTime = (_finalTime < 0 ? 24000 + _finalTime : _finalTime);
	}
	
	@Override
	public void onDispense(BlockDispenseEvent event, DispenserDetails details) {
		event.setCancelled(true);
		details.consumeItem();
		fire(details.getLocation(), details, null);
	}
	
	@Override
	public final boolean onFire(FireworkPlayerDetails details, FireworkMeta meta) {
		details.getFirework().setVelocity(new Vector(0, 0.05, 0));
		meta.setPower(10);
		return true;
	}
	
	@Override
	public final void onExplode(FireworkPlayerDetails details) {
		final Location loc = details.getFirework().getLocation();
		if (!_changing && loc.getY() > 255) {
			_changing = true;
			final World world = loc.getWorld();
			long time = world.getTime() - _finalTime;
			time = (time < 0 ? 24000 + time : time);
			world.setStorm(false);
			world.playSound(loc, Sound.AMBIENCE_THUNDER, 100, 0);
			
			final int n = (time > 12000 ? 1 : -1);
			final BukkitTask[] task = new BukkitTask[1];
			task[0] = Bukkit.getScheduler().runTaskTimer(getPlugin(), new Runnable() {
				@Override
				public void run() {
					long time = world.getFullTime() + n*500;
					world.setFullTime(time);
					if (Math.abs(_finalTime - time%24000) < 500) {
						world.playSound(loc, Sound.AMBIENCE_THUNDER, 100, 0);
						task[0].cancel();
						_changing = false;
					}
				}
			}, 0, 10);
		}
	}
	
}
