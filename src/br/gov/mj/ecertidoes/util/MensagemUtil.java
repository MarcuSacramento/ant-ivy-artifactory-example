package br.gov.mj.ecertidoes.util;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MensagemUtil implements Serializable {

	public boolean isShowed = false;

	public enum TipoMensagem {
		ERRO, AVISO, ALERTA, PERGUNTA
	}

	public enum TipoBotoes {
		OK("Ok"), CANCELAR("Cancelar"), SIM("Sim"), NAO("Não"), FECHAR("Fechar");

		private final String descricaoBotao;

		TipoBotoes(String descricaoBotao) {
			this.descricaoBotao = descricaoBotao;
		}

		public String getDescricaoBotao() {
			return this.descricaoBotao;
		}
	}

	private TipoMensagem tipoMensagem = null;

	private TipoBotoes[] botoes = null;

	private String mensagem = null;

	private Component component = null;

	private String titulo = null;

	public final static String ICONE_ERRO = "/br/gov/mj/recdmc/img/erro.png";
	public final static String ICONE_AVISO = "/br/gov/mj/recdmc/img/aviso.png";
	public final static String ICONE_ALERTA = "/br/gov/mj/recdmc/img/alerta.png";
	public final static String ICONE_PERGUNTA = "/br/gov/mj/recdmc/img/pergunta.png";

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor para exceções
	 * 
	 * @param exception
	 *            Exceção lançada
	 * @param botoes
	 */
	public MensagemUtil(Exception exception, TipoBotoes... botoes) {
		super();
		this.tipoMensagem = TipoMensagem.ERRO;
		this.botoes = botoes;
		if (exception != null)
			this.mensagem = exception.getMessage();
	}

	/**
	 * Construtor para mensagens
	 * 
	 * @param parent
	 *            tela que chamou a mensagem
	 * @param botoes
	 *            tipo de botões que serão fornecidos
	 * @param tipoMensagem
	 *            tipo da mensagem a ser apresentada
	 * @param mensagem
	 *            mensagem
	 */
	public MensagemUtil(Component parent, TipoMensagem tipoMensagem,
			String mensagem, TipoBotoes... botoes) {
		super();
		this.component = parent;
		this.tipoMensagem = tipoMensagem;
		this.botoes = botoes;
		this.mensagem = mensagem;
	}

	/**
	 * 
	 * @param parent
	 * @param tipoMensagem
	 * @param mensagem
	 * @param titulo
	 * @param botoes
	 *            varArgs com os botoes
	 */
	public MensagemUtil(Component parent, TipoMensagem tipoMensagem,
			String mensagem, String titulo, TipoBotoes... botoes) {
		super();
		this.component = parent;
		this.tipoMensagem = tipoMensagem;
		this.botoes = botoes;
		this.mensagem = mensagem;
		this.titulo = titulo;
	}

	/**
	 * @return Returns the tipoMensagem.
	 */
	public int getTipoMensagem() {
		switch (tipoMensagem) {
		case ALERTA:
			return JOptionPane.WARNING_MESSAGE;
		case AVISO:
			return JOptionPane.INFORMATION_MESSAGE;
		case ERRO:
			return JOptionPane.ERROR_MESSAGE;
		case PERGUNTA:
			return JOptionPane.QUESTION_MESSAGE;
		}
		return JOptionPane.INFORMATION_MESSAGE;
	}

	/**
	 * @param tipoMensagem
	 *            The tipoMensagem to set.
	 */
	public void setTipoMensagem(TipoMensagem tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}

	/**
	 * @param parametros
	 *            The parametros to set.
	 */
	public void setBotoes(TipoBotoes[] botoes) {
		this.botoes = botoes;
	}

	public int exibeMensagem() {
		return JOptionPane.showOptionDialog(getComponent(), getMensagem(),
				getTitulo(), JOptionPane.ERROR_MESSAGE,
				JOptionPane.PLAIN_MESSAGE, getTipoIcone(), getTipoBotoes(),
				null);
	}

	private ImageIcon getTipoIcone() {
		if (tipoMensagem != null) {
			switch (tipoMensagem) {
			case ALERTA:
				return new ImageIcon(getClass().getResource(ICONE_ALERTA));
			case AVISO:
				return new ImageIcon(getClass().getResource(ICONE_AVISO));
			case PERGUNTA:
				return new ImageIcon(getClass().getResource(ICONE_PERGUNTA));
			case ERRO:
				return new ImageIcon(getClass().getResource(ICONE_ERRO));
			}
		}
		return new ImageIcon(getClass().getResource(ICONE_ALERTA));
	}

	private String[] getTipoBotoes() {
		String[] retorno = null;
		if (botoes != null) {
			retorno = new String[botoes.length];
			for (int i = 0; i < botoes.length; i++) {
				retorno[i] = botoes[i].getDescricaoBotao();
			}
		}
		return retorno;
	}

	private Component getComponent() {
		return component;
	}

	private String getMensagem() {
		return mensagem;
	}

	private String getTitulo() {
		return titulo;
	}

	public static void pedeParaSair(Component parent) {
		// if(new MensagemUtil(parent,
		// PERGUNTA,"Deseja realmente sair do sistema?","Confirmação", SIM,
		// NAO).exibeMensagem()==0)
		System.exit(0);
	}
}
