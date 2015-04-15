package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProvider;
import net.demilich.metastone.game.targeting.EntityReference;

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
		ValueProvider comboValueProvider = new ValueProvider(null) {
			
			@Override
			public int provideValue(GameContext context, Player player, Entity target) {
				
				return player.getHero().getTagValue(GameTag.COMBO) * 2;
			}
		};
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, comboValueProvider, comboValueProvider);
		edwinVanCleef.setBattlecry(BattlecryAction.createBattlecry(buffSpell));
		return edwinVanCleef;
	}
}
