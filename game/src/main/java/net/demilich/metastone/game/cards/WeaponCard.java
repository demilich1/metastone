package net.demilich.metastone.game.cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayWeaponCardAction;
import net.demilich.metastone.game.cards.desc.WeaponCardDesc;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;
import net.demilich.metastone.game.spells.desc.trigger.TriggerDesc;

public class WeaponCard extends Card {

	private static final Set<Attribute> ignoredAttributes = new HashSet<Attribute>(
			Arrays.asList(new Attribute[] { Attribute.PASSIVE_TRIGGER, Attribute.DECK_TRIGGER, Attribute.MANA_COST_MODIFIER, Attribute.BASE_ATTACK,
					Attribute.BASE_HP, Attribute.SECRET, Attribute.QUEST, Attribute.CHOOSE_ONE, Attribute.BATTLECRY, Attribute.COMBO }));
	
	private final WeaponCardDesc desc;

	public WeaponCard(WeaponCardDesc desc) {
		super(desc);
		setAttribute(Attribute.BASE_ATTACK, desc.damage);
		setAttribute(Attribute.ATTACK, desc.damage);
		setAttribute(Attribute.BASE_HP, desc.durability);
		setAttribute(Attribute.HP, desc.durability);
		setAttribute(Attribute.MAX_HP, desc.durability);
		this.desc = desc;
	}

	protected Weapon createWeapon(Attribute... tags) {
		Weapon weapon = new Weapon(this);
		// assign battlecry if there is one specified
		for (Attribute gameTag : getAttributes().keySet()) {
			if (!ignoredAttributes.contains(gameTag)) {
				weapon.setAttribute(gameTag, getAttribute(gameTag));
			}
		}
		weapon.setAttack(getDamage());
		weapon.setBaseAttack(getBaseDamage());
		weapon.setMaxHp(getDurability());
		weapon.setHp(weapon.getMaxDurability());
		weapon.setBaseHp(getBaseDurability());
		BattlecryDesc battlecry = desc.battlecry;
		if (battlecry != null) {
			BattlecryAction battlecryAction = BattlecryAction.createBattlecry(battlecry.spell, battlecry.getTargetSelection());
			if (battlecry.condition != null) {
				battlecryAction.setCondition(battlecry.condition.create());
			}
	
			weapon.setBattlecry(battlecryAction);
		}
	
		if (desc.deathrattle != null) {
			weapon.removeAttribute(Attribute.DEATHRATTLES);
			weapon.addDeathrattle(desc.deathrattle);
		}
		if (desc.trigger != null) {
			weapon.addSpellTrigger(desc.trigger.create());
		}
		if (desc.triggers != null) {
			for (TriggerDesc trigger : desc.triggers) {
				weapon.addSpellTrigger(trigger.create());
			}
		}
		if (desc.cardCostModifier != null) {
			weapon.setCardCostModifier(desc.cardCostModifier.create());
		}
		weapon.setOnEquip(desc.onEquip);
		weapon.setOnUnequip(desc.onUnequip);
		return weapon;
	}

	public Weapon getWeapon() {
		return createWeapon();
	}

	@Override
	public PlayCardAction play() {
		return new PlayWeaponCardAction(getCardReference());
	}

	public int getDamage() {
		return getAttributeValue(Attribute.ATTACK);
	}
	
	public int getBonusDamage() {
		return getAttributeValue(Attribute.ATTACK_BONUS);
	}

	public int getDurability() {
		return getAttributeValue(Attribute.HP);
	}
	
	public int getBonusDurability() {
		return getAttributeValue(Attribute.HP_BONUS);
	}

	public int getBaseDamage() {
		return getAttributeValue(Attribute.BASE_ATTACK);
	}

	public int getBaseDurability() {
		return getAttributeValue(Attribute.BASE_HP);
	}

}
