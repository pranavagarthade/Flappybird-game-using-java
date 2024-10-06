import javax.swing.JFrame;


public class App {
    public static void main(String[] args) throws Exception {                               

        int boradWidth = 360;
        int boradHeight = 640;

        JFrame frame = new JFrame("Fly Panna");
        // frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(boradWidth,boradHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
     
        
    }
}
