package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class BombLobber extends MinionCard {

	public BombLobber() {
		super("Bomb Lobber", 3, 3, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Battlecry: Deal 4 damage to a random enemy minion.");
	}

	@Override
	public int getTypeId() {
		return 504;
	}

	@Override
	public Minion summon() {
		Minion bombLobber = createMinion();
		SpellDesc damageRandom = DamageSpell.create(EntityReference.ENEMY_MINIONS, 4, true);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(damageRandom);
//		battlecry.setCondition((context, player) -> {
//			Player opponent = context.getOpponent(player);
//			return context.getMinionCount(opponent) > 0;
//		});
		bombLobber.setBattlecry(battlecry);
		return bombLobber;
	}
}
