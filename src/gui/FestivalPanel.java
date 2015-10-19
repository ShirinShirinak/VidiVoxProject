package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import functionality.SaveTextToMp3;
import functionality.SayItWithFestival;

public class FestivalPanel extends JPanel{
	public FestivalPanel(){
		setLayout(new GridBagLayout());
		
		GridBagConstraints festivalConstrants = new GridBagConstraints();
		festivalConstrants.fill = GridBagConstraints.BOTH;
		festivalConstrants.gridy = 0;
		festivalConstrants.gridx = 0;
		setBorder(BorderFactory.createTitledBorder("Text to Speech"));
		//Adding a jtxtfield for the text-to-speech user input
		final JTextField textField = new JTextField();

		festivalConstrants.insets = new Insets(10,10,10,0);
		textField.setPreferredSize(new Dimension(500, 25));

		textField.setText("Enter Maximum 30 commentary words");
		add(textField, festivalConstrants);
		

		
		//Adding "Listen" button: allows user to preview festival commentary alone
		JButton listenbtn = new JButton("Listen");
		listenbtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String toSay = textField.getText();
				//if festival input box is empty, tell user it cannot be empty
				if (toSay.isEmpty()) {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "Please write the commentary text first.");
					//if there are too many words, festival slows down and eventually stops.
					//Any phrases over the word limit are not allowed.
				} else if (toSay.split("\\s").length > 30) {
					JOptionPane option = new JOptionPane();
					JOptionPane.showMessageDialog(option, "Only phrases of up to thirty words are allowed. Please shorten the commentary.");
					//if there are no problems, the user can listen to the commentary
				} else {
					SayItWithFestival speaker = new SayItWithFestival(toSay);
					try {
						speaker.execute();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}

		});
		festivalConstrants.gridy = 0;
		festivalConstrants.gridx = 1;
		festivalConstrants.insets = new Insets(5,5,5,5);
		add(listenbtn, festivalConstrants);
		
		
		//adding a JButton for saving the festival commentary to an mp3 file
				JButton saveCommentarybtn = new JButton("Save Commentary");
				saveCommentarybtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						String toSave = textField.getText();
						//if festival input box is empty, tell user it cannot be empty
						if (toSave.isEmpty()) {
							JOptionPane option = new JOptionPane();
							JOptionPane.showMessageDialog(option, "Please write the commentary text first.");
							//if word limit is surpassed, inform user
						} else if (toSave.split(" ").length > 30){
							JOptionPane option = new JOptionPane();
							JOptionPane.showMessageDialog(option, "Only phrases of up to thirty words are allowed. Please shorten the commentary.");
							//else save commentary as mp3
						} else {
							JOptionPane option = new JOptionPane();
							String filename = new String();
							while(true) {
								filename = JOptionPane.showInputDialog(option, "Please enter a filename for the mp3: ");
								if(filename==null) { //user pressed cancel
									return;
								} else if (filename.equals("")) { //user submitted empty string
									JOptionPane.showMessageDialog(option, "The filename cannot be empty");
									continue;
								} else if (filename.contains(" ")) { //filename contains spaces
									JOptionPane.showMessageDialog(option, "The filename cannot contain whitespace characters");
									continue;
								} else {
									String workingDirectory = System.getProperty("user.dir");
									//System.out.println(workingDirectory + "/" + filename);
									File f = new File(workingDirectory + "/" + filename + ".mp3");
									if(f.exists()) {
										JOptionPane.showMessageDialog(option, "The file already exists. Please choose another filename.");
										continue;
									}
									SaveTextToMp3 save = new SaveTextToMp3(toSave, filename);
									try {
										save.doInBackground();
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									//next: need to make sure following line only appears if saving really worked. (get message from save obj)
									JOptionPane.showMessageDialog(option, "Commentary saved successfully.");
									return;
								}
							}

						}

					}
				});
				festivalConstrants.gridx = 3;
				festivalConstrants.insets = new Insets(5,5,5,5);
				add(saveCommentarybtn, festivalConstrants);
				
				final JComboBox<FestivalPanel.Pace> pace = new JComboBox<FestivalPanel.Pace>(Pace.values());
				pace.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						String paceValue = ((Pace) pace.getSelectedItem()).getPaceValue();
						SayItWithFestival.setPaceSelection(paceValue);
					}
					
				});
				festivalConstrants.gridx = 4;
				pace.setPreferredSize(new Dimension(90,10));
				festivalConstrants.insets = new Insets(5,5,5,5);
				add(pace, festivalConstrants);
				
	}
	public enum Pace{
		Normal, Slow, Fast;
		
		public String getPaceValue(){
			String thePace = "(Parameter.set 'Duration_Stretch 1.0)";;
			switch (this){
			case Normal: 
				thePace = "(Parameter.set 'Duration_Stretch 1.0)";
				break;
			case Slow: 
				thePace = "(Parameter.set 'Duration_Stretch 2.0)";
				break;
			case Fast: 
				thePace = "(Parameter.set 'Duration_Stretch 0.6)";
				break;
			
			}
			return thePace;
			
			
		}
	}
	//echo "(Parameter.set 'Duration_Stretch 2.0) (SayText \"hello this is just a test.\")" | festival
	
}

