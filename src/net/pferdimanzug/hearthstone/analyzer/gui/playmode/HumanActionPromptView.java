package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.util.ArrayList;
import java.util.Collection;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.ActionType;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.ActionGroup;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HumanActionPromptView extends Stage {

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
			Battlecry battlecry = (Battlecry) action;
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
		default:
			return "<unknown action " + action.getActionType() + ">";
		}

		if (action.getActionSuffix() != null) {
			actionString += " (" + action.getActionSuffix() + ")";
		}

		return actionString;
	}

	public HumanActionPromptView(Window parent, HumanActionOptions options) {
		super(StageStyle.UTILITY);
		initOwner(parent);

		Label headerLabel = new Label("Choose action:");
		headerLabel.setStyle("-fx-font-family: Arial;-fx-font-weight: bold; -fx-font-size: 16pt;");
		VBox root = new VBox();
		root.setPrefWidth(Region.USE_COMPUTED_SIZE);
		root.setSpacing(2);
		root.setPadding(new Insets(8));
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(headerLabel);

		Scene scene = new Scene(root);
		root.getChildren().addAll(createActionButtons(options));

		setScene(scene);
		setX(parent.getX() + parent.getWidth());
		setY(parent.getY());
		show();
	}

	private Node createActionButton(final ActionGroup actionGroup, HumanActionOptions options) {
		GameContext context = options.getContext();
		Button button = new Button(getActionString(context, actionGroup.getPrototype()));
		button.setPrefWidth(200);
		// only one action with no target selection or summon with no other minion on board
		if (actionGroup.getActionsInGroup().size() == 1 && (actionGroup.getPrototype().getTargetRequirement() == TargetSelection.NONE
				|| actionGroup.getPrototype().getActionType() == ActionType.SUMMON)) {
			button.setOnAction(event -> {
				options.getBehaviour().onActionSelected(actionGroup.getPrototype());
				close();
			});
			return button;
		}
		HumanTargetOptions humanTargetOptions = new HumanTargetOptions(options.getBehaviour(), context, options.getPlayer().getId(),
				actionGroup);
		button.setOnAction(event -> {
			ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_TARGET, humanTargetOptions);
			close();
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

}
