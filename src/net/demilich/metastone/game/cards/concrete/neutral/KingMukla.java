package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.spells.Bananas;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ReceiveCardSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class KingMukla extends MinionCard {

	public KingMukla() {
		super("King Mukla", 5, 5, Rarity.LEGENDARY, HeroClass.ANY, 3);
		setDescription("Battlecry: Give your opponent 2 Bananas.");
		setRace(Race.BEAST);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 150;
	}

	@Override
	public Minion summon() {
		Minion kingMukla = createMinion();
		SpellDesc bananasSpell = ReceiveCardSpell.create(TargetPlayer.OPPONENT, new Bananas(), new Bananas());
		BattlecryAction battlecry = BattlecryAction.createBattlecry(bananasSpell);
		kingMukla.setBattlecry(battlecry);
		return kingMukla;
	}
}
