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
package com.theminequest.MineQuest.API.Group;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.theminequest.MineQuest.API.ManagerException;
import com.theminequest.MineQuest.API.BukkitEvents.GroupPlayerJoinedEvent;
import com.theminequest.MineQuest.API.BukkitEvents.GroupPlayerQuitEvent;

public interface GroupManager extends Listener {
	
	/**
	 * Create a new Group
	 * @param leader Leader of the group
	 * @return new Group
	 */
	Group createNewGroup(Player leader);
	
	/**
	 * Create a new Group
	 * @param members Members of the group. The leader will be the player in slot 0.
	 * @return new Group
	 * @throws ManagerException if the list has no players in it, or has too
	 * many players (defined by default group capacity)
	 */
	Group createNewGroup(List<Player> members) throws ManagerException;
	
	/**
	 * Return the group ID of the group that a player is in
	 * @param player Member of a group
	 * @return Group ID, or -1 if the player is not in a group
	 */
	long indexOf(Player player);
	
	/**
	 * Retrieve the group.
	 * @param id group ID
	 * @return Group
	 */
	Group get(long id);
	
	/**
	 * Retrieve the group.
	 * @param p Player in group
	 * @return Group
	 */
	Group get(Player p);
	
	/**
	 * Remove all players from this group and null it
	 * @param group Group to dispose
	 */
	void disposeGroup(Group group);
	
	/**
	 * Trigger an invite accepted by a player and add
	 * them to the respective group
	 * @param player Player that accepted invite
	 * @throws ManagerException if player does not have any invite
	 */
	void acceptInvite(Player player) throws ManagerException;
	
	/**
	 * Trash an invite given to a player
	 * @param player Player to trash invite
	 * @throws ManagerException if player does not have any invite
	 */
	void denyInvite(Player player) throws ManagerException;
	
	/**
	 * Invite a player into the group. This merely triggers
	 * a system event (GroupInviteEvent) which is then handled
	 * by all available frontends.
	 * @param p Player to invite
	 * @throws ManagerException if the player is already in a group
	 */
	void invite(Player player, Group group) throws ManagerException;
	
	/**
	 * Check to see if a player has a pending invite to a group.
	 * @param player Player to check
	 * @return true if player has a pending invite
	 */
	boolean hasInvite(Player player);
	
	/**
	 * Handle player joining group events
	 * @param e Event fired
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	void onGroupPlayerJoinedEvent(GroupPlayerJoinedEvent e);
	
	/**
	 * Handle player parting group events
	 * @param e Event fired
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	void onGroupPlayerQuitEvent(GroupPlayerQuitEvent e);
}
