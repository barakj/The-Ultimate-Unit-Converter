import javax.swing.JFrame;
/**
 * A class that is used to launch the program with a certain size and title
 */
public class Starter {

	public static void main(String[] args) {
		JFrame frame = new MyFrame();
		frame.setSize(500,600);
		frame.setTitle("The Ultimate Unit Converter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
