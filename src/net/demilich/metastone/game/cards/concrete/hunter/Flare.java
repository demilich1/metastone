package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.RemoveSecretsSpell;
import net.demilich.metastone.game.spells.RemoveTagSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Flare extends SpellCard {

	public Flare() {
		super("Flare", Rarity.RARE, HeroClass.HUNTER, 2);
		setDescription("All minions lose Stealth. Destroy all enemy Secrets. Draw a card.");
		SpellDesc drawCardSpell = DrawCardSpell.create();
		SpellDesc removeStealthSpell = RemoveTagSpell.create(GameTag.STEALTHED);
		removeStealthSpell.setTarget(EntityReference.ALL_MINIONS);
		SpellDesc removeSecretsSpell = RemoveSecretsSpell.create(TargetPlayer.OPPONENT);
		setSpell(MetaSpell.create(drawCardSpell, removeStealthSpell, removeSecretsSpell));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 33;
	}
}
