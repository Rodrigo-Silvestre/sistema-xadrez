package aplicacao;

import jogoTabuleiro.XadrezException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<PecaXadrez> capturadas = new ArrayList<>();
        while(!partidaXadrez.getCheckMate()) {
            try {
                UI.limparTela();
                UI.imprimirPartida(partidaXadrez, capturadas);
                System.out.println();
                System.out.print("Origem: ");
                PosicaoXadrez origem = UI.leiaPosicaoXadrez(scanner);

                boolean[][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
                UI.imprimirTabuleiro(partidaXadrez.getPecas(), possiveisMovimentos);

                System.out.println();
                System.out.print("Destino: ");
                PosicaoXadrez destino = UI.leiaPosicaoXadrez(scanner);

                PecaXadrez pecaCapturada = partidaXadrez.executarMovimentoXadrez(origem, destino);

                if (pecaCapturada != null) {
                    capturadas.add(pecaCapturada);
                }
            }
            catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        UI.limparTela();
        UI.imprimirPartida(partidaXadrez, capturadas);
    }
}
