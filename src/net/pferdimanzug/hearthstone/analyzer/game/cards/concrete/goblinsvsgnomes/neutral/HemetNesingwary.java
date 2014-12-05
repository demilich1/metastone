package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityRaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HemetNesingwary extends MinionCard {

	public HemetNesingwary() {
		super("Hemet Nesingwary", 6, 3, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Destroy a Beast.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion hemetNesingwary = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DestroySpell.create(), TargetSelection.MINIONS);
		battlecry.setEntityFilter(new EntityRaceFilter(Race.BEAST));
		hemetNesingwary.setBattlecry(battlecry);
		return hemetNesingwary;
	}

}
