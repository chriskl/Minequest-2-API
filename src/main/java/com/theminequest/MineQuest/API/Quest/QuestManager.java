/*
 * This file is part of MineQuest-API, version 2, Specifications for the MineQuest system.
 * MineQuest-API, version 2 is licensed under GNU Lesser General Public License v3.
 * Copyright (C) 2012 The MineQuest Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.theminequest.MineQuest.API.Quest;

import org.bukkit.entity.Player;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.theminequest.MineQuest.API.BukkitEvents.QuestGivenEvent;
import com.theminequest.MineQuest.API.BukkitEvents.QuestCompleteEvent;
import com.theminequest.MineQuest.API.BukkitEvents.TaskCompleteEvent;

public interface QuestManager extends Listener {
	
	QuestParser getParser();

	void reloadQuests();
	void reloadQuest(String name);
	QuestDetails getDetails(String name);
	List<String> getListOfDetails();
	Quest getQuest(long currentquest);
	Quest startQuest(QuestDetails d, String ownerName);
	Quest[] getMainWorldQuests(Player player);
	Quest getMainWorldQuest(String playerName, String questName);
	void removeMainWorldQuest(String playerName, String questName);
	
	@EventHandler
	void taskCompletion(TaskCompleteEvent e);

	@EventHandler
	void onQuestCompletion(QuestCompleteEvent e);

	@EventHandler
	void onBlockPlaceEvent(BlockPlaceEvent e);

	@EventHandler
	void onBlockDamageEvent(BlockDamageEvent e);

	@EventHandler
	void onPlayerRespawnEvent(PlayerRespawnEvent e);
	
}
