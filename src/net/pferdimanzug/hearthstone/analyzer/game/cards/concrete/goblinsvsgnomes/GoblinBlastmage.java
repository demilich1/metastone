package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageRandomSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SpellUtils;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class GoblinBlastmage extends MinionCard {

	public GoblinBlastmage() {
		super("Goblin Blastmage", 5, 4, Rarity.RARE, HeroClass.MAGE, 4);
		setDescription("Battlecry: If you control a Mech, deal 4 damage randomly split among enemy characters.");
		setTag(GameTag.BATTLECRY);
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
