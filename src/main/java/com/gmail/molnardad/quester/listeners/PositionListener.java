package com.gmail.molnardad.quester.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.molnardad.quester.Quest;
import com.gmail.molnardad.quester.QuestFlag;
import com.gmail.molnardad.quester.Quester;
import com.gmail.molnardad.quester.elements.Objective;
import com.gmail.molnardad.quester.exceptions.QuesterException;
import com.gmail.molnardad.quester.managers.LanguageManager;
import com.gmail.molnardad.quester.managers.QuestManager;
import com.gmail.molnardad.quester.objectives.LocObjective;
import com.gmail.molnardad.quester.objectives.WorldObjective;

public class PositionListener implements Runnable {
	
	/**
	 * @uml.property  name="qm"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private QuestManager qm = null;
	/**
	 * @uml.property  name="langMan"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private LanguageManager langMan = null;
	
	public PositionListener(Quester plugin) {
		this.qm = plugin.getQuestManager();
		this.langMan = plugin.getLanguageManager();
	}
	
	@Override
	public void run() {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
	    	Quest quest = qm.getPlayerQuest(player.getName());
		    if(quest != null) {
		    	// LOCATION CHECK
		    	if(!quest.allowedWorld(player.getWorld().getName().toLowerCase()))
		    		return;
		    	List<Objective> objs = quest.getObjectives();
		    	for(int i = 0; i < objs.size(); i++) {
		    		if(objs.get(i).getType().equalsIgnoreCase("LOCATION")) {
		    			if(!qm.isObjectiveActive(player, i)){
		    				continue;
		    			}
		    			LocObjective obj = (LocObjective)objs.get(i);
		    			if(obj.checkLocation(player.getLocation())) {
		    				qm.incProgress(player, i);
		    				return;
		    			}
		    		} else if(objs.get(i).getType().equalsIgnoreCase("WORLD")) {
		    			if(!qm.isObjectiveActive(player, i)){
		    				continue;
		    			}
		    			WorldObjective obj = (WorldObjective)objs.get(i);
		    			if(obj.checkWorld(player.getWorld().getName())) {
		    				qm.incProgress(player, i);
		    				return;
		    			}
		    		}
		    	}
		    	
		    } else {
		    	Location loc = player.getLocation();
		    	for(int ID : qm.questLocations.keySet()) {
		    		Quest qst = qm.getQuest(ID);
		    		Location loc2 = qm.questLocations.get(ID);
		    		if(loc2.getWorld().getName().equals(loc.getWorld().getName())) {
			    		if(loc2.distanceSquared(loc) <= qst.getRange()*qst.getRange() && qst.hasFlag(QuestFlag.ACTIVE)) {
			    			try {
								qm.startQuest(player, qst.getName(), false, langMan.getPlayerLang(player.getName()));
							} catch (QuesterException e) {
							}
			    		}
		    		}
		    	}
		    }
		}
	}

}
