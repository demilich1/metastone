package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.LordJaraxxusSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

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
		BattlecryAction battlecry = BattlecryAction.createBattlecry(spell);
		lordJaraxxus.setBattlecry(battlecry);
		return lordJaraxxus;
	}

	

	
}
