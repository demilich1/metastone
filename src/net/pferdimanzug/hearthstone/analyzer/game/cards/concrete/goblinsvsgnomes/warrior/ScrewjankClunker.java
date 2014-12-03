package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ScrewjankClunker extends MinionCard {

	public ScrewjankClunker() {
		super("Screwjank Clunker", 2, 5, Rarity.RARE, HeroClass.WARRIOR, 4);
		setDescription("Battlecry: Give a friendly Mech +2/+2.");
		setTag(GameTag.BATTLECRY);

		setRace(Race.MECH);
	}

	@Override
	public Minion summon() {
		Minion screwjankClunker = createMinion();
		SpellDesc buffMechSpell = BuffSpell.create(+2, +2);
		Battlecry battlecry = Battlecry.createBattlecry(buffMechSpell, TargetSelection.FRIENDLY_MINIONS);
		battlecry.setEntityFilter(entity -> entity.getTag(GameTag.RACE) == Race.MECH);
		screwjankClunker.setBattlecry(battlecry);
		return screwjankClunker;
	}

}
