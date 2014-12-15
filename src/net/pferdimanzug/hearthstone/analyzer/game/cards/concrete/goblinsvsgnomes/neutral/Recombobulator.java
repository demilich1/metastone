package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformToMinionWithManaCostSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Recombobulator extends MinionCard {

	public Recombobulator() {
		super("Recombobulator", 3, 2, Rarity.EPIC, HeroClass.ANY, 2);
		setDescription("Battlecry: Transform a friendly minion into a random minion with the same Cost.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 541;
	}

	@Override
	public Minion summon() {
		Minion recombolator = createMinion();
		SpellDesc transformSpell = TransformToMinionWithManaCostSpell.create();
		transformSpell.setTarget(EntityReference.FRIENDLY_MINIONS);
		transformSpell.pickRandomTarget(true);
		Battlecry battlecry = Battlecry.createBattlecry(transformSpell);
		recombolator.setBattlecry(battlecry);
		return recombolator;
	}
}
