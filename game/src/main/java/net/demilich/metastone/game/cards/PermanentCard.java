package net.demilich.metastone.game.cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayPermanentCardAction;
import net.demilich.metastone.game.cards.desc.PermanentCardDesc;
import net.demilich.metastone.game.entities.minions.Permanent;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;

public class PermanentCard extends SummonCard {

	private static final Set<Attribute> ignoredAttributes = new HashSet<Attribute>(
			Arrays.asList(new Attribute[] { Attribute.PASSIVE_TRIGGER, Attribute.DECK_TRIGGER, Attribute.MANA_COST_MODIFIER, Attribute.BASE_ATTACK,
					Attribute.BASE_HP, Attribute.SECRET, Attribute.QUEST, Attribute.CHOOSE_ONE, Attribute.BATTLECRY, Attribute.COMBO }));

	private final PermanentCardDesc desc;

	public PermanentCard(PermanentCardDesc desc) {
		super(desc);
		this.desc = desc;
	}

	protected Permanent createPermanent(Attribute... tags) {
		Permanent permanent = new Permanent(this);
		for (Attribute gameTag : getAttributes().keySet()) {
			if (!ignoredAttributes.contains(gameTag)) {
				permanent.setAttribute(gameTag, getAttribute(gameTag));
			}
		}
		BattlecryDesc battlecry = desc.battlecry;
		if (battlecry != null) {
			BattlecryAction battlecryAction = BattlecryAction.createBattlecry(battlecry.spell, battlecry.getTargetSelection());
			if (battlecry.condition != null) {
				battlecryAction.setCondition(battlecry.condition.create());
			}

			permanent.setBattlecry(battlecryAction);
		}

		if (desc.deathrattle != null) {
			permanent.removeAttribute(Attribute.DEATHRATTLES);
			permanent.addDeathrattle(desc.deathrattle);
		}
		if (desc.trigger != null) {
			permanent.addSpellTrigger(desc.trigger.create());
		}
		if (desc.triggers != null) {
			for (TriggerDesc trigger : desc.triggers) {
				permanent.addSpellTrigger(trigger.create());
			}
		}
		if (desc.aura != null) {
			permanent.addSpellTrigger(desc.aura.create());
		}
		if (desc.cardCostModifier != null) {
			permanent.setCardCostModifier(desc.cardCostModifier.create());
		}
		return permanent;
	}

	@Override
	public PlayCardAction play() {
		return new PlayPermanentCardAction(getCardReference());
	}

	public void setRace(Race race) {
		setAttribute(Attribute.RACE, race);
	}

	public Permanent summon() {
		return createPermanent();
	}

}
