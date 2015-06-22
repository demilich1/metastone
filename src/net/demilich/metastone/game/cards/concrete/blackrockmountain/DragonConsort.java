package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.CardCostModifierSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.CardPlayedTrigger;
import net.demilich.metastone.game.spells.trigger.GameEventTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class DragonConsort extends MinionCard {

	public DragonConsort() {
		super("Dragon Consort", 5, 5, Rarity.RARE, HeroClass.PALADIN, 5);
		setDescription("Battlecry: The next Dragon you play costs (2) less.");
		setRace(Race.DRAGON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion dragonConsort = createMinion();
		SpellDesc spellCostReduce = CardCostModifierSpell.create(EntityReference.FRIENDLY_HERO, new DragonCostModifier(-2));
		BattlecryAction battlecry = BattlecryAction.createBattlecry(spellCostReduce);
		dragonConsort.setBattlecry(battlecry);
		return dragonConsort;
	}

	private class DragonCostModifier extends CardCostModifier {
		
		private final GameEventTrigger dragonPlayedTrigger = new CardPlayedTrigger(null); 

		public DragonCostModifier(int manaModifier) {
			super(null);
			//setRequiredRace(Race.DRAGON);
		}
		
		@Override
		public boolean interestedIn(GameEventType eventType) {
			return dragonPlayedTrigger.interestedIn() == eventType;
		}

		@Override
		public void onGameEvent(GameEvent event) {
			Entity host = event.getGameContext().resolveSingleTarget(getHostReference());
			if (dragonPlayedTrigger.fires(event, host)) {
				expire();
			}
		}

	}

	@Override
	public int getTypeId() {
		return 629;
	}
}
