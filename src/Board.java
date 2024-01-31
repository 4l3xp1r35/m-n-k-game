import java.util.*;

import static java.lang.Math.pow;
// import java.util.ArrayList;

/**
 * Represents a board.
 */
public class Board implements Ilayout, Cloneable {
    private ID[][] board;
    private ID playersTurn;
    private ID winner;
    private HashSet<Integer> movesAvailable;

    private int moveCount;
    private boolean gameOver;
    private int move;

    int N, M, K, totalVP;

    Board(int m, int n, int k, int vp) {
        N = m;
        M = n;
        K = k;
        totalVP = vp;
        board = new ID[N][M];
        movesAvailable = new HashSet<>();
        reset();
    }

    Board() {
        N = Ilayout.M;
        M = Ilayout.N;
        K = Ilayout.K;
        int vp;
        if (M == N) {
            vp = 2 * ((int) pow(((M - K) + 1), 2)) + 2 * M * ((M - K) + 1);
        } else vp = 2 * (((M - K) + 1) * ((N - K) + 1)) + N * ((M - K) + 1) + M * ((N - K) + 1);
        this.totalVP = vp;
        board = new ID[Ilayout.N][Ilayout.M];
        movesAvailable = new HashSet<>();
        reset();
    }

    /**
     * Permite saber o numero de linhas da board
     *
     * @return numero de linhas
     */
    public int getM() {
        return M;
    }

    /**
     * Permite saber o numero de colunas da board
     *
     * @return numero de colunas
     */
    public int getN() {
        return N;
    }

    /**
     * Permite saber o numero que a sequencia tem que ter para haver vencedor
     *
     * @return numero da sequencia
     */
    public int getK() {
        return K;
    }

    /**
     * Set the cells to be blank and load the available moves (all the moves are
     * available at the start of the game).
     */
    private void initialize() {
        for (int row = 0; row < N; row++)
            for (int col = 0; col < M; col++) {
                board[row][col] = ID.Blank;
            }
        movesAvailable.clear();

        for (int i = 0; i < N * M; i++) {
            movesAvailable.add(i);
        }
    }

    /**
     * Restart the game with a new blank board.
     */
    public void reset() {
        moveCount = 0;
        gameOver = false;
        playersTurn = ID.X;
        winner = ID.Blank;
        initialize();
    }

    /**
     * Places an X or an O on the specified index depending on whose turn it is.
     *
     * @param index position starts in 0 and increases from left to right and from top to bottom
     * @return true if the move has not already been played
     */
    public boolean move(int index) {
        move = index;
        return move(index % M, index / M);
    }

    public int getMove() {
        return move;
    }

    public int[] calcC(int row, int col, ID turn) {
        int[] values = new int[3];
        int contB=0;
        int contV=0;
        int contVO=0;

        for (int i = 0; i < K; i++) {
            if (board[row + i][col] == turn ) {
                contV++;
                contB=1;
            }

            if(board[row + i][col] == playersTurn){
                contVO++;
            }

            if (contVO>0 &&contV>0){
                contV=0;
                contVO=0;
                break;
            }
        }

        values[0]=contB;
        values[1]=K-contV;
        values[2]=K-contVO;
        return values;
    }

    public int[] calcR(int row, int col, ID turn) {
        int[] values = new int[3];
        int contB=0;
        int contV=0;
        int contVO=0;

        for (int i = 0; i < K; i++) {
            if (board[row][col + i] == turn) {
                contV++;
                contB=1;
            }

            if(board[row][col + i] == playersTurn){
                contVO++;
            }

            if (contVO>0 &&contV>0){
                contV=0;
                contVO=0;
                break;
            }
        }
        values[0]=contB;
        values[1]=K-contV;
        values[2]=K-contVO;
        return values;
    }

    public int[] calcPD(int row, int col, ID turn) {
        int[] values = new int[3];
        int contB=0;
        int contV=0;
        int contVO=0;
        for (int i = 0; i < K; i++) {
            if (board[row + i][col - i] == turn) {
                contV++;
                contB=1;
            }

            if(board[row + i][col - i] == playersTurn){
                contVO++;
            }

            if (contVO>0 &&contV>0){
                contV=0;
                contVO=0;
                break;
            }
        }
        values[0]=contB;
        values[1]=K-contV;
        values[2]=K-contVO;
        return values;
    }

    public int[] calcND(int row, int col, ID turn) {
        int[] values = new int[3];
        int contB=0;
        int contV=0;
        int contVO=0;

        for (int i = 0; i < K; i++) {
            if (board[row + i][col + i] == turn) {
                contV++;
                contB=1;
            }

            if(board[row + i][col + i] == playersTurn){
                contVO++;
            }
            if (contVO>0 &&contV>0){
                contV=0;
                contVO=0;
                break;
            }
        }
        values[0]=contB;
        values[1]=K-contV;
        values[2]=K-contVO;
        return values;
    }

    /**
     * Places an X or an O on the specified location depending on who turn it is.
     *
     * @param x the x coordinate of the location
     * @param y the y coordinate of the location
     * @return true if the move has not already been played
     */
    public boolean move(int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("Game over. No more moves can be played.");
        }

        if (board[y][x] == ID.Blank) {
            board[y][x] = playersTurn;
        } else {
            return false;
        }

        moveCount++;
        movesAvailable.remove(y * M + x);

        // The game is a draw.
        if (moveCount == N * M) {
            winner = ID.Blank;
            gameOver = true;
        }

        // Check for a winner.
        if (moveCount >= 2*(K-1)) checkWinner();

        playersTurn = (playersTurn == ID.X) ? ID.O : ID.X;
        return true;
    }




    /**
     * Verifica as diagonais negativas para ver se existe um vencedor
     *
     * @return true if exist a winner
     */
    private boolean checkPosDiagonal(int row, int col) {
        int count = 0;
        ID first = board[row][col];

        if (first != ID.Blank) {
            for (int i = 0; i < K; i++) {
                if (board[row + i][col - i] == first) {
                    count++;
                } else {
                    break;
                }
            }

            return count == K;
        }
        return false;
    }

    /**
     * Verifica as diagonais positivas para ver se existe um vencedor
     *
     * @return true if exist a winner
     */
    private boolean checkNegDiagonal(int row, int col) {
        int count = 0;
        ID first = board[row][col];

        if (first != ID.Blank) {
            for (int i = 0; i < K; i++) {
                if (board[row + i][col + i] == first) {
                    count++;
                } else {
                    break;
                }
            }

            return count == K;
        }
        return false;
    }

    /**
     * Verifica as colunas para ver se existe um vencedor
     *
     * @return true if exist a winner
     */
    private boolean checkColumnsForWinner(int row, int col) {
        int count = 0;
        ID first = board[row][col];

        if (first != ID.Blank) {
            for (int i = 0; i < K; i++) {
                if (board[row + i][col] == first) {
                    count++;
                } else {
                    break;
                }
            }

            return count == K;
        }
        return false;
    }

    /**
     * Verifica as linhas para ver se existe um vencedor
     *
     * @return true if exist a winner
     */
    private boolean checkRowsForWinner(int row, int col) {
        int count = 0;
        ID first = board[row][col];

        if (first != ID.Blank) {
            for (int i = 0; i < K; i++) {
                if (board[row][col + i] == first) {
                    count++;
                } else {
                    break;
                }
            }
            return count == K;
        }
        return false;
    }

    public int calculateHeuristic(ID turn) {
        if (isGameOver() && turn!=getWinner()){
            return Integer.MAX_VALUE-50;
        }else if(isGameOver() && turn==getWinner()) {
            return Integer.MIN_VALUE+50;
        }
        //numero total de jogadas que impedem a vitoria do oponente
        int totalBlocks = 0;
        //jogadas minimas necessarias para vitoria do oponente
        int minMovesOp = Integer.MAX_VALUE;
        //jogadas minimas necessarias para vitoria do jogador
        int minMoves = Integer.MAX_VALUE;

        int contMDVO = 0;
        int contMDV=0;

        //check left to right
        for (int row = 0; row < N; row++) {
            for (int col = 0; col <= M - K; col++) {
                int[] calculo = calcR(row, col, turn);
                totalBlocks += calculo[0];
                if(minMoves>calculo[1] && calculo[1]!=0){ minMoves = calculo[1]; }
                if(minMovesOp>calculo[2] && calculo[2]!=0){ minMovesOp = calculo[2]; }
                if(calculo[1]==1) contMDV++;
                if(calculo[2]==1) contMDVO++;
            }
        }


        for (int row = 0; row <= N - K; row++) {
            //check up to down
            for (int col = 0; col < M; col++) {
                int[] calculo = calcC(row, col, turn);
                totalBlocks += calculo[0];
                if(minMoves>calculo[1] && calculo[1]!=0){ minMoves = calculo[1]; }
                if(minMovesOp>calculo[2] && calculo[2]!=0){ minMovesOp = calculo[2]; }
                if(calculo[1]==1) contMDV++;
                if(calculo[2]==1) contMDVO++;
            }

            //check negative diagonal(top left to bottom right)
            for (int col = 0; col <= M - K; col++) {
                int[] calculo = calcND(row, col, turn);
                totalBlocks += calculo[0];
                if(minMoves>calculo[1] && calculo[1]!=0){ minMoves = calculo[1]; }
                if(minMovesOp>calculo[2] && calculo[2]!=0){ minMovesOp = calculo[2]; }
                if(calculo[1]==1) contMDV++;
                if(calculo[2]==1) contMDVO++;
            }

            //check positive diagonal(top right to bottom left)
            for (int col = K - 1; col < M; col++) {
                int[] calculo = calcPD(row, col, turn);
                totalBlocks += calculo[0];
                if(minMoves>calculo[1] && calculo[1]!=0){ minMoves = calculo[1]; }
                if(minMovesOp>calculo[2] && calculo[2]!=0){ minMovesOp = calculo[2]; }
                if(calculo[1]==1) contMDV++;
                if(calculo[2]==1) contMDVO++;
            }
        }

        if (contMDVO>=2) minMovesOp = -(contMDVO-1);
        if (contMDV>=2) minMoves = -(contMDV-1);
        return (totalVP-totalBlocks)-minMovesOp+minMoves;
    }

    /**
     * Check if exist a winner.
     */
    public void checkWinner() {
        boolean rows=false, columns=false, negD=false, posD=false;
        //check left to right
        for (int row = 0; row < N; row++) {
            for (int col = 0; col <= M - K; col++) {
                rows = checkRowsForWinner(row, col);
                if (rows) {
                    row=N;
                    break;
                }
            }
        }


        for (int row = 0; row <= N - K; row++) {
            //check up to down
            for (int col = 0; col < M; col++) {
                columns = checkColumnsForWinner(row, col);
                if (columns) {
                    row = N;
                    break;
                }
            }

            if(!columns){//check negative diagonal(top left to bottom right)
                for (int col = 0; col <= M - K; col++) {
                    negD = checkNegDiagonal(row, col);
                    if (negD) {
                        row = N;
                        break;
                    }
                }
                if (!negD) {
                    //check positive diagonal(top right to bottom left)
                    for (int col = K - 1; col < M; col++) {
                        posD = checkPosDiagonal(row, col);
                        if (posD) {
                            row = N;
                            break;
                        }
                    }
                }
            }
        }

        if (rows || columns || negD || posD){
            winner = playersTurn; // Atualiza o vencedor
            gameOver = true;
        }

    }

    /**
     * Check to see if the game is over (if there is a winner or a draw).
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;
    }


    /**
     * Check to see who's turn it is.
     *
     * @return the player who's turn it is
     */
    public ID getTurn() {
        return playersTurn;
    }

    public int getMoveCount() {
        return moveCount;
    }

    /**
     * @return the player who won (or Blank if the game is a draw)
     */
    public ID getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("Not over yet!");
        }
        return winner;
    }

    /**
     * Get the indexes of all the positions on the board that are empty.
     *
     * @return the empty cells
     */
    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }


    /**
     * @return an deep copy of the board
     */
    public Object clone() {
        try {
            Board b = (Board) super.clone();

            b.board = new ID[N][M];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < M; j++)
                    b.board[i][j] = this.board[i][j];

            b.playersTurn = this.playersTurn;
            b.winner = this.winner;
            b.movesAvailable = new HashSet<Integer>();
            b.movesAvailable.addAll(this.movesAvailable);
            b.moveCount = this.moveCount;
            b.gameOver = this.gameOver;
            return b;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                if (board[y][x] == ID.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");
            }
            if (y != N - 1) {
                sb.append("\n");
            }
        }
        return new String(sb);
    }

    /**
     * @return the children of the receiver.
     */
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();

        if(isGameOver()) return children;
        for (int index : movesAvailable) {

            Board newBoard = (Board) this.clone();

            newBoard.move(index);
            children.add(newBoard);

        }
        return children;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        Board that = (Board) other;

        for (int x = 0; x < N; x++)
            for (int y = 0; y < M; y++)
                if (board[x][y] != that.board[x][y]) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }

    public boolean isBlank(int index) {
        int x = index / M;
        int y = index % M;
        return (board[x][y] == ID.Blank);
    }
}