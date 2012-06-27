package com.theminequest.MineQuest.API.Tracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.BukkitEvents.QuestGivenEvent;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestSnapshot;
import com.theminequest.MineQuest.API.Tracker.StatisticManager.Statistic;

@Table("minequest_Quests")
public class QuestStatistic extends Statistic implements Comparable<QuestStatistic> {
	
	@Id
	private long uuid;
		
	@Field
	private String questsGiven;
	
	@Field
	private String questsCompleted;
	
	@Field
	private ArrayList<QuestSnapshot> questsMWSaved;
	
	// NON-PERSISTENT DATA
	private List<String> givenQuests;
	private List<String> completedQuests;
	private Map<String, Quest> questsRegenerated;
	
	public String[] getGivenQuests(){
		setup();
		return givenQuests.toArray(new String[givenQuests.size()]);
	}
	
	public String[] getCompletedQuests(){
		setup();
		return completedQuests.toArray(new String[completedQuests.size()]);
	}
	
	public Quest[] getMainWorldQuests(){
		setup();
		return questsRegenerated.values().toArray(new Quest[questsRegenerated.size()]);
	}
	
	public void addGivenQuest(String questName){
		setup();
		givenQuests.add(questName);
		save();
		QuestGivenEvent e = new QuestGivenEvent(questName,Bukkit.getPlayer(getPlayerName()));
		Bukkit.getPluginManager().callEvent(e);
	}
	
	public void removeGivenQuest(String questName){
		setup();
		givenQuests.remove(questName);
		save();
	}
	
	public void addCompletedQuest(String questName){
		setup();
		if (!completedQuests.contains(questName))
			completedQuests.add(questName);
		save();
	}
	
	public void removeCompletedQuest(String questName){
		setup();
		completedQuests.remove(questName);
		save();
	}
	
	public void saveMainWorldQuest(Quest quest){
		setup();
		questsRegenerated.put((String) quest.getDetails().getProperty(QuestDetails.QUEST_NAME), quest);
		save();
	}
	
	public void removeMainWorldQuest(String questName){
		setup();
		questsRegenerated.remove(questName);
		save();
	}
	
	public void setup(){
		if (questsGiven==null)
			questsGiven = "";
		if (questsCompleted==null)
			questsCompleted = "";
		if (questsMWSaved==null)
			questsMWSaved = new ArrayList<QuestSnapshot>();
		if (givenQuests==null)
			givenQuests = new ArrayList<String>(Arrays.asList(questsGiven.split("/")));
		if (completedQuests==null)
			completedQuests = new ArrayList<String>(Arrays.asList(questsCompleted.split("/")));
		if (questsRegenerated==null){
			questsRegenerated = new LinkedHashMap<String, Quest>();
			for (QuestSnapshot s : questsMWSaved){
				Quest q = s.recreateQuest();
				questsRegenerated.put((String) q.getDetails().getProperty(QuestDetails.QUEST_NAME), q);
				q.startQuest();
			}
		}
	}
	
	public void save(){		
		questsGiven = "";
		for (String s : givenQuests){
			questsGiven += s + "/";
		}
		if (questsGiven.length()!=0)
			questsGiven = questsGiven.substring(0,questsGiven.length()-1);

		questsCompleted = "";
		for (String s : completedQuests){
			questsCompleted += s + "/";
		}
		if (questsCompleted.length()!=0)
			questsCompleted = questsCompleted.substring(0,questsCompleted.length()-1);
		
		questsMWSaved.clear();
		for (Quest q : questsRegenerated.values()){
			questsMWSaved.add(q.createSnapshot());
		}

		Managers.getStatisticManager().setStatistic(this, getClass());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof QuestStatistic))
			return false;
		return getPlayerName().equals(((QuestStatistic)obj).getPlayerName());
	}

	@Override
	public int compareTo(QuestStatistic other) {
		return getPlayerName().compareTo(other.getPlayerName());
	}

}
