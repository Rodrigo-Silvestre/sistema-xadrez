package xadrez.pecas;

import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

    private PartidaXadrez partidaXadrez;
    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    private boolean testeTorreRoque(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return /*p != null &&*/p instanceof Torre && p.getCor() == getCor() && p.getMovimentos() == 0;
    }

    private boolean podeMover(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // Cima
        p.setValues(posicao.getLinha() - 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Noroeste
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Esquerda
        p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Sudoeste
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Nordeste
        p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Direita
        p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Sudeste
        p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Baixo
        p.setValues(posicao.getLinha() + 1, posicao.getColuna());
        if (getTabuleiro().posicaoExistente(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // Movimento especial Roque
        if (getMovimentos() == 0 && !partidaXadrez.getCheck()) {
            // Roque pequeno
            Posicao posicaoTorreDireita = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if (testeTorreRoque(posicaoTorreDireita)) {
                Posicao posicaoDireita1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao posicaoDireita2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if (getTabuleiro().peca(posicaoDireita1) == null && getTabuleiro().peca(posicaoDireita2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }
            // Roque Grande
            Posicao posicaoTorreEsquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if (testeTorreRoque(posicaoTorreEsquerda)) {
                Posicao posicaoEsquerda1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao posicaoEsquerda2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao posicaoEsquerda3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if (getTabuleiro().peca(posicaoEsquerda1) == null && getTabuleiro().peca(posicaoEsquerda2) == null && getTabuleiro().peca(posicaoEsquerda3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "R";
    }
}
