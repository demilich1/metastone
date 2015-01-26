package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.TemporaryAttackSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class AbusiveSergeant extends MinionCard {
	
	public static final int ATTACK_BONUS = 2;

	public AbusiveSergeant() {
		super("Abusive Sergeant", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Battlecry: Give a minion +2 Attack this turn.");
	}
	
	@Override
	public int getTypeId() {
		return 77;
	}
	
	@Override
	public Minion summon() {
		Minion abusiveSergeant = createMinion();
		SpellDesc battlecrySpell = TemporaryAttackSpell.create(+ATTACK_BONUS);
		Battlecry battlecryAbusive = Battlecry.createBattlecry(battlecrySpell, TargetSelection.MINIONS);
		abusiveSergeant.setBattlecry(battlecryAbusive);
		return abusiveSergeant;
	}
}
