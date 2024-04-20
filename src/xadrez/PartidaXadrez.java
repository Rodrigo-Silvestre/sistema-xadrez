package xadrez;

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

    private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
    }

    private void configuracaoInicial() {
        lugarNovaPeca('b', 6, new Torre(tabuleiro, Cor.WHITE));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.BLACK));
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.WHITE));
    }
}
