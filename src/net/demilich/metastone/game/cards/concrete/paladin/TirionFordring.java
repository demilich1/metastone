package net.demilich.metastone.game.cards.concrete.paladin;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.weapons.Ashbringer;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.EquipWeaponSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;

public class TirionFordring extends MinionCard {

	public TirionFordring() {
		super("Tirion Fordring", 6, 6, Rarity.LEGENDARY, HeroClass.PALADIN, 8);
		setDescription("Divine Shield. Taunt. Deathrattle: Equip a 5/3 Ashbringer.");
	}

	@Override
	public int getTypeId() {
		return 257;
	}
	
	@Override
	public Minion summon() {
		Minion tirionFordring = createMinion(GameTag.TAUNT, GameTag.DIVINE_SHIELD);
		SpellDesc deathrattle = EquipWeaponSpell.create(new Ashbringer());
		tirionFordring.addDeathrattle(deathrattle);
		return tirionFordring;
	}
}
