package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DestroySpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class VoidTerror extends MinionCard {

	public VoidTerror() {
		super("Void Terror", 3, 3, Rarity.RARE, HeroClass.WARLOCK, 3);
		setDescription("Battlecry: Destroy the minions on either side of this minion and gain their Attack and Health.");
		setRace(Race.DEMON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 357;
	}

	@Override
	public Minion summon() {
		Minion voidTerror = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, this::provideAttackValue, this::provideHpValue);
		SpellDesc destroySpell = DestroySpell.create(EntityReference.ADJACENT_MINIONS);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(MetaSpell.create(buffSpell, destroySpell));
		battlecry.setResolvedLate(true);
		voidTerror.setBattlecry(battlecry);
		return voidTerror;
	}
	
	private int provideAttackValue(GameContext context, Player player, Entity target) {
		int attackBonus = 0;
		for (Entity adjacent : context.getAdjacentMinions(player, target.getReference())) {
			Minion minion = (Minion) adjacent;
			attackBonus += minion.getAttack();
		}
		return attackBonus;
	}
	
	private int provideHpValue(GameContext context, Player player, Entity target) {
		int hpBonus = 0;
		for (Entity adjacent : context.getAdjacentMinions(player, target.getReference())) {
			Minion minion = (Minion) adjacent;
			hpBonus += minion.getHp();
		}
		return hpBonus;
	}

	
}
