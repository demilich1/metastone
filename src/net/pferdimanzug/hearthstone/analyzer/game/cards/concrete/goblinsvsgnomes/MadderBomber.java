package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class MadderBomber extends MinionCard {

	public MadderBomber() {
		super("Madder Bomber", 5, 6, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Battlecry: Deal 6 damage randomly split between all other characters.");
	}

	@Override
	public Minion summon() {
		Minion madderBomber = createMinion();
		SpellDesc spell = DamageRandomSpell.create(1, 6);
		spell.setTarget(EntityReference.ALL_CHARACTERS);
		Battlecry battlecry = Battlecry.createBattlecry(spell, TargetSelection.NONE);
		madderBomber.setBattlecry(battlecry);
		return madderBomber;
	}

}
