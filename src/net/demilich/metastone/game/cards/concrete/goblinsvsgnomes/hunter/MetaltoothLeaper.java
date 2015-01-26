package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

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

public class MetaltoothLeaper extends MinionCard {

	public MetaltoothLeaper() {
		super("Metaltooth Leaper", 3, 3, Rarity.RARE, HeroClass.HUNTER, 3);
		setDescription("Battlecry: Give your other Mechs +2 Attack.");
		setRace(Race.MECH);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 490;
	}

	@Override
	public Minion summon() {
		Minion metaltoothLeaper = createMinion();
		SpellDesc buffSpell = BuffSpell.create(+2);
		buffSpell.setTarget(EntityReference.OTHER_FRIENDLY_MINIONS);
		buffSpell.setTargetFilter(new RaceFilter(Race.BEAST));
		Battlecry battlecry = Battlecry.createBattlecry(buffSpell);
		metaltoothLeaper.setBattlecry(battlecry);
		return metaltoothLeaper;
	}
}
