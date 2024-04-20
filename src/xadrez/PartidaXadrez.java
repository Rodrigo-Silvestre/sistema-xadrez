package xadrez;

import jogoTabuleiro.*;
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

    public PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
        Posicao origem = posicaoOrigem.toPosicao();
        Posicao destino = posicaoDestino.toPosicao();
        validarPosicaoOrigem(origem);
        Peca pecaCapturada = fazerMovimento(origem, destino);
        return (PecaXadrez) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(origem);
        tabuleiro.lugarPeca(p, destino);
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if (!tabuleiro.temPeca(posicao)) {
            throw new XadrezException("Posição inexistente");
        }
    }

    private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
    }

    private void configuracaoInicial() {
        lugarNovaPeca('c', 1, new Torre(tabuleiro, Cor.WHITE));
        lugarNovaPeca('c', 2, new Torre(tabuleiro, Cor.WHITE));
        lugarNovaPeca('d', 2, new Torre(tabuleiro, Cor.WHITE));
        lugarNovaPeca('e', 2, new Torre(tabuleiro, Cor.WHITE));
        lugarNovaPeca('e', 1, new Torre(tabuleiro, Cor.WHITE));
        lugarNovaPeca('d', 1, new Rei(tabuleiro, Cor.WHITE));

        lugarNovaPeca('c', 7, new Torre(tabuleiro, Cor.BLACK));
        lugarNovaPeca('c', 8, new Torre(tabuleiro, Cor.BLACK));
        lugarNovaPeca('d', 7, new Torre(tabuleiro, Cor.BLACK));
        lugarNovaPeca('e', 7, new Torre(tabuleiro, Cor.BLACK));
        lugarNovaPeca('e', 8, new Torre(tabuleiro, Cor.BLACK));
        lugarNovaPeca('d', 8, new Rei(tabuleiro, Cor.BLACK));
    }
}
