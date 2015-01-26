package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.EntityRaceFilter;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Houndmaster extends MinionCard {

	public Houndmaster() {
		super("Houndmaster", 4, 3, Rarity.FREE, HeroClass.HUNTER, 4);
		setDescription("Battlecry: Give a friendly Beast +2/+2 and Taunt.");
	}

	@Override
	public int getTypeId() {
		return 36;
	}

	@Override
	public Minion summon() {
		Minion houndmaster = createMinion();
		SpellDesc houndmasterSpell = MetaSpell.create(BuffSpell.create(2, 2), ApplyTagSpell.create(GameTag.TAUNT));
		Battlecry battlecry = Battlecry.createBattlecry(houndmasterSpell, TargetSelection.FRIENDLY_MINIONS);
		battlecry.setEntityFilter(new EntityRaceFilter(Race.BEAST));
		houndmaster.setBattlecry(battlecry);
		return houndmaster;
	}
}
