package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas.tokens.Slime;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		summonSlime.setTarget(EntityReference.NONE);
		sludgeBelcher.addDeathrattle(summonSlime);
		return sludgeBelcher;
	}
}
