package xadrez;

import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import util.Constante;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(Constante.TAMANHO_TABULEIRO, Constante.TAMANHO_TABULEIRO);
        configuracaoInicial();
    }

    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    private void configuracaoInicial() {
        tabuleiro.lugarPeca(new Torre(tabuleiro, Cor.WHITE), new Posicao(2, 1));
        tabuleiro.lugarPeca(new Rei(tabuleiro, Cor.BLACK), new Posicao(0, 4));
        tabuleiro.lugarPeca(new Rei(tabuleiro, Cor.WHITE), new Posicao(7, 4));
    }
}
