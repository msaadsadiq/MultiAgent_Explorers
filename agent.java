package marsExplorer;


import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class agent extends JFrame implements ActionListener {

    JTextField[][] b = new JTextField[10][10];
    JButton bt;
    int face;//1 u;2 d; 3 l; 4 r
    int i;
    int j;
    int moves;
    
    int[][] signalStrength = {   { 55,52,51,42,41,32,31,22,21,10},   // The signal strength matrix 
    							 { 60,61,52,51,42,41,32,31,22,21},	 // This matrix is used by the agent when it is returning back 
    							 { 65,62,61,52,51,42,41,32,31,22},   // after collecting the rock
    							 { 70,71,62,61,52,51,42,41,32,31},
    							 { 75,72,71,62,61,52,51,42,41,32},
    							 { 80,81,72,71,62,61,52,51,42,41},
    							 { 85,82,81,72,71,62,61,52,51,42},
    							 { 90,85,82,81,72,71,62,61,52,51},
    							 { 95,90,85,82,81,72,71,62,61,52},
    							 {100,95,90,85,80,75,70,65,60,55},
    						};
  
  
  
    void Search() {
        Random rn = new Random();
        int n = 4;
        int nface = Math.abs(rn.nextInt() % n);
      
                    
                    System.out.printf("X= %d, Y= %d, Facing= %d \n",i,j,face);
                    JOptionPane.showMessageDialog(this, "step");
                    
                    
        switch (face) {
            case 0:
                if (moves%5==0 || i + 1 > 9 || b[i + 1][j].getText().equalsIgnoreCase("R+Cr")) {
                    face = nface;
                    System.out.println(nface);
                
                } else if(b[i + 1][j].getText().equalsIgnoreCase("R")){
                	 b[i][j].setText(""); i++;
                	dropCrumb();
                	returnHome();
                	b[i][j].setText("A");
                   
                	
                }	else if(b[i + 1][j].getText().equalsIgnoreCase("o")){
                	b[i][j].setText("");
                	manueverObstacle();
                	
                 	
                }else {
                
                    b[i + 1][j].setText("A");
                    b[i][j].setText("");
                    i++;
                    
                }
                moves++;  
                break;
            case 1:
                if (moves%5==0 || i - 1 < 0 || b[i - 1][j].getText().equalsIgnoreCase("R+Cr")) {
                    face = nface;
                    System.out.println(nface);
                } else if(b[i -1][j].getText().equalsIgnoreCase("R")){
                	 b[i][j].setText(""); i--;
                    dropCrumb();
                    returnHome();
                    b[i][j].setText("A");
                 	
                 }	else if(b[i - 1][j].getText().equalsIgnoreCase("o")){
                	 b[i][j].setText("");
                 	manueverObstacle();
                 	
                  	
                 }else {
                    b[i - 1][j].setText("A");
                    b[i][j].setText("");
                    i--;
                    
                }
                moves++;   
                break;
            case 2:
                if (moves%5==0 || j - 1 < 0 || b[i][j - 1].getText().equalsIgnoreCase("R+Cr")) {
                    face = nface;
                    System.out.println(nface);
               
                } else if(b[i][j-1].getText().equalsIgnoreCase("R")){
                	 b[i][j].setText(""); j--;
                	dropCrumb();
                    returnHome();
                    b[i][j].setText("A");
                 	
                 }	else if(b[i][j-1].getText().equalsIgnoreCase("o")){
                	 b[i][j].setText("");
                 	manueverObstacle();
                 	
                  	
                 }else {
                    b[i][j - 1].setText("A");
                    b[i][j].setText("");
                    j--;
                    
                }
                moves++;   
                break;
            case 3:
                if (moves%5==0 || j + 1 > 9 || b[i][j + 1].getText().equalsIgnoreCase("R+Cr")) {
                    face = nface;
                    System.out.println(nface);
              
                } else if(b[i][j + 1].getText().equalsIgnoreCase("R")){
                	 b[i][j].setText(""); j++;
                    dropCrumb();
                    returnHome();
                    b[i][j].setText("A");
                 	
                 }	else if(b[i][j + 1].getText().equalsIgnoreCase("o")){
                	
                	 b[i][j].setText("");
                 	manueverObstacle();
                 	
                  	
                 }else {
                    b[i][j + 1].setText("A");
                    b[i][j].setText("");
                    j++;
                    
                }
                moves++;   
                break;
        }
       
           
        
    }

  
    public agent() {
        super("EE674 Assignment 2, Mars explorer Agent");
        bt = new JButton("Start");
        Container content = this.getContentPane();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 10));
        for (int i = 0; i < 10; i++) {
           
            for (int j = 0; j < 10; j++) {
                b[i][j] = new JTextField();
               
                panel.add(b[i][j], j);
            }
        }
        bt.addActionListener(this);
        content.add(new JLabel("A: Agent ; o: obstacle ; R: Rock ; Cr: Crumb"), BorderLayout.PAGE_END);
        content.add(bt, BorderLayout.AFTER_LINE_ENDS);
        content.add(panel);
      
        this.setSize(860, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

   
    public void actionPerformed(ActionEvent be) {
       
           
            placeAgent();
            
            face = 3;
            int l[] = new int[2];
            i = l[0];
            j = l[1];
            moves =1;
            
            placeObstacles();
            placeRocks();
            
            System.out.println("Starting Exploration");
            while (true) {
                Search();
            }
        
    }

    void placeAgent() {
        outerloop:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (b[i][j].getText().equalsIgnoreCase("")) {
                    b[i][j].setText("A");
                    break outerloop;
                }
            }
        }
    }

    
    void manueverObstacle(){
    	//b[i][j].setText("M");
    	 switch (face) {   
         case 0: // facing up
        	 System.out.println("Manuevering around the obstacle");
         	 j--;; System.out.print(j);
         	 i=i+2; System.out.print(i);
         	 j++; System.out.println(j);
         	 b[i][j].setText("A");
         	 break;
         	 
         case 1: // facing down
        	 System.out.println("Manuevering around the obstacle");
          	 j++; System.out.print(j);
          	 i=i-2; System.out.print(i);
          	 j--; System.out.println(j);
          	 b[i][j].setText("A");
          	break;
          	
         case 2: // facing left
        	 
        	 System.out.println("Manuevering around the obstacle");
         	 i--; System.out.print(i);
         	 j=j-2; System.out.print(j);
         	 i++; System.out.println(i);
         	 b[i][j].setText("A");
         	break;
         	
         case 3: // facing right	 
        	 System.out.println("Manuevering around the obstacle");
        	 i++; System.out.print(i);
        	 j=j+2; System.out.print(j);
        	 i--; System.out.println(i);
        	 b[i][j].setText("A");
        	 break;
        	 
    	 }
    }
    
    
    void placeObstacles(){
    	
      	 b[4][2].setText("o");
      	 b[2][8].setText("o");
      	 b[6][4].setText("o");

      }
    
    
    void placeRocks(){
    	
     
     	b[7][2].setText("R");
     	b[4][8].setText("R");
       	b[9][1].setText("R");
     	b[7][7].setText("R");
     	b[6][9].setText("R");
     	b[9][8].setText("R");
     	

     }
    
    
  void dropCrumb(){
    	
     	b[i][j].setText("R+Cr");
     	   	

     }
  
  
  void returnHome(){
  	
	  System.out.println("Returning Rock back to Mother Ship");
	  
	  while(j>0) {
			
		  if(signalStrength[i][j] < signalStrength[i][j-1]) {
		  
		  System.out.print("Checking Signal; Traversing Horizontally --");
			System.out.print(i);
			System.out.println(j);
			
			
				j=j-1;
		  }
			
	  }
	  while(i>0){
		  System.out.print("Checking Signal; Traversing Vertically -- ");
		  i--;
	   
			System.out.print(i);
			System.out.println(j);
			
			
			
	  
	  }
		
  }



    public static void main(String[] args) {
        new agent().setVisible(true);
    }
}
