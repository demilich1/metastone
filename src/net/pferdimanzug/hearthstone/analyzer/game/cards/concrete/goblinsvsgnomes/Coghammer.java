package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.WeaponCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.weapons.Weapon;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Coghammer extends WeaponCard {

	public Coghammer() {
		super("Coghammer", Rarity.EPIC, HeroClass.PALADIN, 3);
		setDescription("Battlecry: Give a random friendly minion Divine Shield and Taunt.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Weapon getWeapon() {
		Weapon coghammer = createWeapon(2, 3);
		SpellDesc divineShield = ApplyTagSpell.create(GameTag.DIVINE_SHIELD);
		SpellDesc taunt = ApplyTagSpell.create(GameTag.TAUNT);
		SpellDesc spell = MetaSpell.create(divineShield, taunt);
		spell.setTarget(EntityReference.FRIENDLY_MINIONS);
		spell.pickRandomTarget(true);
		Battlecry battlecry = Battlecry.createBattlecry(spell);
		coghammer.setBattlecry(battlecry);
		return coghammer;
	}

}
