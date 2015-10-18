package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class BottomPanel extends JPanel{

	public BottomPanel(){
		setLayout(new GridBagLayout());
		GridBagConstraints BottomPanelConstraints = new GridBagConstraints();
		BottomPanelConstraints.fill = GridBagConstraints.BOTH;
		
		BottomPanelConstraints.gridx = 0;
		BottomPanelConstraints.gridy = 0;
		ProgressPanel progressPanel = new ProgressPanel();
		add(progressPanel, BottomPanelConstraints);
		
		BottomPanelConstraints.gridx = 0;
		BottomPanelConstraints.gridy = 1;
		PlayBackPanel playBackPanel = new PlayBackPanel();
		add(playBackPanel, BottomPanelConstraints);
		
		
		BottomPanelConstraints.gridy = 2;
		EditPanel editPanel = new EditPanel();
		add(editPanel, BottomPanelConstraints);
	}
}
