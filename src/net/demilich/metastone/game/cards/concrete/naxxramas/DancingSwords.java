package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class DancingSwords extends MinionCard {

	public DancingSwords() {
		super("Dancing Swords", 4, 4, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Deathrattle: Your opponent draws a card.");
	}

	@Override
	public int getTypeId() {
		return 386;
	}

	@Override
	public Minion summon() {
		Minion dancingSwords = createMinion();
		SpellDesc drawCardSpell = DrawCardSpell.create(1, TargetPlayer.OPPONENT);
		dancingSwords.addDeathrattle(drawCardSpell);
		return dancingSwords;
	}
}
