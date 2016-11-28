package net.demilich.metastone.gui.playmode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.ActionType;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.DiscoverAction;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.HeroPowerAction;
import net.demilich.metastone.game.actions.PhysicalAttackAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.behaviour.human.ActionGroup;
import net.demilich.metastone.game.behaviour.human.HumanActionOptions;
import net.demilich.metastone.game.behaviour.human.HumanTargetOptions;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.targeting.TargetSelection;
import net.demilich.metastone.gui.cards.CardTooltip;

public class HumanActionPromptView extends VBox {

	private static String getActionString(GameContext context, GameAction action) {
		PlayCardAction playCardAction = null;
		Card card = null;
		String actionString = "";
		switch (action.getActionType()) {
		case HERO_POWER:
			HeroPowerAction heroPowerAction = (HeroPowerAction) action;
			card = context.resolveCardReference(heroPowerAction.getCardReference());
			actionString = "HERO POWER: " + card.getName();
			break;
		case BATTLECRY:
			BattlecryAction battlecry = (BattlecryAction) action;
			actionString = "BATTLECRY " + battlecry.getSpell().getSpellClass().getSimpleName();
			break;
		case PHYSICAL_ATTACK:
			PhysicalAttackAction physicalAttackAction = (PhysicalAttackAction) action;
			Entity attacker = context.resolveSingleTarget(physicalAttackAction.getAttackerReference());
			actionString = "ATTACK with " + attacker.getName();
			break;
		case SPELL:
			playCardAction = (PlayCardAction) action;
			card = context.resolveCardReference(playCardAction.getCardReference());
			actionString = "CAST SPELL: " + card.getName();
			break;
		case SUMMON:
			playCardAction = (PlayCardAction) action;
			card = context.resolveCardReference(playCardAction.getCardReference());
			actionString = "SUMMON: " + card.getName();
			break;
		case EQUIP_WEAPON:
			playCardAction = (PlayCardAction) action;
			card = context.resolveCardReference(playCardAction.getCardReference());
			actionString = "WEAPON: " + card.getName();
			break;
		case END_TURN:
			actionString = "END TURN";
			break;
		case DISCOVER:
			DiscoverAction discover = (DiscoverAction) action;
			actionString = "DISCOVER " + discover.getSpell().getSpellClass().getSimpleName();
			break;
		default:
			return "<unknown action " + action.getActionType() + ">";
		}

		if (action.getActionSuffix() != null) {
			actionString += " (" + action.getActionSuffix() + ")";
		}

		return actionString;
	}

	private final List<Node> existingButtons = new ArrayList<Node>();

	public HumanActionPromptView() {
		Label headerLabel = new Label("Choose action:");
		headerLabel.setStyle("-fx-font-family: Arial;-fx-font-weight: bold; -fx-font-size: 16pt;");

		setPrefWidth(Region.USE_COMPUTED_SIZE);
		setSpacing(2);
		setPadding(new Insets(8));
		setAlignment(Pos.CENTER);
		getChildren().add(headerLabel);
	}

	private Node createActionButton(final ActionGroup actionGroup, HumanActionOptions options) {
		GameContext context = options.getContext();
		Button button = new Button(getActionString(context, actionGroup.getPrototype()));
		button.setStyle("-fx-font-size: 12px; -fx-padding: 4 8 4 8;");
		button.setWrapText(true);
		button.setPrefWidth(200);
		button.setTextAlignment(TextAlignment.CENTER);
		switch (actionGroup.getPrototype().getActionType()) {
		case BATTLECRY:
			break;
		case DISCOVER:
			CardTooltip tooltipContent = new CardTooltip();
			DiscoverAction discover = (DiscoverAction) actionGroup.getPrototype();
			Card card = discover.getCard();
			if (card != null) {
				tooltipContent.setCard(card);
				Tooltip tooltip = new Tooltip();
				tooltip.setGraphic(tooltipContent);
				Tooltip.install(button, tooltip);
			} else {
				tooltipContent.setNonCard(discover.getName(), discover.getDescription());
				Tooltip tooltip = new Tooltip();
				tooltip.setGraphic(tooltipContent);
				Tooltip.install(button, tooltip);
			}
			break;
		case END_TURN:
			break;
		case EQUIP_WEAPON:
			break;
		case HERO_POWER:
			break;
		case PHYSICAL_ATTACK:
			break;
		case SPELL:
			break;
		case SUMMON:
			break;
		case SYSTEM:
			break;
		default:
			break;
		
		}
		// only one action with no target selection or summon with no other
		// minion on board
		if (actionGroup.getActionsInGroup().size() == 1 && (actionGroup.getPrototype().getTargetRequirement() == TargetSelection.NONE
				|| actionGroup.getPrototype().getActionType() == ActionType.SUMMON)) {
			button.setOnAction(event -> {
				options.getBehaviour().onActionSelected(actionGroup.getPrototype());
				setVisible(false);

			});
			return button;
		}
		HumanTargetOptions humanTargetOptions = new HumanTargetOptions(options.getBehaviour(), context, options.getPlayer().getId(),
				actionGroup);
		button.setOnAction(event -> {
			NotificationProxy.sendNotification(GameNotification.HUMAN_PROMPT_FOR_TARGET, humanTargetOptions);
			setVisible(false);
		});

		return button;
	}

	private Collection<Node> createActionButtons(HumanActionOptions options) {
		Collection<Node> buttons = new ArrayList<Node>();
		Collection<ActionGroup> actionGrups = groupActions(options);
		for (ActionGroup actionGroup : actionGrups) {
			buttons.add(createActionButton(actionGroup, options));
		}
		return buttons;
	}

	private Collection<ActionGroup> groupActions(HumanActionOptions options) {
		Collection<ActionGroup> actionGroups = new ArrayList<>();
		for (GameAction action : options.getValidActions()) {
			if (!matchesExistingGroup(action, actionGroups)) {
				ActionGroup newActionGroup = new ActionGroup(action);
				actionGroups.add(newActionGroup);
			}
		}
		return actionGroups;
	}

	private boolean matchesExistingGroup(GameAction action, Collection<ActionGroup> existingActionGroups) {
		for (ActionGroup actionGroup : existingActionGroups) {
			if (actionGroup.getPrototype().isSameActionGroup(action)) {
				actionGroup.add(action);
				return true;
			}
		}
		return false;
	}

	public void setActions(HumanActionOptions options) {
		getChildren().removeAll(existingButtons);
		existingButtons.clear();

		Collection<Node> buttons = createActionButtons(options);
		existingButtons.addAll(buttons);
		getChildren().addAll(buttons);
		setVisible(true);
	}

}
