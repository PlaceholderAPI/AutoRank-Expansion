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
import me.armar.plugins.autorank.storage.TimeType;
import me.armar.plugins.autorank.util.AutorankTools;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AutoRankExpansion extends PlaceholderExpansion {

    private API autorank;

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(getRequiredPlugin()) != null;
    }

    @Override
    public boolean register() {

        Autorank ar = (Autorank) Bukkit.getPluginManager().getPlugin(getRequiredPlugin());

        // Autorank is not present.
        if (ar == null) {
            return false;
        }

        autorank = ar.getAPI();

        if (autorank == null) {
            return false;
        }


        return super.register();
    }

    @Override
    public String getAuthor() {
        return "Clip & Staartvin";
    }

    @Override
    public String getIdentifier() {
        return "autorank";
    }

    @Override
    public String getRequiredPlugin() {
        return "Autorank";
    }

    @Override
    public String getVersion() {
        return "1.1.1";
    }


    @Override
    public String onPlaceholderRequest(Player p, String identifier) {

        if (autorank == null || p == null) {
            return "";
        }

        UUID uuid = p.getUniqueId();

        boolean error = false;

        switch (identifier) {

            case "total_time_of_player":
                try {
                    return autorank.getPlayTime(TimeType.TOTAL_TIME, uuid, TimeUnit.MINUTES).get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "total_time_of_player_formatted":
                try {
                    int minutesPlayed = Math.toIntExact(autorank.getPlayTime(TimeType.TOTAL_TIME, uuid,
                            TimeUnit.MINUTES).get());
                    return AutorankTools.timeToString(minutesPlayed, TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "daily_time_of_player":
                try {
                    return autorank.getPlayTime(TimeType.DAILY_TIME, uuid, TimeUnit.MINUTES).get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "daily_time_of_player_formatted":
                try {
                    int minutesPlayed = Math.toIntExact(autorank.getPlayTime(TimeType.DAILY_TIME, uuid,
                            TimeUnit.MINUTES).get());
                    return AutorankTools.timeToString(minutesPlayed, TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "weekly_time_of_player":
                try {
                    return autorank.getPlayTime(TimeType.WEEKLY_TIME, uuid, TimeUnit.MINUTES).get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "weekly_time_of_player_formatted":
                try {
                    int minutesPlayed = Math.toIntExact(autorank.getPlayTime(TimeType.WEEKLY_TIME, uuid,
                            TimeUnit.MINUTES).get());
                    return AutorankTools.timeToString(minutesPlayed, TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "monthly_time_of_player":
                try {
                    return autorank.getPlayTime(TimeType.MONTHLY_TIME, uuid, TimeUnit.MINUTES).get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "monthly_time_of_player_formatted":
                try {
                    int minutesPlayed = Math.toIntExact(autorank.getPlayTime(TimeType.MONTHLY_TIME, uuid,
                            TimeUnit.MINUTES).get());
                    return AutorankTools.timeToString(minutesPlayed, TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "local_time":
                try {
                    return autorank.getLocalPlayTime(p.getUniqueId()).get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "local_time_formatted":
                try {
                    return AutorankTools.timeToString(autorank.getLocalPlayTime(p.getUniqueId()).get(),
                            TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "global_time":
                try {
                    return autorank.getGlobalPlayTime(p.getUniqueId()).get().toString();
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "global_time_formatted":
                try {
                    return AutorankTools.timeToString(autorank.getGlobalPlayTime(p.getUniqueId()).get(),
                            TimeUnit.MINUTES);
                } catch (InterruptedException | ExecutionException e) {
                    error = true;
                    e.printStackTrace();
                    break;
                }
            case "completed_paths":
                return StringUtils.join(autorank.getCompletedPaths(uuid), ", ");
            case "active_paths":
                return StringUtils.join(autorank.getActivePaths(uuid), ", ");
            case "eligible_paths":
                return StringUtils.join(autorank.getEligiblePaths(uuid), ", ");
        }

        return error ? "" : null;

    }
}
