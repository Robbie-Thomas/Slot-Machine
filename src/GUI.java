import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.Timer;

public class GUI extends JFrame {

    private final JTextField credField;
	private final slotMachine slot = new slotMachine();
	private final JButton hldBtn1;
	private final JButton hldBtn2;
	private final JButton hldBtn3;
	private final JButton spinBtn;
	private final JButton nudgeBtn1;
	private final JButton nudgeBtn2;
	private final JButton nudgeBtn3;
	private final JButton addCreditsBtn;
	private final JButton cashOutBtn;
	private final JTextField winningField;
	private final JLabel fruit1Abv;
	private final JLabel fruit2Abv;
	private final JLabel fruit3Abv;
	private final JLabel fruit2Middle;
	protected final JLabel fruit3Middle;
	private final JLabel fruit1Blw;
	private final JLabel fruit2Blw;
	private final JLabel fruit3Blw;
	private JLabel slotMachineLabel;
	private final JLabel fruit1Middle;
    private final JButton transferCredBtn;
	private final JButton replayBtn;
	private int randomNum;
	private boolean isHolding1, isHolding2, isHolding3, canReplay = false;
    final ArrayList<Integer> resultsLog = new ArrayList<>();





	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				GUI frame = new GUI();
				frame.setVisible(true);


			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}



//This method isn't used currently
 /*   public void toLog(String fileName)
    {
        File log = new File(fileName);

        String toLogString = "";
		for (Integer integer : resultsLog) {

			toLogString += integer + "\n";
		}
        try
        {
            FileOutputStream outs = new FileOutputStream(log,false);
            PrintStream writer = new PrintStream(outs);
            writer.append(toLogString);
        }
        catch (IOException e)
        {
           System.out.println("Error Logging results");
        }

    }*/

    public void enableButton(JButton button){
	    button.setEnabled(true);
    }

    public void disableHolding(){
        enableButton(hldBtn1);
        enableButton(hldBtn2);
        enableButton(hldBtn3);
    }



	public void spinAnimation()
	{


        canReplay = true;
        if(isHolding1)
        {
            disableHolding();
            isHolding1 = false;
            hldBtn1.setBackground(Color.LIGHT_GRAY);
            slot.setRoundsLeft(slot.getRoundsLeft() -1);
            spinLane(fruit2Blw, fruit2Middle, fruit2Abv);

        }
        else if(isHolding2)
        {
            disableHolding();
            isHolding2 = false;
            hldBtn2.setBackground(Color.LIGHT_GRAY);
            slot.setRoundsLeft(slot.getRoundsLeft() - 1);
            spinLane(fruit1Blw, fruit1Middle, fruit1Abv);
            spinLane(fruit3Blw, fruit3Middle, fruit3Abv);
			}
        else if(isHolding3)
        {
            disableHolding();
            isHolding3 = false;
            hldBtn3.setBackground(Color.LIGHT_GRAY);
            slot.setRoundsLeft(slot.getRoundsLeft() -1);
            spinLane(fruit1Blw, fruit1Middle, fruit1Abv);
            spinLane(fruit2Blw, fruit2Middle, fruit2Abv);

        }
        else
            {
            slot.setRoundsLeft(slot.getRoundsLeft() -1);
            spinLane(fruit1Blw, fruit1Middle, fruit1Abv);
            spinLane(fruit2Blw, fruit2Middle, fruit2Abv);
            spinLane(fruit3Blw, fruit3Middle, fruit3Abv);
            }
        resultsLog.add(shapeToInt(fruit1Middle.getIcon().toString()));
        resultsLog.add(shapeToInt(fruit2Middle.getIcon().toString()));
        resultsLog.add(shapeToInt(fruit3Middle.getIcon().toString()));

		java.util.Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			int count = 0;
			@Override
			public void run()
			{
				compareWinnings();
				returnCredits();
				winningField.setText(Integer.toString(slot.getCredits()));

			}
		},1501);
	}



	public void spinLane(JLabel Blw, JLabel Middle, JLabel Abv){

        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            int count = 0;
            @Override
            public void run()
            {
                Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                count++;
                if(count == 10)
                {
                    this.cancel();
                    java.util.Timer timer = new Timer();
                    timer.schedule(new TimerTask()
                    {
                        int count2 = 0;
                        @Override
                        public void run()
                        {
                            count2++;
                            if(count2 == 10)
                            {
                                spinBtn.setEnabled(true);
                            }

                        }

                    },10, 150);

                }

            }
        }, 10, 150);

    }


    public int shapeToInt(String fruitIcon)
    {
        int shapeInt= 0;
        if( fruitIcon.equals(fruitType.APPLE.toString()))
        {
			//noinspection ConstantConditions
			shapeInt = 0;
        }
        else if(fruitIcon.equals(fruitType.BANANA.toString()))
        {
            shapeInt = 1;
        }
        else if(fruitIcon.equals(fruitType.BLUEBERRIES.toString()))
        {
            shapeInt = 2;
        }
        else if(fruitIcon.equals(fruitType.CHERRY.toString()))
        {
            shapeInt = 3;
        }
        else if(fruitIcon.equals(fruitType.GRAPES.toString()))
        {
            shapeInt = 4;
        }
        else if(fruitIcon.equals(fruitType.WATERMELON.toString()))
        {
            shapeInt = 5;
        }
        else if(fruitIcon.equals(fruitType.PEACH.toString()))
        {
            shapeInt = 6;
        }
        else if(fruitIcon.equals(fruitType.STRAWBERRY.toString()))
        {
            shapeInt = 7;
        }
        return shapeInt;
    }

    public int compareWinnings(){
        int winnings = 0;

        int fruit1MiddleConv = shapeToInt(fruit1Middle.getIcon().toString());
        int fruit2MiddleConv = shapeToInt(fruit2Middle.getIcon().toString());
        int fruit3MiddleConv = shapeToInt(fruit3Middle.getIcon().toString());
		//Three fruit the same
        if(fruit1MiddleConv == fruit2MiddleConv && fruit2MiddleConv == fruit3MiddleConv)
        {
            winnings = 100;
			slotMachineLabel.setText("Three in a row!");
        }
        //Two of a kind
        else if(fruit1MiddleConv == fruit2MiddleConv || (fruit2MiddleConv==fruit3MiddleConv) ||
                (fruit3MiddleConv==fruit1MiddleConv))
        {
            winnings = 50;
			slotMachineLabel.setText("Two in a row!");

        }
        //Cherry
        else if(fruit1MiddleConv == 3 || (fruit2MiddleConv == 3) || (fruit3MiddleConv == 3))
        {
			slotMachineLabel.setText("Cherry!");

			winnings = 20;
        }
        return winnings;
    }

	public void returnCredits()
	{
		slot.addCredits(compareWinnings());
	}



    public void nudgeHelp(JLabel jMiddle, JLabel jBelow){
        jMiddle.setIcon(jBelow.getIcon());
        jBelow.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
        returnCredits();
        winningField.setText(Integer.toString(slot.getCredits()));
    }


    public void disableNudgeButtons(){
        nudgeBtn1.setEnabled(false);
        nudgeBtn2.setEnabled(false);
        nudgeBtn3.setEnabled(false);
    }



    public void nudge1()
	{
        disableNudgeButtons();
		Random rand = new Random();
		randomNum = rand.nextInt(100);
		if(randomNum <= 10)
		{
            nudgeHelp(fruit1Middle, fruit1Blw);
		}

	}
	public void nudge2()
	{
        disableNudgeButtons();
		Random rand = new Random();
		randomNum = rand.nextInt(100);
		if(randomNum <= 10)
		{
            nudgeHelp(fruit2Middle, fruit2Blw);

		}

	}
	public void nudge3()
	{
        disableNudgeButtons();
		Random rand = new Random();
		randomNum = rand.nextInt(100);
		if(randomNum <= 10)
		{
            nudgeHelp(fruit3Middle, fruit3Blw);
		}

	}


    public void replayLastRound()
    {
        String fruitSave1, fruitSave2, fruitSave3;
        fruitSave1 = fruit1Middle.getIcon().toString();
        fruitSave2 = fruit2Middle.getIcon().toString();
        fruitSave3 = fruit3Middle.getIcon().toString();

        isHolding1 = false;
        isHolding2 = false;
        isHolding3 = false;
        replayBtn.setEnabled(false);


       // spinAnimation();
        java.util.Timer timer2 = new Timer();
        timer2.schedule(new TimerTask()
                       {
                           int count = 0;

                           @Override
                           public void run()
                           {
                               fruit1Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit2Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit3Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit1Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit2Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit3Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit1Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit2Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               fruit3Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
                               count++;
                               if (count == 10)
                               {
                                   this.cancel();
                               }
                           }
                       },0,300);


        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            int count = 0;
            @Override
            public void run()
            {
                count ++;
                if(count == 31)
                {
                    fruit1Middle.setIcon(new ImageIcon(fruitSave1));
                    fruit2Middle.setIcon(new ImageIcon(fruitSave2));
                    fruit3Middle.setIcon(new ImageIcon(fruitSave3));
                    timer.cancel();
                }
            }
        },1000,100);
        int tempInt = Integer.parseInt( winningField.getText());

    }




	/**
	 * Create the frame.
	 */
	public GUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
        JPanel contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(257, 0, 170, 36);
		contentPane.add(panel_3);
		panel_3.setLayout(null);

		slotMachineLabel = new JLabel("TRY YOUR LUCK");
		slotMachineLabel.setBounds(9, 0, 152, 36);
		panel_3.add(slotMachineLabel);
		slotMachineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		slotMachineLabel.setForeground(Color.RED);
		slotMachineLabel.setFont(new Font("Clarendon BT", Font.BOLD, 16));

		hldBtn1 = new JButton("HOLD");
		hldBtn1.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		hldBtn1.addActionListener(this::actionPerformed);
		hldBtn1.setBackground(Color.LIGHT_GRAY);
		hldBtn1.setForeground(Color.BLACK);
		hldBtn1.setBounds(159, 314, 100, 40);
		contentPane.add(hldBtn1);


		hldBtn2 = new JButton("HOLD");
		hldBtn2.setForeground(Color.BLACK);
		hldBtn2.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		hldBtn2.addActionListener(this::actionPerformed);
		hldBtn2.setBackground(Color.LIGHT_GRAY);
		hldBtn2.setBounds(291, 314, 100, 40);
		contentPane.add(hldBtn2);

		hldBtn3 = new JButton("HOLD");
		hldBtn3.setForeground(Color.BLACK);
		hldBtn3.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		hldBtn3.addActionListener(this::actionPerformed);
		hldBtn3.setBackground(Color.LIGHT_GRAY);
		hldBtn3.setBounds(423, 314, 100, 40);
		contentPane.add(hldBtn3);



		spinBtn = new JButton("SPIN");
		spinBtn.setBackground(Color.LIGHT_GRAY);
		spinBtn.setForeground(Color.DARK_GRAY);
		spinBtn.setFont(new Font("Clarendon BT", Font.BOLD, 30));
		spinBtn.addActionListener(this::actionPerformed);
		spinBtn.setBounds(230, 428, 223, 87);
		contentPane.add(spinBtn);

		nudgeBtn1 = new JButton("NUDGE");
		nudgeBtn1.setForeground(Color.BLACK);
		nudgeBtn1.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		nudgeBtn1.addActionListener(this::actionPerformed);
		nudgeBtn1.setBackground(Color.LIGHT_GRAY);
		nudgeBtn1.setBounds(159, 365, 100, 40);
		contentPane.add(nudgeBtn1);

		nudgeBtn2 = new JButton("NUDGE");
		nudgeBtn2.setForeground(Color.BLACK);
		nudgeBtn2.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		nudgeBtn2.addActionListener(this::actionPerformed);
		nudgeBtn2.setBackground(Color.LIGHT_GRAY);
		nudgeBtn2.setBounds(291, 365, 100, 40);
		contentPane.add(nudgeBtn2);

		nudgeBtn3 = new JButton("NUDGE");
		nudgeBtn3.setForeground(Color.BLACK);
		nudgeBtn3.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		nudgeBtn3.addActionListener(this::actionPerformed);
		nudgeBtn3.setBackground(Color.LIGHT_GRAY);
		nudgeBtn3.setBounds(423, 365, 100, 40);
		contentPane.add(nudgeBtn3);


		addCreditsBtn = new JButton("Add Credits");
		addCreditsBtn.setForeground(Color.BLACK);
		addCreditsBtn.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		addCreditsBtn.addActionListener(this::actionPerformed);
		addCreditsBtn.setBackground(Color.LIGHT_GRAY);
		addCreditsBtn.setBounds(73, 451, 130, 40);
		contentPane.add(addCreditsBtn);

		cashOutBtn = new JButton("Cash Out");
		cashOutBtn.setForeground(Color.BLACK);
		cashOutBtn.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		cashOutBtn.addActionListener(this::actionPerformed);
		cashOutBtn.setBackground(Color.LIGHT_GRAY);
		cashOutBtn.setBounds(479, 451, 130, 40);
		contentPane.add(cashOutBtn);

		JPanel iconPanel = new JPanel();
		iconPanel.setBackground(Color.GRAY);
		iconPanel.setBounds(104, 47, 480, 260);
		contentPane.add(iconPanel);
		iconPanel.setLayout(null);

		JPanel topPanel = new JPanel();
		topPanel.setBounds(10, 5, 460, 80);
		iconPanel.add(topPanel);
		topPanel.setBackground(Color.WHITE);
		topPanel.setLayout(null);

		JPanel leftPanelTop = new JPanel();
		leftPanelTop.setBounds(62, 5, 70, 70);
		topPanel.add(leftPanelTop);

		fruit1Abv = new JLabel("");
		fruit1Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		leftPanelTop.add(fruit1Abv);
		leftPanelTop.setOpaque(false);

		JPanel centrePanelTop = new JPanel();
		centrePanelTop.setBounds(194, 5, 70, 70);
		topPanel.add(centrePanelTop);

		fruit2Abv = new JLabel("");
		fruit2Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		centrePanelTop.add(fruit2Abv);
		centrePanelTop.setOpaque(false);

		JPanel rightPanelTop = new JPanel();
		rightPanelTop.setBounds(326, 5, 70, 70);
		topPanel.add(rightPanelTop);

		fruit3Abv = new JLabel("");
		fruit3Abv.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		rightPanelTop.add(fruit3Abv);
		rightPanelTop.setOpaque(false);

		JPanel midPanel = new JPanel();
		midPanel.setBounds(10, 90, 460, 80);
		iconPanel.add(midPanel);
		midPanel.setBackground(UIManager.getColor("Button.background"));
		midPanel.setLayout(null);

		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(62, 5, 70, 70);
		midPanel.add(leftPanel);


		fruit1Middle = new JLabel("");
		fruit1Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		leftPanel.add(fruit1Middle);
		leftPanel.setOpaque(false);

		JPanel centrePanel = new JPanel();
		centrePanel.setBounds(194, 5, 70, 70);
		midPanel.add(centrePanel);
		centrePanel.setOpaque(false);


		fruit2Middle = new JLabel("");
		fruit2Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		centrePanel.add(fruit2Middle);
		centrePanel.setOpaque(false);

		JPanel rightPanel = new JPanel();
		rightPanel.setBounds(326, 5, 70, 70);
		midPanel.add(rightPanel);
		rightPanel.setOpaque(false);

		fruit3Middle = new JLabel("");
		fruit3Middle.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		rightPanel.add(fruit3Middle);
		rightPanel.setOpaque(false);

		JPanel botPanel = new JPanel();
		botPanel.setBounds(10, 175, 460, 80);
		iconPanel.add(botPanel);
		botPanel.setBackground(Color.WHITE);
		botPanel.setLayout(null);

		JPanel leftPanelBot = new JPanel();
		leftPanelBot.setBounds(62, 5, 70, 70);
		botPanel.add(leftPanelBot);

		fruit1Blw = new JLabel("");
		fruit1Blw.setLocation(-41, 5);
		fruit1Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		leftPanelBot.add(fruit1Blw);
		leftPanelBot.setOpaque(false);

		JPanel centrePanelBot = new JPanel();
		centrePanelBot.setBounds(194, 5, 70, 70);
		botPanel.add(centrePanelBot);

		fruit2Blw = new JLabel("");
		fruit2Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		centrePanelBot.add(fruit2Blw);
		centrePanelBot.setOpaque(false);

		JPanel rightPanelBot = new JPanel();
		rightPanelBot.setBounds(326, 5, 70, 70);
		botPanel.add(rightPanelBot);

		fruit3Blw = new JLabel("");
		fruit3Blw.setIcon(new ImageIcon(slot.setImageRef(slot.genRandNum())));
		rightPanelBot.add(fruit3Blw);
		rightPanelBot.setOpaque(false);

		winningField = new JTextField();
		winningField.setEditable(false);
		winningField.setBounds(10, 375, 86, 20);
		contentPane.add(winningField);
		winningField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Winnings!");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBackground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Clarendon BT", Font.BOLD, 14));
		lblNewLabel.setBounds(-8, 350, 123, 25);
		contentPane.add(lblNewLabel);

		credField = new JTextField();
		credField.setBounds(523, 11, 86, 20);
		contentPane.add(credField);
		credField.setEditable(false);
		credField.setColumns(10);

		transferCredBtn = new JButton("Transfer");
		transferCredBtn.setForeground(Color.BLACK);
		transferCredBtn.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		transferCredBtn.addActionListener(this::actionPerformed);
		transferCredBtn.setBackground(Color.LIGHT_GRAY);
		transferCredBtn.setBounds(73, 502, 130, 40);
		contentPane.add(transferCredBtn);

		replayBtn = new JButton("Replay");
		replayBtn.setForeground(Color.BLACK);
		replayBtn.setFont(new Font("Clarendon BT", Font.PLAIN, 11));
		replayBtn.addActionListener(this::actionPerformed);
		replayBtn.setBackground(Color.LIGHT_GRAY);
		replayBtn.setBounds(479, 502, 130, 40);
		replayBtn.setEnabled(false);
		contentPane.add(replayBtn);

        JLabel roundsLeftLabel = new JLabel("Rounds left:");
        roundsLeftLabel.setForeground(Color.RED);
        roundsLeftLabel.setFont(new Font("Clarendon BT", Font.BOLD, 11));
        roundsLeftLabel.setBounds(452, 11, 105, 20);
        contentPane.add(roundsLeftLabel);


	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == spinBtn)
		{
			if(slot.canPlay()) {

				slotMachineLabel.setText("Spinning");
				spinAnimation();

				credField.setText(Integer.toString(slot.getRoundsLeft()));

				replayBtn.setEnabled(true);

				nudgeBtn1.setEnabled(true);
				nudgeBtn2.setEnabled(true);
				nudgeBtn3.setEnabled(true);

				spinBtn.setEnabled(false);

				sound.play("Resources/Sounds/test.wav");

			}
		}
		else if(e.getSource() == addCreditsBtn)
		{
			slot.addCoins();
			credField.setText(Integer.toString(slot.getRoundsLeft()));


		}
		else if(e.getSource() == cashOutBtn)
		{
			int reply = JOptionPane.showConfirmDialog(null, "Cash out?", "", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION)
			{
				JOptionPane.showMessageDialog(null,String.format("You've cashed out %d" , slot.cashOut()));
				winningField.setText(Integer.toString(slot.getCredits()));
			}

		}
		else if (e.getSource() == hldBtn1)
		{
            isHolding1 = true;
            hldBtn1.setEnabled(false);
            hldBtn2.setEnabled(false);
            hldBtn3.setEnabled(false);
            hldBtn1.setBackground(Color.RED);
		}
		else if (e.getSource() == hldBtn2)
		{
            isHolding2 =true;
            hldBtn1.setEnabled(false);
            hldBtn2.setEnabled(false);
            hldBtn3.setEnabled(false);
            hldBtn2.setBackground(Color.RED);
		}
		else if (e.getSource() == hldBtn3)
		{
            isHolding3 = true;
            hldBtn1.setEnabled(false);
            hldBtn2.setEnabled(false);
            hldBtn3.setEnabled(false);
            hldBtn3.setBackground(Color.RED);
		}

		else if(e.getSource() == nudgeBtn1)
		{
			nudge1();
			nudgeBtn1.setEnabled(false);
		}
		else if(e.getSource() == nudgeBtn2)
		{
			nudge2();
			nudgeBtn2.setEnabled(false);
		}
		else if(e.getSource() == nudgeBtn3)
		{
			nudge3();
			nudgeBtn3.setEnabled(false);
		}
		else if(e.getSource() == replayBtn)
        {
            if(canReplay)
            {
                replayLastRound();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Can't replay yet");
            }
        }
        else if(e.getSource() == transferCredBtn)
        {
            JOptionPane.showMessageDialog(null,String.format("You're cashing out %d P too",slot.transferWinningsToCreditRemainder()));
            slot.transferWinningsToCredit(slot.getCredits());
            credField.setText(Integer.toString(slot.getRoundsLeft()));
        }
	}
}

