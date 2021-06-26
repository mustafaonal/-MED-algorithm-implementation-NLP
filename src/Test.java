import java.awt.EventQueue;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Test extends JFrame {

	/**
	 * Launch the application.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

	}

	/**
	 * Create the frame.
	 * @return 
	 */
	public Test() {

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 800, 436);
			getContentPane().setLayout(null);

			Button button = new Button("5 alternative correct words");
			Button button1 = new Button(" find the MED value between two words");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Test1 frame1 = new Test1(1);
						frame1.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			button1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Test1 frame1 = new Test1(2);
						frame1.setVisible(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			button.setBounds(573, 57, 200, 21);
			getContentPane().add(button);
			button1.setBounds(173, 57, 220, 21);
			getContentPane().add(button1);
	}
	

	

}
