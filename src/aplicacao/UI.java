package aplicacao;

import util.Constante;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    public static void limparTela() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static PosicaoXadrez leiaPosicaoXadrez(Scanner scanner) {
        try {
            String s = scanner.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new PosicaoXadrez(coluna, linha);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler PosicaoXadrez. Os valores válidos são de a1 a h8.");
        }
    }

    public static void imprimirPartida(PartidaXadrez partidaXadrez) {
        imprimirTabuleiro(partidaXadrez.getPecas());
        System.out.println();
        System.out.println("Turno: " + partidaXadrez.getTurno());
        System.out.println("Jogador esperando: " + partidaXadrez.getJogadorAtual());
    }

    public static void imprimirTabuleiro(PecaXadrez[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((Constante.TAMANHO_TABULEIRO - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.print("  a b c d e f g h");
    }

    public static void imprimirTabuleiro(PecaXadrez[][] pecas, boolean[][] possiveisMovimentos) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((Constante.TAMANHO_TABULEIRO - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j], possiveisMovimentos[i][j]);
            }
            System.out.println();
        }
        System.out.print("  a b c d e f g h");
    }

    private static void imprimirPeca(PecaXadrez peca, boolean fundo) {
        if (fundo) {
            System.out.print(Constante.ANSI_BLUE_BACKGROUND);
        }
        if (peca == null) {
            System.out.print("-" + Constante.ANSI_RESET);
        }
        else {
            if (peca.getCor() == Cor.BRANCO) {
                System.out.print(Constante.ANSI_WHITE + peca + Constante.ANSI_RESET);
            }
            else {
                System.out.print(Constante.ANSI_YELLOW + peca + Constante.ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
}
