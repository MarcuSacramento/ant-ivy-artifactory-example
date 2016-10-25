package br.gov.mj.ecertidoes.web.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import br.gov.mj.corporativo.dominios.Dominio;

/**
 * @author Uni�o Federativa do Brasil
 * @author Minist�rio da Justi�a
 * @author CGTI
 * @version 1.0
 */
public class PesquisaCertidoesForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String numeroCertidao;
	private String[] nome;
	private String nomeInteressado;
	private String nomeMae;
	private String nomePai;
	private String dataNascimento;
	private String codigoSeguranca;
	private String dataEmissaoCertidao;
	private String horaEmissaoCertidao;
	private String naturalidade;
	private String motivoSolicitacao;
	private String motivoPais;
	private String motivoOutros;
	private String grauParentesco;
	private String sexoRequerente;
	private String sexoRequerido;
	private List<Dominio> listaMotivo;
	private List<Dominio> listaSexo;
	private List<Dominio> listaGrauParentesco;
	private List<Dominio> listaPais;
	private List<Dominio> listaNacionalidade;
	private String todosOsNomes;
	private String protocoloMj;
	private String observacao;
	private String tamanhoArrayNome;
	private String anoNascimento;
	private String opcaoConsulta;
	private String protocoloMJ;
	private String motivoCertidaoIndeferida;
	private String codigoCertidaoEmitida;
	private String cpf;
	private String nacionalidade;
	private String registroEstrangeiro;
	private String numeroPassaporte;
	private Boolean teste;
	private String tipoCertidao;
	

	public Boolean getTeste() {
		return teste;
	}

	public void setTeste(Boolean teste) {
		this.teste = teste;
	}

	public String getRegistroEstrangeiro() {
		return registroEstrangeiro;
	}

	public void setRegistroEstrangeiro(String registroEstrangeiro) {
		this.registroEstrangeiro = registroEstrangeiro;
	}

	public String getNumeroPassaporte() {
		return numeroPassaporte;
	}

	public void setNumeroPassaporte(String numeroPassaporte) {
		this.numeroPassaporte = numeroPassaporte;
	}

	public void limparForm() {
		setTamanhoArrayNome("0");
		setSexoRequerente("");
		setSexoRequerido("");
		setGrauParentesco("");
		setTodosOsNomes("");
		setNome(null);
		setMotivoPais("");
		setMotivoOutros("");
		setNaturalidade("");
		setMotivoSolicitacao("");
		setCodigoSeguranca("");
		setNumeroCertidao("");
		setNomeInteressado("");
		setNomePai("");
		setNomeMae("");
		setDataNascimento("");
		setDataEmissaoCertidao("");
		setHoraEmissaoCertidao("");
		setProtocoloMj("");
		setCpf("");
	}

	public String getTamanhoArrayNome() {
		if (getNome() == null || getNome().length == 0) {
			tamanhoArrayNome = "0";
		} else {
			tamanhoArrayNome = String.valueOf(getNome().length - 1);
		}
		return tamanhoArrayNome;
	}

	public void setTamanhoArrayNome(String tamanhoArrayNome) {
		this.tamanhoArrayNome = tamanhoArrayNome;
	}

	public String getTodosOsNomes() {
		return todosOsNomes;
	}

	public void setTodosOsNomes(String todosOsNomes) {
		this.todosOsNomes = todosOsNomes;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String[] getNome() {
		return nome;
	}

	public void setNome(String[] nome) {
		this.nome = nome;
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

	public String getNumeroCertidao() {
		return numeroCertidao;
	}

	public void setNumeroCertidao(String numeroCertidao) {
		this.numeroCertidao = numeroCertidao;
	}

	public String getDataEmissaoCertidao() {
		return dataEmissaoCertidao;
	}

	public void setDataEmissaoCertidao(String dataEmissaoCertidao) {
		this.dataEmissaoCertidao = dataEmissaoCertidao;
	}

	public String getHoraEmissaoCertidao() {
		return horaEmissaoCertidao;
	}

	public void setHoraEmissaoCertidao(String horaEmissaoCertidao) {
		this.horaEmissaoCertidao = horaEmissaoCertidao;
	}

	public List<Dominio> getListaMotivo() {
		if (listaMotivo == null) {
			listaMotivo = new ArrayList<Dominio>();
		}
		return listaMotivo;
	}

	public void setListaMotivo(List<Dominio> listaMotivo) {
		this.listaMotivo = listaMotivo;
	}

	public List<Dominio> getListaPais() {
		if (listaPais == null) {
			listaPais = new ArrayList<Dominio>();
		}
		return listaPais;
	}

	public void setListaPais(List<Dominio> listaPais) {
		this.listaPais = listaPais;
	}

	public String getMotivoSolicitacao() {
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(String motivoSolicitacao) {
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getMotivoOutros() {
		return motivoOutros;
	}

	public void setMotivoOutros(String motivoOutros) {
		this.motivoOutros = motivoOutros;
	}

	public String getMotivoPais() {
		return motivoPais;
	}

	public void setMotivoPais(String motivoPais) {
		this.motivoPais = motivoPais;
	}

	public String getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(String grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

	public List<Dominio> getListaGrauParentesco() {
		if (listaGrauParentesco == null) {
			listaGrauParentesco = new ArrayList<Dominio>();
		}
		return listaGrauParentesco;
	}

	public void setListaGrauParentesco(List<Dominio> listaGrauParentesco) {
		this.listaGrauParentesco = listaGrauParentesco;
	}

	public List<Dominio> getListaSexo() {
		if (listaSexo == null) {
			listaSexo = new ArrayList<Dominio>();
		}
		return listaSexo;
	}

	public void setListaSexo(List<Dominio> listaSexo) {
		this.listaSexo = listaSexo;
	}

	public String getSexoRequerente() {
		return sexoRequerente;
	}

	public void setSexoRequerente(String sexoRequerente) {
		this.sexoRequerente = sexoRequerente;
	}

	public String getSexoRequerido() {
		return sexoRequerido;
	}

	public void setSexoRequerido(String sexoRequerido) {
		this.sexoRequerido = sexoRequerido;
	}

	public String getProtocoloMj() {
		return protocoloMj;
	}

	public void setProtocoloMj(String protocoloMj) {
		this.protocoloMj = protocoloMj;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(String anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public String getOpcaoConsulta() {
		return opcaoConsulta;
	}

	public void setOpcaoConsulta(String opcaoConsulta) {
		this.opcaoConsulta = opcaoConsulta;
	}

	public String getProtocoloMJ() {
		return protocoloMJ;
	}

	public void setProtocoloMJ(String protocoloMJ) {
		this.protocoloMJ = protocoloMJ;
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

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public List<Dominio> getListaNacionalidade() {
		if (listaNacionalidade == null) {
			listaNacionalidade = new ArrayList<Dominio>();
		}
		return listaNacionalidade;
	}

	public void setListaNacionalidade(List<Dominio> listaNacionalidade) {
		this.listaNacionalidade = listaNacionalidade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getTipoCertidao() {
		return tipoCertidao;
	}

	public void setTipoCertidao(String tipoCertidao) {
		this.tipoCertidao = tipoCertidao;
	}
	
	

}