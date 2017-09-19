package jedatarii;

import java.util.Scanner;

public class Connect4 {
    private ConsoleUI console;
    
    private Connect4(){}
    
    public static void run(ConsoleUI console, int players) throws InterruptedException {
        (new Connect4()).internalRun(console, players);
    }
    
    public void internalRun(ConsoleUI console, int players) throws InterruptedException {
        this.console = console;
        Board b = new Board();
        if(players == 1) {
            Connect4AI ai = new Connect4AI(b);  
            ai.playAgainstAIConsole();
        } else if(players == 2) {
            twoPlayerGame(b);
        }
    }
    
    class Board{
        byte[][] board = new byte[6][7];

        public Board(){
            board = new byte[][]{
                {0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,},
                {0,0,0,0,0,0,0,},    
            };
        } 

        public boolean isLegalMove(int column){
            return board[0][column]==0;
        }

        //Placing a Move on the board
        public boolean placeMove(int column, int player){ 
            if(!isLegalMove(column)) {console.println("Illegal move!"); return false;}
            for(int i=5;i>=0;--i){
                if(board[i][column] == 0) {
                    board[i][column] = (byte)player;
                    return true;
                }
            }
            return false;
        }

        public void undoMove(int column){
            for(int i=0;i<=5;++i){
                if(board[i][column] != 0) {
                    board[i][column] = 0;
                    break;
                }
            }        
        }
        //Printing the board
        public void displayBoard(){
            for(int i=0;i<=5;++i){
                for(int j=0;j<=6;++j){
                    console.print((board[i][j] == 2)?"O":(board[i][j] == 1)?"X":"_");
                    console.print(" ");
                }
                console.println();
            }
            console.println();
        }
        
        public void displayBoard(int pointerLocation) {
            for(int i = 0;i<7;i++) {
                console.print((pointerLocation == i)?"↓":" "); // ↓ is Alt+25
                console.print(" ");
            }
            console.println();
            displayBoard();
        }
        
        public void printBoard() {
            System.out.println();
            for(int i=0;i<=5;++i){
                for(int j=0;j<=6;++j){
                    System.out.print((board[i][j] == 2)?"O":(board[i][j] == 1)?"X":"_");
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    class Connect4AI { 
        private Board b;
        private Scanner scan;
        private int nextMoveLocation=-1;
        private int maxDepth = 9;

        public Connect4AI(Board b){
            this.b = b;
            scan = new Scanner(System.in);
        }

        //Opponent's turn
        public void letOpponentMove() throws InterruptedException{
            console.displayNothing();
            int move = 3;
            boolean notFinalMove = true;
            do {
                console.clear();
                b.displayBoard(move);
                console.println("Your move.");
                Direction userInput = console.getNextLeftRightEnter();
                switch(userInput) {
                    case WEST:
                        if(move != 0) move--;
                        break;
                    case SOUTH:
                        if(!b.isLegalMove(move)) console.println("Invalid move.\n\n");
                        else notFinalMove = false;
                        break;
                    case EAST:
                        if(move != 6) move++;
                        break;
                }
            } while (notFinalMove);

            //Assume 1 is the opponent
            b.placeMove(move, (byte)2);
            console.clear();
            console.println();
            b.displayBoard();
            console.println("\nAI thinking....");
        }

        //Game Result
        public int gameResult(Board b){
            int aiScore = 0, humanScore = 0;
            for(int i=5;i>=0;--i){
                for(int j=0;j<=6;++j){
                    if(b.board[i][j]==0) continue;

                    //Checking cells to the right
                    if(j<=3){
                        OUTER_3:
                        for (int k = 0; k<4; ++k) {
                            switch (b.board[i][j+k]) {
                                case 1: 
                                    aiScore++;
                                    break;
                                case 2:
                                    humanScore++;
                                    break;
                                default:
                                    break OUTER_3;
                            }
                        }
                        if(aiScore==4)return 1; else if (humanScore==4)return 2;
                        aiScore = 0; humanScore = 0;
                    } 

                    //Checking cells up
                    if(i>=3){
                        OUTER_2:
                        for (int k = 0; k<4; ++k) {
                            switch (b.board[i-k][j]) {
                                case 1:
                                    aiScore++;
                                    break;
                                case 2:
                                    humanScore++;
                                    break;
                                default:
                                    break OUTER_2;
                            }
                        }
                        if(aiScore==4)return 1; else if (humanScore==4)return 2;
                        aiScore = 0; humanScore = 0;
                    } 

                    //Checking diagonal up-right
                    if(j<=3 && i>= 3){
                        OUTER_1:
                        for (int k = 0; k<4; ++k) {
                            switch (b.board[i-k][j+k]) {
                                case 1:
                                    aiScore++;
                                    break;
                                case 2:
                                    humanScore++;
                                    break;
                                default:
                                    break OUTER_1;
                            }
                        }
                        if(aiScore==4)return 1; else if (humanScore==4)return 2;
                        aiScore = 0; humanScore = 0;
                    }

                    //Checking diagonal up-left
                    if(j>=3 && i>=3){
                        OUTER:
                        for (int k = 0; k<4; ++k) {
                            switch (b.board[i-k][j-k]) {
                                case 1:
                                    aiScore++; 
                                    break;
                                case 2:
                                    humanScore++;
                                    break;
                                default:
                                    break OUTER;
                            }
                        }
                        if(aiScore==4)return 1; else if (humanScore==4)return 2;
                        aiScore = 0; humanScore = 0;
                    }  
                }
            }

            for(int j=0;j<7;++j){
                //Game has not ended yet
                if(b.board[0][j]==0)return -1;
            }
            //Game draw!
            return 0;
        }

        int calculateScore(int aiScore, int moreMoves){   
            int moveScore = 4 - moreMoves;
            switch (aiScore) {
                case 0:
                    return 0;
                case 1:
                    return 1*moveScore;
                case 2:
                    return 10*moveScore;
                case 3:
                    return 100*moveScore;
                default:
                    return 1000;
            }
        }

        //Evaluate board favorableness for AI
        public int evaluateBoard(Board b){

            int aiScore=1;
            int score=0;
            int blanks = 0;
            int k=0, moreMoves=0;
            for(int i=5;i>=0;--i){
                for(int j=0;j<=6;++j){

                    if(b.board[i][j]==0 || b.board[i][j]==2) continue; 

                    if(j<=3){ 
                        OUTER:
                        for (k=1; k<4; ++k) {
                            switch (b.board[i][j+k]) {
                                case 1:
                                    aiScore++;
                                    break;
                                case 2:
                                    aiScore=0;
                                    blanks = 0;
                                    break OUTER;
                                default:
                                    blanks++;
                                    break;
                            }
                        }

                        moreMoves = 0; 
                        if(blanks>0) 
                            for(int c=1;c<4;++c){
                                int column = j+c;
                                for(int m=i; m<= 5;m++){
                                 if(b.board[m][column]==0)moreMoves++;
                                    else break;
                                } 
                            } 

                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;   
                        blanks = 0;
                    } 

                    if(i>=3){
                        for(k=1;k<4;++k){
                            if(b.board[i-k][j]==1)aiScore++;
                            else if(b.board[i-k][j]==2){aiScore=0;break;} 
                        } 
                        moreMoves = 0; 

                        if(aiScore>0){
                            int column = j;
                            for(int m=i-k+1; m<=i-1;m++){
                             if(b.board[m][column]==0)moreMoves++;
                                else break;
                            }  
                        }
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;  
                        blanks = 0;
                    }

                    if(j>=3){
                        OUTER_1:
                        for (k=1; k<4; ++k) {
                            switch (b.board[i][j-k]) {
                                case 1:
                                    aiScore++;
                                    break;
                                case 2:
                                    aiScore=0;
                                    blanks=0;
                                    break OUTER_1;
                                default:
                                    blanks++;
                                    break;
                            }
                        }
                        moreMoves=0;
                        if(blanks>0) 
                            for(int c=1;c<4;++c){
                                int column = j- c;
                                for(int m=i; m<= 5;m++){
                                 if(b.board[m][column]==0)moreMoves++;
                                    else break;
                                } 
                            } 

                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1; 
                        blanks = 0;
                    }

                    if(j<=3 && i>=3){
                        OUTER_2:
                        for (k=1; k<4; ++k) {
                            switch (b.board[i-k][j+k]) {
                                case 1:                        
                                    aiScore++;
                                    break;
                                case 2:
                                    aiScore=0;
                                    blanks=0;
                                    break OUTER_2;
                                default:
                                    blanks++;
                                    break;
                            }
                        }
                        moreMoves=0;
                        if(blanks>0){
                            for(int c=1;c<4;++c){
                                int column = j+c, row = i-c;
                                OUTER_3:
                                for (int m = row; m<=5; ++m) {
                                    switch (b.board[m][column]) {
                                        case 0:
                                            moreMoves++;
                                            break;
                                        case 1:
                                            ;
                                            break;
                                        default:
                                            break OUTER_3;
                                    }
                                }
                            } 
                            if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                            aiScore=1;
                            blanks = 0;
                        }
                    }

                    if(i>=3 && j>=3){
                        OUTER_4:
                        for (k=1; k<4; ++k) {
                            switch (b.board[i-k][j-k]) {
                                case 1:                        
                                    aiScore++;
                                    break;
                                case 2:
                                    aiScore=0;
                                    blanks=0;
                                    break OUTER_4;
                                default:
                                    blanks++;
                                    break;
                            }
                        }
                        moreMoves=0;
                        if(blanks>0){
                            for(int c=1;c<4;++c){
                                int column = j-c, row = i-c;
                                OUTER_5:
                                for (int m = row; m<=5; ++m) {
                                    switch (b.board[m][column]) {
                                        case 0:
                                            moreMoves++;
                                            break;
                                        case 1:
                                            ;
                                            break;
                                        default:
                                            break OUTER_5;
                                    }
                                }
                            } 
                            if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                            aiScore=1;
                            blanks = 0;
                        }
                    } 
                }
            }
            return score;
        } 

        public int minimax(int depth, int turn, int alpha, int beta){

            if(beta<=alpha){if(turn == 1) return Integer.MAX_VALUE; else return Integer.MIN_VALUE; }
            int gameResult = gameResult(b);

            switch (gameResult) {
                case 1:
                    return Integer.MAX_VALUE/2; 
                case 2:
                    return Integer.MIN_VALUE/2;
                case 0:
                    return 0;
                default:
                    break;
            }

            if(depth==maxDepth)return evaluateBoard(b);

            int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;

            for(int j=0;j<=6;++j){

                int currentScore = 0;

                if(!b.isLegalMove(j)) continue; 

                if(turn==1){
                        b.placeMove(j, 1);
                        currentScore = minimax(depth+1, 2, alpha, beta);

                        if(depth==0){
                            System.out.println("Score for location "+j+" = "+currentScore);
                            if(currentScore > maxScore)nextMoveLocation = j; 
                            if(currentScore == Integer.MAX_VALUE/2){b.undoMove(j);break;}
                        }

                        maxScore = Math.max(currentScore, maxScore);

                        alpha = Math.max(currentScore, alpha);  
                } 
                else if(turn==2){
                        b.placeMove(j, 2);
                        currentScore = minimax(depth+1, 1, alpha, beta);
                        minScore = Math.min(currentScore, minScore);

                        beta = Math.min(currentScore, beta); 
                }  
                b.undoMove(j); 
                if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break; 
            }  
            return turn==1?maxScore:minScore;
        }

        public int getAIMove(){
            nextMoveLocation = -1;
            minimax(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            return nextMoveLocation;
        }

        public void playAgainstAIConsole() throws InterruptedException{
            console.displayButtons(new String[]{"Yes", "No"});
            console.println("Would you like to play first?");
            String answer = console.getNextButtonPress().trim();
            console.clear();

            if(answer.equalsIgnoreCase("yes")) {
                b.displayBoard(); 
                letOpponentMove();
            }
            b.printBoard();
            b.placeMove(3, 1);

            while(true){
                letOpponentMove();
                b.printBoard();

                int gameResult = gameResult(b);
                if(gameResult==1 || gameResult==2 || gameResult==0){
                    endGame(gameResult);
                    break;
                }

                b.placeMove(getAIMove(), 1);
                gameResult = gameResult(b);
                if(gameResult==1 || gameResult==2 || gameResult==0){
                    endGame(gameResult);
                    break;
                }
            }
        }
        
        private void endGame(int result) {
            console.clear();
            b.displayBoard();
            switch(result) {
                case 1:
                    console.println("AI Wins!");
                    break;
                case 2:
                    console.println("You Win!");
                    break;
                case 0:
                    console.println("Draw!");
                    break;
            }
            console.println();
        }
    }
    
    private void twoPlayerGame(Board b) throws InterruptedException {
        Connect4AI helper = new Connect4AI(b);
        int evaluation = -1;
        int player = 2;
        console.displayNothing();
        while(evaluation == -1) {
            player = (player == 1)?2:1;
            console.clear();
            b.displayBoard();
            int move = 3;
            boolean notFinalMove = true;
            do {
                console.clear();
                b.displayBoard(move);
                console.println("Player " + player + "'s turn");
                Direction userInput = console.getNextLeftRightEnter();
                switch(userInput) {
                    case WEST:
                        if(move != 0) move--;
                        break;
                    case SOUTH:
                        if(!b.isLegalMove(move)) console.println("Invalid move.\n\n");
                        else notFinalMove = false;
                        break;
                    case EAST:
                        if(move != 6) move++;
                        break;
                }
            } while (notFinalMove);
            b.placeMove(move, player);
            evaluation = helper.gameResult(b);
        }
        console.clear();
        b.displayBoard();
        switch(evaluation) {
            case 1:
            case 2:
                console.println("Player " + evaluation + " wins!");
                break;
            case 0:
                console.println("Draw!");
                break;
        }
        console.println();
    }
}