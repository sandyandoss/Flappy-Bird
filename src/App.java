import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWith = 360;
        int boardHeight = 640;
        JFrame frame = new JFrame("Flappy Bird");
        // frame.setVisible(true);
        frame.setSize(boardWith,boardHeight);
        frame.setLocationRelativeTo(null); //bt7etta bl center
        frame.setResizable(false); //cant change the size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close when pressing x
      
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack(); //nshil l appbar mn l dimentions
        flappyBird.requestFocus(); 
        frame.setVisible(true);

    
    
    }
}
