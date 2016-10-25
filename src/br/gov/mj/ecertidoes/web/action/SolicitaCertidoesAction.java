package br.gov.mj.ecertidoes.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.gov.mj.corporativo.dominios.Dominio;
import br.gov.mj.corporativo.ejb.CorporativoDelegate;
import br.gov.mj.corporativo.servicos.Corporativo;
import br.gov.mj.corporativo.servicos.exceptions.ErroInternoException;
import br.gov.mj.corporativo.servicos.exceptions.ParametroInvalidoException;
import br.gov.mj.dnn.dominios.DominioStatusSolicitacao;
import br.gov.mj.dnn.ejb.DominiosDNNDelegate_ECertidao;
import br.gov.mj.dnn.ejb.ECertidoesDelegate_ECertidao;
import br.gov.mj.dnn.ejb.ECertidoesEstatisticaDelegate_ECertidao;
import br.gov.mj.dnn.pojo.AndamentoSolicitacao;
import br.gov.mj.dnn.pojo.EstadoCivil;
import br.gov.mj.dnn.pojo.OutrosNomesSolicitacaoCertidao;
import br.gov.mj.dnn.pojo.PaisCorporativo;
import br.gov.mj.dnn.pojo.Parentesco;
import br.gov.mj.dnn.pojo.SolicitacaoCertidao;
import br.gov.mj.dnn.servicos.DominiosDNN;
import br.gov.mj.dnn.servicos.ECertidoes;
import br.gov.mj.dnn.util.CPF;
import br.gov.mj.dnn.util.Protocolo;
import br.gov.mj.ecertidoes.util.Captcha;
import br.gov.mj.ecertidoes.util.DataUtil;
import br.gov.mj.ecertidoes.util.Util;
import br.gov.mj.ecertidoes.web.form.SolicitaCertidoesForm;
import br.gov.mj.ecertidoes.web.mail.MailHelper;
import br.gov.mj.struts.action.MjBaseDispatchAction;
import br.gov.mj.web.exception.NegocioException;

/**
 * Action que efetua pesquisa na aplicação.
 * 
 * @author União Federativa do Brasil
 * @author Ministério da Justiça
 * @author CGTI
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class SolicitaCertidoesAction extends MjBaseDispatchAction {

	private static final String EMAIL_EMISSOR = "sistemae-certidao@mj.gov.br";

	public ActionForward solicitarCNN(ActionMapping mapping, ActionForm _form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitaCertidoesForm form = (SolicitaCertidoesForm) _form;

		if (verificaCamposObrigatorios(form, request)) {
			carregarCombosSolicitacao(form);
			form.setCodigoSeguranca("");
			return mapping.findForward("solicitacao");
		}

		try {
			if (!Captcha.getInstance().isCaptchaOk(request, form.getCodigoSeguranca().trim())) {
				salvarMsgErro("erro.campo.caracteres", request);
				form.setCodigoSeguranca("");
				carregarCombosSolicitacao(form);
				return mapping.findForward("solicitacao");
			}
		} catch (Exception e) {
			salvarMsgErro("erro.campo.caracteres", request);
			form.setCodigoSeguranca("");
			carregarCombosSolicitacao(form);
			return mapping.findForward("solicitacao");
		}

		Date dataCadastro = new Date();

		SolicitacaoCertidao sc = preencheSolicitacaoCertidao(form, dataCadastro);
		sc.setHost(request.getRemoteHost());

		validaCamposObrigatorios(form);

		getECertidoesDelegate().salvarSolicitacaoCertidao(sc);

		incluiAndamentoSolicitacao(sc, DominioStatusSolicitacao.CERTIDAO_AGUARDANDO_ANALISE.getCodigo());

		formatarDadosEmail(form, dataCadastro, sc);

		enviaEmailSolicitacao(form);

		ECertidoesEstatisticaDelegate_ECertidao.getInstance().registrarSolicitacaoCertidaoDeest(request.getRemoteHost(), request.getRemoteAddr(), dataCadastro);

		return mapping.findForward("encaminha");
	}

	private void validaCamposObrigatorios(SolicitaCertidoesForm form) throws ParametroInvalidoException {
		if (form.getEnderecoPais() != null) {
			form.setLabelPais(getDominiosDNN().getPais(form.getEnderecoPais()).getNome());
		}

		if (form.getNacionalidade() != null) {
			form.setLabelNacionalidade(getDominiosDNN().getPais(form.getNacionalidade()).getNome());
		}

		if (form.getEnderecoPais().equalsIgnoreCase("BR")) {
			if (form.getEnderecoUF() != null) {
				form.setLabelUf(form.getEnderecoUF());
			}
			if (form.getEnderecoCidade() != null) {
				if (getCorporativoDelegate().getMunicipio(form.getEnderecoCidade()) != null) {
					form.setLabelCidade(getCorporativoDelegate().getMunicipio(form.getEnderecoCidade()).getNome());
				} else {
					form.setLabelCidade("");
				}
			}
		}
	}

	private void incluiAndamentoSolicitacao(SolicitacaoCertidao sc, int codigoSituacao) throws ParametroInvalidoException, ErroInternoException {
		AndamentoSolicitacao as = new AndamentoSolicitacao();
		as.setDataAndamento(new Date());
		// 1 Aguardando Análise
		// 2 Emitida CNN
		// 3 Indeferida
		// 4 Emitida CPN
		// 5 Em Análise
		// 6 Em Exigência
		as.setSituacaoAndamento(codigoSituacao);
		as.setSolicitacaoCertidao(sc);
		getECertidoesDelegate().salvarAndamentoSolicitacao(as);
	}

	/**
	 * @param form
	 * @param dataCadastro
	 * @param sc
	 */
	private void formatarDadosEmail(SolicitaCertidoesForm form, Date dataCadastro, SolicitacaoCertidao sc) {
		if (sc.getNomeRequerente() != null && !sc.getNomeRequerente().equalsIgnoreCase("")) {
			form.setNomeRequerente(form.getNomeRequerente().toUpperCase());
		}
		if (sc.getNomePai() != null && !sc.getNomePai().equalsIgnoreCase("")) {
			form.setNomePaiRequerido(sc.getNomePai().toUpperCase());
		}
		if (sc.getNomeMae() != null && !sc.getNomeMae().equalsIgnoreCase("")) {
			form.setNomeMaeRequerido(sc.getNomeMae().toUpperCase());
		}
		if (sc.getProtocoloMj() != null && !sc.getProtocoloMj().equalsIgnoreCase("")) {
			form.setProtocoloMj(sc.getProtocoloMj().trim());
		}
		if (sc.getObservacao() != null && !sc.getObservacao().equalsIgnoreCase("")) {
			form.setObservacao(sc.getObservacao().trim());
		}
		form.setSexoRequerente(getNomeSexo(form.getSexoRequerente()));
		form.setSexoRequerido(getNomeSexo(form.getSexoRequerido()));
		form.setDataSolicitacao(Util.formatarData(dataCadastro));
		form.setHoraSolicitacao(Util.formatarHora(dataCadastro));
		form.setNumeroProtocolo(Protocolo.formataCertidao(sc.getNumeroProtocolo()));
		form.setNaturalidadeRequerido(getNomePais(form, sc.getNaturalidadeRequerido().getSiglaPais()));
		String extras = "";
		if (sc.getMotivo() == 1) {
			extras = getNomePais(form, sc.getNaturalidadeRequerente().getSiglaPais());
		} else {
			extras = sc.getOutrosMotivos();
		}

		if (sc.getParentesco() != null) {
			form.setGrauParentesco(getNomeGrauParentesco(form, sc.getParentesco().getCodigoGrauParentesco().toString()));
		} else {
			form.setGrauParentesco("");
		}

		form.setMotivo(getNomeMotivoSolicitacao(form, String.valueOf(sc.getMotivo())) + " (" + extras + ")");
		String todosOsNomes = "";
		if (sc.getOutrosNomesSolicitacaoCertidaos() != null) {
			try {
				Object[] nomes = sc.getOutrosNomesSolicitacaoCertidaos().toArray();

				OutrosNomesSolicitacaoCertidao on = (OutrosNomesSolicitacaoCertidao) nomes[0];
				todosOsNomes = on.getNome().toUpperCase();
				for (int i = 1, j = 2; i < nomes.length; i++, j++) {
					on = (OutrosNomesSolicitacaoCertidao) nomes[i];
					if (nomes.length == j) {
						todosOsNomes = todosOsNomes + " ou " + on.getNome().toUpperCase();
					} else {
						todosOsNomes = todosOsNomes + ", " + on.getNome().toUpperCase();
					}
				}
			} catch (Exception e) {
				todosOsNomes = "";
			}
		}
		if (sc.getEstadoCivil() != null) {
			form.setEstadoCivilRequerido(getNomeEstadoCivil(form, String.valueOf(sc.getEstadoCivil().getCodigoEstadoCivil())));
		}
		if (nuloOuVazio(form.getDataObito()) && !nuloOuVazio(form.getAnoObito())) {
			form.setDataObito(form.getAnoObito());
		}
		if (nuloOuVazio(form.getDataNascimentoRequerido()) && !nuloOuVazio(form.getAnoNascimento())) {
			form.setDataNascimentoRequerido(form.getAnoNascimento());
		}
		if (nuloOuVazio(form.getDataChegadaBrasil()) && !nuloOuVazio(form.getAnoChegadaBrasil())) {
			form.setDataChegadaBrasil(form.getAnoChegadaBrasil());
		}

		form.setTodosOsNomes(todosOsNomes);
	}

	private String getNomePais(SolicitaCertidoesForm form, String codigoPais) {
		String retorno = "";

		if (codigoPais != null && !codigoPais.equalsIgnoreCase("0")) {
			PaisCorporativo corporativo = getDominiosDNN().getPais(codigoPais);
			if (corporativo != null) {
				retorno = corporativo.getNome();
			}
		}

		return retorno;
	}

	private String getNomeEstadoCivil(SolicitaCertidoesForm form, String codigoEstadoCivil) {
		String retorno = "";
		if (codigoEstadoCivil != null && !codigoEstadoCivil.equalsIgnoreCase("0")) {
			if (form.getListaEstadoCivil().size() == 0) {
				carregarCombosSolicitacao(form);
			}
			for (int i = 0; i < form.getListaEstadoCivil().size(); i++) {
				String codigo = form.getListaEstadoCivil().get(i).getCodigo().trim();
				if (codigo.equalsIgnoreCase(codigoEstadoCivil.trim())) {
					retorno = form.getListaEstadoCivil().get(i).getDescricao();
					break;
				}
			}
		}

		return retorno;
	}

	private String getNomeSexo(String sigla) {
		String retorno = "";
		if (sigla.trim().equalsIgnoreCase("M")) {
			retorno = "Masculino";
		} else if (sigla.trim().equalsIgnoreCase("F")) {
			retorno = "Feminino";
		}
		return retorno;
	}

	private String getNomeMotivoSolicitacao(SolicitaCertidoesForm form, String codigoMotivoSolicitacao) {
		String retorno = "";
		if (codigoMotivoSolicitacao != null && !codigoMotivoSolicitacao.equalsIgnoreCase("0")) {
			if (form.getListaMotivo().size() == 0) {
				carregarCombosSolicitacao(form);
			}
			for (int i = 0; i < form.getListaMotivo().size(); i++) {
				String codigo = form.getListaMotivo().get(i).getCodigo().trim();
				if (codigo.equalsIgnoreCase(codigoMotivoSolicitacao.trim())) {
					retorno = form.getListaMotivo().get(i).getDescricao();
					break;
				}
			}
		}
		return retorno;
	}

	private String getNomeGrauParentesco(SolicitaCertidoesForm form, String codigoGrauParentesco) {
		String retorno = "";
		if (codigoGrauParentesco != null && !codigoGrauParentesco.equalsIgnoreCase("0")) {
			if (form.getListaGrauParentesco().size() == 0) {
				carregarCombosSolicitacao(form);
			}
			for (int i = 0; i < form.getListaGrauParentesco().size(); i++) {
				String codigo = form.getListaGrauParentesco().get(i).getCodigo().toString();
				if (codigo.equalsIgnoreCase(codigoGrauParentesco.trim())) {
					retorno = form.getListaGrauParentesco().get(i).getDescricao();
					break;
				}
			}
		}
		return retorno;
	}

	private void enviaEmailSolicitacao(SolicitaCertidoesForm form) throws Exception {
		enviarEmailInteressado(form.getEmail(), parametrosEmail(form));
		enviarEmailSistema(parametrosEmail(form));
	}

	private void enviarEmailInteressado(String emailInteressado, String[] parametros) throws Exception {
		try {
			MailHelper.getInstance().enviarEmailSolicitacaoSucesso(emailInteressado, parametros);
		} catch (ErroInternoException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível enviar e-mail de confirmação.");
		}
	}

	private void enviarEmailSistema(String[] parametros) throws Exception {
		try {
			MailHelper.getInstance().enviarEmailSolicitacaoSucesso(EMAIL_EMISSOR, parametros);
		} catch (ErroInternoException e) {
			e.printStackTrace();
			throw new NegocioException("Não foi possível enviar e-mail de confirmação.");
		}
	}

	private String[] parametrosEmail(SolicitaCertidoesForm form) {
		List<String> parametros = new ArrayList<String>();

		parametros.add(form.getDataSolicitacao() != null ? form.getDataSolicitacao() : "");
		parametros.add(form.getHoraSolicitacao() != null ? form.getHoraSolicitacao() : "");
		parametros.add(form.getNumeroProtocolo() != null ? form.getNumeroProtocolo() : "");
		parametros.add(form.getNomeRequerente() != null ? form.getNomeRequerente() : "");
		parametros.add(form.getSexoRequerente() != null ? form.getSexoRequerente() : "");
		parametros.add(form.getGrauParentesco() != null ? form.getGrauParentesco() : "");
		parametros.add(form.getCpf() != null ? form.getCpf() : "");
		parametros.add(form.getPassaporte() != null ? form.getPassaporte() : "");
		parametros.add(form.getEndereco() != null ? form.getEndereco() : "");
		parametros.add(form.getEnderecoPais() != null ? form.getEnderecoPais() : "");
		parametros.add(form.getEnderecoUF() != null ? form.getEnderecoUF() : "");
		parametros.add(form.getCep() != null ? form.getCep() : "");
		parametros.add(form.getEmail() != null ? form.getEmail() : "");

		StringBuilder telefone = new StringBuilder();
		if (!nuloOuVazio(form.getDdi())) {
			telefone.append("(").append(form.getDdi()).append(") ");
		}
		if (!nuloOuVazio(form.getDdd())) {
			telefone.append("(").append(form.getDdd()).append(") ");
		}
		telefone.append(form.getTelefone());

		parametros.add(telefone.toString());
		parametros.add(form.getMotivo() != null ? form.getMotivo() : "");
		parametros.add(form.getProtocoloMj() != null ? form.getProtocoloMj() : "");
		parametros.add(form.getTodosOsNomes() != null ? form.getTodosOsNomes() : "");
		parametros.add(form.getSexoRequerido() != null ? form.getSexoRequerido() : "");
		parametros.add(form.getNomePaiRequerido() != null ? form.getNomePaiRequerido() : "");
		parametros.add(form.getNomeMaeRequerido() != null ? form.getNomeMaeRequerido() : "");
		if (!nuloOuVazio(form.getDataNascimentoRequerido())) {
			parametros.add(form.getDataNascimentoRequerido());
		} else if (!nuloOuVazio(form.getAnoNascimento())) {
			parametros.add(form.getAnoNascimento());
		} else {
			parametros.add("");
		}
		parametros.add(form.getNaturalidadeRequerido() != null ? form.getNaturalidadeRequerido() : "");
		parametros.add(form.getEstadoCivilRequerido() != null ? form.getEstadoCivilRequerido() : "");
		if (!nuloOuVazio(form.getDataChegadaBrasil())) {
			parametros.add(form.getDataChegadaBrasil());
		} else if (!nuloOuVazio(form.getAnoChegadaBrasil())) {
			parametros.add(form.getAnoChegadaBrasil());
		} else {
			parametros.add("");
		}
		parametros.add(form.getRneRg() != null ? form.getRneRg() : "");
		parametros.add(form.getCarteiraModelo() != null ? form.getCarteiraModelo() : "");
		if (!nuloOuVazio(form.getDataObito())) {
			parametros.add(form.getDataObito());
		} else if (!nuloOuVazio(form.getAnoObito())) {
			parametros.add(form.getAnoObito());
		} else {
			parametros.add("");
		}
		parametros.add(form.getObservacao() != null ? form.getObservacao() : "");
		parametros.add(form.getRneRgRequerente() != null ? form.getRneRgRequerente() : "");
		parametros.add(form.getNacionalidade() != null ? form.getNacionalidade() : "");
		return parametros.toArray(new String[parametros.size()]);
	}

	/**
	 * @param form
	 * @return
	 * @throws ErroInternoException
	 */
	private SolicitacaoCertidao preencheSolicitacaoCertidao(SolicitaCertidoesForm form, Date dataCadastro) throws ErroInternoException {

		SolicitacaoCertidao sc = new SolicitacaoCertidao();

		if (!form.getSexoRequerente().equalsIgnoreCase("")) {
			char ch = form.getSexoRequerente().charAt(0);
			sc.setSexoRequerente(ch);
		}
		if (!form.getSexoRequerido().equalsIgnoreCase("")) {
			char ch = form.getSexoRequerido().charAt(0);
			sc.setSexoRequerido(ch);
		}
		if (!nuloOuVazio(form.getEnderecoPais())) {
			sc.setSiglaPaisEnderecoRequerente(form.getEnderecoPais().trim());
		}
		if (!nuloOuVazio(form.getEnderecoUF())) {
			sc.setSiglaUfenderecoRequerente(form.getEnderecoUF().trim());
		}

		if (!nuloOuVazio(form.getEnderecoNumero())) {
			sc.setEnderecoNumero(form.getEnderecoNumero().trim());
		}

		if (!nuloOuVazio(form.getEnderecoComplemento())) {
			sc.setEnderecoComplemento(form.getEnderecoComplemento().trim());
		}

		if (!nuloOuVazio(form.getEnderecoCidade())) {
			sc.setEnderecoCodigoMunicipio(form.getEnderecoCidade().trim());
		}

		if (!nuloOuVazio(form.getEnderecoBairro())) {
			sc.setEnderecoBairro(form.getEnderecoBairro().trim());
		}

		sc.setDataHoraInsercao(dataCadastro);
		sc.setNumeroProtocolo(getECertidoesDelegate().gerarNumeroProtocolo());
		sc.setNomeRequerente(form.getNomeRequerente().toUpperCase());

		if (!nuloOuVazio(form.getEndereco())) {
			sc.setEndereco(form.getEndereco());
		} else {
			sc.setEndereco("** FORA DO BRASIL **");
		}

		if (!nuloOuVazio(form.getCep())) {
			sc.setCep(Util.retiraFormatacao(form.getCep()));
		} else {
			sc.setCep("");
		}
		sc.setNumeroTelefone(form.getTelefone());
		sc.setEMail(form.getEmail());
		sc.setNumeroIdentificacaoRequerente(form.getRneRgRequerente());
		if (form.getMotivo().equalsIgnoreCase("1")) {
			sc.setMotivo(Util.intValue(form.getMotivo()));
			sc.setNaturalidadeRequerente(new PaisCorporativo(form.getMotivoPais().trim()));
		} else if (form.getMotivo().equalsIgnoreCase("2")) {
			sc.setMotivo(Util.intValue(form.getMotivo()));
			sc.setOutrosMotivos(form.getMotivoOutros().trim());
			sc.setNaturalidadeRequerente(null);
		} else if (form.getMotivo().equalsIgnoreCase("")) {
			sc.setMotivo(0);
			sc.setOutrosMotivos(null);
			sc.setNaturalidadeRequerente(null);
		}
		Set<OutrosNomesSolicitacaoCertidao> outros = new HashSet<OutrosNomesSolicitacaoCertidao>();
		for (int i = 0; i < form.getNomeRequerido().length; i++) {
			if (!form.getNomeRequerido()[i].equalsIgnoreCase("")) {
				OutrosNomesSolicitacaoCertidao onsc = new OutrosNomesSolicitacaoCertidao();
				onsc.setNome(form.getNomeRequerido()[i].toUpperCase());
				onsc.setSolicitacaoCertidao(sc);
				outros.add(onsc);
			}
		}
		sc.setOutrosNomesSolicitacaoCertidaos(outros);
		sc.setNomeMae(form.getNomeMaeRequerido().toUpperCase());
		if (form.getDataNascimentoRequerido() != null && !form.getDataNascimentoRequerido().trim().equals("")) {
			sc.setDataNascimento(Util.converteToDate(form.getDataNascimentoRequerido()));
		}
		if (form.getAnoNascimento() != null && !form.getAnoNascimento().trim().equals("")) {
			sc.setAnoNascimento(new Integer(form.getAnoNascimento()));
		}

		if (!nuloOuVazio(form.getCpf())) {
			sc.setNumeroCpf(Util.retiraFormatacao(form.getCpf()));
		} else {
			sc.setNumeroCpf(null);
		}

		if (!nuloOuVazio(form.getPassaporte())) {
			sc.setNumeroPassaporte(form.getPassaporte());
		} else {
			sc.setNumeroPassaporte(null);
		}

		if (!nuloOuVazio(form.getEstadoCivilRequerido())) {
			EstadoCivil ec = new EstadoCivil();
			ec.setCodigoEstadoCivil(Util.intValue(form.getEstadoCivilRequerido()));
			sc.setEstadoCivil(ec);
		} else {
			sc.setEstadoCivil(null);
		}

		if (!nuloOuVazio(form.getDdi())) {
			sc.setCodigoDdi(form.getDdi());
		} else {
			sc.setCodigoDdi(null);
		}

		if (!nuloOuVazio(form.getDdd())) {
			sc.setCodigoDdd(form.getDdd());
		} else {
			sc.setCodigoDdd(null);
		}

		if (!nuloOuVazio(form.getNomePaiRequerido())) {
			sc.setNomePai(form.getNomePaiRequerido().toUpperCase());
		} else {
			sc.setNomePai(null);
		}

		if (!nuloOuVazio(form.getProtocoloMj())) {
			sc.setProtocoloMj(form.getProtocoloMj().trim());
		} else {
			sc.setProtocoloMj(null);
		}

		if (!nuloOuVazio(form.getObservacao())) {
			sc.setObservacao(form.getObservacao().trim());
		} else {
			sc.setObservacao(null);
		}
		if (!nuloOuVazio(form.getNaturalidadeRequerido())) {
			sc.setNaturalidadeRequerido(new PaisCorporativo(form.getNaturalidadeRequerido()));
		}

		// if (!nuloOuVazio(form.getEstadoCivilRequerido())) {
		// sc.setCodigoSolicitacaoCertidao(Util.intValue(form.getEstadoCivilRequerido()));
		// } else {
		// sc.setCodigoSolicitacaoCertidao(0);
		// }

		if (!nuloOuVazio(form.getDataChegadaBrasil())) {
			sc.setDataChegadaBrasil(Util.converteToDate(form.getDataChegadaBrasil()));
			sc.setAnoChegadaBrasil(null);
		} else if (!nuloOuVazio(form.getAnoChegadaBrasil())) {
			sc.setAnoChegadaBrasil(form.getAnoChegadaBrasil());
			sc.setDataChegadaBrasil(null);
		} else {
			sc.setAnoChegadaBrasil(null);
			sc.setDataChegadaBrasil(null);
		}

		if (!nuloOuVazio(form.getRneRg())) {
			sc.setNumeroIdentificacao(form.getRneRg());
		} else {
			sc.setNumeroIdentificacao(null);
		}

		if (!nuloOuVazio(form.getDataObito())) {
			sc.setDataObito(Util.converteToDate(form.getDataObito()));
			sc.setAnoObito("");
		} else if (!nuloOuVazio(form.getAnoObito())) {
			sc.setAnoObito(form.getAnoObito());
			sc.setDataObito(null);
		} else {
			sc.setAnoObito("");
			sc.setDataObito(null);
		}

		if (!nuloOuVazio(form.getGrauParentesco())) {
			Parentesco pc = new Parentesco();
			pc.setCodigoGrauParentesco(Util.intValue(form.getGrauParentesco()));
			sc.setParentesco(pc);
		} else {
			sc.setParentesco(null);
		}

		if (!nuloOuVazio(form.getCarteiraModelo())) {
			sc.setNumeroCarteira19(form.getCarteiraModelo());
		} else {
			sc.setNumeroCarteira19(null);
		}
		return sc;
	}

	private boolean verificaCamposObrigatorios(SolicitaCertidoesForm form, HttpServletRequest request) {
		ActionErrors erros = new ActionErrors();

		boolean retorno = false;

		if (form.getNacionalidade().trim().equals("")) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNacionalidade"));
			retorno = true;
		} else {
			if (form.getNacionalidade().trim().equalsIgnoreCase("BR")) {
				if (!CPF.validaCPF(form.getCpf())) {
					erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.CpfInvalido"));
					retorno = true;
				}
			} else {
				if (nuloOuVazio(form.getRneRgRequerente()) && nuloOuVazio(form.getPassaporte())) {
					erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioRneOuPassaporte"));
					retorno = true;
				}
			}
		}

		if (nuloOuVazio(form.getNomeRequerente())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNomeRequerente"));
			retorno = true;
		}

		if (nuloOuVazio(form.getSexoRequerido())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioSexoRequerido"));
			retorno = true;
		}
		if (nuloOuVazio(form.getEnderecoPais())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioPaisEndereco"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getEnderecoPais()) && nuloOuVazio(form.getEndereco())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioEndereco"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getEnderecoPais()) && form.getEnderecoPais().equalsIgnoreCase("BR") && nuloOuVazio(form.getEnderecoNumero())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNumeroEndereco"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getEnderecoPais()) && form.getEnderecoPais().equalsIgnoreCase("BR") && nuloOuVazio(form.getEnderecoUF())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioUFEndereco"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getEnderecoPais()) && form.getEnderecoPais().equalsIgnoreCase("BR") && nuloOuVazio(form.getEnderecoCidade())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioCidadeEndereco"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getEnderecoPais()) && form.getEnderecoPais().equalsIgnoreCase("BR") && nuloOuVazio(form.getEnderecoBairro())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioBairroEndereco"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getEnderecoPais()) && form.getEnderecoPais().equalsIgnoreCase("BR") && nuloOuVazio(form.getCep())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioCEP"));
			retorno = true;
		}
		if (nuloOuVazio(form.getEmail())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioEmail"));
			retorno = true;
		}
		if (nuloOuVazio(form.getTelefone())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioTelefone"));
			retorno = true;
		}
		if (nuloOuVazio(form.getMotivo())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioMotivoSolicitacao"));
			retorno = true;
		}
		if (nuloOuVazio(form.getNomeRequerido()[0])) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNome"));
			retorno = true;
		}
		if (nuloOuVazio(form.getNaturalidadeRequerido())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNaturalidadeRequerido"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getDataNascimentoRequerido()) && !validaDataParaPesquisa(form.getDataNascimentoRequerido())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataNascimento"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getDataChegadaBrasil()) && !validaDataParaPesquisa(form.getDataChegadaBrasil())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataChegada"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getDataObito()) && !validaDataParaPesquisa(form.getDataObito())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataObito"));
			retorno = true;
		}
		if (nuloOuVazio(form.getCodigoSeguranca())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioCaracteres"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getMotivo())) {
			if (form.getMotivo().equalsIgnoreCase("1") && nuloOuVazio(form.getMotivoPais())) {
				erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioPais"));
				retorno = true;
			} else if (form.getMotivo().equalsIgnoreCase("2") && nuloOuVazio(form.getMotivoOutros())) {
				erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioOutro"));
				retorno = true;
			}
		}

		saveErrors(request, erros);

		return retorno;
	}

	private boolean validaDataParaPesquisa(String dataPassada) {
		boolean dataValida = true;
		if (dataPassada != null && !dataPassada.equalsIgnoreCase("")) {
			dataValida = DataUtil.isDataValida(dataPassada);
		}
		return dataValida;
	}

	private void carregarCombosSolicitacao(SolicitaCertidoesForm form) {
		form.setListaPais(getDominiosDNN().getListaTodosDominioPaises());
		form.setListaNacionalidade(getDominiosDNN().getListaTodosDominioPaises());
		form.setListaUF(getListaPaises());

		if (form.getEnderecoUF() != null && !form.getEnderecoUF().equalsIgnoreCase("")) {
			form.setListaMunicipio(getDominiosDNN().getListaMunicipio(form.getEnderecoUF().trim().toUpperCase()));
		}

		List<Dominio> estadoCivil = getDominiosDNN().getListaTodosDominioEstadoCivil();
		estadoCivil.remove(0);
		form.setListaEstadoCivil(estadoCivil);
		form.setListaMotivo(Util.preencherComboMotivo());
		form.setListaGrauParentesco(getDominiosDNN().getListaTodosParentesco());
		form.setListaSexo(Util.preencherComboSexo());
	}

	private List<Dominio> getListaPaises() {
		List<Dominio> lista = getDominiosDNN().listarTodosUf();
		List<Dominio> retorno = new ArrayList<Dominio>();
		for (int i = 0; i < lista.size(); i++) {
			if (!lista.get(i).getDescricao().equalsIgnoreCase("00")) {
				retorno.add(lista.get(i));
			}
		}

		return retorno;
	}

	protected ECertidoes getECertidoesDelegate() {
		return ECertidoesDelegate_ECertidao.getInstancia();
	}

	protected DominiosDNN getDominiosDNN() {
		return DominiosDNNDelegate_ECertidao.getInstancia();
	}

	protected Corporativo getCorporativoDelegate() {
		return CorporativoDelegate.getInstancia();
	}

	public boolean nuloOuVazio(String campo) {

		boolean retorno = false;

		if (campo == null || campo.trim().equalsIgnoreCase("")) {
			retorno = true;
		}

		return retorno;
	}
}