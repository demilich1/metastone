package com.hiddenswitch.proto3.net.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.messages.*;
import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.*;
import net.demilich.metastone.game.cards.*;
import net.demilich.metastone.game.cards.desc.*;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.heroes.powers.HeroPower;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;
import net.demilich.metastone.game.spells.desc.condition.ConditionDesc;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDeserializer;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderDesc;
import net.demilich.metastone.game.targeting.EntityReference;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;

public class Serialization {
	static private Gson gson;

	static {
		RuntimeTypeAdapterFactory<GameAction> gameActions = RuntimeTypeAdapterFactory.of(GameAction.class, "actionType");
		gameActions.registerSubtype(EndTurnAction.class, ActionType.END_TURN.toString());
		gameActions.registerSubtype(PhysicalAttackAction.class, ActionType.PHYSICAL_ATTACK.toString());
		gameActions.registerSubtype(PlaySpellCardAction.class, ActionType.SPELL.toString());
		gameActions.registerSubtype(PlayMinionCardAction.class, ActionType.SUMMON.toString());
		gameActions.registerSubtype(HeroPowerAction.class, ActionType.HERO_POWER.toString());
		gameActions.registerSubtype(BattlecryAction.class, ActionType.BATTLECRY.toString());
		gameActions.registerSubtype(PlayWeaponCardAction.class, ActionType.EQUIP_WEAPON.toString());
		gameActions.registerSubtype(DiscoverAction.class, ActionType.DISCOVER.toString());

		RuntimeTypeAdapterFactory<NetworkMessage> networkMessages = RuntimeTypeAdapterFactory.of(NetworkMessage.class, "messageType");
		networkMessages.registerSubtype(GameActionMessage.class, MessageType.GAME_ACTION.toString());
		networkMessages.registerSubtype(ConfigureGameMessage.class, MessageType.CONFIGURE_GAME.toString());
		networkMessages.registerSubtype(EndGameMessage.class, MessageType.END_GAME.toString());
		networkMessages.registerSubtype(PregameMessage.class, MessageType.PREGAME.toString());
		networkMessages.registerSubtype(GameContextMessage.class, MessageType.GAME_CONTEXT.toString());

		RuntimeTypeAdapterFactory<Card> cards = RuntimeTypeAdapterFactory.of(Card.class, "cardType");
		cards.registerSubtype(ChooseOneCard.class, CardType.CHOOSE_ONE.toString());
		cards.registerSubtype(HeroCard.class, CardType.HERO.toString());
		cards.registerSubtype(MinionCard.class, CardType.MINION.toString());
		cards.registerSubtype(SpellCard.class, CardType.SPELL.toString());
		cards.registerSubtype(WeaponCard.class, CardType.WEAPON.toString());
		cards.registerSubtype(HeroPower.class, CardType.HERO_POWER.toString());

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapterFactory(networkMessages);
		gsonBuilder.registerTypeAdapterFactory(gameActions);
		gsonBuilder.registerTypeAdapterFactory(cards);
		Type mapType = new TypeToken<Map<Attribute, Object>>() {
		}.getType();
		gsonBuilder.registerTypeHierarchyAdapter(GameContext.class, new GameContextSerializer());
		gsonBuilder.registerTypeHierarchyAdapter(Deck.class, new DeckSerializer());
		gsonBuilder.registerTypeAdapter(ClientConnectionConfiguration.class, new ClientConnectionConfigurationSerializer());
		gsonBuilder.registerTypeAdapter(mapType, new AttributeDeserializer());
		gsonBuilder.registerTypeAdapter(EntityReference.class, new EntityReferenceSerializer());
		gsonBuilder.registerTypeAdapter(SpellDesc.class, new SpellDeserializer());
		gsonBuilder.registerTypeAdapter(ConditionDesc.class, new ConditionDeserializer());
		gsonBuilder.registerTypeAdapter(EventTriggerDesc.class, new EventTriggerDeserializer());
		gsonBuilder.registerTypeAdapter(AuraDesc.class, new AuraDeserializer());
		gsonBuilder.registerTypeAdapter(ValueProviderDesc.class, new ValueProviderDeserializer());
		gsonBuilder.registerTypeAdapter(CardCostModifierDesc.class, new CardCostModifierDeserializer());
		gsonBuilder.serializeNulls();
		gsonBuilder.enableComplexMapKeySerialization();
		gson = gsonBuilder.create();
	}

	public static Gson getGson() {
		return gson;
	}

	public static String serialize(Object object) {
		return gson.toJson(object);
	}

	public static <T> T deserialize(String json, Class<T> classOfT) throws JsonSyntaxException {
		return gson.fromJson(json, classOfT);
	}

	public static <T> T deserialize(String json, Type typeOfT) throws JsonSyntaxException {
		return gson.fromJson(json, typeOfT);
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] buffer) throws IOException, ClassNotFoundException {
		return deserialize(new ByteArrayInputStream(buffer));
	}


	@SuppressWarnings("unchecked")
	public static <T> T deserialize(InputStream stream) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(stream);
		T result = (T) ois.readObject();
		ois.close();
		return result;
	}

	public static <T> T deserialize(InputStream stream, Class<? extends T> returnClass) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(stream);
		T result = returnClass.cast(ois.readObject());
		ois.close();
		return result;
	}


	public static void serialize(Object obj, OutputStream output) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(output);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
}
