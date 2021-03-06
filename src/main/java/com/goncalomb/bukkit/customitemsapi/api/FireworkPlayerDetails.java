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

import org.bukkit.entity.Firework;

public final class FireworkPlayerDetails extends DelayedPlayerDetails {
	
	private Firework _firework;
	
	static FireworkPlayerDetails fromConsumableDetails(IConsumableDetails details, Firework firework, Object userObject) {
		if (details instanceof PlayerDetails) {
			return new FireworkPlayerDetails((PlayerDetails) details, firework, userObject);
		}
		return new FireworkPlayerDetails(details, firework, userObject);
	}
	
	private FireworkPlayerDetails(IConsumableDetails details, Firework firework, Object userObject) {
		super(details.getItem(), null);
		_firework = firework;
		_userObject = userObject;
	}
	
	private FireworkPlayerDetails(PlayerDetails details, Firework firework, Object userObject) {
		super(details._item, details._player);
		_firework = firework;
		_userObject = userObject;
	}
	
	public Firework getFirework() {
		return _firework;
	}

}
