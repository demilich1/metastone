package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class AncientMage extends MinionCard {

	public AncientMage() {
		super("Ancient Mage", 2, 5, Rarity.RARE, HeroClass.ANY, 4);
		setDescription("Battlecry: Give adjacent minions Spell Damage +1.");
	}

	@Override
	public Minion summon() {
		Minion ancientMage = createMinion();
		Spell spellpowerSpell = new ApplyTagSpell(GameTag.SPELL_POWER);
		spellpowerSpell.setTarget(EntityReference.ADJACENT_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(spellpowerSpell);
		ancientMage.setBattlecry(battlecry);
		return ancientMage;
	}
	
	
	

}
