package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MadBomber extends MinionCard {

	public MadBomber() {
		super("Mad Bomber", 3, 2, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Deal 3 damage randomly split between all other characters.");
	}

	@Override
	public Minion summon() {
		Minion madBomber = createMinion();
		Spell spell = new DamageRandomSpell(1, 3);
		spell.setTarget(EntityReference.ALL_CHARACTERS);
		Battlecry battlecry = Battlecry.createBattlecry(spell, TargetSelection.NONE);
		madBomber.setTag(GameTag.BATTLECRY, battlecry);
		return madBomber;
	}

}
