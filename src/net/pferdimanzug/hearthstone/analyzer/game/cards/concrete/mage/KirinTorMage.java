package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class KirinTorMage extends MinionCard {

	public KirinTorMage() {
		super("Kirin Tor Mage", 4, 3, Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Battlecry: The next Secret you play this turn costs (0).");
	}

	@Override
	public Minion summon() {
		Minion kirinTorMage = createMinion();
		Spell kirinTorSpell = new ApplyTagSpell(GameTag.PLAY_SECRET_AT_NO_COST);
		kirinTorSpell.setTarget(EntityReference.FRIENDLY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(kirinTorSpell);
		kirinTorMage.setBattlecry(battlecry);
		return kirinTorMage;
	}

}
