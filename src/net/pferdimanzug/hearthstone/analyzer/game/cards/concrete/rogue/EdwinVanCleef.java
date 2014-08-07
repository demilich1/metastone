package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

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
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EdwinVanCleef extends MinionCard {

	public EdwinVanCleef() {
		super("Edwin VanCleef", 2, 2, Rarity.LEGENDARY, HeroClass.ROGUE, 3);
		setDescription("Combo: Gain +2/+2 for each card played earlier this turn.");
	}

	@Override
	public int getTypeId() {
		return 293;
	}

	@Override
	public Minion summon() {
		Minion edwinVanCleef = createMinion();
		IValueProvider comboValueProvider = new IValueProvider() {
			
			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				
				return player.getHero().getTagValue(GameTag.COMBO) * 2;
			}
		};
		SpellDesc buffSpell = BuffSpell.create(comboValueProvider, comboValueProvider);
		buffSpell.setTarget(EntityReference.SELF);
		edwinVanCleef.setBattlecry(Battlecry.createBattlecry(buffSpell));
		return edwinVanCleef;
	}
}
