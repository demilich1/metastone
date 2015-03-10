package net.demilich.metastone.game.cards.concrete.blackrockmountain;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SummonRandomMinionPredicateSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class HungryDragon extends MinionCard {

	public HungryDragon() {
		super("Hungry Dragon", 5, 6, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Battlecry: Summon a random 1-Cost minion for your opponent.");
		setTag(GameTag.BATTLECRY);
		setRace(Race.DRAGON);
	}

	@Override
	public Minion summon() {
		Minion hungryDragon = createMinion();
		SpellDesc summonSpell = SummonRandomMinionPredicateSpell.create(card -> card.getBaseManaCost() == 1);
		summonSpell.setTargetPlayer(TargetPlayer.OPPONENT);
		Battlecry battlecry = Battlecry.createBattlecry(summonSpell);
		hungryDragon.setBattlecry(battlecry);
		return hungryDragon;
	}

}
