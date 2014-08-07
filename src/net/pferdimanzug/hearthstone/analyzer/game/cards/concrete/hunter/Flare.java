package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.hunter;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveSecretsSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.RemoveTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Flare extends SpellCard {

	public Flare() {
		super("Flare", Rarity.RARE, HeroClass.HUNTER, 1);
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
