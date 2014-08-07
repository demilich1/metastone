package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

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

public class Loatheb extends MinionCard {

	public Loatheb() {
		super("Loatheb", 5, 5, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Enemy spells cost (5) more next turn.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 407;
	}

	@Override
	public Minion summon() {
		Minion loatheb = createMinion();
		OneTurnCostModifier costModifier = new OneTurnCostModifier(CardType.SPELL, 5, false);
		costModifier.setTargetPlayer(TargetPlayer.OPPONENT);
		SpellDesc increaseSpellCost = AddCostModifierSpell.create(costModifier);
		increaseSpellCost.setTarget(EntityReference.ENEMY_HERO);
		loatheb.setBattlecry(Battlecry.createBattlecry(increaseSpellCost));
		return loatheb;
	}
}
