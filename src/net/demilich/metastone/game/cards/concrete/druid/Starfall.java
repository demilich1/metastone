package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.ChooseOneCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Starfall extends ChooseOneCard {

	public Starfall() {
		super("Starfall", CardType.SPELL, Rarity.RARE, HeroClass.DRUID, 5);
		setDescription("Choose One - Deal $5 damage to a minion; or $2 damage to all enemy minions.");
		
		setCard1(new StarfallSingleTarget());
		setCard2(new StarfallAoE());
	}

	@Override
	public int getTypeId() {
		return 21;
	}

	private class StarfallAoE extends SpellCard {

		public StarfallAoE() {
			super("2 damage AoE", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Deal $2 damage to all enemy minions");
			setCollectible(false);

			SpellDesc damageSpell = DamageSpell.create(EntityReference.ENEMY_MINIONS, 2);
			setSpell(damageSpell);
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	private class StarfallSingleTarget extends SpellCard {

		public StarfallSingleTarget() {
			super("5 damage single target", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Deal 5 damage to a minion");
			setCollectible(false);

			setSpell(DamageSpell.create(5));
			setTargetRequirement(TargetSelection.MINIONS);
		}

	}
}
