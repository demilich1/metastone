package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SecretCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.hunter.Snake;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionAttacksTrigger;

public class SnakeTrap extends SecretCard {

	public SnakeTrap() {
		super("Snake Trap", Rarity.EPIC, HeroClass.HUNTER, 2);
		setDescription("Secret: When one of your minions is attacked, summon three 1/1 Snakes.");
		
		SpellDesc summonSpell = SummonSpell.create(new Snake(), new Snake(), new Snake());
		setTriggerAndEffect(new MinionAttacksTrigger(), summonSpell);
	}
	
	@Override
	public int getTypeId() {
		return 44;
	}
}
