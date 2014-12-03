package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.Lightwarden;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ConditionalEffectSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.HealingSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LightOfTheNaaru extends SpellCard {

	public LightOfTheNaaru() {
		super("Light of the Naaru", Rarity.RARE, HeroClass.PRIEST, 1);
		setDescription("Restore 3 Health. If the target is still damaged, summon a Lightwarden.");
		SpellDesc heal = HealingSpell.create(3);
		SpellDesc summonLightwarden = SummonSpell.create(new Lightwarden());
		SpellDesc lightOfTheNaaru = ConditionalEffectSpell.create(heal, summonLightwarden, (context, player, target) -> {
			Actor actor = (Actor) target;
			return actor.isWounded();
		});
		setSpell(lightOfTheNaaru);
		setTargetRequirement(TargetSelection.ANY);
	}
	

}
