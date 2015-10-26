package gui;

import javax.swing.SwingUtilities;

import gui.high_level_panels.MainPlayer;

public class MainRunnerClass {
	public static void main(final String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainPlayer();

			}
		});



	}
}
