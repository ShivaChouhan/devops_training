import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Fdemo extends JFrame
{
	JPdemo jp1;
	Fdemo()
	{
		super.setTitle("Snake & Ladder");
		jp1=new JPdemo();
		add(jp1);
	}
}
class JPdemo extends JPanel implements ActionListener
{
	int s=0,s1=0,i=1;
	boolean st=false;
	JButton b1,b2,b3,b4;
	String df;
	JTextField t1,t2,t3;
	ImageIcon img1,img2,img3,img4;
	ImageIcon img5,img6;
	ImageIcon img7,img8;
	ImageIcon img9,img10,img11;
	Image swt,board,sl,start,dice,dice1;
	Image player1,player2,player11,player12;
	int px1=40;
	int py1=620;
	int px2=120;
	int py2=620;
	JPdemo()
	{
		setBackground(Color.black);
		img1=new ImageIcon("swt.jpeg");
		img2=new ImageIcon("board.jpeg");
		img3=new ImageIcon("sl.jpeg");
		img4=new ImageIcon("start.jpeg");
		img11=new ImageIcon(startgif.gif");
		img5=new ImageIcon(aboutgif.gif");
		img6=new ImageIcon("reset.jpeg");
		
		img7=new ImageIcon("player1.jpeg");
		img8=new ImageIcon("player2.jpeg");
		
		img9=new ImageIcon("dice.jpeg");
		img10=new ImageIcon(dicegif.gif");
	
		Font front=new Font("Algerian",Font.BOLD,20);
		
		swt=img1.getImage();
		board=img2.getImage();
		sl=img3.getImage();
		start=img4.getImage();
		
		
		player1=img7.getImage();

		player2=img8.getImage();
				
		player11=img7.getImage();
		player12=img8.getImage();
		
		dice=img9.getImage();
		dice1=img10.getImage();
		
		setLayout(null);
		b1=new JButton(img5);
		b1.setBounds(50,120,100,30);
		add(b1);
	    b1.setBackground(new Color(100,209,225));
		
		b2=new JButton(img6);
		b2.setBounds(50,180,100,30);
		add(b2);
		b2.addActionListener(this);
		
		b3=new JButton("ROLL");
		b3.setBounds(50,500,100,30);
		add(b3);
		b3.setFont(front);
		b3.setForeground(Color.red);
		b3.addActionListener(this);
		
		b4=new JButton(img11);
		b4.setBounds(20,560,150,60);
		add(b4);
		b4.addActionListener(this);
		b4.setFont(front);
		b4.setForeground(Color.red);
		
		t1=new JTextField("START GAME");
		t1.setBounds(50,230,150,35);
		add(t1);
		t1.setBackground(Color.black);
		t1.setForeground(Color.green);
		t1.setFont(front);
		
		t2=new JTextField("PLAYER 1");
		t2.setBounds(50,290,150,35);
		add(t2);
		t2.setBackground(Color.black);
		t2.setForeground(Color.green);
		t2.setFont(front);
		
		t3=new JTextField("PLAYER 2");
		t3.setBounds(50,375,150,35);
		add(t3);
		t3.setBackground(Color.black);
		t3.setForeground(Color.green);
		t3.setFont(front);
		
		
	}
	public void  paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(new Color(100,209,225));
		g.fillRect(0,0,955,735);
		// g.drawImage(swt,0,0,this);
		g.drawImage(board,200,0,this);
		g.drawImage(sl,900,0,this);
		g.drawImage(start,20,550,this);
		
		
		g.drawImage(player1,px1,py1,this);
		g.drawImage(player2,px2,py2,this);
		
		g.drawImage(player11,10,280,this);
		g.drawImage(player12,10,360,this);
		
		g.drawImage(dice1,10,420,this);
		g.drawImage(dice,110,430,this);
		
		if(s==100||s1==100)
		{
		g.setColor(Color.black);
		g.setFont(new Font("Algerian",Font.BOLD,50));
		g.drawString(df,350,350);
		}
		if(s==0||s1==0)
		{
		g.drawString("",350,350);
		}
		
	
	}
	public void  actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b2)
		{
			px1=40;
        	py1=620;
	        px2=120;
	        py2=620;
			if(s==100||s1==100)
			b3.addActionListener(this);
			st=false;
			s=0;
			s1=0;
			i=1;
			img9=new ImageIcon("dice.jpeg");
			 
			 repaint();
		}
     	

		dice=img9.getImage();
		if(e.getSource()==b4)
		{
			st=true;
		}
		if(st)
		if(e.getSource()==b3)
		{
	
			int random=(int)Math.round(Math.random()*5+1);
		switch(i)
		{
		
		case 1:
			if(s+random<=100)
			{
			s=s+random;
			}	
				if(s<=100)
				{
				px1=210;
				py1=645;
				int k=1;
				int count=s;
				int count1=0;
				M:
				for(int i=1;i<=10;i++)
				{
					if(k==11)
					{
						px1-=70;
						k--;
					}
					if(k==0)
					{
						px1+=70;
						k++;
					}
					for(int j=1;j<=10;j++)
					{
						count1++;
			switch(random)
			{
				case 1:
            		img9=new ImageIcon("dice1.jpeg");
                 
				break;
				case 2:
            		img9=new ImageIcon("dice2.jpeg");	
                 
				break;
				case 3:
            		img9=new ImageIcon("dice3.jpeg");	
                 
				break;
				case 4:
            		img9=new ImageIcon("dice4.jpeg");	
                 
				break;
				case 5:
            		img9=new ImageIcon("dice5.jpeg");	
                 
				break;
				case 6:
            		img9=new ImageIcon("dice6.jpeg");	
                    //i=1; 
				break;
			}
				
						if(count==count1)break M;
						if(i%2==1)
						{
							px1+=70;
							k++;
						}
						else
						{
							if(px1>250)
							{
							px1-=70;
							k--;
							}
						}
						
					}
					if(py1>70)
					{
					 py1-=70;
					}
				}
     		dice=img9.getImage();
				}
		if(random!=6)
		{
           i=2;
		   t1.setText("player 2 ch");
		}	
		else
		{
			i=1;
			t1.setText("agn plr 1 ch");

		}
		
		//SNAKE
		
		if(s==27)
		{
			s=5;
			px1=490;
			py1=645;

		}
		if(s==40)
		{
			s=3;
			px1=350;
			py1=645;

		}
		if(s==43)
		{
			s=18;
			px1=350;
			py1=575;

		}
		if(s==54)
		{
			s=31;
			px1=840;
			py1=435;

		}
		if(s==66)
		{
			s=45;
			px1=490;
			py1=365;

		}
		if(s==76)
		{
			s=58;
			px1=350;
			py1=295;

		}
		if(s==89)
		{
			s=53;
			px1=700;
			py1=295;

		}
		if(s==99)
		{
			s=41;
			px1=210;
			py1=355;

		}
		//SNAKE OVER HERE
		
		//STAIERS
		if(s==4)
		{
			s=25;
			px1=490;
			py1=500;	
		}
		if(s==13)
		{
			s=46;
			px1=560;
			py1=360;	
		}
		if(s==33)
		{
			s=49;
			px1=770;
			py1=360;	
		}
		if(s==42)
		{
			s=63;
			px1=280;
			py1=220;	
		}
		if(s==50)
		{
			s=69;
			px1=770;
			py1=220;	
		}
		if(s==62)
		{
			s=81;
			px1=210;
			py1=80;	
		}
		if(s==74)
		{
			s=92;
			px1=630;
			py1=10;	
		}
		//STAIERS OVER HERE
	  if(s==100)
	   {
		   
		   df="Player 1 win";
			t1.setText(df);
			b3.removeActionListener(this);
		}
			repaint();
	  break;
			case 2:
			if(s1+random<=100)
			{
			s1=s1+random;
			}	
				if(s1<=100)
				{
				px2=240;
				py2=645;
				int k=1;
				int count=s1;
				int count1=0;
				M:
				for(int i=1;i<=10;i++)
				{
					if(k==11)
					{
						px2-=70;
						k--;
					}
					if(k==0)
					{
						px2+=70;
						k++;
					}
					for(int j=1;j<=10;j++)
					{
						count1++;
					
			switch(random)
			{
				case 1:
            		img9=new ImageIcon("dice1.jpeg");
                 
				break;
				case 2:
            		img9=new ImageIcon("dice2.jpeg");	
                 
				break;
				case 3:
            		img9=new ImageIcon("dice3.jpeg");	
                 
				break;
				case 4:
            		img9=new ImageIcon("dice4.jpeg");	
                 
				break;
				case 5:
            		img9=new ImageIcon("dice5.jpeg");	
                 
				break;
				case 6:
            		img9=new ImageIcon("dice6.jpeg");	 
				break;
			}
				
						if(count==count1)break M;
						if(i%2==1)
						{
							px2+=70;
							k++;
						}
						else
						{
							if(px2>250)
							{
							px2-=70;
							k--;
							}
						}
						
					}
					if(py2>70)
					{
					 py2-=70;
					}
				}
     		dice=img9.getImage();
				}
		if(random!=6)
		{
			t1.setText("player 1 ch");
            i=1;
		}	
		else
		{
			t1.setText("agn plr 2 ch");
			i=2;
		}
		
		//SNAKE CONDITIOS
		if(s1==27)
		{
			s1=5;
			px2=520;
			py2=645;
	
		}
		if(s1==40)
		{
			s1=3;
			px2=380;
			py2=645;
	
		}
		if(s1==43)
		{
			s1=18;
			px2=380;
			py2=575;
	
		}
		if(s1==54)
		{
			s1=31;
			px2=870;
			py2=435;
	
		}
		if(s1==66)
		{
			s1=45;
			px2=520;
			py2=365;
	
		}
		if(s1==76)
		{
			s1=58;
			px2=380;
			py2=295;
		
		}
		if(s1==89)
		{
			s1=53;
			px2=730;
			py2=225;
		
		}
		if(s1==99)
		{
			s1=41;
			px2=240;
			py2=375;
		
		}
		
		//SNAKE CONDITIONS OVER HERE
		
		//STAIRES CONDITIONS
		
		if(s1==4)
		{
			s1=25;
			px2=520;
			py2=500;	
		}
		if(s1==13)
		{
			s1=46;
			px1=560;
			py1=360;	
		}
		if(s1==33)
		{
			s1=49;
			px1=770;
			py1=360;	
		}
		if(s1==42)
		{
			s1=63;
			px1=280;
			py1=220;	
		}
		if(s1==50)
		{
			s1=69;
			px1=770;
			py1=220;	
		}
		if(s1==62)
		{
			s1=81;
			px1=210;
			py1=80;	
		}
		if(s1==74)
		{
			s1=92;
			px1=630;
			py1=10;	
		}
		//STAIERS OVER HERE
			
				
				if(s1==100)
				{
				   
				    df="Player 2 win";
					t1.setText(df);
					b3.removeActionListener(this);
				}
			repaint();
            break;
		}
	}
	
	}
	

}
class SnakeLadder
{
	public static void  main(String[] ar)
	{
		Fdemo front=new Fdemo();
		front.setVisible(true);
		//front.setBounds(x,y,w,h);
		front.setBounds(100,-40,955,735);
		front.setResizable(false);
		front.setDefaultCloseOperation(front.EXIT_ON_CLOSE);
	}
}
