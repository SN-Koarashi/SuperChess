package SuperChees;

/*
 * mainWindow.java
 * �{���Ұʮɪ��Ĥ@�ӭ����A�غc�ê�l�� Board.java ���X�ʾ�ӹC��
 */

import java.awt.EventQueue;
import javax.swing.UIManager;


public class mainWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// �]�w�������欰�t�έ���
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					// ��l�ƴѽL�Ψ�����\��
					new Board();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
