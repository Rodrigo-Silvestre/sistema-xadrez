package aplicacao;

import jogoTabuleiro.XadrezException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        while(true) {
            try {
                UI.limparTela();
                UI.imprimirPartida(partidaXadrez);
                System.out.println();
                System.out.print("Origem: ");
                PosicaoXadrez origem = UI.leiaPosicaoXadrez(scanner);

                boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
                UI.imprimirTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);

                System.out.println();
                System.out.print("Destino: ");
                PosicaoXadrez destino = UI.leiaPosicaoXadrez(scanner);

                PecaXadrez pecaCapturada = partidaXadrez.executarMovimentoXadrez(origem, destino);
            }
            catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
