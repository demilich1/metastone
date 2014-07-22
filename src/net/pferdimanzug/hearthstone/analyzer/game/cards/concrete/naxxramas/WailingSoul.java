package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SilenceSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class WailingSoul extends MinionCard {

	public WailingSoul() {
		super("Wailing Soul", 3, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Silence your other minions.");
	}

	@Override
	public Minion summon() {
		Minion wailingSoul = createMinion();
		Spell silenceOwnMinions = new SilenceSpell();
		silenceOwnMinions.setTarget(EntityReference.FRIENDLY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(silenceOwnMinions);
		wailingSoul.setBattlecry(battlecry);
		return wailingSoul;
	}

}
