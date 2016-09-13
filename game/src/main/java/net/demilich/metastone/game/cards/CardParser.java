package net.demilich.metastone.game.cards;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import net.demilich.metastone.utils.ResourceInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import net.demilich.metastone.game.Attribute;
import net.demilich.metastone.game.cards.desc.AttributeDeserializer;
import net.demilich.metastone.game.cards.desc.AuraDeserializer;
import net.demilich.metastone.game.cards.desc.CardCostModifierDeserializer;
import net.demilich.metastone.game.cards.desc.CardDesc;
import net.demilich.metastone.game.cards.desc.ChooseBattlecryCardDesc;
import net.demilich.metastone.game.cards.desc.ChooseOneCardDesc;
import net.demilich.metastone.game.cards.desc.ConditionDeserializer;
import net.demilich.metastone.game.cards.desc.HeroCardDesc;
import net.demilich.metastone.game.cards.desc.HeroPowerCardDesc;
import net.demilich.metastone.game.cards.desc.MinionCardDesc;
import net.demilich.metastone.game.cards.desc.SecretCardDesc;
import net.demilich.metastone.game.cards.desc.SpellCardDesc;
import net.demilich.metastone.game.cards.desc.SpellDeserializer;
import net.demilich.metastone.game.cards.desc.ValueProviderDeserializer;
import net.demilich.metastone.game.cards.desc.WeaponCardDesc;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.desc.aura.AuraDesc;
import net.demilich.metastone.game.spells.desc.condition.ConditionDesc;
import net.demilich.metastone.game.spells.desc.manamodifier.CardCostModifierDesc;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDeserializer;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderDesc;

public class CardParser {

	private static Logger logger = LoggerFactory.getLogger(CardParser.class);

	private final Gson gson;

	public CardParser() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(SpellDesc.class, new SpellDeserializer());
		Type mapType = new TypeToken<Map<Attribute, Object>>() {
		}.getType();
		gsonBuilder.registerTypeAdapter(mapType, new AttributeDeserializer());
		gsonBuilder.registerTypeAdapter(ConditionDesc.class, new ConditionDeserializer());
		gsonBuilder.registerTypeAdapter(EventTriggerDesc.class, new EventTriggerDeserializer());
		gsonBuilder.registerTypeAdapter(AuraDesc.class, new AuraDeserializer());
		gsonBuilder.registerTypeAdapter(ValueProviderDesc.class, new ValueProviderDeserializer());
		gsonBuilder.registerTypeAdapter(CardCostModifierDesc.class, new CardCostModifierDeserializer());
		gson = gsonBuilder.create();
	}

	public CardDesc parseCard(ResourceInputStream resourceInputStream) throws FileNotFoundException {
		JsonElement jsonData = gson.fromJson(new InputStreamReader(resourceInputStream.inputStream), JsonElement.class);

		if (!jsonData.getAsJsonObject().has("id")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'id' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("name")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'name' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("baseManaCost")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'baseManaCost' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("type")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'type' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("heroClass")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'heroClass' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("rarity")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'rarity' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("collectible")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'collectible' attribute!");
		}
		if (!jsonData.getAsJsonObject().has("set")) {
			throw new RuntimeException(resourceInputStream.fileName + " is missing 'set' attribute!");
		}
		CardType type = CardType.valueOf((String) jsonData.getAsJsonObject().get("type").getAsString());
		switch (type) {
		case SPELL:
			if (!jsonData.getAsJsonObject().has("description")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'description' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("spell")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'spell' attribute!");
			}
			if (jsonData.getAsJsonObject().has("trigger")) {
				return gson.fromJson(jsonData, SecretCardDesc.class);
			} else {
				if (!jsonData.getAsJsonObject().has("targetSelection")) {
					throw new RuntimeException(resourceInputStream.fileName + " is missing 'targetSelection' attribute!");
				}
				return gson.fromJson(jsonData, SpellCardDesc.class);
			}
		case CHOOSE_ONE:
			if (!jsonData.getAsJsonObject().has("description")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'description' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("options")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'options' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("bothOptions")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'bothOptions' attribute!");
			}
			return gson.fromJson(jsonData, ChooseOneCardDesc.class);
		case MINION:
			if (!jsonData.getAsJsonObject().has("baseAttack")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'baseAttack' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("baseHp")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'baseHp' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("description") && (jsonData.getAsJsonObject().has("battlecry") || 
					jsonData.getAsJsonObject().has("deathrattle") ||jsonData.getAsJsonObject().has("attributes") || 
					jsonData.getAsJsonObject().has("trigger") || jsonData.getAsJsonObject().has("passiveTrigger") || 
					jsonData.getAsJsonObject().has("deckTrigger") || jsonData.getAsJsonObject().has("options"))) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'description' attribute!");
			}
			if (jsonData.getAsJsonObject().has("options")) {
				return gson.fromJson(jsonData, ChooseBattlecryCardDesc.class);
			} else {
				return gson.fromJson(jsonData, MinionCardDesc.class);
			}
		case WEAPON:
			if (!jsonData.getAsJsonObject().has("damage")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'damage' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("durability")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'durability' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("description") && (jsonData.getAsJsonObject().has("battlecry") || 
					jsonData.getAsJsonObject().has("deathrattle") ||jsonData.getAsJsonObject().has("attributes") || 
					jsonData.getAsJsonObject().has("trigger") || jsonData.getAsJsonObject().has("passiveTrigger") || 
					jsonData.getAsJsonObject().has("deckTrigger") || jsonData.getAsJsonObject().has("onEquip") || 
					jsonData.getAsJsonObject().has("onUnequip"))) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'description' attribute!");
			}
			return gson.fromJson(jsonData, WeaponCardDesc.class);
		case HERO_POWER:
			if (!jsonData.getAsJsonObject().has("description")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'description' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("spell") && !jsonData.getAsJsonObject().has("options")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'spell' or 'options' attribute!");
			}
			if (!jsonData.getAsJsonObject().has("targetSelection")) {
				throw new RuntimeException(resourceInputStream.fileName + " is missing 'targetSelection' attribute!");
			}
			return gson.fromJson(jsonData, HeroPowerCardDesc.class);	
		case HERO:
			return gson.fromJson(jsonData, HeroCardDesc.class);
		default:
			logger.error("Unknown cardType: " + type);
			break;
		}
		return null;
	}
}
