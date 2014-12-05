package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.EntityRaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroySpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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
