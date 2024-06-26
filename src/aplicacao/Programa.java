package aplicacao;

import jogoTabuleiro.XadrezException;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

import java.security.InvalidParameterException;
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
                if (partidaXadrez.getPromocao() != null) {
                    System.out.print("Entre com a peça para a promoção (B/C/T/Q): ");
                    String tipo = scanner.nextLine().toUpperCase();
                    while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
                        System.out.print("Entre com a peça para a promoção (B/C/T/Q): ");
                        tipo = scanner.nextLine().toUpperCase();
                    }
                    partidaXadrez.subistituirPecaPromovida(tipo);
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
