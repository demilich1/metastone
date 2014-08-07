package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonNewAttackTargetSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionTargetedBySpellTrigger;

public class Spellbender extends SecretCard {

	public Spellbender() {
		super("Spellbender", Rarity.EPIC, HeroClass.MAGE, 3);
		setDescription("Secret: When an enemy casts a spell on a minion, summon a 1/3 as the new target.");

		SpellDesc spellbenderSpell = SummonNewAttackTargetSpell.create(new SpellbenderMinion());
		setTriggerAndEffect(new MinionTargetedBySpellTrigger(), spellbenderSpell);
	}

	@Override
	public int getTypeId() {
		return 73;
	}

	private class SpellbenderMinion extends MinionCard {

		public SpellbenderMinion() {
			super("Spellbender", 1, 3, Rarity.EPIC, HeroClass.MAGE, 0);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}

	}
}
