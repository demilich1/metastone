package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class KingMukla extends MinionCard {

	private class Bananas extends SpellCard {

		public Bananas() {
			super("Bananas", Rarity.FREE, HeroClass.ANY, 1);
			setCollectible(false);

			setSpell(new BuffSpell(1, 1));
			setTargetRequirement(TargetSelection.MINIONS);
		}

	}

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
		Spell bananasSpell = new ReceiveCardSpell(TargetPlayer.OPPONENT, new Bananas(), new Bananas());
		Battlecry battlecry = Battlecry.createBattlecry(bananasSpell);
		kingMukla.setBattlecry(battlecry);
		return kingMukla;
	}
}
