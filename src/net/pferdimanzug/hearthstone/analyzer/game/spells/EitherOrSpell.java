package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EitherOrSpell extends Spell {

	private final ISpellConditionChecker condition;

	public EitherOrSpell(Spell either, Spell or, ISpellConditionChecker condition) {
		setCloneableData(either, or);
		this.condition = condition;
	}

	@Override
	public EitherOrSpell clone() {
		return (EitherOrSpell) super.clone();
	}
	
	public Spell getEither() {
		return (Spell) getCloneableData()[0];
	}

	public Spell getOr() {
		return (Spell) getCloneableData()[1];
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Spell spellToCast = condition.isFulfilled(context, player, target) ? getEither() : getOr();

		if (!spellToCast.hasPredefinedTarget()) {
			spellToCast.setTarget(getTarget());
		}
		context.getLogic().castSpell(player.getId(), spellToCast);
	}

	@Override
	public void setSource(SpellSource source) {
		super.setSource(source);
		getEither().setSource(source);
		getOr().setSource(source);
	}

	@Override
	public void setSourceEntity(EntityReference sourceEntity) {
		super.setSourceEntity(sourceEntity);
		getEither().setSourceEntity(sourceEntity);
		getOr().setSourceEntity(sourceEntity);
	}

}
