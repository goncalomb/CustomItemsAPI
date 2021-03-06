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

package com.goncalomb.bukkit.customitemsapi.api;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.material.MaterialData;


public abstract class CustomBow extends CustomItem {
	
	protected CustomBow(String slug, String name) {
		super(slug, name, new MaterialData(Material.BOW));
	}
	
	public void onShootBow(EntityShootBowEvent event, DelayedPlayerDetails details) { }
	
	public void onProjectileHit(ProjectileHitEvent event, DelayedPlayerDetails details) { }
	
	public void onProjectileDamageEntity(EntityDamageByEntityEvent event, DelayedPlayerDetails details) { }
}
