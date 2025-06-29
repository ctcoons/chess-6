package chess;

import java.util.ArrayList;
import java.util.Arrays;
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

        // ACCOUNTING FOR COLOR
        int multiplier = 1;
        if (myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            multiplier = -1;
        }

        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();


        // Check if on starting line
        boolean onStartingLine = (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) || (myPiece.getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7);


        // MOVING STRAIGHT FORWARD
        if (pieceAtLocation(currentRow + multiplier, currentCol) == null) {
            ChessPosition destination = new ChessPosition(currentRow + multiplier, currentCol);
            pawn_with_promotions_possible(myPosition, destination, validMoves);
            if (pieceAtLocation(currentRow + 2*multiplier, currentCol) == null && onStartingLine) {
                ChessPosition destination2 = new ChessPosition(currentRow + 2*multiplier, currentCol);
                pawn_with_promotions_possible(myPosition, destination2, validMoves);
            }
        }


        // Taking an Enemy at + 1 column
        if (pieceAtLocation(currentRow + multiplier, currentCol + 1) != null) {
            if (pieceAtLocation(currentRow + multiplier, currentCol + 1).getTeamColor() != myPiece.getTeamColor()) {
                ChessPosition destination = new ChessPosition(currentRow + multiplier, currentCol + 1);
                pawn_with_promotions_possible(myPosition, destination, validMoves);
            }
        }

        // Taking an Enemy at -1 column
        if (pieceAtLocation(currentRow + multiplier, currentCol -1) != null) {
            if (pieceAtLocation(currentRow + multiplier, currentCol -1).getTeamColor() != myPiece.getTeamColor()) {
                ChessPosition destination = new ChessPosition(currentRow + multiplier, currentCol - 1);
                pawn_with_promotions_possible(myPosition, destination, validMoves);
            }

        }



        // If pawn is on the back line
            // If there is a piece 2 spaces ahead while on the back line
        // If the pawn is not in the back line
        // If there is a piece in front
        // If there is an enemy piece diagonal
        return validMoves;
    }

    public void pawn_with_promotions_possible(ChessPosition start, ChessPosition destination, Collection<ChessMove> validMoves) {

        // Check to see if there is possibility of promotion
        boolean potentialPromotion = false;
        if (myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (myPosition.getRow() == 7) {
                potentialPromotion = true;
            }
        } else {
            if (myPosition.getRow() == 2) {
                potentialPromotion = true;
            }
        }


        if (potentialPromotion) {
            Collection<ChessPiece.PieceType> validPromotions = Arrays.asList(ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT);

            for (ChessPiece.PieceType type : validPromotions) {
                ChessMove move = new ChessMove(start, destination, type);
                validMoves.add(move);
            }
        } else {
            ChessMove newMove = new ChessMove(start, destination, null);
            validMoves.add(newMove);
        }
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

    ChessPiece pieceAtLocation (int row, int col) {
        if (row < 1 || row > 8 || col < 1 || col > 8) {
            return myPiece;
        }
        ChessPosition queryPosition = new ChessPosition(row, col);
        return board.getPiece(queryPosition);
    }
}

