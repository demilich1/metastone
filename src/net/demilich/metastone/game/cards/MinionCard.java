package net.demilich.metastone.game.cards;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.actions.PlayMinionCardAction;
import net.demilich.metastone.game.cards.desc.MinionCardDesc;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.desc.BattlecryDesc;

public class MinionCard extends Card {

	private static final Set<GameTag> inheritedAttributes = new HashSet<GameTag>(Arrays.asList(new GameTag[] { GameTag.STEALTH,
			GameTag.CANNOT_ATTACK, GameTag.TAUNT, GameTag.UNTARGETABLE_BY_SPELLS, GameTag.CHARGE, GameTag.DIVINE_SHIELD, GameTag.WINDFURY,
			GameTag.MEGA_WINDFURY, GameTag.SPELL_DAMAGE, GameTag.ATTACK_EQUALS_HP, GameTag.INVERT_HEALING,
			GameTag.SPELL_AMPLIFY_MULTIPLIER, GameTag.ENRAGABLE

	}));

	private final MinionCardDesc desc;

	public MinionCard(MinionCardDesc desc) {
		super(desc);
		setTag(GameTag.BASE_ATTACK, desc.baseAttack);
		setTag(GameTag.BASE_HP, desc.baseHp);
		if (desc.race != null) {
			setRace(desc.race);
		}
		this.desc = desc;
	}

	public MinionCard(String name, int baseAttack, int baseHp, Rarity rarity, HeroClass classRestriction, int manaCost) {
		super(name, CardType.MINION, rarity, classRestriction, manaCost);
		setTag(GameTag.BASE_ATTACK, baseAttack);
		setTag(GameTag.BASE_HP, baseHp);
		this.desc = new MinionCardDesc();
	}

	protected Minion createMinion(GameTag... tags) {
		Minion minion = new Minion(this);
		for (GameTag gameTag : getTags().keySet()) {
			if (inheritedAttributes.contains(gameTag)) {
				minion.setTag(gameTag, getTag(gameTag));
			}
		}
		minion.setBaseAttack(getBaseAttack());
		minion.setTag(GameTag.ATTACK_BONUS, getTagValue(GameTag.ATTACK_BONUS));
		minion.setBaseHp(getBaseHp());
		BattlecryDesc battlecry = desc.battlecry;
		if (battlecry != null) {
			BattlecryAction battlecryAction = BattlecryAction.createBattlecry(battlecry.spell, battlecry.getTargetSelection());
			battlecryAction.setResolvedLate(battlecry.resolvedLate);
			if (battlecry.condition != null) {
				battlecryAction.setCondition(battlecry.condition.create());
			}

			minion.setBattlecry(battlecryAction);
		}

		if (desc.deathrattle != null) {
			minion.addDeathrattle(desc.deathrattle);
		}
		if (desc.trigger != null) {
			minion.setSpellTrigger(desc.trigger.create());
		} else if (desc.aura != null) {
			minion.setSpellTrigger(desc.aura.create());
		}
		if (desc.cardCostModifier != null) {
			minion.setCardCostModifier(desc.cardCostModifier.create());
		}
		return minion;
	}

	public int getAttack() {
		return getBaseAttack() + getTagValue(GameTag.ATTACK_BONUS);
	}

	public int getBaseAttack() {
		return getTagValue(GameTag.BASE_ATTACK);
	}

	public int getBaseHp() {
		return getTagValue(GameTag.BASE_HP);
	}

	@Override
	public PlayCardAction play() {
		return new PlayMinionCardAction(getCardReference());
	}

	public void setRace(Race race) {
		setTag(GameTag.RACE, race);
	}

	public Minion summon() {
		return createMinion();
	}

}
