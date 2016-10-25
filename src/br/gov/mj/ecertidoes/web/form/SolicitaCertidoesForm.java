package br.gov.mj.ecertidoes.web.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import br.gov.mj.corporativo.dominios.Dominio;

/**
 * @author União Federativa do Brasil
 * @author Ministério da Justiça
 * @author CGTI
 * @version 1.0
 */
public class SolicitaCertidoesForm extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String numeroProtocolo;
	private String nomeRequerente;
	private String cpf;
	private String passaporte;
	private String endereco;
	private String cep;
	private String email;
	private String ddi;
	private String ddd;
	private String telefone;
	private String motivo;
	private String motivoPais;
	private String motivoOutros;
	private List<Dominio> listaMotivo;
	private String todosOsNomes;
	private String codigoSeguranca;
	private String protocoloMj;
	private String observacao;
	private String anoNascimento;
	private String labelUf;
	private String labelPais;
	private String labelNacionalidade;
	private String rneRgRequerente;

	private String[] nomeRequerido;
	private String nomePaiRequerido;
	private String nomeMaeRequerido;
	private String dataNascimentoRequerido;
	private String naturalidadeRequerido;
	private String enderecoPais;
	private String enderecoUF;
	private List<Dominio> listaUF;
	private List<Dominio> listaPais;
	private List<Dominio> listaNacionalidade;
	private String estadoCivilRequerido;
	private List<Dominio> listaEstadoCivil;
	private String dataChegadaBrasil;
	private String rneRg;
	private String carteiraModelo;
	private String dataObito;
	private String grauParentesco;
	private List<Dominio> listaGrauParentesco;
	private String dataSolicitacao;
	private String horaSolicitacao;
	private String anoChegadaBrasil;
	private String anoObito;
	private List<Dominio> listaSexo;
	private String sexoRequerente;
	private String sexoRequerido;
	private String tamanhoArrayNome;
	private String nacionalidade;

	private String labelCidade;
	private String enderecoNumero;
	private String enderecoComplemento;
	private String enderecoBairro;
	private String enderecoCidade;
	private List<Dominio> listaMunicipio;

	public void limparForm() {
		setLabelCidade("");
		setEnderecoBairro("");
		setEnderecoNumero("");
		setEnderecoComplemento("");
		setEnderecoCidade("");
		setTamanhoArrayNome("0");
		setEnderecoPais("");
		setEnderecoUF("");
		setProtocoloMj("");
		setObservacao("");
		setSexoRequerente("");
		setSexoRequerido("");
		setGrauParentesco("");
		setCodigoSeguranca("");
		setTodosOsNomes("");
		setNomeRequerido(null);
		setMotivoOutros("");
		setMotivoPais("");
		setDdd("");
		setDdi("");
		setNumeroProtocolo("");
		setDataSolicitacao("");
		setHoraSolicitacao("");
		setDataObito("");
		setCarteiraModelo("");
		setRneRg("");
		setDataChegadaBrasil("");
		setEstadoCivilRequerido("");
		setNaturalidadeRequerido("");
		setDataNascimentoRequerido("");
		setNomeMaeRequerido("");
		setNomePaiRequerido("");
		setMotivo("");
		setTelefone("");
		setEmail("");
		setCep("");
		setEndereco("");
		setCpf("");
		setPassaporte("");
		setNomeRequerente("");
		setAnoChegadaBrasil("");
		setAnoObito("");
	}

	public String getTamanhoArrayNome() {
		if (getNomeRequerido() == null || getNomeRequerido().length == 0) {
			tamanhoArrayNome = "0";
		} else {
			tamanhoArrayNome = String.valueOf(getNomeRequerido().length - 1);
		}
		return tamanhoArrayNome;
	}

	public void setTamanhoArrayNome(String tamanhoArrayNome) {
		this.tamanhoArrayNome = tamanhoArrayNome;
	}

	public String getAnoChegadaBrasil() {
		return anoChegadaBrasil;
	}

	public void setAnoChegadaBrasil(String anoChegadaBrasil) {
		this.anoChegadaBrasil = anoChegadaBrasil;
	}

	public String getAnoObito() {
		return anoObito;
	}

	public void setAnoObito(String anoObito) {
		this.anoObito = anoObito;
	}

	public String getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(String dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getHoraSolicitacao() {
		return horaSolicitacao;
	}

	public void setHoraSolicitacao(String horaSolicitacao) {
		this.horaSolicitacao = horaSolicitacao;
	}

	public String getNomeRequerente() {
		return nomeRequerente;
	}

	public void setNomeRequerente(String nomeRequerente) {
		this.nomeRequerente = nomeRequerente;
	}

	public String getCarteiraModelo() {
		return carteiraModelo;
	}

	public void setCarteiraModelo(String carteiraModelo) {
		this.carteiraModelo = carteiraModelo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPassaporte() {
		return passaporte;
	}

	public void setPassaporte(String passaporte) {
		this.passaporte = passaporte;
	}

	public String getDataChegadaBrasil() {
		return dataChegadaBrasil;
	}

	public void setDataChegadaBrasil(String dataChegadaBrasil) {
		this.dataChegadaBrasil = dataChegadaBrasil;
	}

	public String getDataNascimentoRequerido() {
		return dataNascimentoRequerido;
	}

	public void setDataNascimentoRequerido(String dataNascimentoRequerido) {
		this.dataNascimentoRequerido = dataNascimentoRequerido;
	}

	public String getDataObito() {
		return dataObito;
	}

	public void setDataObito(String dataObito) {
		this.dataObito = dataObito;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEstadoCivilRequerido() {
		return estadoCivilRequerido;
	}

	public void setEstadoCivilRequerido(String estadoCivilRequerido) {
		this.estadoCivilRequerido = estadoCivilRequerido;
	}

	public List<Dominio> getListaEstadoCivil() {
		if (listaEstadoCivil == null) {
			listaEstadoCivil = new ArrayList<Dominio>();
		}
		return listaEstadoCivil;
	}

	public void setListaEstadoCivil(List<Dominio> listaEstadoCivil) {
		this.listaEstadoCivil = listaEstadoCivil;
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

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getNaturalidadeRequerido() {
		return naturalidadeRequerido;
	}

	public void setNaturalidadeRequerido(String naturalidadeRequerido) {
		this.naturalidadeRequerido = naturalidadeRequerido;
	}

	public String getNomeMaeRequerido() {
		return nomeMaeRequerido;
	}

	public void setNomeMaeRequerido(String nomeMaeRequerido) {
		this.nomeMaeRequerido = nomeMaeRequerido;
	}

	public String getNomePaiRequerido() {
		return nomePaiRequerido;
	}

	public void setNomePaiRequerido(String nomePaiRequerido) {
		this.nomePaiRequerido = nomePaiRequerido;
	}

	public String getRneRg() {
		return rneRg;
	}

	public void setRneRg(String rneRg) {
		this.rneRg = rneRg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public String getNumeroProtocolo() {
		return numeroProtocolo;
	}

	public void setNumeroProtocolo(String numeroProtocolo) {
		this.numeroProtocolo = numeroProtocolo;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getDdi() {
		return ddi;
	}

	public void setDdi(String ddi) {
		this.ddi = ddi;
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

	public void setNomeRequerido(String[] nomeRequerido) {
		this.nomeRequerido = nomeRequerido;
	}

	public String[] getNomeRequerido() {
		return nomeRequerido;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public String getTodosOsNomes() {
		return todosOsNomes;
	}

	public void setTodosOsNomes(String todosOsNomes) {
		this.todosOsNomes = todosOsNomes;
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

	public String getEnderecoPais() {
		return enderecoPais;
	}

	public void setEnderecoPais(String enderecoPais) {
		this.enderecoPais = enderecoPais;
	}

	public String getEnderecoUF() {
		return enderecoUF;
	}

	public void setEnderecoUF(String enderecoUF) {
		this.enderecoUF = enderecoUF;
	}

	public List<Dominio> getListaUF() {
		if (listaUF == null) {
			listaUF = new ArrayList<Dominio>();
		}
		return listaUF;
	}

	public void setListaUF(List<Dominio> listaUF) {
		this.listaUF = listaUF;
	}

	public String getAnoNascimento() {
		return anoNascimento;
	}

	public void setAnoNascimento(String anoNascimento) {
		this.anoNascimento = anoNascimento;
	}

	public String getLabelUf() {
		return labelUf;
	}

	public void setLabelUf(String labelUf) {
		this.labelUf = labelUf;
	}

	public String getLabelPais() {
		return labelPais;
	}

	public void setLabelPais(String labelPais) {
		this.labelPais = labelPais;
	}

	public String getLabelNacionalidade() {
		return labelNacionalidade;
	}

	public void setLabelNacionalidade(String labelNacionalidade) {
		this.labelNacionalidade = labelNacionalidade;
	}

	public String getRneRgRequerente() {
		return rneRgRequerente;
	}

	public void setRneRgRequerente(String rneRgRequerente) {
		this.rneRgRequerente = rneRgRequerente;
	}

	public String getEnderecoNumero() {
		return enderecoNumero;
	}

	public void setEnderecoNumero(String enderecoNumero) {
		this.enderecoNumero = enderecoNumero;
	}

	public String getEnderecoComplemento() {
		return enderecoComplemento;
	}

	public void setEnderecoComplemento(String enderecoComplemento) {
		this.enderecoComplemento = enderecoComplemento;
	}

	public String getEnderecoBairro() {
		return enderecoBairro;
	}

	public void setEnderecoBairro(String enderecoBairro) {
		this.enderecoBairro = enderecoBairro;
	}

	public String getEnderecoCidade() {
		return enderecoCidade;
	}

	public void setEnderecoCidade(String enderecoCidade) {
		this.enderecoCidade = enderecoCidade;
	}

	public List<Dominio> getListaMunicipio() {
		if (listaMunicipio == null) {
			listaMunicipio = new ArrayList<Dominio>();
		}
		return listaMunicipio;
	}

	public void setListaMunicipio(List<Dominio> listaMunicipio) {
		this.listaMunicipio = listaMunicipio;
	}

	public String getLabelCidade() {
		return labelCidade;
	}

	public void setLabelCidade(String labelCidade) {
		this.labelCidade = labelCidade;
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

}