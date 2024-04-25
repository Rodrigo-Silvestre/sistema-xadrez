package xadrez.pecas;

import jogoTabuleiro.Posicao;
import jogoTabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Peao extends PecaXadrez {

    private PartidaXadrez partidaXadrez;
    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if (getCor() == Cor.BRANCO) {
            p.setValues(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2) && getMovimentos() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() - 1, posicao.getColuna() + 1);
            // Movimento especial en passant brancas
            if (posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExistente(esquerda) && existePecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExistente(direita) && existePecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
            }
        }
        else {
            p.setValues(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().temPeca(p) && getTabuleiro().posicaoExistente(p2) && !getTabuleiro().temPeca(p2) && getMovimentos() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValues(posicao.getLinha() + 1, posicao.getColuna() + 1);
            // Movimento especial en passant pretas
            if (posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if (getTabuleiro().posicaoExistente(esquerda) && existePecaAdversaria(esquerda) && getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }
                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                if (getTabuleiro().posicaoExistente(direita) && existePecaAdversaria(direita) && getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulneravel()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        if (getTabuleiro().posicaoExistente(p) && existePecaAdversaria(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
