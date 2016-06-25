package net.demilich.metastone;

public enum GameNotification {
	APPLICATION_STARTUP,
	CANVAS_CREATED,
	SHOW_VIEW,
	CHECK_FOR_UPDATE,
	NEW_VERSION_AVAILABLE,

	START_GAME,
	PLAY_GAME,
	GAME_STATE_UPDATE,
	GAME_STATE_LATE_UPDATE,
	GAME_ACTION_PERFORMED,
	GAME_OVER,

	SIMULATE_GAMES,
	SIMULATION_PROGRESS_UPDATE,
	SIMULATION_RESULT,

	HUMAN_PROMPT_FOR_MULLIGAN,
	HUMAN_PROMPT_FOR_ACTION,
	HUMAN_PROMPT_FOR_TARGET,

	SHOW_MODAL_DIALOG,
	SHOW_USER_DIALOG,

	MAIN_MENU,
	PLAY_MODE_SELECTED,
	DECK_BUILDER_SELECTED,
	SIMULATION_MODE_SELECTED,
	SANDBOX_MODE_SELECTED,
	TRAINING_MODE_SELECTED,
	BATTLE_OF_DECKS_SELECTED,
	ANALYTICS_DISCLAIMER_SELECTED,

	TRAINING_PROGRESS_UPDATE,
	START_TRAINING,

	BATTLE_OF_DECKS_PROGRESS_UPDATE,

	REQUEST_DECKS,
	REQUEST_DECK_FORMATS,
	REPLY_DECKS,
	REPLY_DECK_FORMATS,
	COMMIT_PLAYMODE_CONFIG,
	COMMIT_SIMULATIONMODE_CONFIG,
	COMMIT_TRAININGMODE_CONFIG,
	COMMIT_SANDBOXMODE_CONFIG,
	COMMIT_BATTLE_OF_DECKS_CONFIG,

	LOAD_DECKS,
	LOAD_DECK_FORMATS,
	DECKS_LOADED,
	DECK_FORMATS_LOADED,
	CREATE_NEW_DECK,
	SET_ACTIVE_DECK,
	EDIT_DECK,
	CHANGE_DECK_NAME,
	ACTIVE_DECK_CHANGED,
	FILTERED_CARDS,
	FILTER_CARDS,
	ADD_CARD_TO_DECK,
	REMOVE_CARD_FROM_DECK,
	FILL_DECK_WITH_RANDOM_CARDS,
	SAVE_ACTIVE_DECK,
	IMPORT_DECK_FROM_URL,
	INVALID_DECK_NAME,
	DUPLICATE_DECK_NAME,
	ADD_DECK_TO_META_DECK,
	REMOVE_DECK_FROM_META_DECK,
	DELETE_DECK,

	// sandbox notifications
	SELECT_PLAYER,
	SELECT_TARGET,
	MODIFY_PLAYER_HAND,
	MODIFY_PLAYER_DECK,
	SPAWN_MINION,
	PERFORM_ACTION,
	UPDATE_SANDBOX_STATE,
	CREATE_NEW_SANDBOX,
	START_PLAY_SANDBOX,
	STOP_PLAY_SANDBOX,

	ANIMATION_STARTED,
	ANIMATION_COMPLETED,

	SAVE_TRAINING_DATA,
	REQUEST_TRAINING_DATA,

	ANALYTICS_OPT_OUT_TOGGLED
}
