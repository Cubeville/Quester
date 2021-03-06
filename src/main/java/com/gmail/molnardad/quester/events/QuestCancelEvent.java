package com.gmail.molnardad.quester.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import com.gmail.molnardad.quester.ActionSource;
import com.gmail.molnardad.quester.quests.Quest;

public class QuestCancelEvent extends QuesterEvent implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
	
	private boolean cancelled = false;
	private final Player player;
	private final Quest quest;
	private final ActionSource actionSource;
	
	public QuestCancelEvent(ActionSource actionSource, Player player, Quest quest) {
		this.player = player;
		this.quest = quest;
		this.actionSource = actionSource;
	}
	
	public ActionSource getActionSource() {
		return actionSource;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Quest getQuest() {
		return quest;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean value) {
		cancelled = false;
	}
	
}
