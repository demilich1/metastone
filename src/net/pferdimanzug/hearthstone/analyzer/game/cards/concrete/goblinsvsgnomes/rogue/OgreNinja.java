package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.MisdirectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.FumbleTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class OgreNinja extends MinionCard {

	public OgreNinja() {
		super("Ogre Ninja", 6, 6, Rarity.RARE, HeroClass.ROGUE, 5);
		setDescription("Stealth. 50% chance to attack the wrong enemy.");
	}

	@Override
	public Minion summon() {
		Minion ogreNinja = createMinion(GameTag.STEALTHED);
		SpellDesc fumble = MisdirectSpell.create();
		fumble.setTarget(EntityReference.EVENT_TARGET);
		SpellTrigger trigger = new SpellTrigger(new FumbleTrigger(), MisdirectSpell.create());
		ogreNinja.setSpellTrigger(trigger);
		return ogreNinja;
	}

}
