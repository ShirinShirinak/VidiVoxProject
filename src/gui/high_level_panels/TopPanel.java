package gui.high_level_panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;

import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class TopPanel extends JPanel{
	public static EmbeddedMediaPlayerComponent mediaPlayerComponent = null;
	public TopPanel(){
		setLayout(new GridBagLayout());
		//topPanel.setBounds(0, 0, 3000, 2000);
		//setPreferredSize(new Dimension(1000, 500));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

		mediaPlayerComponent.setPreferredSize(new Dimension(1000, 500));
		c.gridx = 0;
		c.gridy = 0;
		add(mediaPlayerComponent, c);
		
		setSize(500, 500);
		
	}
	
	public static EmbeddedMediaPlayerComponent getMPComponent(){
		return mediaPlayerComponent;
	}
}
