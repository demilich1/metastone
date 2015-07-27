package net.demilich.metastone.tests;
import java.util.EnumMap;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.desc.MinionCardDesc;
import net.demilich.metastone.game.entities.Actor;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;


public class TestMinionCard extends MinionCard {
	
	private static int id = 1;

	private final Minion minion;
	
	private static MinionCardDesc getDesc(int attack, int hp, GameTag... attributes) {
		MinionCardDesc desc = new MinionCardDesc();
		desc.name = "Test monster " + ++id;
		desc.rarity = Rarity.FREE;
		desc.baseAttack = attack;
		desc.baseHp = hp;
		desc.type = CardType.MINION;
		desc.heroClass = HeroClass.ANY;
		desc.attributes = new EnumMap<GameTag, Object>(GameTag.class);
		for (GameTag gameTag : attributes) {
			desc.attributes.put(gameTag, true);
		}
		return desc;
	}

	public TestMinionCard(int baseAttack, int baseHp, GameTag... tags) {
		super(getDesc(baseAttack, baseHp, tags));
		setCollectible(false);
		
		this.minion = createMinion();
		for(GameTag attribute : tags) {
			minion.setTag(attribute);
		}
	}
	
	public TestMinionCard(int baseAttack, int baseHp, int manaCost) {
		super(getDesc(baseAttack, baseHp));
		setCollectible(false);
		this.minion = createMinion();
	}

	@Override
	public String getCardId() {
		return "minion_test";
	}

	public Actor getMinion() {
		return minion;
	}
	
	@Override
	public Minion summon() {
		return minion.clone();
	}

}
