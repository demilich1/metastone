package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.neutral.Lightwarden;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ConditionalEffectSpell;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

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
	
	@Override
	public int getTypeId() {
		return 560;
	}
}
