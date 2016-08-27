package shuffle;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ShuffleUI extends JFrame implements ActionListener {

	private ShuffleLogic logic;

	private JPanel mainPanel;
	private JPanel centerPanel;
	private JToolBar mainToolbar;
	private JButton randBut;
	private JButton runBut;
	private JButton stopBut;
	private JButton oneStepBut;
	private JButton recoverBut;
	JButton[][] shuffleBut;
	
	public void setLogic(ShuffleLogic logic) {
		this.logic = logic;
	}

	public void init() {
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		add(mainPanel);

		mainToolbar = new JToolBar();
		randBut = new JButton("Ramdomize");
		randBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logic.randomize();
			}});
		mainToolbar.add(randBut);

		runBut = new JButton("Run");
		runBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logic.run();
			}});
		mainToolbar.add(runBut);
		
		stopBut = new JButton("Stop");
		stopBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logic.stop();
			}});
		mainToolbar.add(stopBut);
		
		oneStepBut = new JButton("One step");
		oneStepBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logic.oneStep();
			}});
		mainToolbar.add(oneStepBut);

		recoverBut = new JButton("Recover");
		recoverBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				logic.recover();
			}});
		mainToolbar.add(recoverBut);
		
		mainToolbar.setPreferredSize(new Dimension(getWidth(), 24));
		mainPanel.add(mainToolbar, BorderLayout.NORTH);


		
		centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(600,600));
		centerPanel.setLayout(new GridLayout(4, 4));
		// centerPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
	
		shuffleBut = new JButton[4][4];
		Font myFont = new Font("SansSerif", Font.PLAIN, 50);
		for (int i = 0; i < 4; ++i) 
			for (int j = 0; j < 4; ++j) {
				shuffleBut[i][j] = new JButton("X" + (i * 4 + j));
				shuffleBut[i][j].setFont(myFont);
				centerPanel.add(shuffleBut[i][j]);
				shuffleBut[i][j].setBackground(Color.lightGray);
				shuffleBut[i][j].setBorder(BorderFactory.createLineBorder(Color.darkGray, 4));;
			}
		shuffleBut[0][0].setBackground(Color.black);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		
	}

	public void run() {
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

	public void showOne(int row, int column, int num, boolean fixed) {
		shuffleBut[row][column].setText(" " + num + " ");
		shuffleBut[row][column].setBackground(num == 0 ? Color.black : Color.lightGray);
		// shuffleBut[row][column].setBackground(num == 0 ? Color.black : fixed ? Color.blue : Color.lightGray);
		validate();
		repaint();
	}

}
