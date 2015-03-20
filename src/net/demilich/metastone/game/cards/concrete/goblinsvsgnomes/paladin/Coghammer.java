package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.WeaponCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.weapons.Weapon;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class Coghammer extends WeaponCard {

	public Coghammer() {
		super("Coghammer", Rarity.EPIC, HeroClass.PALADIN, 3);
		setDescription("Battlecry: Give a random friendly minion Divine Shield and Taunt.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 553;
	}

	@Override
	public Weapon getWeapon() {
		Weapon coghammer = createWeapon(2, 3);
		SpellDesc divineShield = ApplyTagSpell.create(GameTag.DIVINE_SHIELD);
		SpellDesc taunt = ApplyTagSpell.create(GameTag.TAUNT);
		SpellDesc spell = MetaSpell.create(EntityReference.FRIENDLY_MINIONS, divineShield, taunt, true);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(spell);
		coghammer.setBattlecry(battlecry);
		return coghammer;
	}
}
