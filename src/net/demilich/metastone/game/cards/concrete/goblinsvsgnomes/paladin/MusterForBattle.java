package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.paladin;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.paladin.LightsJustice;
import net.demilich.metastone.game.cards.concrete.tokens.paladin.SilverHandRecruit;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.EquipWeaponSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MusterForBattle extends SpellCard {

	public MusterForBattle() {
		super("Muster for Battle", Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Summon three 1/1 Silver Hand Recruits. Equip a 1/4 Weapon.");
		
		SpellDesc summonSpell = SummonSpell.create(new SilverHandRecruit(), new SilverHandRecruit(), new SilverHandRecruit());
		SpellDesc equipWeaponSpell = EquipWeaponSpell.create(new LightsJustice());
		setSpell(MetaSpell.create(summonSpell, equipWeaponSpell));
		setPredefinedTarget(EntityReference.NONE);
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 554;
	}
}
