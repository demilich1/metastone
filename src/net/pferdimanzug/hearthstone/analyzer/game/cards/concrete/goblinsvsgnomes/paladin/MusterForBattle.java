package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin.LightsJustice;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.paladin.SilverHandRecruit;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.EquipWeaponSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MusterForBattle extends SpellCard {

	public MusterForBattle() {
		super("Muster For Battle", Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Summon three 1/1 Silver Hand Recruits. Equip a 1/4 Weapon.");
		
		SpellDesc summonSpell = SummonSpell.create(new SilverHandRecruit(), new SilverHandRecruit(), new SilverHandRecruit());
		SpellDesc equipWeaponSpell = EquipWeaponSpell.create(new LightsJustice());
		setSpell(MetaSpell.create(summonSpell, equipWeaponSpell));
		setPredefinedTarget(EntityReference.NONE);
	}



	@Override
	public int getTypeId() {
		return 554;
	}
}
