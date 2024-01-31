import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestProbl {

    @Test
    public void testBoardHeurist() {
        //testar a funcao para a heuristica
        int M=3,N=3,K=3;

        Board board = new Board(M,N,K,8);
        board.move(0);
        board.move(1);
        board.move(2);
        board.move(4);
        board.move(7);
        board.move(8);

        assertEquals("X O X \n- O - \n- X O ", board.toString());

        int totalC = 0;
        int totalR = 0;
        int totalND = 0;
        int totalPD = 0;


        //check left to right
        for (int row = 0; row < N; row++) {
            for (int col = 0; col <= M - K; col++) {
                totalR += board.calcR(row, col, Ilayout.ID.X)[0];
            }
        }

        for (int row = 0; row <= N - K; row++) {
            //check up to down
            for (int col = 0; col < M; col++) {
                totalC += board.calcC(row,col, Ilayout.ID.X)[0];
            }
            for (int col = 0; col <= M - K; col++) {
                totalND += board.calcND(row,col, Ilayout.ID.X)[0];
            }
            for (int col = K - 1; col < M; col++) {
                totalPD += board.calcPD(row,col, Ilayout.ID.X)[0];
            }
        }

        //Jogador X
        //teste diagonais negativas
        assertEquals(1, totalND);
        //Teste diagonais positivas
        assertEquals(1, totalPD);
        //Teste linhas
        assertEquals(2, totalR);

        assertEquals(3, totalC);

        assertEquals(1,board.calculateHeuristic(Ilayout.ID.X));

        //Testar o winner no caso de empate tem que ser blank
        board.move(3);
        board.move(6);
        board.move(5);
        assertEquals("X O X \nX O X \nO X O ", board.toString());
        assertEquals(Ilayout.ID.Blank, board.getWinner());

    }

    @Test
    public void testBoardHeurist1() {
        int M=3,N=4,K=3;

        //M N
        Board board = new Board(M,N,K,14);
        board.move(0);
        board.move(1);
        board.move(4);
        board.move(8);
        board.move(5);
        board.move(10);

        assertEquals("X O - - \nX X - - \nO - O - ", board.toString());

        int totalC = 0;
        int totalR = 0;
        int totalND = 0;
        int totalPD = 0;


        //check left to right
        for (int row = 0; row < M; row++) {
            for (int col = 0; col <= N - K; col++) {
                totalR += board.calcR(row, col, Ilayout.ID.X)[0];
            }
        }

        for (int row = 0; row <= M - K; row++) {
            //check up to down
            for (int col = 0; col < N; col++) {
                totalC += board.calcC(row,col, Ilayout.ID.X)[0];
            }
            for (int col = 0; col <= N - K; col++) {
                totalND += board.calcND(row,col, Ilayout.ID.X)[0];
            }
            for (int col = K - 1; col < M; col++) {
                totalPD += board.calcPD(row,col, Ilayout.ID.X)[0];
            }
        }


        //Jogador X
        //teste diagonais negativas
        assertEquals(1, totalND);
        //teste diagonais positivas
        assertEquals(1, totalPD);
        //Teste linhas
        assertEquals(3, totalR);
        //Teste colunas
        assertEquals(2, totalC);
        assertEquals(7, board.calculateHeuristic(Ilayout.ID.X));
    }

    @Test
    public void testBoardHeurist2() {
        int M=4,N=4,K=3;

        Board board = new Board(M,N,K,24);
        board.move(5);
        board.move(1);
        board.move(6);
        board.move(11);
        board.move(9);
        board.move(14);

        assertEquals("- O - - \n- X X - \n- X - O \n- - O - ", board.toString());

        int totalC = 0;
        int totalR = 0;
        int totalND = 0;
        int totalPD = 0;


        //check left to right
        for (int row = 0; row < N; row++) {
            for (int col = 0; col <= M - K; col++) {
                totalR += board.calcR(row, col, Ilayout.ID.X)[0];
            }
        }

        for (int row = 0; row <= N - K; row++) {
            //check up to down
            for (int col = 0; col < M; col++) {
                totalC += board.calcC(row,col, Ilayout.ID.X)[0];
            }
            for (int col = 0; col <= M - K; col++) {
                totalND += board.calcND(row,col, Ilayout.ID.X)[0];
            }
            for (int col = K - 1; col < M; col++) {
                totalPD += board.calcPD(row,col, Ilayout.ID.X)[0];
            }
        }


        //Jogador X
        assertEquals(4, totalND);
        assertEquals(3, totalPD);
        //Teste linhas
        assertEquals(4, totalR);
        //Teste colunas
        assertEquals(4, totalC);
        assertEquals(8, board.calculateHeuristic(Ilayout.ID.X));

    }
    @Test
    public void testBoard() {

        int M = 3, N = 3, K = 3;

        Board board = new Board(M, N, K, 8);
        board.move(0);
        board.move(1);
        board.move(2);
        board.move(4);
        ;
        assertEquals("X O X \n- O - \n- - - ", board.toString());

        assertEquals(7, MinMaxABAgent.play(board));

    }

    @Test
    public void testBoardH5() {
        Board board = new Board(3,3,3,8);
        board.move(7);
        board.move(0);
        board.move(4);
        board.move(2);


        assertEquals("O - O \n- X - \n- X - ", board.toString());


        assertEquals(3,board.calculateHeuristic(Ilayout.ID.X));

    }

    @Test
    public void testBoardH6() {
        Board board = new Board(3,3,3,8);

        board.move(0);
        board.move(1);
        board.move(2);
        board.move(4);

        assertEquals("X O X \n- O - \n- - - ", board.toString());

        assertEquals(7,MinMaxABAgent.play(board));

    }

    @Test
    public void testBoardH7() {
        Board board = new Board(3,3,3,8);

        board.move(0);
        board.move(1);
        board.move(2);
        board.move(4);
        board.move(3);
        board.move(7);


        assertEquals("X O X \nX O - \n- O - ", board.toString());

        assertEquals(Integer.MAX_VALUE,board.calculateHeuristic(Ilayout.ID.X));

    }

    @Test
    public void testBoardH8() {
        Board board = new Board(3,3,3,8);

        board.move(0);
        board.move(4);
        board.move(2);
        board.move(7);
        board.move(6);
        board.move(8);


        assertEquals("X - X \n- O - \nX O O ", board.toString());

        assertEquals(2,board.calculateHeuristic(Ilayout.ID.X));

    }

    @Test
    public void testBoardH9(){
        int M = 4, N = 4, K = 3;
        Board board = new Board(4,4,3,24);
        board.move(5);
        board.move(1);
        board.move(6);
        board.move(11);
        board.move(9);
        board.move(14);

        assertEquals("- O - - \n- X X - \n- X - O \n- - O - ", board.toString());

        int totalC = 0;
        int totalR = 0;
        int totalND = 0;
        int totalPD = 0;


        //check left to right
        for (int row = 0; row < N; row++) {
            for (int col = 0; col <= M - K; col++) {
                totalR += board.calcR(row, col, Ilayout.ID.X)[0];
            }
        }

        for (int row = 0; row <= N - K; row++) {
            //check up to down
            for (int col = 0; col < M; col++) {
                totalC += board.calcC(row,col, Ilayout.ID.X)[0];
            }
            for (int col = 0; col <= M - K; col++) {
                totalND += board.calcND(row,col, Ilayout.ID.X)[0];
            }
            for (int col = K - 1; col < M; col++) {
                totalPD += board.calcPD(row,col, Ilayout.ID.X)[0];
            }
        }

        //Jogador X
        assertEquals(4, totalND);
        assertEquals(3,totalPD);
        //Teste linhas
        assertEquals(4, totalR);
    }

    //VERIFICAR O WINNER
    @Test
    public void testBoardH10() {
        Board board = new Board(3,3,3,8);

        board.move(0);
        board.move(1);
        board.move(2);
        board.move(5);
        board.move(4);
        board.move(6);
        board.move(8);

        assertEquals("X O X \n- X O \nO - X ", board.toString());

        Board board2 = new Board(3,3,3,8);

        board2.move(0);
        board2.move(1);
        board2.move(2);
        board2.move(4);
        board2.move(5);
        board2.move(7);

        assertEquals("X O X \n- O X \n- O - ", board2.toString());


        //assertEquals(1,MinMaxABAgent.play(board));

    }
}
