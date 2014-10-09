package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.weapons.Ashbringer;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

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
