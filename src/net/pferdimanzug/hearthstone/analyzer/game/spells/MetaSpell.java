package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MetaSpell extends Spell {

	protected final Spell[] spells;

	public MetaSpell(Spell... spells) {
		this.spells = spells;
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		for (Spell spell : spells) {
			if (!spell.hasPredefinedTarget()) {
				spell.setTarget(getTarget());
			}
			context.getLogic().castSpell(player.getId(), spell);
		}
	}

	@Override
	public Spell clone() {
		Spell[] spellsClone = new Spell[spells.length];
		for (int i = 0; i < spellsClone.length; i++) {
			spellsClone[i] = spells[i].clone();
		}
		MetaSpell clone = new MetaSpell(spellsClone);
		clone.setSource(getSource());
		clone.setTarget(getTarget());
		clone.setSourceEntity(getSourceEntity());
		return clone;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
	}

	@Override
	public void setSource(SpellSource source) {
		super.setSource(source);
		for (Spell spell : spells) {
			spell.setSource(source);
		}
	}

	@Override
	public void setSourceEntity(EntityReference sourceEntity) {
		super.setSourceEntity(sourceEntity);
		for (Spell spell : spells) {
			spell.setSourceEntity(sourceEntity);
		}
	}

}
