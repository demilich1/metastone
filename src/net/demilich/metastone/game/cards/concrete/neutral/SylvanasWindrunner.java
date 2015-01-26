package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.MindControlRandomSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class SylvanasWindrunner extends MinionCard {

	public SylvanasWindrunner() {
		super("Sylvanas Windrunner", 5, 5, Rarity.LEGENDARY, HeroClass.ANY, 6);
		setDescription("Deathrattle: Take control of a random enemy minion.");
	}

	@Override
	public int getTypeId() {
		return 213;
	}

	@Override
	public Minion summon() {
		Minion sylvanasWindrunner = createMinion();
		SpellDesc takeControl = MindControlRandomSpell.create();
		takeControl.setTarget(EntityReference.ENEMY_MINIONS);
		sylvanasWindrunner.addDeathrattle(takeControl);
		return sylvanasWindrunner;
	}
}
