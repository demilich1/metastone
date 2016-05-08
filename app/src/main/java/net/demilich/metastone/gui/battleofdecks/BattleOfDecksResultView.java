package net.demilich.metastone.gui.battleofdecks;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.NotificationProxy;

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
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/BattleOfDecksResultView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		TableColumn<BattleDeckResult, String> nameColumn = new TableColumn<>("Deck name");
		nameColumn.setPrefWidth(200);
		TableColumn<BattleDeckResult, Double> winRateColumn = new TableColumn<>("Win rate");
		winRateColumn.setPrefWidth(150);

		nameColumn.setCellValueFactory(new PropertyValueFactory<BattleDeckResult, String>("deckName"));
		winRateColumn.setCellValueFactory(new PropertyValueFactory<BattleDeckResult, Double>("winRate"));

		winRateColumn.setCellFactory(new Callback<TableColumn<BattleDeckResult, Double>, TableCell<BattleDeckResult, Double>>() {
			public TableCell<BattleDeckResult, Double> call(TableColumn<BattleDeckResult, Double> p) {
				TableCell<BattleDeckResult, Double> cell = new TableCell<BattleDeckResult, Double>() {
					private final Label label = new Label();
					private final ProgressBar progressBar = new ProgressBar();
					private final StackPane stackPane = new StackPane();

					{
						label.getStyleClass().setAll("progress-text");
						stackPane.setAlignment(Pos.CENTER);
						stackPane.getChildren().setAll(progressBar, label);
						setGraphic(stackPane);
					}

					@Override
					protected void updateItem(Double winrate, boolean empty) {
						super.updateItem(winrate, empty);
						if (winrate == null || empty) {
							setGraphic(null);
							return;
						}
						progressBar.setProgress(winrate);
						label.setText(String.format("%.2f", winrate * 100) + "%");
						setGraphic(stackPane);
					}

				};
				return cell;
			}
		});

		rankingTable.getColumns().setAll(nameColumn, winRateColumn);
		rankingTable.getColumns().get(1).setSortType(SortType.DESCENDING);

		backButton.setOnAction(event -> NotificationProxy.sendNotification(GameNotification.MAIN_MENU));
	}

	@SuppressWarnings("unchecked")
	public void updateResults(BattleResult result) {

		for (BattleBatchResult batchResult : result.getBatchResults()) {
			if (!tokenMap.containsKey(batchResult)) {
				BattleBatchResultToken token = new BattleBatchResultToken();
				tokenMap.put(batchResult, token);
				batchResultPane.getChildren().add(token);
			}
			BattleBatchResultToken batchResultToken = tokenMap.get(batchResult);
			batchResultToken.displayBatchResult(batchResult);
		}

		rankingTable.getItems().setAll(result.getDeckResults());
		rankingTable.getSortOrder().setAll(rankingTable.getColumns().get(1));
	}

}
