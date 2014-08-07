package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddSpellTriggerSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonCopySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellTrigger trigger = new SpellTrigger(new TurnEndTrigger(), summonCopy);
		SpellDesc addTrigger = AddSpellTriggerSpell.create(trigger);
		addTrigger.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(addTrigger);
		echoingOoze.setBattlecry(battlecry);
		return echoingOoze;
	}
}
