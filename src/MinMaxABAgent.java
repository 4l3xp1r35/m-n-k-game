import java.util.*;

import static java.lang.Math.abs;

public class MinMaxABAgent {

    /**
     * Indica o limite de corte
     */
    private static int limCut = 0;

    /**
     * O estado atual a ser explorado pelo algoritmo.
     */
    private static State actual;

    /**
     * Indica qual o jogador em que a heuristica vai ser sempre calculada em funcao do mesmo
     */
    private static Ilayout.ID constHeuristicID;



    /**
     * Classe interna State representa um estado dentro do espaço de busca do algoritmo Min Max com corte alfa e beta.
     * Cada estado tem um layout, um pai e uma heurística (h) quando o estado esta á profundidade do limite de corte.
     */
    static class State {
        /**
         * Layout atual.
         */
        private Ilayout layout;

        /**
         * Estado pai.
         */
        private State father;


        /**
         * Construtor para o estado.
         *
         * @param l O layout atual do estado.
         * @param n O estado pai deste estado.
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;

        }

        /**
         * Gera a heurística para o estado atual baseado na configuração e se o numero é par ou ímpar.
         */
        public int getHeuristic() {
            return layout.calculateHeuristic(constHeuristicID);
        }


    }

    /**
     * De acordo com o state vai colocar numa lista os sucessores(filhos)
     * @param n o estado que queremos calcular os filhos
     * @return lista com os sucessores(filhos)
     */
    static final public List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();

        for (Ilayout e : children) {

            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);

            }
        }

        return sucs;
    }

    /**
     * Método para iniciar a busca Alpha-Beta e retornar a melhor ação
     * @param state de partida
     * @param turnPlay qual o jogador que vai jogar
     * @return melhor jogada para o jogador turnPlay
     */
    public static int alphaBetaSearch(State state, Ilayout.ID turnPlay) {
        int bestPosition;
        if(turnPlay == Ilayout.ID.X){
            //bestPosition = minValue(state,  Integer.MIN_VALUE, Integer.MAX_VALUE)[1];
            bestPosition = minValue(state, Integer.MAX_VALUE ,  Integer.MIN_VALUE)[1];
        }else {
            //bestPosition = maxValue(state, Integer.MIN_VALUE,  Integer.MAX_VALUE)[1];
            bestPosition = maxValue(state,  Integer.MAX_VALUE, Integer.MIN_VALUE)[1];
        }

        return bestPosition;
    }

    private static int[] maxValue(State state, int alpha, int beta) {
        int[] array = new int[2];

        if (state.layout.getMoveCount() == limCut || state.layout.isGameOver()) {
            limCut = state.layout.getMoveCount();
            array[0] = utility(state);
            array[1] = state.layout.getMove();

            return array;
        }
        int value = Integer.MIN_VALUE;
        int vlinha = Integer.MIN_VALUE;
        for (State nextState : sucessores(state)) {
            System.out.println(nextState.layout.toString());
            value = Math.max(value, minValue(nextState,alpha,beta)[0]);
            System.out.println(value);
            if(value>vlinha)
            {
                vlinha=value;
                array[1] = nextState.layout.getMove();
            }

            if (value >= beta) {
                array[0] = value;
                array[1] = nextState.layout.getMove();

                return array;
            }
            alpha = Math.max(alpha, value);
        }
        array[0] = value;

        return array;
    }

    // Método para o valor mínimo
    private static int[] minValue(State state, int alpha, int beta) {
        int[] array = new int[2];

        if (state.layout.getMoveCount() == limCut || state.layout.isGameOver()) {
            limCut = state.layout.getMoveCount();
            array[0] = utility(state);
            array[1] = state.layout.getMove();

            return array;
        }
        int value = Integer.MAX_VALUE;
        int vlinha = Integer.MAX_VALUE;
        for (State nextState : sucessores(state)) {
            System.out.println(nextState.layout.toString());
            value = Math.min(value, maxValue(nextState,alpha,beta)[0]);
            System.out.println(value);
            if(value<vlinha)
            {
                vlinha=value;
                array[1] = nextState.layout.getMove();
            }
            if (value <= alpha) {
                array[0] = value;
                array[1] = nextState.layout.getMove();

                return array;
            }
            beta = Math.min(beta, value);
        }
        array[0] = value;
        return array;
    }


    /*public static int alphaBetaSearch(State state, Ilayout.ID turnPlay) {
        int bestPosition;
        if(turnPlay == Ilayout.ID.X){
            bestPosition = minValue(state)[1];
            //bestPosition = minValue(state, Integer.MAX_VALUE ,  Integer.MIN_VALUE)[1];
        }else {
            bestPosition = maxValue(state)[1];
            //bestPosition = maxValue(state,  Integer.MAX_VALUE, Integer.MIN_VALUE)[1];
        }

        return bestPosition;
    }


    private static int[] maxValue(State state) {
        int[] array = new int[2];

        if (state.layout.getMoveCount() == limCut || state.layout.isGameOver()) {
            limCut = state.layout.getMoveCount();
            array[0] = utility(state);
            array[1] = state.layout.getMove();

            return array;
        }
        int value = Integer.MIN_VALUE;
        int vlinha = Integer.MIN_VALUE;
        for (State nextState : sucessores(state)) {
            System.out.println(nextState.layout.toString());
            value = Math.max(value, minValue(nextState)[0]);
            System.out.println(value);
            if(value>vlinha)
            {
                vlinha=value;
                array[1] = nextState.layout.getMove();
            }
        }
        array[0] = value;

        return array;
    }

    // Método para o valor mínimo
    private static int[] minValue(State state) {
        int[] array = new int[2];

        if (state.layout.getMoveCount() == limCut || state.layout.isGameOver()) {
            limCut = state.layout.getMoveCount();
            array[0] = utility(state);
            array[1] = state.layout.getMove();

            return array;
        }
        int value = Integer.MAX_VALUE;
        int vlinha = Integer.MAX_VALUE;
        for (State nextState : sucessores(state)) {
            System.out.println(nextState.layout.toString());
            value = Math.min(value, maxValue(nextState)[0]);
            System.out.println(value);
            if(value<vlinha)
            {
                vlinha=value;
                array[1] = nextState.layout.getMove();
            }
        }
        array[0] = value;
        return array;
    }*/


    // Método para calcular a utilidade do estado (isto é, o valor do estado)
    private static int utility(State state) {

        // Implemente a lógica para calcular o valor de utilidade do estado
        return state.getHeuristic();
    }

    /**
     * retorna um movimento valido e de acordo com o algoritmo.
     *
     * @param board A board que vamos executar a jogada.
     */
    static int play(Ilayout board) {
        actual = new State(board, null);
        limCut = actual.layout.getMoveCount() + (2 * (actual.layout.getK() - 1));
        Ilayout.ID turnToPlay = actual.layout.getTurn();
        constHeuristicID = Ilayout.ID.X;
        return alphaBetaSearch(actual, turnToPlay);
    }
}
