package org.iesvdm.tddjava.connect4;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.iesvdm.tddjava.connect4.Connect4.Color.GREEN;
import static org.iesvdm.tddjava.connect4.Connect4.Color.RED;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Connect4TDDSpec {

    /**
     * clase bajo testeo
     */
    private Connect4TDD tested;

    private OutputStream output;

    @BeforeEach
    public void beforeEachTest() {
        output = new ByteArrayOutputStream();

        //Se instancia el juego modificado para acceder a la salida de consola
        tested = new Connect4TDD(new PrintStream(output));
    }

    /*
     * The board is composed by 7 horizontal and 6 vertical empty positions
     */

    @Test
    public void whenTheGameStartsTheBoardIsEmpty() {
        assertThat(tested.getNumberOfDiscs()).isEqualTo(0);
    }

    /*
     * Players introduce discs on the top of the columns.
     * Introduced disc drops down the board if the column is empty.
     * Future discs introduced in the same column will stack over previous ones
     */

    @Test
    public void whenDiscOutsideBoardThenRuntimeException() {
        assertThatThrownBy(
                () -> tested.putDiscInColumn(9))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid column 9");
        assertThatThrownBy(
                () -> tested.putDiscInColumn(-1))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid column -1");
        assertThatThrownBy(
                () -> tested.putDiscInColumn(-3))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid column -3");
    }

    @Test
    public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
        assertThat(tested.putDiscInColumn(0)).isEqualTo(0);
    }

    @Test
    public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
        tested.putDiscInColumn(0);
        tested.putDiscInColumn(1);
        assertThat(tested.putDiscInColumn(1)).isEqualTo(1);
    }

    @Test
    public void whenDiscInsertedThenNumberOfDiscsIncreases() {
        int discoIniciales = 0;
        for (int i = 0; i < 7; i++) {
            tested.putDiscInColumn(i);
            assertThat(tested.getNumberOfDiscs())
                    .isEqualTo(discoIniciales+1+i);
        }
    }

    @Test
    public void whenNoMoreRoomInColumnThenRuntimeException() {
        int discosMaximos = tested.getNumberOfDiscs();
        for (int i = 0; i < discosMaximos; i++) {
            tested.putDiscInColumn(i);
        }
        assertThatThrownBy( () -> tested.putDiscInColumn(7)
        ).isInstanceOf(RuntimeException.class).hasMessageContaining("Invalid column 7");
    }

    /*
     * It is a two-person game so there is one colour for each player.
     * One player uses red ('R'), the other one uses green ('G').
     * Players alternate turns, inserting one disc every time
     */

    @Test
    public void whenFirstPlayerPlaysThenDiscColorIsRed() {
        tested.getCurrentPlayer();
        assertThat(tested.getCurrentPlayer()).isEqualTo("R");
    }

    @Test
    public void whenSecondPlayerPlaysThenDiscColorIsGreen() {
        tested.putDiscInColumn(1);
        tested.getCurrentPlayer();
        assertThat(tested.getNumberOfDiscs()).isEqualTo(1);
        assertThat(tested.getCurrentPlayer()).isEqualTo("G");
    }

    /*
     * We want feedback when either, event or error occur within the game.
     * The output shows the status of the board on every move
     */

    @Test
    public void whenAskedForCurrentPlayerTheOutputNotice() {
        String currentPlayer = tested.getCurrentPlayer();
        String mensajeOutput = String.format("Player %s turn%n", currentPlayer);
        assertThat(output.toString()).isEqualTo(mensajeOutput);
    }

    @Test
    public void whenADiscIsIntroducedTheBoardIsPrinted() {
        String expected = """
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                | | | | | | | |
                |R| | | | | | |
                """;
        tested.putDiscInColumn(1);
        assertThat(output.toString().replaceAll("\r", "")).isEqualTo(expected);
    }

    /*
     * When no more discs can be inserted, the game finishes and it is considered a draw
     */

    @Test
    public void whenTheGameStartsItIsNotFinished() {
        tested.putDiscInColumn(1);
        assertThat(tested.getNumberOfDiscs()).isEqualTo(1);
        assertThat(tested.isFinished()).isFalse();
    }

    @Test
    public void whenNoDiscCanBeIntroducedTheGamesIsFinished() {

    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight vertical line then that player wins
     */

    @Test
    public void when4VerticalDiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);

        assertThat(tested.getNumberOfDiscs()).isEqualTo(7);
        assertThat(tested.getWinner()).isEqualTo("R");
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight horizontal line then that player wins
     */

    @Test
    public void when4HorizontalDiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(4);

        assertThat(tested.getNumberOfDiscs()).isEqualTo(7);
        assertThat(tested.isFinished()).isTrue();
    }

    /*
     * If a player inserts a disc and connects more than 3 discs of his colour
     * in a straight diagonal line then that player wins
     */

    @Test
    public void when4Diagonal1DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);

        assertThat(tested.isFinished()).isTrue();
        assertThat(tested.getWinner()).isEqualTo(RED);
    }

    @Test
    public void when4Diagonal2DiscsAreConnectedThenThatPlayerWins() {
        tested.putDiscInColumn(1);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(2);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(3);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(4);
        tested.putDiscInColumn(5);
        tested.putDiscInColumn(4);

        assertThat(tested.isFinished()).isTrue();
        assertThat(tested.getWinner()).isEqualTo("R");
    }
}
