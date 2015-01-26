package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.EntityRaceFilter;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HemetNesingwary extends MinionCard {

	public HemetNesingwary() {
		super("Hemet Nesingwary", 6, 3, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Destroy a Beast.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 521;
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
