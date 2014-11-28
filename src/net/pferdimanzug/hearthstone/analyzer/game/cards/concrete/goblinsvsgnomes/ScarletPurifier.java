package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class ScarletPurifier extends MinionCard {

	public ScarletPurifier() {
		super("Scarlet Purifier", 4, 3, Rarity.RARE, HeroClass.PALADIN, 3);
		setDescription("Battlecry: Deal 2 damage to all minions with Deathrattle.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public Minion summon() {
		Minion scarletPurifier = createMinion();
		SpellDesc damage = DamageSpell.create(2);
		damage.setTarget(EntityReference.ALL_MINIONS);
		damage.setTargetFilter(entity -> entity.hasTag(GameTag.DEATHRATTLES));
		Battlecry battlecry = Battlecry.createBattlecry(damage);
		scarletPurifier.setBattlecry(battlecry);
		return scarletPurifier;
	}

}
