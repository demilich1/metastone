package net.demilich.metastone.game.cards.concrete.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.OneTurnCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddCostModifierSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.SecretPlayedTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

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
		OneTurnCostModifier costModifier = new OneTurnCostModifier(CardType.SPELL, -99, new SecretPlayedTrigger());
		costModifier.setRequiredTag(GameTag.SECRET);
		SpellDesc castSecretForFree = AddCostModifierSpell.create(costModifier);
		castSecretForFree.setTarget(EntityReference.FRIENDLY_HERO);
		kirinTorMage.setBattlecry(Battlecry.createBattlecry(castSecretForFree));
		return kirinTorMage;
	}
}
