import java.awt.*; // Import the Abstract Window Toolkit (AWT) classes eg. color font graphics
import java.awt.event.*;  // Import AWT event classes for handling user interactions ActionListener, MouseListener, and KeyListener.
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener , KeyListener{
    int boardWidth = 360;
    int boardHeight = 640;


    //images variables
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //add few variables for the bird x w y positions
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;


    //create a class to hold these values

    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
    


        //constructor for the image
        Bird(Image img){
            this.img = img;
        }
    }

    //pipes

    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 514;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;


       Pipe(Image img){
       this.img=img;
       } 
    }

    //game logic

    Bird bird;

    //velocity
    int velocityX =-4; //move to keft so the bird seems to move to right
    int velocityY =0;
    int gravity  = 1;
  
   //li2n eena 2 pipes up w buttom so we do array
ArrayList<Pipe>pipes; 

Random random = new Random();
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;
    
   //constructor 
     FlappyBird(){
        setPreferredSize(new Dimension(boardWidth,boardHeight));
        // setBackground(Color.blue);



        setFocusable(true);
        addKeyListener(this);
        //to load the images

        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg= new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
       //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();
     
        //place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });

        placePipesTimer.start();
        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start();

    
    } 



     //create a function for the pipes
     public void placePipes(){
        //(0-1) * pipeheight/2 -> 256
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int  openingSpace = boardHeight/4;
        
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y+ pipeHeight  + openingSpace;
        pipes.add(bottomPipe);
     }




    //define a function
    public void paintComponent(Graphics g){
        super.paintComponent(g); //to envoke it mn jpanel
        draw(g);
    }

    public void draw(Graphics g){
        //draw the background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
       //bird drawing
       g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    // pipes drawing
    for (int i =0; i<pipes.size(); i++){
        Pipe pipe = pipes.get(i);
        g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
    }
       //score
       g.setColor(Color.white);
       g.setFont(new Font("Arial", Font.PLAIN,32));
       if (gameOver){
        g.drawString("Game Over: " + String.valueOf((int)score),10, 35 );
       }
       else g.drawString(String.valueOf((int)score),10, 35);
    }


    public void move(){
        //update here the birds x and y
        velocityY += gravity;     
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0);

        //pipes
        for(int i=0; i<pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5;
            }
            if (collision(bird,pipe)){
                gameOver = true;
            }
        }
        if (bird.y > boardHeight){
            gameOver=true;
        }
    }

    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();    
        if (gameOver){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }


    


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
            if (gameOver){
                //restart the game by reseting the conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score=0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
       
    }
    @Override
    public void keyTyped(KeyEvent e) {
   
    }

    @Override
    public void keyReleased(KeyEvent e) {
     
    }
    
}
