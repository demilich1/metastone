package net.demilich.metastone.game.cards.desc;

import java.util.Map;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderDesc;

public abstract class CardDesc {
	
	public String id;
	public String name;
	public String description;
	public CardType type;
	public HeroClass heroClass;
	public Rarity rarity;
	public int baseManaCost;
	public boolean collectible = true;
	public Map<GameTag, Object> attributes;
	public int fileFormatVersion = 1;
	public ValueProviderDesc manaCostModifier;
	
	public abstract Card createInstance();

}
