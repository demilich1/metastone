package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class VioletTeacher extends MinionCard {

	private class VioletApprentice extends MinionCard {

		public VioletApprentice() {
			super("Violet Apprentice", 1, 1, Rarity.FREE, HeroClass.ANY, 0);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}

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
		Spell summonSpell = new SummonSpell(new VioletApprentice());
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), summonSpell);
		violetTeacher.setSpellTrigger(trigger);
		return violetTeacher;
	}
}
