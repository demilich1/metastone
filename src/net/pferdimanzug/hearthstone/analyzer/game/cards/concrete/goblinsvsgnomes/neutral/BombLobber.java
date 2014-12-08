package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

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
		SpellDesc damageRandom = DamageSpell.create(4);
		damageRandom.setTarget(EntityReference.ENEMY_MINIONS);
		damageRandom.pickRandomTarget(true);
		Battlecry battlecry = Battlecry.createBattlecry(damageRandom);
		battlecry.setCondition((context, player) -> {
			Player opponent = context.getOpponent(player);
			return context.getMinionCount(opponent) > 0;
		});
		bombLobber.setBattlecry(battlecry);
		return bombLobber;
	}
}
