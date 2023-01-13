package br.com.eduardolpsss.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.eduardolpsss.cm.excecao.ExplosaoException;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	// Construtor de linha e coluna
	Campo(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		// Calculando diferença da linha e da coluna
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		// If que só adiciona vizinhos se estiverem nas laterais ou diagonais
		if(deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if(deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}
	
	// Controle de marcação do campo com a bandeirinha
	void alternarMarcacao() {
		if(!aberto) {
			marcado = !marcado;
		}
	}
	
	// Método para abrir campos de forma automática, verificando os seguros
	boolean abrirCampos() {
		if(!aberto && !marcado) {
			aberto = true;
			
			// If de derrota
			if(minado) {
				throw new ExplosaoException();
			}
			
			if(verificarVizinhancaSegura()) {
				// Chamando função abrirCampos para cada vizinho seguro
				vizinhos.forEach(v -> v.abrirCampos());
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}

	// Verificando se é seguro abrir o campo (vendo se ele está minado)
	boolean verificarVizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	// Método para marcar um campo
	public boolean isMarcado() {
		return marcado;
	}
	
	// Método para minar um campo
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	// Método para abrir um campo
	public boolean isAberto() {
		return aberto;
	}
	
	// Método para fechar um campo
	public boolean isFechado() {
		return !aberto;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	// Objetivo de um campo especificamente
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	// Método para saber a quantidade de minas na vizinhança de um campo
	long minasNaVizinhanca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}
	
	// Método para reiniciar o estado de cada um dos campos
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}
	
	// Método que imprime respostas do jogo
	public String toString() {
		if (marcado) {
			return "x";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		} else if(aberto) {
			return " ";
		} else {
			return "?";
		}
	}
}
