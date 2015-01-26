package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.FinkleEinhorn;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class TheBeast extends MinionCard {

	public TheBeast() {
		super("The Beast", 9, 7, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Deathrattle: Summon a 3/3 Finkle Einhorn for your opponent.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 215;
	}

	@Override
	public Minion summon() {
		Minion theBeast = createMinion();
		SpellDesc deathrattle = SummonSpell.create(TargetPlayer.OPPONENT, new FinkleEinhorn());
		theBeast.addDeathrattle(deathrattle);
		return theBeast;
	}
}
