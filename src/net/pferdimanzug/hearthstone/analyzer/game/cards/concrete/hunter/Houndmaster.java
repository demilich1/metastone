package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityRaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Houndmaster extends MinionCard {

	public Houndmaster() {
		super("Houndmaster", 4, 3, Rarity.FREE, HeroClass.HUNTER, 4);
		setDescription("Battlecry: Give a friendly Beast +2/+2 and Taunt.");
	}

	@Override
	public int getTypeId() {
		return 36;
	}

	@Override
	public Minion summon() {
		Minion houndmaster = createMinion();
		SpellDesc houndmasterSpell = MetaSpell.create(BuffSpell.create(2, 2), ApplyTagSpell.create(GameTag.TAUNT));
		Battlecry battlecry = Battlecry.createBattlecry(houndmasterSpell, TargetSelection.FRIENDLY_MINIONS);
		battlecry.setEntityFilter(new EntityRaceFilter(Race.BEAST));
		houndmaster.setBattlecry(battlecry);
		return houndmaster;
	}
}
