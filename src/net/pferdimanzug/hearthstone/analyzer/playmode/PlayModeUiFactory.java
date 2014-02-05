package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.Hero;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;

public class PlayModeUiFactory {
	
	private PlayModeUiFactory() {}
	
	public static JComponent createHandCard(Card card, Player player) {
		JPanel cardPanel = new JPanel(new BorderLayout());
		cardPanel.setPreferredSize(new Dimension(100, 110));
		JTextArea nameArea = new JTextArea(card.getName());
		nameArea.setLineWrap(true);
		nameArea.setWrapStyleWord(true);
		nameArea.setEditable(false);
		nameArea.setFont(new Font("Arial", Font.BOLD, 12));
		cardPanel.add(nameArea, BorderLayout.CENTER);
		JLabel costLabel = new JLabel(card.getManaCost(player) + "");
		costLabel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		cardPanel.add(costLabel, BorderLayout.NORTH);
		
		cardPanel.setBorder(createRarityBorder(card.getRarity()));
		return cardPanel;
	}
	
	public static JPanel createMinionToken(Minion minion) {
		JPanel minionTokenPanel = new MinionTokenPanel(minion);
		/*
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(80, 80));
		JLabel nameLabel = new JLabel(minion.getName());
		nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(nameLabel, BorderLayout.CENTER);
		
		JPanel bottomPanel = new JPanel(null);
		
		final int numberLabelSize = 16;
		JLabel attackLabel = new JLabel(minion.getAttack() + "");
		attackLabel.setLocation(8, bottomPanel.getHeight()- 2 * numberLabelSize);
		attackLabel.setOpaque(true);
		attackLabel.setBackground(Color.YELLOW);
		attackLabel.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel hpLabel = new JLabel(minion.getHp() + "");
		hpLabel.setHorizontalAlignment(JLabel.RIGHT);
		hpLabel.setOpaque(true);
		hpLabel.setBackground(Color.RED);
		hpLabel.setFont(new Font("Arial", Font.BOLD, 14));
		
		bottomPanel.add(attackLabel);
		bottomPanel.add(hpLabel);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		panel.setBorder(createRarityBorder(minion.getSourceCard().getRarity()));
		*/
		minionTokenPanel.setBorder(createRarityBorder(minion.getSourceCard().getRarity()));
		return minionTokenPanel;
	}
	
	public static Border createRarityBorder(Rarity rarity) {
		Color color;
		int thickness = 2;
		switch (rarity) {
		case COMMON:
			color = Color.WHITE;
			break;
		case EPIC:
			// a335ee
			color = new Color(163, 53, 238);
			break;
		case LEGENDARY:
			// ff8000
			color = new Color(255, 128, 0);
			thickness = 3;
			break;
		case RARE:
			// 0070dd
			color = new Color(0, 112, 221);
			break;
		default:
			color = Color.GRAY;
			break;
		}
		return BorderFactory.createLineBorder(color, thickness);
		
	}
	
	public static Icon createIcon(Hero hero) {
		String iconName = null;
		switch (hero.getHeroClass()) {
		case DRUID:
			iconName = "malfurion";
			break;
		case HUNTER:
			iconName = "rexxar";
			break;
		case MAGE:
			iconName = "jaina";
			break;
		case PALADIN:
			iconName = "paladin";
			break;
		case PRIEST:
			iconName = "anduin";
			break;
		case ROGUE:
			iconName = "valeera";
			break;
		case SHAMAN:
			iconName = "thrall";
			break;
		case WARLOCK:
			iconName = "guldan";
			break;
		case WARRIOR:
			iconName = "garrosh";
			break;
		default:
		case ANY:
			break;
		
		}
		return new ImageIcon(PlayModePanel.class.getResource("/net/pferdimanzug/hearthstone/analyzer/resources/img/heroes/" + iconName + ".png"));
	}
}