package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DestroyWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class HarrisonJones extends MinionCard {

	public HarrisonJones() {
		super("Harrison Jones", 5, 4, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Destroy your opponent's weapon and draw cards equal to its Durability.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 139;
	}

	@Override
	public Minion summon() {
		Minion harrisonJones = createMinion();
		IValueProvider valueProvider = new IValueProvider() {
			
			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				Player opponent = context.getOpponent(player);
				Weapon weapon = opponent.getHero().getWeapon();
				return weapon != null ? weapon.getDurability() : 0;
			}
		};
		Spell drawCardSpell = new DrawCardSpell(valueProvider, TargetPlayer.SELF);
		Spell destroyWeaponSpell = new DestroyWeaponSpell();
		destroyWeaponSpell.setTarget(EntityReference.ENEMY_HERO);
		Battlecry battlecry = Battlecry.createBattlecry(new MetaSpell(drawCardSpell, destroyWeaponSpell));
		harrisonJones.setBattlecry(battlecry);
		return harrisonJones;
	}
}
