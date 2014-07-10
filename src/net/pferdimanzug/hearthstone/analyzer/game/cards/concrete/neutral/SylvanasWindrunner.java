package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MindControlRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class SylvanasWindrunner extends MinionCard {

	public SylvanasWindrunner() {
		super("Sylvanas Windrunner", 5, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Deathrattle: Take control of a random enemy minion.");
	}

	@Override
	public Minion summon() {
		Minion sylvanasWindrunner = createMinion();
		Spell takeControl = new MindControlRandomSpell();
		takeControl.setTarget(EntityReference.ENEMY_MINIONS);
		sylvanasWindrunner.addDeathrattle(takeControl);
		return sylvanasWindrunner;
	}

}
