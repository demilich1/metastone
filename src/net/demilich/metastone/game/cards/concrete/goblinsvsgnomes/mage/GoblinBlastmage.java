package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.mage;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DamageRandomSpell;
import net.demilich.metastone.game.spells.SpellUtils;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class GoblinBlastmage extends MinionCard {

	public GoblinBlastmage() {
		super("Goblin Blastmage", 5, 4, Rarity.RARE, HeroClass.MAGE, 4);
		setDescription("Battlecry: If you control a Mech, deal 4 damage randomly split among enemy characters.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 495;
	}



	@Override
	public Minion summon() {
		Minion goblinBlastmage = createMinion();
		SpellDesc spell = DamageRandomSpell.create(1, 4);
		spell.setTarget(EntityReference.ENEMY_CHARACTERS);
		Battlecry battlecry = Battlecry.createBattlecry(spell);
		battlecry.setCondition((context, player) -> SpellUtils.hasMinionOfRace(player, Race.MECH));
		goblinBlastmage.setBattlecry(battlecry);
		return goblinBlastmage;
	}
}
