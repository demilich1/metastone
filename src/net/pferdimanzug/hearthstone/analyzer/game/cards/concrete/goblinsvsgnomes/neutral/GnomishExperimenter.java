package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.GnomishExperimenterSpell;

public class GnomishExperimenter extends MinionCard {

	public GnomishExperimenter() {
		super("Gnomish Experimenter", 3, 2, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlery: Draw a card, if it is a minion turn it into a chicken.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 519;
	}

	@Override
	public Minion summon() {
		Minion gnomishExperimenter = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(GnomishExperimenterSpell.create());
		gnomishExperimenter.setBattlecry(battlecry);
		return gnomishExperimenter;
	}
}
