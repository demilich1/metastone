package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.filter.RaceFilter;
import net.demilich.metastone.game.targeting.EntityReference;

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
