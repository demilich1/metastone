package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.EntityRaceFilter;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HungryCrab extends MinionCard {

	public HungryCrab() {
		super("Hungry Crab", 1, 2, Rarity.EPIC, HeroClass.ANY, 1);
		setDescription("Battlecry: Destroy a Murloc and gain +2/+2.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 142;
	}

	@Override
	public Minion summon() {
		Minion hungryCrab = createMinion();
		SpellDesc buffSpell = BuffSpell.create(2, 2);
		buffSpell.setTarget(EntityReference.SELF);
		SpellDesc hungryCrabSpell = MetaSpell.create(DestroySpell.create(), buffSpell);
		Battlecry battlecry = Battlecry.createBattlecry(hungryCrabSpell, TargetSelection.MINIONS);
		battlecry.setEntityFilter(new EntityRaceFilter(Race.MURLOC));
		hungryCrab.setBattlecry(battlecry);
		return hungryCrab;
	}
}
