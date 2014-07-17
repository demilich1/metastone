package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.ChooseOneCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Starfall extends ChooseOneCard {

	private class StarfallAoE extends SpellCard {

		public StarfallAoE() {
			super("Starfall (AoE)", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Deal $2 damage to all enemy minions");
			setCollectible(false);

			Spell damageSpell = new DamageSpell(2);
			damageSpell.setTarget(EntityReference.ENEMY_MINIONS);
			setSpell(damageSpell);
			setTargetRequirement(TargetSelection.NONE);
		}

	}

	private class StarfallSingleTarget extends SpellCard {

		public StarfallSingleTarget() {
			super("Starfall (single target)", Rarity.RARE, HeroClass.DRUID, 5);
			setDescription("Deal 5 damage to a minion");
			setCollectible(false);

			setSpell(new DamageSpell(5));
			setTargetRequirement(TargetSelection.MINIONS);
		}

	}

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
}
