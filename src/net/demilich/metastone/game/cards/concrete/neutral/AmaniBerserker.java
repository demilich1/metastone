package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.EnrageSpell;
import net.demilich.metastone.game.spells.trigger.EnrageChangedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class AmaniBerserker extends MinionCard {

	public static final int BASE_ATTACK = 2;
	public static final int ENRAGE_ATTACK_BONUS = 3;

	public AmaniBerserker() {
		super("Amani Berserker", BASE_ATTACK, 3, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Enrage: +3 Attack");
	}

	@Override
	public int getTypeId() {
		return 82;
	}

	@Override
	public Minion summon() {
		Minion amaniBerserker = createMinion(GameTag.ENRAGABLE);
		SpellTrigger trigger = new SpellTrigger(new EnrageChangedTrigger(null), EnrageSpell.create(ENRAGE_ATTACK_BONUS));
		amaniBerserker.setSpellTrigger(trigger);
		return amaniBerserker;
	}
}
