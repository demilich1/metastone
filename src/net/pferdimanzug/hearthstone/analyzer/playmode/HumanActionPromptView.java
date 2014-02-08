package net.pferdimanzug.hearthstone.analyzer.playmode;

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
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.HeroPowerAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PhysicalAttackAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class HumanActionPromptView extends Stage {
	
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
	
	
	private Collection<Node> createActionButtons(HumanActionOptions options) {
		Collection<Node> buttons = new ArrayList<Node>();
		for (GameAction action : options.getValidActions()) {
			buttons.add(createActionButton(options, action));
		}
		// add button for action null, which is 'end turn'
		buttons.add(createActionButton(options, null));
		return buttons;
	}
	
	private Node createActionButton(final HumanActionOptions options, final GameAction action) {
		Button button = new Button(action != null ? getActionString(options.getContext(), action) : "END TURN");
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
	
	private static String getActionString(GameContext context, GameAction action) {
		PlayCardAction playCardAction = null;
		switch (action.getActionType()) {
		case HERO_POWER:
			HeroPowerAction heroPowerAction = (HeroPowerAction) action;
			return "HERO POWER: " + heroPowerAction.getHeroPower().getName();
		case MINION_ABILITY:
			break;
		case PHYSICAL_ATTACK:
			PhysicalAttackAction physicalAttackAction = (PhysicalAttackAction) action;
			Entity attacker = context.resolveSingleTarget(0, physicalAttackAction.getAttackerReference());
			return "ATTACK with " + attacker.getName();
		case SPELL:
			playCardAction = (PlayCardAction) action;
			return "CAST SPELL: " + playCardAction.getCard().getName();
		case SUMMON:
			playCardAction = (PlayCardAction) action;
			return "SUMMON: " + playCardAction.getCard().getName();
		case UNDEFINED:
			break;
		default:
			break;
		}
		return "<unknown action " + action.getActionType() + ">";
	}

}
