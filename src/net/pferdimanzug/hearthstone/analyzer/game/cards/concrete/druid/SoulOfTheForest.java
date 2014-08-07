package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddDeathrattleSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SoulOfTheForest extends SpellCard {

	public SoulOfTheForest() {
		super("Soul of the Forest", Rarity.COMMON, HeroClass.DRUID, 4);
		setDescription("Give your minions \"Deathrattle: Summon a 2/2 Treant.\"");

		SpellDesc summonSpell = SummonSpell.create(new Treant());
		SpellDesc addDeathrattleSpell = AddDeathrattleSpell.create(summonSpell);
		addDeathrattleSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		setSpell(addDeathrattleSpell);

		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 20;
	}

	private class Treant extends MinionCard {

		public Treant() {
			super("Treant", 2, 2, Rarity.COMMON, HeroClass.DRUID, 1);
			setCollectible(false);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
	}
}
