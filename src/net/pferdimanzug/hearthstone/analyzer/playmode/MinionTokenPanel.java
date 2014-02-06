package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

@SuppressWarnings("serial")
public class MinionTokenPanel extends JPanel {
	private final static Set<GameTag> displayedTags = new HashSet<GameTag>(Arrays.asList(new GameTag[] {
			GameTag.CHARGE, GameTag.ENRAGED, GameTag.FROZEN, GameTag.DIVINE_SHIELD, GameTag.WINDFURY,
			GameTag.SPELL_POWER, GameTag.STEALTHED, GameTag.TAUNT }));
	private final JTextArea nameArea;
	private final JLabel attackLabel;
	private final JLabel hpLabel;

	public MinionTokenPanel() {
		setBorder(null);
		setLayout(new MigLayout("", "[16!][80!][16!]", "[][40!][32!]"));

		nameArea = new JTextArea();
		nameArea.setWrapStyleWord(true);
		nameArea.setLineWrap(true);
		nameArea.setFont(new Font("Arial", Font.BOLD, 12));
		nameArea.setEditable(false);
		nameArea.setText("<minion name>");
		add(nameArea, "cell 1 1,width 80!,alignx center,aligny top");

		attackLabel = new JLabel("00");
		attackLabel.setForeground(Color.BLACK);
		attackLabel.setFont(new Font("Arial", Font.BOLD, 16));
		attackLabel.setHorizontalTextPosition(JLabel.CENTER);
		attackLabel.setVerticalTextPosition(JLabel.CENTER);
		add(attackLabel, "cell 0 2,alignx center,aligny center");

		hpLabel = new JLabel("00");
		hpLabel.setFont(new Font("Arial", Font.BOLD, 16));
		add(hpLabel, "cell 2 2,alignx center,aligny center");
	}
	
	public void setMinion(Minion minion) {
		nameArea.setText(minion.getName());
		attackLabel.setText(String.valueOf(minion.getAttack()));
		hpLabel.setText(String.valueOf(minion.getHp()));
		setBorder(PlayModeUiFactory.createRarityBorder(minion.getSourceCard().getRarity()));
	}

	private static String getTagString(Minion minion) {
		String result = "";
		String prefix = " ";
		for (GameTag tag : displayedTags) {
			if (!minion.hasTag(tag)) {
				continue;
			}
			result += prefix + tag;
			if (tag == GameTag.SPELL_POWER) {
				result += " +" + minion.getTagValue(GameTag.SPELL_POWER);
			}
			prefix = ", ";
		}
		return result;
	}

}