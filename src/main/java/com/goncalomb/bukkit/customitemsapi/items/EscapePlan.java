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
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.goncalomb.bukkit.customitemsapi.api.CustomFirework;
import com.goncalomb.bukkit.customitemsapi.api.FireworkPlayerDetails;
import com.goncalomb.bukkit.customitemsapi.api.PlayerDetails;

public final class EscapePlan extends CustomFirework {
	
	public EscapePlan() {
		super("escape-plan", ChatColor.YELLOW + "Escape Plan");
		setLore("§bSteve Co. Space Program!",
				"§bProvides a quick escape from your foes.",
				"§b... or just send 'em into spaaaace!",
				"§bUse on open areas.");
	}
	
	@Override
	public void onAttack(EntityDamageByEntityEvent event, PlayerDetails details) {
		Entity entity = event.getEntity();
		if (entity instanceof LivingEntity) {
			details.consumeItem();
			fire(entity.getLocation(), details, entity);
		}
	}
	
	@Override
	public boolean onFire(FireworkPlayerDetails details, FireworkMeta meta) {
		if (details.getUserObject() == null) {
			// This was fired with right click, not by attacking another entity.
			if (details.getPlayer().getVehicle() != null) {
				return false;
			}
			details.setUserObject(details.getPlayer());
		}
		details.getFirework().setPassenger((LivingEntity) details.getUserObject());
		meta.setPower(2);
		meta.addEffect(FireworkEffect.builder().withColor(Color.YELLOW).withFade(Color.WHITE).withFlicker().withTrail().build());
		return true;
	}
	
	@Override
	public void onExplode(final FireworkPlayerDetails details) {
		final Vector v = details.getFirework().getVelocity().setY(0).normalize().multiply(7).setY(1);
		final Entity passenger = details.getFirework().getPassenger();
		if (passenger != null && passenger == details.getUserObject()) {
			Bukkit.getScheduler().runTaskLater(getPlugin(), new Runnable() {
				@Override
				public void run() {
					((LivingEntity) passenger).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 8*20, 4), true);
					passenger.setVelocity(v);
				}
			}, 2);
		}
	}
	
}
