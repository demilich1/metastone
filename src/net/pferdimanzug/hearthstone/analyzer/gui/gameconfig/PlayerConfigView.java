package net.pferdimanzug.hearthstone.analyzer.gui.gameconfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.GreedyOptimizeMove;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.IBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.NoAggressionBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.PlayRandomBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.heuristic.WeightedHeuristic;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.threat.GameStateValueBehaviour;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.game.decks.DeckFactory;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Anduin;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Garrosh;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Guldan;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Jaina;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Malfurion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Rexxar;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Thrall;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Uther;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Valeera;
import net.pferdimanzug.hearthstone.analyzer.gui.IconFactory;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.config.PlayerConfigType;

public class PlayerConfigView extends VBox {

	@FXML
	protected Label heroNameLabel;

	@FXML
	protected ImageView heroIcon;

	@FXML
	protected ChoiceBox<IBehaviour> behaviourBox;

	@FXML
	protected ChoiceBox<Hero> heroBox;

	@FXML
	protected ChoiceBox<Deck> deckBox;

	@FXML
	protected CheckBox hideCardsCheckBox;

	private final PlayerConfig playerConfig = new PlayerConfig();

	private List<Deck> decks = new ArrayList<Deck>();

	private PlayerConfigType selectionHint;

	public PlayerConfigView(PlayerConfigType selectionHint) {
		this.selectionHint = selectionHint;
		FXMLLoader fxmlLoader = new FXMLLoader(PlayerConfigView.class.getResource("PlayerConfigView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		heroBox.setConverter(new HeroStringConverter());
		deckBox.setConverter(new DeckStringConverter());
		behaviourBox.setConverter(new BehaviourStringConverter());

		setupHideCardsBox(selectionHint);
		setupHeroes();
		setupBehaviours();
		deckBox.valueProperty().addListener((ChangeListener<Deck>) (observableProperty, oldDeck, newDeck) -> {
			getPlayerConfig().setDeck(newDeck);
		});
	}

	private void setupHideCardsBox(PlayerConfigType configType) {
		hideCardsCheckBox.selectedProperty().addListener(this::onHideCardBoxChanged);
		hideCardsCheckBox.setSelected(selectionHint == PlayerConfigType.OPPONENT);
		if (configType == PlayerConfigType.SIMULATION || configType == PlayerConfigType.SANDBOX) {
			hideCardsCheckBox.setVisible(false);
		}
	}

	private void filterDecks() {
		HeroClass heroClass = getPlayerConfig().getHero().getHeroClass();
		ObservableList<Deck> deckList = FXCollections.observableArrayList();

		Deck randomDeck = DeckFactory.getRandomDeck(heroClass);
		deckList.add(randomDeck);
		for (Deck deck : decks) {
			if (deck.getHeroClass() == heroClass || deck.getHeroClass() == HeroClass.ANY) {
				deckList.add(deck);
			}
		}
		deckBox.setItems(deckList);
		deckBox.getSelectionModel().selectFirst();
	}

	private void onHideCardBoxChanged(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
		playerConfig.setHideCards(newValue);
	}

	public PlayerConfig getPlayerConfig() {
		return playerConfig;
	}

	public void injectDecks(List<Deck> decks) {
		this.decks = decks;
		heroBox.getSelectionModel().selectFirst();
		behaviourBox.getSelectionModel().selectFirst();
	}

	private void selectHero(Hero hero) {
		Image heroPortrait = new Image(IconFactory.getHeroIconUrl(hero));
		heroIcon.setImage(heroPortrait);
		heroNameLabel.setText(hero.getName());
		getPlayerConfig().setHero(hero);
		filterDecks();
	}

	public void setupBehaviours() {
		ObservableList<IBehaviour> behaviourList = FXCollections.observableArrayList();
		if (selectionHint == PlayerConfigType.HUMAN || selectionHint == PlayerConfigType.SANDBOX) {
			behaviourList.add(new HumanBehaviour());
		}

		behaviourList.add(new PlayRandomBehaviour());
		if (selectionHint == PlayerConfigType.OPPONENT) {
			behaviourList.add(new HumanBehaviour());
		}

		behaviourList.add(new GameStateValueBehaviour());
		behaviourList.add(new GreedyOptimizeMove(new WeightedHeuristic()));
		behaviourList.add(new NoAggressionBehaviour());

		behaviourBox.setItems(behaviourList);
		behaviourBox.valueProperty().addListener(this::onBehaviourChanged);
	}

	private void onBehaviourChanged(ObservableValue<? extends IBehaviour> ov, IBehaviour oldBehaviour, IBehaviour newBehaviour) {
		getPlayerConfig().setBehaviour(newBehaviour);
		hideCardsCheckBox.setDisable(newBehaviour instanceof HumanBehaviour);
	}

	public void setupHeroes() {
		ObservableList<Hero> heroList = FXCollections.observableArrayList();

		heroList.add(new Garrosh());
		heroList.add(new Jaina());
		heroList.add(new Valeera());
		heroList.add(new Guldan());
		heroList.add(new Rexxar());
		heroList.add(new Thrall());
		heroList.add(new Anduin());
		heroList.add(new Uther());
		heroList.add(new Malfurion());

		heroBox.setItems(heroList);
		heroBox.valueProperty().addListener((ChangeListener<Hero>) (observableValue, oldHero, newHero) -> {
			selectHero(newHero);
		});
	}

	private class BehaviourStringConverter extends StringConverter<IBehaviour> {

		@Override
		public IBehaviour fromString(String string) {
			return null;
		}

		@Override
		public String toString(IBehaviour behaviour) {
			return behaviour.getName();
		}

	}

	private class DeckStringConverter extends StringConverter<Deck> {

		@Override
		public Deck fromString(String arg0) {
			return null;
		}

		@Override
		public String toString(Deck deck) {
			return deck.getName();
		}

	}

	private class HeroStringConverter extends StringConverter<Hero> {

		@Override
		public Hero fromString(String arg0) {
			return null;
		}

		@Override
		public String toString(Hero hero) {
			return hero.getHeroClass().toString();
		}

	}

}
