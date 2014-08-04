package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class EitherOrSpell extends Spell {

	private final Spell either;
	private final Spell or;
	private final ISpellConditionChecker condition;

	public EitherOrSpell(Spell either, Spell or, ISpellConditionChecker condition) {
		this.either = either;
		this.or = or;
		this.condition = condition;
	}

	@Override
	public Spell clone() {
		EitherOrSpell clone = new EitherOrSpell(either.clone(), or.clone(), condition);
		clone.setSource(getSource());
		clone.setTarget(getTarget());
		clone.setSourceEntity(getSourceEntity());
		return clone;
	}
	
	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		Spell spellToCast = condition.isFulfilled(context, player, target) ? either : or;

		if (!spellToCast.hasPredefinedTarget()) {
			spellToCast.setTarget(getTarget());
		}
		context.getLogic().castSpell(player.getId(), spellToCast);
	}

	@Override
	public void setSource(SpellSource source) {
		super.setSource(source);
		either.setSource(source);
		or.setSource(source);
	}

	@Override
	public void setSourceEntity(EntityReference sourceEntity) {
		super.setSourceEntity(sourceEntity);
		either.setSourceEntity(sourceEntity);
		or.setSourceEntity(sourceEntity);
	}

}
