/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.alphabetaplayer;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;
import put.ai.games.game.moves.PlaceMove;
import put.ai.games.game.moves.impl.MoveMoveImpl;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AlphaBetaPlayer extends Player {
    private long startTime;

    @Override
    public String getName() {
        return "Mikołaj Frankowski 132220 Krzysztof Pasiewicz 132302";
    }


    @Override
    public Move nextMove(Board b) {
        long startTime = System.currentTimeMillis();

        List<Move> moves = b.getMovesFor(getColor());

        int maxRating=-1000;
        int i=0;
        int bestMoveIdx=0;

        //For every possible move
        for(Move m: moves){
            Board tempBoard = b;

            //Make a move on a copy of the board
            tempBoard.doMove(m);

            int rating=-1000;
            //If there are more than 125 possible moves try to cover as much space as possible
            if(moves.size()>125){
                if(b.getMovesFor(getColor()).size()==b.getSize()||b.getMovesFor(getColor()).size()==b.getSize()-5){
                    return moves.get(b.getSize()+2);
                }
                if(isValid(tempBoard,m)){
                    rating=moveRating(tempBoard);
                }
            }
            //If there are between 125 and 50 possible moves fire the alpha-beta algorithm with the depth of 2
            else if(moves.size()>50) {
                rating = alphaBeta(tempBoard, 2, -1000, 1000, true);
            }
            //If there are less than 50 possible moves fire the alpha-beta algorithm with the depth of 3
            else{
                rating = alphaBeta(tempBoard, 3, -1000, 1000, true);
            }

            //Withdraw the move from the copy
            tempBoard.undoMove(m);

            //If the rating is better set as the new best move
            if(rating>maxRating){
                bestMoveIdx=i;
                maxRating=rating;
            }

            i++;

            if(System.currentTimeMillis()-startTime+5>getTime()){
                return moves.get(bestMoveIdx);
            }
        }

        return moves.get(bestMoveIdx);
    }

    //Alpha-beta algorithm
    private int alphaBeta(Board b,int depth,int alpha,int beta,boolean isMin){
        if(depth==0||isLast(b))
        {
            return moveRating(b);
        }

        if(isMin){
            for(Move m: b.getMovesFor(getOpponent(getColor()))){
                Board tempBoard = b;

                tempBoard.doMove(m);

                int value = alphaBeta(tempBoard,depth-1,-1000,1000,false);
                alpha=max(alpha,value);

                tempBoard.undoMove(m);

                if(alpha>=beta){
                    return beta;
                }

                if(System.currentTimeMillis()-startTime+5>getTime()){
                    return alpha;
                }
            }

            return alpha;
        }
        else{
            for(Move m: b.getMovesFor(getColor())){
                Board tempBoard = b;

                tempBoard.doMove(m);

                int value = alphaBeta(tempBoard,depth-1,-1000,1000,true);
                beta=min(beta,value);

                tempBoard.undoMove(m);

                if(alpha>=beta){
                    return alpha;
                }

                if(System.currentTimeMillis()-startTime+5>getTime()){
                    return beta;
                }
            }

            return beta;
        }
    }

    //Check if the level is not terminal
    private boolean isLast(Board b){
        if(b.getMovesFor(getColor()).size()==0||b.getMovesFor(getOpponent(getColor())).size()==0){
            return true;
        }

        return false;
    }

    //Evaluate the effectivness of a move
    private int moveRating(Board b){
        return b.getMovesFor(getColor()).size()-b.getMovesFor(getOpponent(getColor())).size();
    }

    //Check if the move is the correct place relative to other stones
    private boolean isValid(Board b,Move m){
        PlaceMove move;
        move=(PlaceMove) m;
        int x=move.getX();
        int y=move.getY();
        Color color=getColor();
        int size=b.getSize();

        if(x+1<size&&y+2<size&&b.getState(x+1,y+2)==color){
            if(notRepetative(b,x+1,y+2)) {
                return true;
            }
        }
        if(x-1>=0&&y+2<size&&b.getState(x-1,y+2)==color){
            if(notRepetative(b,x-1,y+2)) {
                return true;
            }
        }
        if(x+1<size&&y-2>=0&&b.getState(x+1,y-2)==color){
            if(notRepetative(b,x+1,y-2)) {
                return true;
            }
        }
        if(x-1>=0&&y-2>=0&&b.getState(x-1,y-2)==color){
            if(notRepetative(b,x-1,y-2)) {
                return true;
            }
        }
        if(x+2<size&&y+1<size&&b.getState(x+2,y+1)==color){
            if(notRepetative(b,x+2,y+1)) {
                return true;
            }
        }
        if(x-2>=0&&y+1<size&&b.getState(x-2,y+1)==color){
            if(notRepetative(b,x-2,y+1)) {
                return true;
            }
        }
        if(x+2<size&&y-1>=0&&b.getState(x+2,y-1)==color){
            if(notRepetative(b,x+2,y-1)) {
                return true;
            }
        }
        if(x-2>=0&&y-1>=0&&b.getState(x-2,y-1)==color){
            if(notRepetative(b,x-2,y-1)) {
                return true;
            }
        }

        return false;
    }

    //Check if the move doesn't take up space that the opponent can't place their stones at
    private boolean notRepetative(Board b,int x,int y){
        Color color=getColor();
        int size=b.getSize();

        if(x+1<size&&b.getState(x+1,y)==color){
            return false;
        }
        if(x-1>=0&&b.getState(x-1,y)==color){
            return false;
        }
        if(y+1>=0&&b.getState(x-1,y)==color){
            return false;
        }
        if(y-1>=0&&b.getState(x-1,y)==color){
            return false;
        }

        return true;
    }
}
