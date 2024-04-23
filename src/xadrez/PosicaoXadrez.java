package xadrez;

import jogoTabuleiro.Posicao;
import jogoTabuleiro.XadrezException;
import util.Constante;

public class PosicaoXadrez {

    private char coluna;
    private int linha;

    public PosicaoXadrez(char coluna, int linha) {
        if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > Constante.TAMANHO_TABULEIRO) {
            throw new XadrezException("Erro ao instanciar PosicaoXadrez. Os valores válidos são de a1 a h8.");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Posicao toPosicao() {
        return new Posicao(8 - linha, coluna - 'a');
    }

    protected static PosicaoXadrez daPosicao(Posicao posicao) {
        return new PosicaoXadrez((char) ('a' + posicao.getColuna()), Constante.TAMANHO_TABULEIRO - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
