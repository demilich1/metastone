package net.pferdimanzug.hearthstone.analyzer.gui.battleofdecks;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;

public class BattleOfDecksResultView extends BorderPane {
	
	@FXML
	private FlowPane batchResultPane;
	
	@FXML
	private TableView<BattleDeckResult> rankingTable;
	
	@FXML
	private Button backButton;
	
	private final HashMap<BattleBatchResult, BattleBatchResultToken> tokenMap = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public BattleOfDecksResultView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BattleOfDecksResultView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		TableColumn<BattleDeckResult, String> nameColumn = new TableColumn<>("Deck name"); 
		TableColumn<BattleDeckResult, Double> winRateColumn = new TableColumn<>("Win rate");
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<BattleDeckResult, String>("deckName"));
		winRateColumn.setCellValueFactory(new PropertyValueFactory<BattleDeckResult, Double>(
		        "winRate"));
		winRateColumn.setCellFactory(ProgressBarTableCell.<BattleDeckResult> forTableColumn());
		
		rankingTable.getColumns().setAll(nameColumn, winRateColumn);
		
		backButton.setOnAction(event -> ApplicationFacade.getInstance().sendNotification(GameNotification.MAIN_MENU));
	}
	
	public void updateResults(BattleResult result) {
		batchResultPane.getChildren().clear();
		for (BattleBatchResult batchResult : result.getBatchResults()) {
			if (!tokenMap.containsKey(batchResult)) {
				tokenMap.put(batchResult, new BattleBatchResultToken());
			}
			BattleBatchResultToken batchResultToken = tokenMap.get(batchResult);
			batchResultToken.displayBatchResult(batchResult);
		}
		
		batchResultPane.getChildren().setAll(tokenMap.values());
		
		rankingTable.getItems().setAll(result.getDeckResults());
	}

}
