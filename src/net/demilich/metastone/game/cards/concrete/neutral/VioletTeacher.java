package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.VioletApprentice;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.RelativeToSource;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SpellCastedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class VioletTeacher extends MinionCard {

	public VioletTeacher() {
		super("Violet Teacher", 3, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Whenever you cast a spell, summon a 1/1 Violet Apprentice.");
	}

	@Override
	public int getTypeId() {
		return 222;
	}

	@Override
	public Minion summon() {
		Minion violetTeacher = createMinion();
		SpellDesc summonSpell = SummonSpell.create(TargetPlayer.SELF, RelativeToSource.RIGHT, new VioletApprentice());
		//SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), summonSpell);
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(null), summonSpell);
		violetTeacher.setSpellTrigger(trigger);
		return violetTeacher;
	}
}
