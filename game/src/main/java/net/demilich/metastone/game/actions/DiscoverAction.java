package net.demilich.metastone.game.actions;

import java.util.function.Predicate;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.condition.Condition;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DiscoverAction extends GameAction {
	private int groupIndex;

	public static DiscoverAction createDiscover(SpellDesc spell) {
		DiscoverAction discover = new DiscoverAction(spell);
		discover.setTargetRequirement(TargetSelection.NONE);
		return discover;
	}

	private final SpellDesc spell;
	private Condition condition;
	private Card card;
	private String name = "";
	private String description = "";

	protected DiscoverAction(SpellDesc spell) {
		this.spell = spell;
		setActionType(ActionType.DISCOVER);
	}

	public boolean canBeExecuted(GameContext context, Player player) {
		if (getCondition() == null) {
			return true;
		}
		return getCondition().isFulfilled(context, player, null, null);
	}

	@Override
	public final boolean canBeExecutedOn(GameContext context, Player player, Entity entity) {
		if (!super.canBeExecutedOn(context, player, entity)) {
			return false;
		}
		if (getSource().getId() == entity.getId()) {
			return false;
		}
		if (getEntityFilter() == null) {
			return true;
		}
		return getEntityFilter().matches(context, player, entity);
	}

	@Override
	public DiscoverAction clone() {
		DiscoverAction clone = DiscoverAction.createDiscover(getSpell().clone());
		clone.setActionSuffix(getActionSuffix());
		clone.setSource(getSource());
		return clone;
	}

	@Override
	public void execute(GameContext context, int playerId) {
		EntityReference target = getSpell().hasPredefinedTarget() ? getSpell().getTarget() : getTargetKey();
		context.getLogic().castSpell(playerId, getSpell(), getSource(), target, false);
	}

	public Card getCard() {
		return card;
	}

	private Condition getCondition() {
		return condition;
	}

	public String getDescription() {
		return description;
	}

	public EntityFilter getEntityFilter() {
		return spell.getEntityFilter();
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getPromptText() {
		return "[Discover]";
	}

	public SpellDesc getSpell() {
		return spell;
	}

	@Override
	public boolean isSameActionGroup(GameAction anotherAction) {
		return false;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEntityFilter(Predicate<Entity> entityFilter) {
		// this.entityFilter = entityFilter;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("[%s '%s' %s]", getActionType(), getSpell().getSpellClass().getSimpleName(), "Test");
	}
}
