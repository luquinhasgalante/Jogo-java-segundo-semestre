import java.awt.BorderLayout;

import javax.swing.*;

public class Game {
	public static void main(String[] args) {
		int largura = 720;
		int altura = 250;

		JFrame frame = new JFrame();
		Menu menu = new Menu(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(largura, altura);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		frame.add(menu);
		frame.setLocationRelativeTo(null);
		
		
		menu.requestFocus();
		frame.pack();
		frame.setVisible(true);
	}
}
