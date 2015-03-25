package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveSecretsSpell;
import net.demilich.metastone.game.spells.RemoveAttributeSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Flare extends SpellCard {

	public Flare() {
		super("Flare", Rarity.RARE, HeroClass.HUNTER, 2);
		setDescription("All minions lose Stealth. Destroy all enemy Secrets. Draw a card.");
		SpellDesc drawCardSpell = DrawCardSpell.create();
		SpellDesc removeStealthSpell = RemoveAttributeSpell.create(EntityReference.ALL_MINIONS, GameTag.STEALTHED);
		SpellDesc removeSecretsSpell = RemoveSecretsSpell.create(TargetPlayer.OPPONENT);
		setSpell(MetaSpell.create(EntityReference.NONE, drawCardSpell, removeStealthSpell, removeSecretsSpell, false));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 33;
	}
}
