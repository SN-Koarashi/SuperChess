package SuperChees;

/*
 * Board.java
 * 	���󤶭���ø�s�B�z
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
	
	
	// ��l�ƪ�����ܼ�
	private JPanel panel;
	private JPopupMenu popup = new JPopupMenu();
	
	private ImageIcon appIcon = new ImageIcon(getClass().getResource("/images/outline_r.png")); // �{���ϼ�
	private ImageIcon checkIcon = new ImageIcon(getClass().getResource("/images/check.png")); // ���ĲŸ�
	private ImageIcon chessIcons[] = { new ImageIcon(getClass().getResource("/images/chess_b.png")), // �´�
			new ImageIcon(getClass().getResource("/images/chess_w.png"))}; // �մ�
	private ImageIcon background = new ImageIcon(getClass().getResource("/images/bg.png")); // �I���Ϥ�
	private boolean onTop = false; // �m�����A
	
	// �غc�C������ά����ܼ�
	private Game g = new Game();
	
	Board(){
		
		ContextMenuCreator(); // �إߥk����
		setJMenuBar(menuBarCreator()); // �إߤu��C
	    setTitle("�W�Ť��l��"); // �]�w���D
	    setIconImage(appIcon.getImage()); // �]�w�ϼ�
		setResizable(false); // �]�w���i�Y��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �]�w���U�����ɵ{�����ʧ@�������{��
		setVisible(true); // ��ܵ���
		
		// ø�s�s�ѽL�ö}�l�C��
		drawGameBoard(g.gameSize);
	}


	// ø�s�s�ѽL�ö}�l�C��
	private void drawGameBoard(int size) {
		// ���o�ù��e��
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		// �]�w�j�p
		int bRow = size;
		int bCol = size;
		int w = bCol * 32;
		int h = bRow * 32;

		g.board = new int[size][size];
		
		// �p�G�C���O�Ĥ@���ҰʡA�h�N������ܦb����
		if(!g.firstStart) {
			setLocationRelativeTo(null);
			setLocation((int) screen.width / 2 - w / 2,(int) screen.height / 2 - h / 2);
	        g.firstStart = true;
		}
		
		// �إߨ�ø�s�ѽL
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel.setLayout(new GridLayout(bRow, bCol, 0, 0));
		panel.setBackground(new Color(233,199,127));
		setContentPane(panel);
		
		// �]�w�j�p
		setSize(w, h);
		
		// �̾ڴѽL�j�pø�s���s��
		for (int k = 0; k < bRow * bCol; k++) {
			JButton btn = new JButton();
			btn.setActionCommand(String.valueOf(k));
			btn.addActionListener(this);
			btn.setIcon(background);
			btn.setBorderPainted(false);
			btn.setContentAreaFilled(false);
			btn.setBackground(new Color(233,199,127));
			
			// ����s���U�k�����ܿ��
			btn.addMouseListener(new MouseAdapter() {
			   public void mouseReleased(MouseEvent me) {
			     showPopup(me); // showPopup() is our own user-defined method
			   }
			});
			
			panel.add(btn);
		}
	}
	
	private void ContextMenuCreator() {
		// �إ߿ﶵ
		JMenuItem menuRepent = new JMenuItem("����");
	    JMenuItem menuRestart = new JMenuItem("���s�}�l");
	    JMenuItem menuNewgame = new JMenuItem("�s���ѧ�");
	    
	    // �W�[�I���ƥ�
	    menuRepent.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	
	        	
	        	swapTop();
	        	
	        	// ��w�ô����C������
	        	if(g.locked) {
	        		JOptionPane.showMessageDialog(null, "�����ѧ��w�g����", "����", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        	else {
		        	// ��ܮ��Ѥ�
		        	String chess = (g.tokener == 2)?"�´�":"�մ�";
		        	int dialogResult = JOptionPane.showConfirmDialog (null, "�T�w�n�Ϯ���? " + chess +"�N�^��W�@�B","Warning",JOptionPane.YES_NO_OPTION);
		        	if(dialogResult == JOptionPane.YES_OPTION){ // ���UYES��
		        		
		        		// �p�G���W�S���Ѥl�άO�w�g�Ϯ��L��
		        		if(g.lastCheesPos[0] == -2) {
		        			JOptionPane.showMessageDialog(null, "��~�w�g�Ϯ��L�F", "����", JOptionPane.INFORMATION_MESSAGE);
		        		}
		        		else if(g.lastChees == null || g.lastChees.isEmpty()) {
		        			JOptionPane.showMessageDialog(null, "�S������Ѥl�i�H�Ϯ�", "����", JOptionPane.INFORMATION_MESSAGE);
		        		}
		        		else {
		        			// �ϥΰj���X�W���U�Ѫ��y�ШñN��M��
		        			Component[] components = panel.getComponents();
		        			for (Component btn : components) {
		        			    if (btn instanceof JButton) {
		        			        if(((JButton) btn).getActionCommand().equals(g.lastChees)) {
		        			        	((JButton) btn).setIcon(background);
		        			        	g.board[g.lastCheesPos[0]][g.lastCheesPos[1]] = 0;
		        			        }
		        			    }
		        			}
		        			
		        			// �M���x�s�U���I���ܼ�
		        			g.lastChees = "";
		        			g.lastCheesPos[0] = -2; // ���O��C���@�}�l���ܼơA�]�w��-2�i�H�P�_�O�_�O�C����}�l(�ܼ�: -1)�άO�U�ѫ�h������(�ܼ�: -2)
		        			g.lastCheesPos[1] = -1;
			        		
							// �洫���v
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
	        	resetBoard(); // ���m�ѽL
	        }
	    });
	    

	    menuNewgame.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent evt) {
	        	swapTop();
	        	// �}�l�s�C��
	        	String input = JOptionPane.showInputDialog("�п�J�ѽL�j�p(6~36):","13");

	        	// �ˬd�O�_��J�Ʀr
	            try {
		        	int size = Integer.parseInt(input);
		        	g.gameSize = (size < 6)?6:(size > 36)?36:size; // �W�L�̤j/�̤p�j�p�ɨϥγ̱���j�p
		        	swapTop();
		        	resetBoard();
	            } catch (NumberFormatException e) {
	            	swapTop();
	                return;
	            }
	        }
	    });
	    
		
	    // �N�k����[�J��D���O
	    popup.add(menuRepent);
	    popup.addSeparator();
	    
	    popup.add(menuNewgame);
	    popup.add(menuRestart);
	}
	
	// �إߤu��C
	private JMenuBar menuBarCreator() {
		
			// �إߤu��C
	       	JMenuBar menuBar = new JMenuBar();
	       	JMenu menuFile = new JMenu("�ɮ�");
	        JMenuItem menuFileExit = new JMenuItem("����");
	        JMenuItem menuShowTop = new JMenuItem("��ܦb�̤W�h");
	       
	        // ���U���}�ɪ��ʧ@
	        menuFileExit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                System.exit(0); 
	            }
	        });

	        // ���U��̤ܳW�h�ɪ��ʧ@
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
	        
	        // �N�ɮ׿ﶵ�W�[�ֱ���A���UALT+F�i�H�������
	        menuFile.setMnemonic(KeyEvent.VK_F);

	        // ���D���O��
	        menuBar.add(menuFile);
	        menuFile.add(menuShowTop);
	        menuFile.add(menuFileExit);

	        return menuBar;
	}
	
	// ���U�k��ɩI�s���
	private void showPopup(MouseEvent me) {
		if (me.isPopupTrigger())
		   popup.show(me.getComponent(), me.getX(), me.getY());
	}
	
	// ���m�ѽL
	private void resetBoard() {
		remove(panel); // �M������
		drawGameBoard(g.gameSize); // �}�l�s�ѽL�ö}�l�C��
		
		// ��s����
		validate();
		repaint();
		
		// ��l���ܼ�
		g.locked = false;
		g.lastChees = "";
		g.lastCheesPos[0] = -1;
		g.lastCheesPos[1] = -1;
		g.tokener = 1;
	}

	
	// �p�G�]�w���m���A�h�b�}�ҹ�ܮخɤ����m�����A�F�p�G���o�˰��A�|�]���m�����A�����Y�ɭP��ܮ���ܤ��X��
	private void swapTop() {
		if(onTop) {
			if(isAlwaysOnTop())
				setAlwaysOnTop(false);
			else
				setAlwaysOnTop(true);
		}
	}

	
	
	// �л\�D���OJFrame����H��k�A�w�q���U���s�ɪ��ʧ@
	@Override
	public void actionPerformed(ActionEvent e) {
		if(g.locked) return;
		swapTop();
		
		// ���o�I�������s�M�y��
		String pos = e.getActionCommand();
		JButton btn = (JButton) e.getSource();
		
		if(!btn.getIcon().equals(background)) {
			JOptionPane.showMessageDialog(null, "�o�̤w�g�U�L�Ѥl�F!", "����", JOptionPane.INFORMATION_MESSAGE);
		
		}
		else {
			// �N�Ѥl��b�ѽL�W
			btn.setIcon(chessIcons[g.tokener-1]);

			int height = (int)((Integer.parseInt(pos))/g.gameSize);	
			int width = (Integer.parseInt(pos) - height*g.gameSize);
			
			// �N�Ѥl��m���C��O���b�}�C��
			g.board[width][height] = g.tokener;
			
			// �����̫�U�Ѧ�m
			g.lastChees = pos;
			g.lastCheesPos[0] = width;
			g.lastCheesPos[1] = height;
			
			// �P�_��Ĺ
			if(g.checkWin(g.tokener,width,height)) {
				String winner = (g.tokener == 1)?"�´�":"�մ�";
				JOptionPane.showMessageDialog(null, winner + " Ĺ�o�F�o���C��", "����", JOptionPane.INFORMATION_MESSAGE);
				g.lockGame();
			}
			else {
				g.swapChees();
			}
		}
		swapTop();
	}
}
