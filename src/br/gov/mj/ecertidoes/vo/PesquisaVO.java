package br.gov.mj.ecertidoes.vo;

public class PesquisaVO {

	private String dataHora;
	private String nomeQuemCadastrou;
	private String nomeInteressado;
	private String codigoReferencia;
	private String titulo;
	private String anoProducao;
	private String numeroProcesso;
	private String notacaoAnterior;
	private String codigoProcesso;
	private String posicaoRegistro;

	public String getAnoProducao() {
		return anoProducao;
	}

	public void setAnoProducao(String anoProducao) {
		this.anoProducao = anoProducao;
	}

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getNomeQuemCadastrou() {
		return nomeQuemCadastrou;
	}

	public void setNomeQuemCadastrou(String nomeQuemCadastrou) {
		this.nomeQuemCadastrou = nomeQuemCadastrou;
	}

	public String getNotacaoAnterior() {
		return notacaoAnterior;
	}

	public void setNotacaoAnterior(String notacaoAnterior) {
		this.notacaoAnterior = notacaoAnterior;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCodigoProcesso() {
		return codigoProcesso;
	}

	public void setCodigoProcesso(String codigoProcesso) {
		this.codigoProcesso = codigoProcesso;
	}

	public String getPosicaoRegistro() {
		return posicaoRegistro;
	}

	public void setPosicaoRegistro(String posicaoRegistro) {
		this.posicaoRegistro = posicaoRegistro;
	}

	public String getNomeInteressado() {
		return nomeInteressado;
	}

	public void setNomeInteressado(String nomeInteressado) {
		this.nomeInteressado = nomeInteressado;
	}

}
