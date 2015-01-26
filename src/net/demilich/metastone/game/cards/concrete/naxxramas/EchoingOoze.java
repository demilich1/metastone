package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddSpellTriggerSpell;
import net.demilich.metastone.game.spells.SummonCopySpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.spells.trigger.TurnEndTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class EchoingOoze extends MinionCard {

	public EchoingOoze() {
		super("Echoing Ooze", 1, 2, Rarity.EPIC, HeroClass.ANY, 2);
		setDescription("Battlecry: Summon an exact copy of this minion at the end of the turn.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 390;
	}

	@Override
	public Minion summon() {
		Minion echoingOoze = createMinion();
		SpellDesc summonCopy = SummonCopySpell.create();
		summonCopy.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), summonCopy, true);
		SpellDesc addTrigger = AddSpellTriggerSpell.create(trigger);
		addTrigger.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(addTrigger);
		echoingOoze.setBattlecry(battlecry);
		return echoingOoze;
	}
}
