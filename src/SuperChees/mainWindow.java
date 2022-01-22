package SuperChees;

/*
 * mainWindow.java
 * 程式啟動時的第一個頁面，建構並初始化 Board.java 來驅動整個遊戲
 */

import java.awt.EventQueue;
import javax.swing.UIManager;


public class mainWindow {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 設定介面風格為系統風格
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					// 初始化棋盤及其相關功能
					new Board();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
