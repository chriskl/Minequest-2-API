package com.theminequest.MineQuest.API.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.LivingEntity;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.Quest;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Target.TargetDetails;

public abstract class TargetQuestEvent extends DelayedQuestEvent {

	public TargetQuestEvent() {}
	
	private boolean targeted;

	@Override
	public long getDelay() {
		return 0;
	}

	@Override
	public final void parseDetails(String[] details) {
		targeted = false;
		if (details.length!=0){
			if (details[0].equalsIgnoreCase("T"))
				targeted = true;
		}
		additionalDetails(shrinkArray(details));
	}
	
	private String[] shrinkArray(String[] array){
		if (array.length<=1)
			return new String[0];
		String[] toreturn = new String[array.length-1];
		for (int i=1; i<array.length; i++)
			toreturn[i-1] = array[i];
		return toreturn;
	}
	
	/**
	 * Parse details for this targeted event.<br>
	 * This would normally be done in {@link #parseDetails(String[])},
	 * but must be overriden in TargetQuestEvent to define {@link #enableTargets()}.
	 * @param details
	 */
	public abstract void additionalDetails(String[] details);

	/**
	 * Some events are dual normal/targeted events.
	 * Figure out if getTargets() should check.
	 * @return true if event is targeted
	 */
	public boolean enableTargets(){
		return targeted;
	}
	
	/**
	 * Retrieve the target ID.
	 * @return target ID
	 */
	public abstract int getTargetId();
	
	/**
	 * Retrieve the targets with this event.
	 * @return list of LivingEntites (empty if disabled, or null if no associated quest)
	 */
	public List<LivingEntity> getTargets(){
		if (!enableTargets())
			return new ArrayList<LivingEntity>();
		Quest quest = getQuest();
		if (quest==null)
			return null;
		@SuppressWarnings("unchecked")
		TargetDetails details = ((Map<Integer,TargetDetails>) getQuest().getDetails().getProperty(QuestDetails.QUEST_TARGETS)).get(getTargetId());
		return Managers.getTargetManager().processTargetDetails(quest,details);
	}

}
