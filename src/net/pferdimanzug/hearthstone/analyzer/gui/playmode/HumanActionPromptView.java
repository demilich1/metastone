package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.util.ArrayList;
import java.util.Collection;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HumanActionPromptView extends Stage {
	
	private static String getActionString(GameContext context, GameAction action) {
		PlayCardAction playCardAction = null;
		String actionString = "";
		switch (action.getActionType()) {
		case HERO_POWER:
			HeroPowerAction heroPowerAction = (HeroPowerAction) action;
			actionString = "HERO POWER: " + heroPowerAction.getHeroPower().getName();
			break;
		case BATTLECRY:
			break;
		case PHYSICAL_ATTACK:
			PhysicalAttackAction physicalAttackAction = (PhysicalAttackAction) action;
			Entity attacker = context.resolveSingleTarget(0, physicalAttackAction.getAttackerReference());
			actionString = "ATTACK with " + attacker.getName();
			break;
		case SPELL:
			playCardAction = (PlayCardAction) action;
			actionString = "CAST SPELL: " + playCardAction.getCard().getName();
			break;
		case SUMMON:
			playCardAction = (PlayCardAction) action;
			actionString = "SUMMON: " + playCardAction.getCard().getName();
			break;
		case EQUIP_WEAPON:
			playCardAction = (PlayCardAction) action;
			actionString = "WEAPON: " + playCardAction.getCard().getName();
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
		TilePane root = new TilePane(Orientation.HORIZONTAL);
		root.setPrefRows(options.getValidActions().size() + 2);
		root.setPrefColumns(1);
		root.setVgap(2);
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
	
	private Node createActionButton(final HumanActionOptions options, final GameAction action) {
		Button button = new Button(getActionString(options.getContext(), action));
		button.setMaxWidth(Double.MAX_VALUE);
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				/*
				if (action != null && action.getValidTargets() != null && !action.getValidTargets().isEmpty()) {
					ApplicationFacade.getInstance().sendNotification(GameNotification.HUMAN_PROMPT_FOR_TARGET, new HumanTargetOptions(options.getBehaviour(), action));
					action.setTarget(options.getBehaviour().getSelectedTarget());
				}
				*/
				options.getBehaviour().setSelectedAction(action);
				close();
			}
		
		});
		return button;
	}
	
	private Collection<Node> createActionButtons(HumanActionOptions options) {
		Collection<Node> buttons = new ArrayList<Node>();
		for (GameAction action : options.getValidActions()) {
			buttons.add(createActionButton(options, action));
		}
		return buttons;
	}

}
