package gui;

import javax.swing.SwingUtilities;

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
