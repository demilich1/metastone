package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.naxxramas.tokens.Slime;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class SludgeBelcher extends MinionCard {

	public SludgeBelcher() {
		super("Sludge Belcher", 3, 5, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Taunt. Deathrattle: Summon a 1/2 Slime with Taunt.");
	}

	@Override
	public int getTypeId() {
		return 398;
	}

	@Override
	public Minion summon() {
		Minion sludgeBelcher = createMinion(GameTag.TAUNT);
		SpellDesc summonSlime = SummonSpell.create(new Slime());
		sludgeBelcher.addDeathrattle(summonSlime);
		return sludgeBelcher;
	}
}
