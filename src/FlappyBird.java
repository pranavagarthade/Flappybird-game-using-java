import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ArrayList.*;
import java.util.Random;
import java.util.Random.*; // this helps you to to put the bars on screen randomly
import javax.swing.*;


// so here Jpanel is inherited from swing library which is use to create flow layoout for game
public class FlappyBird extends JPanel implements ActionListener, KeyListener{ 

    int boradWidth =360;
    int boradHeight=640;


    //Bird
    int birdX = boradWidth/8;
    int birdy = boradHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird{
        int  x = birdX;
        int y = birdy;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img ){ //here is the condstructor for bird class 
            this.img = img;
        }
    }

    //Pipes
    int pipeX = boradWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed  = false;

        Pipe(Image img){
            this.img = img;
        }

    }


    //game logic
    Bird bird;
    int velocityX = -4; // move  pipe to the left which will simulate bird moving right
    int velocityY = 0;// move bird up and down
    int gravity = 1;


    //declarations
    ArrayList<Pipe> pipes;
    Random random = new Random();
    boolean gameOver = false;
    double score = 0;

    //gameloop
    Timer gameloop;

    Timer placePipeTimer;
   

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    FlappyBird(){
        setPreferredSize(new Dimension (boradWidth,boradHeight));
        // setBackground(Color.cyan);


        setFocusable(true);
        addKeyListener(this);



         //making constructor for images 
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

         //place pipes timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // Code to be executed
              placePipes();
            }
        }); placePipeTimer.start();

        //gameloop
        gameloop = new Timer(1000/60, this);
        gameloop.start();
     }


     public void placePipes(){

        //this logic is to generate  random top pipes in a loop  
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boradHeight/4; // this will keep the space for the bird to fly

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);



     }


     public void paintComponent(Graphics g){
        // System.out.println("hh");

         super.paintComponent(g);
         draw(g);
     }  

     public void  draw(Graphics g){
         //backgroud
         g.drawImage(backgroundImg,0,0,boradWidth,boradHeight,null);

         //bird
         g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height, null);

         //pipe
         for(int i =0; i< pipes.size();i++)
         {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);

         }

         //score
         g.setColor(Color.BLACK);
         g.setFont(new Font("Arial",Font.PLAIN,35));
         if(gameOver){
            g.drawString("Your Done with: " +String.valueOf((int) score),10,35);
         }
         else{
            g.drawString(String.valueOf((int) score),10,35);
         }
     }



     // movement tracking of objects 
     public void move(){
         // we'll fit x n y cooridinates of object 
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y,0);

        //for pipes
        for(int i = 0 ; i< pipes.size();i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;


            // showing Socre part where when the bird collids the pipe or misses at start it will stop and show the score. 
            // here pipe.x is right side and pipe.width is left side of the game in terms of pipe
            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;
                score += 0.5; // here we hae added 0.5 for each pipe so when the usser passes 2 pipe point would be 1. 
            }



            
        if (collision(bird, pipe)) {
            gameOver = true;
         }
    }

        if(bird.y > boradHeight ){
            gameOver = true;

        }
        
     }

     boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameOver){
            placePipeTimer.stop();
            gameloop.stop();
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            velocityY = -9;
        }

        if (gameOver) {
            //restart game by resetting conditions
            bird.y = birdy;
            velocityY = 0;
            pipes.clear();
            gameOver = false;
            score = 0;
            gameloop.start();
            placePipeTimer.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    @Override
    public void keyReleased(KeyEvent e) {}
}
