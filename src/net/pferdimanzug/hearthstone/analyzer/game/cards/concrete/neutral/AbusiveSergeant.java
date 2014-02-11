package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class AbusiveSergeant extends MinionCard {
	
	public static final int ATTACK_BONUS = 2;

	public AbusiveSergeant() {
		super("Abusive Sergeant", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Battlecry: Give a minion +2 Attack this turn.");
	}
	
	@Override
	public Minion summon() {
		Minion abusiveSergeant = createMinion();
		
		BuffSpell battlecrySpell = new BuffSpell(+ATTACK_BONUS);
		battlecrySpell.setTemporary(true);
		Battlecry battlecryAbusive = Battlecry.createBattlecry(battlecrySpell, TargetSelection.MINIONS);
		abusiveSergeant.setTag(GameTag.BATTLECRY, battlecryAbusive);
		return abusiveSergeant;
	}
	

}
