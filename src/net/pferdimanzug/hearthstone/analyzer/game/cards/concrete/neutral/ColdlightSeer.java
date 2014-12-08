package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.filter.RaceFilter;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ColdlightSeer extends MinionCard {

	public ColdlightSeer() {
		super("Coldlight Seer", 2, 3, Rarity.RARE, HeroClass.ANY, 3);
		setDescription("Battlecry: Give ALL other Murlocs +2 Health.");
		setRace(Race.MURLOC);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 107;
	}

	@Override
	public Minion summon() {
		Minion coldlightSeer = createMinion();
		SpellDesc murlocBuffSpell = BuffSpell.create(0, 2);
		murlocBuffSpell.setTargetFilter(new RaceFilter(Race.MURLOC));
		murlocBuffSpell.setTarget(EntityReference.ALL_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(murlocBuffSpell);
		coldlightSeer.setBattlecry(battlecry);
		return coldlightSeer;
	}
}
