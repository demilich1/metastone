package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

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

public class DunemaulShaman extends MinionCard {

	public DunemaulShaman() {
		super("Dunemaul Shaman", 5, 4, Rarity.RARE, HeroClass.SHAMAN, 4);
		setDescription("Windfury, Overload: (1) 50% chance to attack the wrong enemy.");
		setTag(GameTag.OVERLOAD, 1);
	}

	@Override
	public Minion summon() {
		Minion dunemaulShaman = createMinion(GameTag.WINDFURY);
		SpellDesc fumble = MisdirectSpell.create();
		fumble.setTarget(EntityReference.EVENT_TARGET);
		SpellTrigger trigger = new SpellTrigger(new FumbleTrigger(), MisdirectSpell.create());
		dunemaulShaman.setSpellTrigger(trigger);
		return dunemaulShaman;
	}

}
