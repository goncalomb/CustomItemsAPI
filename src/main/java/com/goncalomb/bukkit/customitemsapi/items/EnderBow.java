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

import org.bukkit.ChatColor;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.goncalomb.bukkit.customitemsapi.api.CustomBow;
import com.goncalomb.bukkit.customitemsapi.api.DelayedPlayerDetails;

public final class EnderBow extends CustomBow {
	
	public EnderBow() {
		super("ender-bow", ChatColor.GREEN + "Ender Bow");
		setLore("§bA bow that shoots Ender Pearls.");
	}
	
	@Override
	public void onShootBow(EntityShootBowEvent event, DelayedPlayerDetails details) {
		Entity perl = event.getEntity().launchProjectile(EnderPearl.class);
		perl.setVelocity(event.getProjectile().getVelocity());
		event.setProjectile(perl);
	}

}
