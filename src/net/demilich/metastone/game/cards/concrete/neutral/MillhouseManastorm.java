package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.costmodifier.OneTurnCostModifier;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddCostModifierSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class MillhouseManastorm extends MinionCard {

	public MillhouseManastorm() {
		super("Millhouse Manastorm", 4, 4, Rarity.LEGENDARY, HeroClass.ANY, 2);
		setDescription("Battlecry: Enemy spells cost (0) next turn.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 165;
	}

	@Override
	public Minion summon() {
		Minion millhouseManastorm = createMinion();
		OneTurnCostModifier costModifier = new OneTurnCostModifier(CardType.SPELL, -99, false);
		costModifier.setTargetPlayer(TargetPlayer.OPPONENT);
		SpellDesc castSpellsForFree = AddCostModifierSpell.create(costModifier);
		castSpellsForFree.setTarget(EntityReference.FRIENDLY_HERO);
		millhouseManastorm.setBattlecry(Battlecry.createBattlecry(castSpellsForFree));
		return millhouseManastorm;
	}
}
