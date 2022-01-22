package SuperChees;

/*
 * Game.java
 * 	關於遊戲內的機制判斷及變數儲存
 */

public class Game {
	public int[][] board; // 棋盤座標陣列
	public String lastChees; // 最後下棋位置
	public int[] lastCheesPos = {-1, -1}; // 最後下棋座標
	public int gameSize = 13; // 棋盤大小
	public int tokener = 1; // 棋權
	public boolean firstStart = false; // 遊戲是否是第一次啟動
	public boolean locked = false; // 遊戲鎖定狀態
	
	// 鎖定遊戲
	public void lockGame() {
		locked = true;
	}
	
	
	// 交換棋權
	public void swapChees() {
		// 1 = 黑 ; 2 = 白
		if(tokener == 1)
			tokener = 2;
		else
			tokener = 1;
	}
	

	// 判斷輸贏
	public boolean checkWin(int color,int x,int y) {
		int count,k; // 相連次數, 指標
		boolean flag = false; // 判斷輸贏
		
		// ================================
		// 根據下棋位置作左右橫向判斷是否連續
		
		// 初始化指標及連續數
		k = 1;
		count = 1;
		// 開始進行往右計算
		while(x+k < gameSize && board[x+k][y] == color) {
			count++;
			k++;
		}
		
		// 初始化指標
		k = 1;
		// 開始進行往左計算
		while(x-k > 0 && board[x-k][y] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;

		// ================================
		// 根據下棋位置作上下縱向判斷是否連續
		
		// 初始化指標及連續數
		k = 1;
		count = 1;
		// 開始進行往上計算
		while(y+k < gameSize && board[x][y+k] == color) {
			count++;
			k++;
		}
		
		k = 1; // 初始化指標
		// 開始進行往下計算
		while(y-k > 0 && board[x][y-k] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;
		
		// ================================
		// 根據下棋位置作正斜率方向判斷是否連續
		
		// 初始化指標及連續數
		k = 1;
		count = 1;
		// 開始進行往右上計算
		while(x+k < gameSize && y-k > 0 && board[x+k][y-k] == color) {
			count++;
			k++;
		}
		
		k = 1; // 初始化指標
		// 開始進行往左下計算
		while(y+k < gameSize && x-k > 0 && board[x-k][y+k] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;
		
		// ================================
		// 根據下棋位置作負斜率方向判斷是否連續
		
		// 初始化指標及連續數
		k = 1;
		count = 1;
		// 開始進行往左上計算
		while(x-k > 0 && y-k > 0 && board[x-k][y-k] == color) {
			count++;
			k++;
		}
		
		k = 1; // 初始化指標
		// 開始進行往右下計算
		while(x+k < gameSize && y+k < gameSize && board[x+k][y+k] == color) {
			count++;
			k++;
		}
		if (count >= 5)
			flag = true;
		

		return flag;
	}
}
