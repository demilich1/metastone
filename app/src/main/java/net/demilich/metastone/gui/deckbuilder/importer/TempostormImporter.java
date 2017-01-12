package net.demilich.metastone.gui.deckbuilder.importer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCatalogue;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TempostormImporter implements IDeckImporter{

    private static Logger logger = LoggerFactory.getLogger(TempostormImporter.class);

    Deck parse(JsonObject root)
    {
        try {
            List<Card> cards = new ArrayList<Card>();
            String deckName = root.get("name").getAsString();
            String hero = root.get("playerClass").getAsString();
            HeroClass heroClass = HeroClass.valueOf(hero.toUpperCase());
            JsonElement cardsEl = root.get("cards");
            JsonArray cardsArray = cardsEl.getAsJsonArray();

            for (JsonElement cardTypeElem : cardsArray) {
                JsonObject cardTypeObj = cardTypeElem.getAsJsonObject();
                int cardCount = cardTypeObj.get("cardQuantity").getAsInt();
                JsonObject cardObj = cardTypeObj.get("card").getAsJsonObject();
                String cardName = cardObj.get("name").getAsString();

                Card card = CardCatalogue.getCardByName(cardName);

                if (card != null) {
                    if(cardCount > 0)
                        cards.add(card);

                    for (int i = 1; i < cardCount; i++)
                        cards.add(card.clone());
                } else {
                    logger.error("Card with name {} could not be found", cardName);
                    return null;
                }
            }

            Deck deck = new Deck(heroClass);
            deck.setName(deckName);

            for (Card card : cards)
                deck.getCards().add(card);

            if (!deck.isComplete())
                return null;

            return deck;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    String convertUrl(String url)
    {
        Pattern pattern = Pattern.compile(".*/decks/([^/]+)$");
        Matcher matcher = pattern.matcher(url);

        if(!matcher.matches())
            return null;

        String identifier = matcher.group(1);
        String filter = "{\"where\":{\"slug\":\"" + identifier
                + "\"},\"fields\":{},\"include\":[{\"relation\":\"cards\",\"scope\":{\"include\":[\"card\"]}}]}";
        return "https://tempostorm.com/api/decks/findOne?filter=" + filter;
    }
    @Override
    public Deck importFrom(String requestedUrl) {

        String apiUrl = convertUrl(requestedUrl);
        logger.debug("Requesting: " + apiUrl);

        URL url;
        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        HttpURLConnection request;

        try {
            request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jobj = root.getAsJsonObject();
            return parse(jobj);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
