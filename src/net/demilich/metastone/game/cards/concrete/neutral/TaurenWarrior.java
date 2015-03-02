package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.EnrageSpell;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class TaurenWarrior extends MinionCard {

	public TaurenWarrior() {
		super("Tauren Warrior", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Taunt. Enrage: +3 Attack");
	}

	@Override
	public int getTypeId() {
		return 214;
	}

	@Override
	public Minion summon() {
		Minion taurenWarrior = createMinion(GameTag.TAUNT, GameTag.ENRAGABLE);
		SpellTrigger trigger = new SpellTrigger(new EnrageChangedTrigger(), EnrageSpell.create(+3));
		taurenWarrior.setSpellTrigger(trigger);
		return taurenWarrior;
	}
}
