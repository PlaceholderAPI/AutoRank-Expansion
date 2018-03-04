/*
 *
 * AutoRank-Expansion
 * Copyright (C) 2018 Ryan McCarthy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package com.extendedclip.papi.expansion.autorank;

import me.armar.plugins.autorank.Autorank;
import me.armar.plugins.autorank.api.API;
import me.armar.plugins.autorank.util.AutorankTools;
import me.armar.plugins.autorank.util.AutorankTools.Time;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.clip.placeholderapi.util.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AutoRankExpansion extends PlaceholderExpansion {

	private API autorank;
	
	@Override
	public boolean canRegister() {
		return Bukkit.getPluginManager().getPlugin(getPlugin()) != null;
	}
	
	@Override
	public boolean register() {
		
		Autorank ar = (Autorank) Bukkit.getPluginManager().getPlugin(getPlugin());
		
		if (ar != null) {
			
			autorank = ar.getAPI();
			
			if (autorank == null) {
				return false;
			}
		}
		
		return PlaceholderAPI.registerPlaceholderHook(getIdentifier(), this);
	}

	@Override
	public String getAuthor() {
		return "clip";
	}

	@Override
	public String getIdentifier() {
		return "autorank";
	}

	@Override
	public String getPlugin() {
		return "Autorank";
	}

	@Override
	public String getVersion() {
		return "1.0.2";
	}
	
	

	@Override
	public String onPlaceholderRequest(Player p, String identifier) {

		if (autorank == null || p == null) {
			return "";
		}
		
		switch(identifier) {
		
		case "active_path":
			return autorank.getActivePath(p.getUniqueId()) != null ? autorank.getActivePath(p.getUniqueId()).getDisplayName() : "";
		case "time_of_player":
			return String.valueOf(autorank.getTimeOfPlayer(p));
		case "time_of_player_formatted":
			return TimeUtil.getTime(autorank.getTimeOfPlayer(p));
		case "local_time":
			String localTime = AutorankTools.timeToString(autorank.getLocalPlayTime(p.getUniqueId()), Time.MINUTES);
			return localTime != null ? localTime : ""; 
		case "local_time_formatted":
			String localTimeFormatted = AutorankTools.timeToString(autorank.getLocalPlayTime(p.getUniqueId()), Time.SECONDS);
			if (localTimeFormatted == null) {
				return "";
			}
			try {
				int t = Integer.parseInt(localTimeFormatted);
				return TimeUtil.getTime(t);
			} catch (NumberFormatException ex) {
				return "";
			}
		case "global_time":
			String globalTime = AutorankTools.timeToString(autorank.getGlobalPlayTime(p.getUniqueId()), Time.MINUTES);
			return globalTime != null ? globalTime : ""; 
		case "global_time_formatted":
			String globalTimeFormatted = AutorankTools.timeToString(autorank.getGlobalPlayTime(p.getUniqueId()), Time.SECONDS);
			if (globalTimeFormatted == null) {
				return "";
			}
			try {
				int t = Integer.parseInt(globalTimeFormatted);
				return TimeUtil.getTime(t);
			} catch (NumberFormatException ex) {
				return "";
			}
		}
	
		return null;
	
	}	
}
