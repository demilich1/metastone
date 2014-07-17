package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReceiveCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellCastedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;

public class ArchmageAntonidas extends MinionCard {

	public ArchmageAntonidas() {
		super("Archmage Antonidas", 5, 7, Rarity.LEGENDARY, HeroClass.MAGE, 7);
		setDescription("Whenever you cast a spell, put a 'Fireball' spell into your hand.");
	}

	@Override
	public int getTypeId() {
		return 54;
	}



	@Override
	public Minion summon() {
		Minion archmageAntonidas = createMinion();
		Spell receiveFireballSpell = new ReceiveCardSpell(new Fireball());
		SpellTrigger trigger = new SpellTrigger(new SpellCastedTrigger(TargetPlayer.SELF), receiveFireballSpell);
		archmageAntonidas.setSpellTrigger(trigger);
		return archmageAntonidas;
	}
}
