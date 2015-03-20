package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.DestroyWeaponSpell;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.IValueProvider;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

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
		SpellDesc drawCardSpell = DrawCardSpell.create(valueProvider, TargetPlayer.SELF);
		SpellDesc destroyWeaponSpell = DestroyWeaponSpell.create(EntityReference.ENEMY_HERO);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(drawCardSpell, destroyWeaponSpell));
		harrisonJones.setBattlecry(battlecry);
		return harrisonJones;
	}
}
