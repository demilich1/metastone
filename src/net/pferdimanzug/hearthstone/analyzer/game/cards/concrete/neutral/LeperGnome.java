package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class LeperGnome extends MinionCard {

	public LeperGnome() {
		super("Leper Gnome", Rarity.COMMON, HeroClass.ANY, 1);
	}

	@Override
	public Minion summon() {
		Spell deathrattle = new DamageSpell(2);
		deathrattle.setTarget(EntityReference.ENEMY_HERO);
		Minion leperGnome = createMinion(2, 1);
		leperGnome.setTag(GameTag.DEATHRATTLE, deathrattle);
		return leperGnome;
	}

}
