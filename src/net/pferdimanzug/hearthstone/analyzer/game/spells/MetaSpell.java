package net.pferdimanzug.hearthstone.analyzer.game.spells;

import java.util.Collection;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class MetaSpell extends Spell {

	public MetaSpell(Spell... spells) {
		setCloneableData(spells);
	}

	@Override
	public void cast(GameContext context, Player player, List<Entity> targets) {
		for (Spell spell : getSpells()) {
			if (!spell.hasPredefinedTarget()) {
				spell.setTarget(getTarget());
			}
			context.getLogic().castSpell(player.getId(), spell);
		}
	}

	@Override
	public MetaSpell clone() {
		return (MetaSpell) super.clone();
	}
	
	public Collection<Spell> getSpells() {
		return getCloneableDataCollection();
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
	}

	@Override
	public void setSource(SpellSource source) {
		super.setSource(source);
		for (Spell spell : getSpells()) {
			spell.setSource(source);
		}
	}

	@Override
	public void setSourceEntity(EntityReference sourceEntity) {
		super.setSourceEntity(sourceEntity);
		for (Spell spell : getSpells()) {
			spell.setSourceEntity(sourceEntity);
		}
	}

}
