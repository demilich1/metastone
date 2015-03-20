package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;

public class LeperGnome extends MinionCard {

	public LeperGnome() {
		super("Leper Gnome", 2, 1, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Deathrattle: Deal 2 damage to the enemy hero.");
	}

	@Override
	public int getTypeId() {
		return 154;
	}

	@Override
	public Minion summon() {
		Minion leperGnome = createMinion();
		SpellDesc deathrattle = DamageSpell.create(EntityReference.ENEMY_HERO, 2);
		leperGnome.addDeathrattle(deathrattle);
		return leperGnome;
	}
}
