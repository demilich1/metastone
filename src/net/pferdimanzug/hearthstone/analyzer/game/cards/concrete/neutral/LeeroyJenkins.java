package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;

public class LeeroyJenkins extends MinionCard {

	public LeeroyJenkins() {
		super("Leeroy Jenkins", 6, 2, Rarity.LEGENDARY, HeroClass.ANY, 4);
		setDescription("Charge. Battlecry: Summon two 1/1 Whelps for your opponent.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 153;
	}
	
	@Override
	public Minion summon() {
		Minion leeroyJenkins = createMinion(GameTag.CHARGE);
		SpellDesc summonSpell = SummonSpell.create(TargetPlayer.OPPONENT, new Whelp(), new Whelp());
		leeroyJenkins.setBattlecry(Battlecry.createBattlecry(summonSpell));
		return leeroyJenkins;
	}

	private class Whelp extends MinionCard {

		public Whelp() {
			super("Whelp", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
			setRace(Race.DRAGON);
		}

		@Override
		public Minion summon() {
			return createMinion();
		}
		
	}
}
