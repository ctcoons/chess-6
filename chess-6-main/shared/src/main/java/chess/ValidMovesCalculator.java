package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ValidMovesCalculator {
    ChessBoard board;
    ChessPosition myPosition;
    ChessPiece myPiece;
    ChessPiece.PieceType myPieceType;

    public ValidMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        System.out.println("At *ValidMovesCalculator*");
        this.board = board;
        this.myPosition = myPosition;
        this.myPiece = board.getPiece(myPosition);
        this.myPieceType = myPiece.getPieceType();
    }

    Collection<ChessMove> getMoves() {
        if (myPieceType == ChessPiece.PieceType.QUEEN) {
            return queenMoves();
        } else if (myPieceType == ChessPiece.PieceType.KING) {
            return kingMoves();
        } else if (myPieceType == ChessPiece.PieceType.BISHOP) {
            return bishopMoves();
        } else if (myPieceType == ChessPiece.PieceType.ROOK) {
            return rookMoves();
        } else if (myPieceType == ChessPiece.PieceType.PAWN) {
            return pawnMoves();
        } else {
            return new ArrayList<>();
        }

    }

    Collection<ChessMove> kingMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        for(int colChange = -1; colChange < 2; colChange++) {
            for (int rowChange = -1; rowChange < 2; rowChange++) {

                int newRow = myPosition.getRow() + rowChange;
                int newCol = myPosition.getColumn() + colChange;

                // Check For Out Of Bounds
                if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) continue;

                ChessPosition newPos = new ChessPosition(newRow, newCol);

                if (board.getPiece(newPos) == null) {
                    validMoves.add(new ChessMove(myPosition, newPos, myPieceType));
                } else if (board.getPiece(newPos).getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPos, myPieceType));
                }

            }
        }
        return validMoves;
    }

    Collection<ChessMove> queenMoves() {
        throw new RuntimeException("Not implemented");
    }

    Collection<ChessMove> bishopMoves() {
        throw new RuntimeException("Not implemented");
    }

    Collection<ChessMove> rookMoves() {
        throw new RuntimeException("Not implemented");
    }

    Collection<ChessMove> pawnMoves() {
        throw new RuntimeException("Not implemented");
    }

}

/*
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
 */