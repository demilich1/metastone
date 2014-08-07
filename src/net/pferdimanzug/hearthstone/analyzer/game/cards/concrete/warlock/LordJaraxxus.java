package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.LordJaraxxusSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
