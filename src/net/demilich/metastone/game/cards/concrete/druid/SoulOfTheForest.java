package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.druid.Treant;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.AddDeathrattleSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class SoulOfTheForest extends SpellCard {

	public SoulOfTheForest() {
		super("Soul of the Forest", Rarity.COMMON, HeroClass.DRUID, 4);
		setDescription("Give your minions \"Deathrattle: Summon a 2/2 Treant.\"");

		SpellDesc summonSpell = SummonSpell.create(new Treant());
		SpellDesc addDeathrattleSpell = AddDeathrattleSpell.create(EntityReference.FRIENDLY_MINIONS, summonSpell);
		setSpell(addDeathrattleSpell);

		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 20;
	}
	
}
