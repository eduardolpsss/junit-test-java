package br.com.eduardolpsss.cm;

import br.com.eduardolpsss.cm.modelo.Tabuleiro;
import br.com.eduardolpsss.cm.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		
		// Criando tabuleiro com quantidade de linhas, colunas e bombas
		Tabuleiro tabuleiro = new Tabuleiro(6, 6, 3);
		new TabuleiroConsole(tabuleiro);
	}
}
