package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.OneTurnCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddCostModifierSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class KirinTorMage extends MinionCard {

	public KirinTorMage() {
		super("Kirin Tor Mage", 4, 3, Rarity.RARE, HeroClass.MAGE, 3);
		setDescription("Battlecry: The next Secret you play this turn costs (0).");
	}

	@Override
	public int getTypeId() {
		return 66;
	}

	@Override
	public Minion summon() {
		Minion kirinTorMage = createMinion();
		OneTurnCostModifier costModifier = new OneTurnCostModifier(CardType.SPELL, -99, true);
		costModifier.setRequiredTag(GameTag.SECRET);
		SpellDesc castSecretForFree = AddCostModifierSpell.create(costModifier);
		castSecretForFree.setTarget(EntityReference.FRIENDLY_HERO);
		kirinTorMage.setBattlecry(Battlecry.createBattlecry(castSecretForFree));
		return kirinTorMage;
	}
}
