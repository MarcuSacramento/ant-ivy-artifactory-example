package br.gov.mj.ecertidoes.web.form;

import org.apache.struts.validator.ValidatorForm;

public class ManterSolicitacaoForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;
	
	private String opcaoConsulta;
	private String numeroCertidao;
	private String protocoloMJ;
	private String codigoSeguranca;
	private String motivoCertidaoIndeferida;
	private String codigoCertidaoEmitida;
	private String dataNascimento;
	private String nomeInteressado;
	private String nomeMae;
	private String nomePai;

	public String getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(String numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public String getMotivoCertidaoIndeferida() {
		return motivoCertidaoIndeferida;
	}

	public void setMotivoCertidaoIndeferida(String motivoCertidaoIndeferida) {
		this.motivoCertidaoIndeferida = motivoCertidaoIndeferida;
	}

	public String getCodigoCertidaoEmitida() {
		return codigoCertidaoEmitida;
	}

	public void setCodigoCertidaoEmitida(String codigoCertidaoEmitida) {
		this.codigoCertidaoEmitida = codigoCertidaoEmitida;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeInteressado() {
		return nomeInteressado;
	}

	public void setNomeInteressado(String nomeInteressado) {
		this.nomeInteressado = nomeInteressado;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getProtocoloMJ() {
		return protocoloMJ;
	}

	public void setProtocoloMJ(String protocoloMJ) {
		this.protocoloMJ = protocoloMJ;
	}

	public String getOpcaoConsulta() {
		return opcaoConsulta;
	}

	public void setOpcaoConsulta(String opcaoConsulta) {
		this.opcaoConsulta = opcaoConsulta;
	}
}
