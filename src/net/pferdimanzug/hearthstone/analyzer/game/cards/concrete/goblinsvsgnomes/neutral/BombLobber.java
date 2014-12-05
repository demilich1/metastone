package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BombLobber extends MinionCard {

	public BombLobber() {
		super("Bomb Lobber", 3, 3, Rarity.RARE, HeroClass.ANY, 5);
		setDescription("Battlecry: Deal 4 damage to a random enemy minion.");
	}

	@Override
	public Minion summon() {
		Minion bombLobber = createMinion();
		SpellDesc damageRandom = DamageRandomSpell.create(4, 1);
		damageRandom.setTarget(EntityReference.ENEMY_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(damageRandom);
		bombLobber.setBattlecry(battlecry);
		return bombLobber;
	}

}
