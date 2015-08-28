package net.demilich.metastone.game.actions;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PhysicalAttackAction extends GameAction {

	private final EntityReference attackerReference;

	public PhysicalAttackAction(EntityReference attackerReference) {
		setTargetRequirement(TargetSelection.ENEMY_CHARACTERS);
		setActionType(ActionType.PHYSICAL_ATTACK);
		this.attackerReference = attackerReference;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		Actor defender = (Actor) context.resolveSingleTarget(getTargetKey());
		Actor attacker = (Actor) context.resolveSingleTarget(attackerReference);

		context.getLogic().fight(context.getPlayer(playerId), attacker, defender);
	}

	public EntityReference getAttackerReference() {
		return attackerReference;
	}

	@Override
	public String getPromptText() {
		return "[Physical Attack]";
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		if (anotherAction.getActionType() != getActionType()) {
			return false;
		}
		PhysicalAttackAction physicalAttackAction = (PhysicalAttackAction) anotherAction;

		return this.getAttackerReference().equals(physicalAttackAction.getAttackerReference());
	}

	@Override
	public String toString() {
		return String.format("%s Attacker: %s Defender: %s", getActionType(), attackerReference, getTargetKey());
	}

}
