package xadrez;

import jogoTabuleiro.*;
import util.Constante;
import xadrez.pecas.*;

import java.util.ArrayList;
import java.util.List;

public class PartidaXadrez {

    private int turno;
    private Cor jogadorAtual;
    private Tabuleiro tabuleiro;
    private boolean check;
    private boolean checkMate;
    private PecaXadrez enPassantVulneravel;

    private List<Peca> pecasNoTabuleiro = new ArrayList<>();
    private List<Peca> pecasCapturadas = new ArrayList<>();

    public PartidaXadrez() {
        tabuleiro = new Tabuleiro(Constante.TAMANHO_TABULEIRO, Constante.TAMANHO_TABULEIRO);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        configuracaoInicial();
    }

    public int getTurno() {
        return turno;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public PecaXadrez getEnPassantVulneravel() {
        return enPassantVulneravel;
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

    public boolean[][] possiveisMovimentos(PosicaoXadrez posicaoOrigem) {
        Posicao posicao = posicaoOrigem.toPosicao();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).possiveisMovimentos();
    }

    public PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
        Posicao origem = posicaoOrigem.toPosicao();
        Posicao destino = posicaoDestino.toPosicao();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);
        Peca pecaCapturada = fazerMovimento(origem, destino);
        if (testeCheck(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Você não pode se colocar em check.");
        }
        PecaXadrez pecaMovida = (PecaXadrez) tabuleiro.peca(destino);
        check = (testeCheck(oponente(jogadorAtual)));
        if (testeCheckMate(oponente(jogadorAtual))) {
            checkMate = true;
        }
        else {
            proximoTurno();
        }
        // Movimento especial en passant
        if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
            enPassantVulneravel = pecaMovida;
        }
        else {
            enPassantVulneravel = null;
        }
        return (PecaXadrez) pecaCapturada;
    }

    private Peca fazerMovimento(Posicao origem, Posicao destino) {
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
        p.incrementarMovimentos();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.lugarPeca(p, destino);
        if (pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        // Movimento especial Roque pequeno
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorre);
            tabuleiro.lugarPeca(torre, destinoTorre);
            torre.incrementarMovimentos();
        }
        // Movimento especial Roque Grande
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(origemTorre);
            tabuleiro.lugarPeca(torre, destinoTorre);
            torre.incrementarMovimentos();
        }
        // Movimento especial en passant
        if (p instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao posicaoPeao;
                if (p.getCor() == Cor.BRANCO) {
                    posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                }
                else {
                    posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
        p.decrementarMovimentos();
        tabuleiro.lugarPeca(p, origem);
        if (pecaCapturada != null) {
            tabuleiro.lugarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
        // Movimento especial Roque pequeno
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorre);
            tabuleiro.lugarPeca(torre, origemTorre);
            torre.decrementarMovimentos();
        }
        // Movimento especial Roque Grande
        if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez) tabuleiro.removerPeca(destinoTorre);
            tabuleiro.lugarPeca(torre, origemTorre);
            torre.decrementarMovimentos();
        }
        // Movimento especial en passant
        if (p instanceof Peao) {
            if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
                PecaXadrez peao = (PecaXadrez) tabuleiro.removerPeca(destino);
                Posicao posicaoPeao;
                if (p.getCor() == Cor.BRANCO) {
                    posicaoPeao = new Posicao(3, destino.getColuna());
                }
                else {
                    posicaoPeao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.lugarPeca(peao, posicaoPeao);
            }
        }
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if (!tabuleiro.temPeca(posicao)) {
            throw new XadrezException("Posição inexistente.");
        }
        if (jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
            throw new XadrezException("A peça escolhida não é sua.");
        }
        if (!tabuleiro.peca(posicao).algumMovimentoPossivel()) {
            throw new XadrezException("Não existe movimento possível para a peça escolhida.");
        }
    }

    private void validarPosicaoDestino(Posicao origem, Posicao destino) {
        if (!tabuleiro.peca(origem).possivelMovimento(destino)) {
            throw new XadrezException("A peça escolhida não pode ser movida para a posição de destino.");
        }
    }

    private void proximoTurno() {
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private void lugarNovaPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.lugarPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
        pecasNoTabuleiro.add(peca);
    }

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaXadrez rei(Cor cor) {
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor).toList();
        for (Peca p : lista) {
            if (p instanceof Rei) {
                return (PecaXadrez) p;
            }
        }
        throw new IllegalStateException("Não existe o rei da cor " + cor + " no tabuleiro.");
    }

    private boolean testeCheck(Cor cor) {
        Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
        List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor)).toList();
        for (Peca p : pecasOponentes) {
            boolean[][] mat = p.possiveisMovimentos();
            if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Cor cor) {
        if (!testeCheck(cor)) return false;
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor).toList();
        for (Peca p : lista) {
            boolean[][] mat = p.possiveisMovimentos();
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat.length; j++) {
                    if (mat[i][j]) {
                        Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().toPosicao();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = fazerMovimento(origem, destino);
                        boolean testeCheck = testeCheck(cor);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if (!testeCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void configuracaoInicial() {
        lugarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        lugarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        lugarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        lugarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        lugarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        lugarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        lugarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        lugarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
    }
}
