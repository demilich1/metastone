package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.LordJaraxxusSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class LordJaraxxus extends MinionCard {

	public LordJaraxxus() {
		super("Lord Jaraxxus", 3, 15, Rarity.LEGENDARY, HeroClass.WARLOCK, 9);
		setDescription("Battlecry: Destroy your hero and replace him with Lord Jaraxxus.");
		setRace(Race.DEMON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 344;
	}

	@Override
	public Minion summon() {
		Minion lordJaraxxus = createMinion();
		SpellDesc spell = LordJaraxxusSpell.create();
		spell.setTarget(EntityReference.SELF);
		Battlecry battlecry = Battlecry.createBattlecry(spell);
		lordJaraxxus.setBattlecry(battlecry);
		return lordJaraxxus;
	}

	

	
}
