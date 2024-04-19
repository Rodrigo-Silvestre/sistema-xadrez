package aplicacao;

import util.Constante;
import xadrez.PecaXadrez;

public class UI {

    public static void imprimirTabuleiro(PecaXadrez[][] pecas) {
        for (int i = 0; i < pecas.length; i++) {
            System.out.print((Constante.TAMANHO_TABULEIRO - i) + " ");
            for (int j = 0; j < pecas.length; j++) {
                imprimirPeca(pecas[i][j]);
            }
            System.out.println();
        }
        System.out.print("  a b c d e f g h");
    }

    private static void imprimirPeca(PecaXadrez peca) {
        if (peca == null) {
            System.out.print("-");
        }
        else {
            System.out.print(peca);
        }
        System.out.print(" ");
    }
}
