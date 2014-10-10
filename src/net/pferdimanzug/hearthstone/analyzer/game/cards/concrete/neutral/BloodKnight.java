package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.IValueProvider;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class BloodKnight extends MinionCard {

	public BloodKnight() {
		super("Blood Knight", 3, 3, Rarity.EPIC, HeroClass.ANY, 3);
		setDescription("Battlecry: All minions lose Divine Shield. Gain +3/+3 for each Shield lost.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 95;
	}
	
	@Override
	public Minion summon() {
		Minion bloodKnight = createMinion();
		IValueProvider bloodKnightValueProvider = new BloodKnightBuffValueProvider();
		SpellDesc buffSpell = BuffSpell.create(bloodKnightValueProvider, bloodKnightValueProvider);
		buffSpell.setTarget(EntityReference.SELF);
		SpellDesc removeDivineShields = RemoveTagSpell.create(GameTag.DIVINE_SHIELD);
		removeDivineShields.setTarget(EntityReference.ALL_MINIONS);
		Battlecry battlecry = Battlecry.createBattlecry(MetaSpell.create(buffSpell, removeDivineShields));
		bloodKnight.setBattlecry(battlecry);
		return bloodKnight;
	}

	private class BloodKnightBuffValueProvider implements IValueProvider {

		@Override
		public int provideValue(GameContext context, Player player, Entity target) {
			List<Entity> allMinions = context.resolveTarget(player, null, EntityReference.ALL_MINIONS);
			int minionsWithDivineShield = 0;
			for (Entity entity : allMinions) {
				if (entity.hasStatus(GameTag.DIVINE_SHIELD)) {
					minionsWithDivineShield++;
				}
			}
			return minionsWithDivineShield * 3;
		}
		
	}
}
