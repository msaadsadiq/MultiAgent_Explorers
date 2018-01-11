package vaccumCleaner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class vaccum extends JFrame implements ActionListener {

    JTextField[][] b = new JTextField[5][5];
    JButton bt;
    int face;//0 u;1 d; 2 l; 3 r
    int i=0;
    int j=0;
    int moves;
    
  
  
	void move() {

		//printing the coordinates and facing of the agent
		System.out.printf("X= %d, Y= %d, Facing= %d \n", i, j, face);
		
		//stepping same as in debugging
		JOptionPane.showMessageDialog(this, "step");
		
		switch (face) { // 4 cases for the up down left right. based on the face
						// direction the agent moves 1 step
		
		case 0:
			
				// The detector checks 1 block ahead of the agent for wall or previously traversed path
			if (i + 1 > 4 || b[i + 1][j].getText().equalsIgnoreCase("A")) { 
				face = 2;  // if wall is reached, turn face left
				System.out.println(face);

				// The detector checks 1 block ahead of the agent for dirt
			} else if (b[i + 1][j].getText().equalsIgnoreCase("*")) {
				System.out.printf("Cleaning dirt at %d,%d \n", i + 1, j);
				b[i + 1][j].setText("C");
				i++;
			}
				// The detector checks 1 block ahead of the agent for obstacle
			 else if (b[i + 1][j].getText().equalsIgnoreCase("o")) {
				manueverObstacle();
			} 
			 	//seems like an empty clean block ahead, move the agent forward 
			 else {
				b[i + 1][j].setText("A");
				i++;
			}
			moves = moves + 1;
			break;
		case 1:
			if (i - 1 < 0 || b[i - 1][j].getText().equalsIgnoreCase("A")) {

				face = 3;  // if wall is reached, turn face right
				System.out.println(face);
			} else if (b[i - 1][j].getText().equalsIgnoreCase("*")) {
				System.out.printf("Cleaning dirt at %d,%d \n", i - 1, j);
				b[i - 1][j].setText("C");
				i--;
			} else if (b[i - 1][j].getText().equalsIgnoreCase("o")) {
				manueverObstacle();
			} else {
				b[i - 1][j].setText("A");
				i--;
			}
			moves = moves + 1;
			break;
		case 2:
			if (j - 1 < 0 || b[i][j - 1].getText().equalsIgnoreCase("A")) {

				face = 1; // if wall is reached, turn face down
				System.out.println(face);
			} else if (b[i][j - 1].getText().equalsIgnoreCase("*")) {
				System.out.printf("Cleaning dirt at %d,%d \n", i, j - 1);
				b[i][j - 1].setText("C");
				j--;
			} else if (b[i][j - 1].getText().equalsIgnoreCase("o")) {
				manueverObstacle();
			} else {
				b[i][j - 1].setText("A");
				// b[i][j].setText("c");
				j--;
			}
			moves = moves + 1;
			break;
		case 3:
			if (j + 1 > 4 || b[i][j + 1].getText().equalsIgnoreCase("A")) {

				face = 0;  // if wall is reached, turn face up
				System.out.println(face);
			} else if (b[i][j + 1].getText().equalsIgnoreCase("*")) {
				System.out.printf("Cleaning dirt at %d,%d \n", i, j + 1);

				b[i][j + 1].setText("C");
				j++;
			} else if (b[i][j + 1].getText().equalsIgnoreCase("o")) {
				manueverObstacle();
			} else {
				b[i][j + 1].setText("A");

				j++;
			}
			moves = moves + 1;
			break;
		}

	}

    
    public vaccum() {  
    	
    	//container to visually depict the grid layout and movement of the agent
    	
        super("EE674 Assignment 2 vacuum agent");
        bt = new JButton("Start Cleaning");
        Container content = this.getContentPane();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5));
        for (int i = 0; i < 5; i++) {
           
            for (int j = 0; j < 5; j++) {
                b[i][j] = new JTextField();
                panel.add(b[i][j], j);
            }
        }
        bt.addActionListener(this);
        
        //adding legends
        content.add(new JLabel("A: Agent ; o: obstacle ; *: dirt ; M: Manuever ; R: Returning"), BorderLayout.PAGE_END);
        content.add(bt, BorderLayout.AFTER_LINE_ENDS);
        content.add(panel);
        this.setSize(660, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void actionPerformed(ActionEvent be) {
   
    	//When the start button is pressed, run the following code
        
            placeAgent();
            placeDirt();
            placeObstacles();
           
            face = 3;
            int l[] = new int[2];
            i = l[0];
            j = l[1];
            moves=1;
            
            System.out.println("Starting Cleaning");
            while (!(b[2][2].getText().equalsIgnoreCase("A"))) {    //until the agent reaches the center location
                move();
            }   returnAgent();// call the return function to return agent to charging station/starting position
        }


    
    
    
    
    void manueverObstacle(){
    	System.out.println("Found Obstacle");
    	 switch (face) {   
         case 0: // facing up
        	 System.out.println("Manuevering around the obstacle");
         	 j--;; System.out.print(j);
         	 i+=2; System.out.print(i);
         	 j++; System.out.println(j);
         	 b[i][j].setText("A");
         	 
         case 1: // facing down
        	 System.out.println("Manuevering around the obstacle");
          	 j++; System.out.print(j);
          	 i-=2; System.out.print(i);
          	 j--; System.out.println(j);
          	 b[i][j].setText("A");
         case 2: // facing left
        	 j=j-2;
        	 System.out.println("Manuevering around the obstacle");
         	 i--; System.out.print(i);
         	 j-=2; System.out.print(j);
         	 i++; System.out.println(i);
         	 b[i][j].setText("A");
         case 3: // facing right	 
        	 System.out.println("Manuevering around the obstacle");
        	 i++; System.out.print(i);
        	 j+=2; System.out.print(j);
        	 i--; System.out.println(i);
        	 b[i][j].setText("A");
    	 }
    }
    
    
    void returnAgent(){
    	System.out.println("Room Cleaned, returning to charging station");
    	face =2;
    	while(i>0){
    	b[i][j].setText("R");
    	i--;
    }
    	
    	face=3;
    	while(j>0){
    		b[i][j].setText("R");
    		j--;
    	}
    	
    	b[0][0].setText("R");
    	
    }
    
    
    void placeAgent() {
        outerloop:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (b[i][j].getText().equalsIgnoreCase("")) {
                    b[i][j].setText("A");
                    break outerloop;
                }
            }
        }
    }

    
    void placeDirt(){
    	 b[0][2].setText("*");
    	 b[2][1].setText("*");
    	 b[3][2].setText("*");
    	 
    }
    
    void placeObstacles(){
    	
   	 b[1][2].setText("o");

   }

   
   
    
    public static void main(String[] args) {
        new vaccum().setVisible(true);
    }
}

