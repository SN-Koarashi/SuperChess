package SuperChees;

/*
 * Game.java
 * 	����C����������P�_���ܼ��x�s
 */

public class Game {
	public int[][] board; // �ѽL�y�а}�C
	public String lastChees; // �̫�U�Ѧ�m
	public int[] lastCheesPos = {-1, -1}; // �̫�U�Ѯy��
	public int gameSize = 13; // �ѽL�j�p
	public int tokener = 1; // ���v
	public boolean firstStart = false; // �C���O�_�O�Ĥ@���Ұ�
	public boolean locked = false; // �C����w���A
	
	// ��w�C��
	public void lockGame() {
		locked = true;
	}
	
	
	// �洫���v
	public void swapChees() {
		// 1 = �� ; 2 = ��
		if(tokener == 1)
			tokener = 2;
		else
			tokener = 1;
	}
	

	// �P�_��Ĺ
	public boolean checkWin(int color,int x,int y) {
		int count,k; // �۳s����, ����
		boolean flag = false; // �P�_��Ĺ
		
		// ================================
		// �ھڤU�Ѧ�m�@���k��V�P�_�O�_�s��
		
		// ��l�ƫ��Фγs���
		k = 1;
		count = 1;
		// �}�l�i�橹�k�p��
		while(x+k < gameSize && board[x+k][y] == color) {
			count++;
			k++;
		}
		
		// ��l�ƫ���
		k = 1;
		// �}�l�i�橹���p��
		while(x-k > 0 && board[x-k][y] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;

		// ================================
		// �ھڤU�Ѧ�m�@�W�U�a�V�P�_�O�_�s��
		
		// ��l�ƫ��Фγs���
		k = 1;
		count = 1;
		// �}�l�i�橹�W�p��
		while(y+k < gameSize && board[x][y+k] == color) {
			count++;
			k++;
		}
		
		k = 1; // ��l�ƫ���
		// �}�l�i�橹�U�p��
		while(y-k > 0 && board[x][y-k] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;
		
		// ================================
		// �ھڤU�Ѧ�m�@���ײv��V�P�_�O�_�s��
		
		// ��l�ƫ��Фγs���
		k = 1;
		count = 1;
		// �}�l�i�橹�k�W�p��
		while(x+k < gameSize && y-k > 0 && board[x+k][y-k] == color) {
			count++;
			k++;
		}
		
		k = 1; // ��l�ƫ���
		// �}�l�i�橹���U�p��
		while(y+k < gameSize && x-k > 0 && board[x-k][y+k] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;
		
		// ================================
		// �ھڤU�Ѧ�m�@�t�ײv��V�P�_�O�_�s��
		
		// ��l�ƫ��Фγs���
		k = 1;
		count = 1;
		// �}�l�i�橹���W�p��
		while(x-k > 0 && y-k > 0 && board[x-k][y-k] == color) {
			count++;
			k++;
		}
		
		k = 1; // ��l�ƫ���
		// �}�l�i�橹�k�U�p��
		while(x+k < gameSize && y+k < gameSize && board[x+k][y+k] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;
		

		return flag;
	}
}
