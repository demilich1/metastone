package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
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
		//SpellDesc buffSpell = BuffSpell.create(EntityReference.OTHER_FRIENDLY_MINIONS, +2, 0, new RaceFilter(Race.MECH), false);
		SpellDesc buffSpell = BuffSpell.create(EntityReference.OTHER_FRIENDLY_MINIONS, +2, 0, null, false);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(buffSpell);
		metaltoothLeaper.setBattlecry(battlecry);
		return metaltoothLeaper;
	}
}
