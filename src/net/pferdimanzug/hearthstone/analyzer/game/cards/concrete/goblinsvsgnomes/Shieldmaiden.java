package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Shieldmaiden extends MinionCard {

	public Shieldmaiden() {
		super("Shieldmaiden", 5, 5, Rarity.RARE, HeroClass.WARRIOR, 6);
		setDescription("Battlecry: Gain 5 Armor.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion shieldmaiden = createMinion();
		SpellDesc gainArmorSpell = BuffHeroSpell.create(0, 5);
		gainArmorSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(gainArmorSpell);
		shieldmaiden.setBattlecry(battlecry);
		return shieldmaiden;
	}

}
