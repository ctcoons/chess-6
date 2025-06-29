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
        } else if (myPieceType == ChessPiece.PieceType.KNIGHT) {
            return knightMoves();
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
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        validMoves.addAll(diagonal());
        validMoves.addAll(up_and_down());

        return validMoves;
    }

    Collection<ChessMove> bishopMoves() {
        return diagonal();
    }

    Collection<ChessMove> rookMoves() {
        return up_and_down();
    }

    Collection<ChessMove> knightMoves() {
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        int[][] locations = {{-2, 1}, {-2, -1}, {-1, 2}, {-1, -2}, {1, 2}, {1, -2}, {2, 1}, {2, -1}};
        for(int[] loc : locations) {
            int newRow = myPosition.getRow() + loc[0];
            int newCol = myPosition.getColumn() + loc[1];

            if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) continue;

            ChessPosition newPos = new ChessPosition(newRow, newCol);
            ChessPiece newSquarePiece = board.getPiece(newPos);

            if (newSquarePiece == null) {
                validMoves.add(new ChessMove(myPosition, newPos, null));
                continue;
            }

            if (newSquarePiece.getTeamColor() == myPiece.getTeamColor()) {
                continue;
            }

            if (newSquarePiece.getTeamColor() != myPiece.getTeamColor()) {
                validMoves.add(new ChessMove(myPosition, newPos, null));
            }

        }


        return validMoves;
    }

    Collection<ChessMove> pawnMoves() {

        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // If pawn is on the back line
            // If there is a piece 2 spaces ahead while on the back line
        // If the pawn is not in the back line
        // If there is a piece in front
        // If there is an enemy piece diagonal
        return validMoves;



    }

    Collection<ChessMove> diagonal() {
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

    Collection<ChessMove> up_and_down() {
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

    boolean outOfRange (int[] coordinates) {
        int newRow = coordinates[0];
        int newCol = coordinates[1];

        return newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8;
    }
}