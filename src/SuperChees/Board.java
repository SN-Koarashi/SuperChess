package SuperChees;

/*
 * Board.java
 * 	關於介面的繪製處理
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class Board extends JFrame implements ActionListener{
	
	
	// 初始化物件及變數
	private JPanel panel;
	private JPopupMenu popup = new JPopupMenu();
	
	private ImageIcon appIcon = new ImageIcon(getClass().getResource("/images/outline_r.png")); // 程式圖標
	private ImageIcon checkIcon = new ImageIcon(getClass().getResource("/images/check.png")); // 打勾符號
	private ImageIcon chessIcons[] = { new ImageIcon(getClass().getResource("/images/chess_b.png")), // 黑棋
			new ImageIcon(getClass().getResource("/images/chess_w.png"))}; // 白棋
	private ImageIcon background = new ImageIcon(getClass().getResource("/images/bg.png")); // 背景圖片
	private boolean onTop = false; // 置頂狀態
	
	// 建構遊戲機制及相關變數
	private Game g = new Game();
	
	Board(){
		
		ContextMenuCreator(); // 建立右鍵選單
		setJMenuBar(menuBarCreator()); // 建立工具列
	    setTitle("超級五子棋"); // 設定標題
	    setIconImage(appIcon.getImage()); // 設定圖標
		setResizable(false); // 設定不可縮放
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 設定按下關閉時程式的動作為關閉程式
		setVisible(true); // 顯示視窗
		
		// 繪製新棋盤並開始遊戲
		drawGameBoard(g.gameSize);
	}


	// 繪製新棋盤並開始遊戲
	private void drawGameBoard(int size) {
		// 取得螢幕寬高
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		// 設定大小
		int bRow = size;
		int bCol = size;
		int w = bCol * 32;
		int h = bRow * 32;

		g.board = new int[size][size];
		
		// 如果遊戲是第一次啟動，則將視窗顯示在中央
		if(!g.firstStart) {
			setLocationRelativeTo(null);
			setLocation((int) screen.width / 2 - w / 2,(int) screen.height / 2 - h / 2);
	        g.firstStart = true;
		}
		
		// 建立並繪製棋盤
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setLayout(new GridLayout(bRow, bCol, 0, 0));
		panel.setBackground(new Color(233,199,127));
		setContentPane(panel);
		
		// 設定大小
		setSize(w, h);
		
		// 依據棋盤大小繪製按鈕數
		for (int k = 0; k < bRow * bCol; k++) {
			JButton btn = new JButton();
			btn.setActionCommand(String.valueOf(k));
			btn.addActionListener(this);
			btn.setIcon(background);
			btn.setBorderPainted(false);
			btn.setContentAreaFilled(false);
			btn.setBackground(new Color(233,199,127));
			
			// 對按鈕按下右鍵時顯示選單
			btn.addMouseListener(new MouseAdapter() {
			   public void mouseReleased(MouseEvent me) {
			     showPopup(me); // showPopup() is our own user-defined method
			   }
			});
			
			panel.add(btn);
		}
	}
	
	private void ContextMenuCreator() {
		// 建立選項
		JMenuItem menuRepent = new JMenuItem("悔棋");
	    JMenuItem menuRestart = new JMenuItem("重新開始");
	    JMenuItem menuNewgame = new JMenuItem("新的棋局");
	    
	    // 增加點擊事件
	    menuRepent.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	
	        	
	        	swapTop();
	        	
	        	// 鎖定並提醒遊戲結束
	        	if(g.locked) {
	        		JOptionPane.showMessageDialog(null, "本次棋局已經結束", "提示", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        	else {
		        	// 顯示悔棋方
		        	String chess = (g.tokener == 2)?"黑棋":"白棋";
		        	int dialogResult = JOptionPane.showConfirmDialog (null, "確定要反悔嗎? " + chess +"將回到上一步","Warning",JOptionPane.YES_NO_OPTION);
		        	if(dialogResult == JOptionPane.YES_OPTION){ // 按下YES時
		        		
		        		// 如果場上沒有棋子或是已經反悔過時
		        		if(g.lastCheesPos[0] == -2) {
		        			JOptionPane.showMessageDialog(null, "剛才已經反悔過了", "提示", JOptionPane.INFORMATION_MESSAGE);
		        		}
		        		else if(g.lastChees == null || g.lastChees.isEmpty()) {
		        			JOptionPane.showMessageDialog(null, "沒有任何棋子可以反悔", "提示", JOptionPane.INFORMATION_MESSAGE);
		        		}
		        		else {
		        			// 使用迴圈找出上次下棋的座標並將其清空
		        			Component[] components = panel.getComponents();
		        			for (Component btn : components) {
		        			    if (btn instanceof JButton) {
		        			        if(((JButton) btn).getActionCommand().equals(g.lastChees)) {
		        			        	((JButton) btn).setIcon(background);
		        			        	g.board[g.lastCheesPos[0]][g.lastCheesPos[1]] = 0;
		        			        }
		        			    }
		        			}
		        			
		        			// 清空儲存下棋點的變數
		        			g.lastChees = "";
		        			g.lastCheesPos[0] = -2; // 有別於遊戲一開始的變數，設定為-2可以判斷是否是遊戲剛開始(變數: -1)或是下棋後多次悔棋(變數: -2)
		        			g.lastCheesPos[1] = -1;
			        		
							// 交換棋權
			        		g.swapChees();
		        		}
		        	}
	        	
	        	}
	        	
	        	swapTop();
	        }
	    });
	    
	    menuRestart.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	resetBoard(); // 重置棋盤
	        }
	    });
	    

	    menuNewgame.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	swapTop();
	        	// 開始新遊戲
	        	String input = JOptionPane.showInputDialog("請輸入棋盤大小(6~36):","13");

	        	// 檢查是否輸入數字
	            try {
		        	int size = Integer.parseInt(input);
		        	g.gameSize = (size < 6)?6:(size > 36)?36:size; // 超過最大/最小大小時使用最接近大小
		        	swapTop();
		        	resetBoard();
	            } catch (NumberFormatException e) {
	            	swapTop();
	                return;
	            }
	        }
	    });
	    
		
	    // 將右鍵選單加入到主類別
	    popup.add(menuRepent);
	    popup.addSeparator();
	    
	    popup.add(menuNewgame);
	    popup.add(menuRestart);
	}
	
	// 建立工具列
	private JMenuBar menuBarCreator() {
		
			// 建立工具列
	       	JMenuBar menuBar = new JMenuBar();
	       	JMenu menuFile = new JMenu("檔案");
	        JMenuItem menuFileExit = new JMenuItem("關閉");
	        JMenuItem menuShowTop = new JMenuItem("顯示在最上層");
	       
	        // 按下離開時的動作
	        menuFileExit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0); 
	            }
	        });

	        // 按下顯示最上層時的動作
	        menuShowTop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(menuShowTop.getIcon() == null) {
	            		menuShowTop.setIcon(checkIcon);
	            		setAlwaysOnTop(true);
	            		onTop = true;
	            	}
	            	else {
	            		menuShowTop.setIcon(null);
	            		setAlwaysOnTop(false);
	            		onTop = false;
	            	}
	            }
	        });
	        
	        // 將檔案選項增加快捷鍵，按下ALT+F可以直接選擇
	        menuFile.setMnemonic(KeyEvent.VK_F);

	        // 放到主類別中
	        menuBar.add(menuFile);
	        menuFile.add(menuShowTop);
	        menuFile.add(menuFileExit);

	        return menuBar;
	}
	
	// 按下右鍵時呼叫選單
	private void showPopup(MouseEvent me) {
		if (me.isPopupTrigger())
		   popup.show(me.getComponent(), me.getX(), me.getY());
	}
	
	// 重置棋盤
	private void resetBoard() {
		remove(panel); // 清除物件
		drawGameBoard(g.gameSize); // 開始新棋盤並開始遊戲
		
		// 刷新視窗
		validate();
		repaint();
		
		// 初始化變數
		g.locked = false;
		g.lastChees = "";
		g.lastCheesPos[0] = -1;
		g.lastCheesPos[1] = -1;
		g.tokener = 1;
	}

	
	// 如果設定為置頂，則在開啟對話框時切換置頂狀態；如果不這樣做，會因為置頂狀態的關係導致對話框顯示不出來
	private void swapTop() {
		if(onTop) {
			if(isAlwaysOnTop())
				setAlwaysOnTop(false);
			else
				setAlwaysOnTop(true);
		}
	}

	
	
	// 覆蓋主類別JFrame的抽象方法，定義按下按鈕時的動作
	@Override
	public void actionPerformed(ActionEvent e) {
		if(g.locked) return;
		swapTop();
		
		// 取得點擊的按鈕和座標
		String pos = e.getActionCommand();
		JButton btn = (JButton) e.getSource();
		
		if(!btn.getIcon().equals(background)) {
			JOptionPane.showMessageDialog(null, "這裡已經下過棋子了!", "提示", JOptionPane.INFORMATION_MESSAGE);
		
		}
		else {
			// 將棋子放在棋盤上
			btn.setIcon(chessIcons[g.tokener-1]);

			int height = (int)((Integer.parseInt(pos))/g.gameSize);	
			int width = (Integer.parseInt(pos) - height*g.gameSize);
			
			// 將棋子位置及顏色記錄在陣列中
			g.board[width][height] = g.tokener;
			
			// 紀錄最後下棋位置
			g.lastChees = pos;
			g.lastCheesPos[0] = width;
			g.lastCheesPos[1] = height;
			
			// 判斷輸贏
			if(g.checkWin(g.tokener,width,height)) {
				String winner = (g.tokener == 1)?"黑棋":"白棋";
				JOptionPane.showMessageDialog(null, winner + " 贏得了這場遊戲", "提示", JOptionPane.INFORMATION_MESSAGE);
				g.lockGame();
			}
			else {
				g.swapChees();
			}
		}
		swapTop();
	}
}
