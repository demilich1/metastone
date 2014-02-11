package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DreadInfernal extends MinionCard {

	public DreadInfernal() {
		super("Dread Infernal", 6, 6, Rarity.FREE, HeroClass.WARLOCK, 6);
		setDescription("Battlecry: Deal 1 damage to ALL other characters. ");
	}

	@Override
	public Minion summon() {
		Minion dreadInfernal = createMinion(Race.DEMON);
		Battlecry infernoBattlecry = Battlecry.createBattlecry(new DamageSpell(1), TargetSelection.NONE);
		infernoBattlecry.setTargetKey(EntityReference.ALL_CHARACTERS);
		dreadInfernal.setTag(GameTag.BATTLECRY, infernoBattlecry);
		return dreadInfernal;
	}
	

}
