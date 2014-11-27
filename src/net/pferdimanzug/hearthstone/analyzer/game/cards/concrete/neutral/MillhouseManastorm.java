package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.CardType;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.costmodifier.OneTurnCostModifier;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.AddCostModifierSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
