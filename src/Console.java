import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.pow;


/**
 * For playing in the console.
 */


public class Console {
    private Board board;
    private Scanner sc = new Scanner(System.in);
    private int turnPlayer1;
    private int turnPlayer2;


    /**
     * Permite ao utilizador escolher o tamanho da board e a sequencia  para ganhar
     */
    private Console(boolean utilizador) {
        if(utilizador) {
            System.out.println("Please enter the number of rows (M): ");
            int M = sc.nextInt();
            System.out.println("Please enter the number of columns (N): ");
            int N = sc.nextInt();
            System.out.println("Please enter the target sequence length for a win (K): ");
            int K = sc.nextInt();
            System.out.println("Please choose the player 1 (just put the number of player you want): ");
            System.out.println("1-Random player");
            System.out.println("2-Human player");
            System.out.println("3-Ai(MinMax alfa beta pruning) player");
            turnPlayer1=sc.nextInt();
            System.out.println("Please choose the player 2 (just put the number of player you want): ");
            System.out.println("1-Random player");
            System.out.println("2-Human player");
            System.out.println("3-Ai(MinMax alfa beta pruning) player");
            turnPlayer2=sc.nextInt();
            int vp;
            if (M == N) {
                vp = 2 * ((int) pow(((M - K) + 1), 2)) + 2 * M * ((M - K) + 1);
            } else vp = 2 * (((M - K) + 1) * ((N - K) + 1)) + N * ((M - K) + 1) + M * ((N - K) + 1);

            board = new Board(M, N, K, vp);
        }
        else board = new Board();
    }


    /**
     * Game on
     */
    private void play () {
        while (true) {
            playMove();
            if (board.isGameOver()) {
                printWinner();
                break;
            }
        }
    }

    
    /**
     * Handle the move to be played,(
     */
   
    private void playMove () {
    	int position;
    	
        if (board.getTurn() == Ilayout.ID.X) {
            position = XAgent.play(board);
            if(turnPlayer1==2)
            position=getHumanMove();
            if(turnPlayer1==3)
            position=MinMaxABAgent.play(board);
        	
        	board.move(position);
 
        } else {
        	position = XAgent.play(board);
            if(turnPlayer2==2)
            position=getHumanMove();
            if(turnPlayer2==3)
            position=MinMaxABAgent.play(board);
        	board.move(position);
        }
    }
 
    private void printGameStatus () {
        System.out.println("\n" + board + "\n");
        System.out.println(board.getTurn().name() + "'s turn.");
    }

    /**
     * For reading in and interpreting the move that the user types into the console.
     */
    private int getHumanMove() {
        printGameStatus ();
        System.out.print("Index of move: ");

        int move = sc.nextInt();

        if (move < 0 || move >= board.getN()* board.getM()) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe index of the move must be between 0 and "
                    + (board.getN() * board.getM() - 1) + ", inclusive.");
        } else if (!board.isBlank(move)) {
            System.out.println("\nInvalid move.");
            System.out.println("\nThe selected index must be blank.");
        }
        return move;
    }

    
    private void printWinner () {
        Ilayout.ID winner = board.getWinner();

        System.out.println("\n" + board + "\n");

        if (winner == Ilayout.ID.Blank) {
            System.out.println("It's a draw.");
        } else {
            System.out.println("Player " + winner.toString() + " wins!");
        }
    }



    public static void main(String[] args)  {
    	    final int repetitions=1;
    	    long times = 0;
            Scanner sc = new Scanner(System.in);

    	    for(int i=0; i<repetitions; i++) {
                System.out.println("Do you want to manually set the game settings in the iLayout interface, or would you prefer to enter them in the console as the prompts appear?");
                System.out.println("1-manualy");
                System.out.println("2-console");
                int choose=sc.nextInt();
                System.out.println("resposta: "+choose);
                if(choose==0||choose>=3){
                    System.out.println("Invalid number");
                }

                Console game;
                if (choose==1) {
                    game = new Console(false);
                    long startTime = System.currentTimeMillis();
                    game.play();
                    long totalTime = System.currentTimeMillis() - startTime;
                    times += totalTime;
                }else if (choose==2) {
                    game = new Console(true);
                    long startTime = System.currentTimeMillis();
                    game.play();
                    long totalTime = System.currentTimeMillis() - startTime;
                    times += totalTime;
                }

                sc.close();
    	    }
    	    System.out.println("Av Time: " + times*1.0f/repetitions+ " milisecs");

    }

}
