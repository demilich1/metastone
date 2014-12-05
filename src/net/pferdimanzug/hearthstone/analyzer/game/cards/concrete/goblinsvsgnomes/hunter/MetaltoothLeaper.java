package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.hunter;

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
