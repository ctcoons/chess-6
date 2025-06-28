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

        System.out.println("KING STARTING POSITION [" + myPosition.getRow() + ":" + myPosition.getColumn() + "]");

        ArrayList<ChessMove> validMoves = new ArrayList<>();
        for(int colChange = -1; colChange < 2; colChange++) {
            for (int rowChange = -1; rowChange < 2; rowChange++) {

                int newRow = myPosition.getRow() + rowChange;
                int newCol = myPosition.getColumn() + colChange;

                // Check For Out Of Bounds
                if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) continue;

                ChessPosition newPos = new ChessPosition(newRow, newCol);

                if (board.getPiece(newPos) == null) {
                    System.out.println("Adding New Position at [" + newRow + ":" + newCol + "]");
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                } else if (board.getPiece(newPos).getTeamColor() != myPiece.getTeamColor()) {
                    System.out.println("Adding New Position at [" + newRow + ":" + newCol);
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                }

            }
        }
        return validMoves;
    }

    Collection<ChessMove> queenMoves() {
        throw new RuntimeException("Not implemented");
    }

    Collection<ChessMove> bishopMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        int[] direction = {-1, 1};

        for (int rowMult : direction) {
            for (int colMult : direction) {
                int mag = 1;
                while (true) {
                    int newRow = myPosition.getRow() + rowMult * mag;
                    int newCol = myPosition.getColumn() + colMult * mag;

                    if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) break;
                    mag++;

                    ChessPosition newPos = new ChessPosition(newRow, newCol);
                    ChessPiece newSquarePiece = board.getPiece(newPos);

                    if (newSquarePiece == null) {
                        validMoves.add(new ChessMove(myPosition, newPos, null));
                        continue;
                    }

                    if (newSquarePiece.getTeamColor() == myPiece.getTeamColor()) {
                        break;
                    }

                    if (newSquarePiece.getTeamColor() != myPiece.getTeamColor()) {
                        validMoves.add(new ChessMove(myPosition, newPos, null));
                        break;
                    }

                    break;

                }
            }
        }


        return validMoves;
    }

    Collection<ChessMove> rookMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        int[] direction = {-1, 1};

        for (int dir : direction) {

            // ROWS
            int mag = 1;
            while (true) {
                int newRow = myPosition.getRow() + dir * mag;
                int myCol = myPosition.getColumn();

                if (newRow < 1 || newRow > 8) break;
                mag++;

                ChessPosition newPos = new ChessPosition(newRow, myCol);
                ChessPiece newSquarePiece = board.getPiece(newPos);

                if (newSquarePiece == null) {
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                    continue;
                }

                if (newSquarePiece.getTeamColor() == myPiece.getTeamColor()) {
                    break;
                }

                if (newSquarePiece.getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                    break;
                }

                break;
            }

            mag = 1;
            while (true) {
                int myRow = myPosition.getRow();
                int newCol = myPosition.getColumn() + dir * mag;

                if (newCol < 1 || newCol > 8) break;
                mag++;

                ChessPosition newPos = new ChessPosition(myRow, newCol);
                ChessPiece newSquarePiece = board.getPiece(newPos);

                if (newSquarePiece == null) {
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                    continue;
                }

                if (newSquarePiece.getTeamColor() == myPiece.getTeamColor()) {
                    break;
                }

                if (newSquarePiece.getTeamColor() != myPiece.getTeamColor()) {
                    validMoves.add(new ChessMove(myPosition, newPos, null));
                    break;
                }

                break;
            }





        }


        return validMoves;
    }


    Collection<ChessMove> pawnMoves() {
        throw new RuntimeException("Not implemented");
    }

}