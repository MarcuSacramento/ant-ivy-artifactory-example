package br.gov.mj.ecertidoes.web.action;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.gov.mj.corporativo.ejb.CorporativoDelegate;
import br.gov.mj.corporativo.servicos.Corporativo;
import br.gov.mj.dnn.ejb.DominiosDNNDelegate_ECertidao;
import br.gov.mj.dnn.ejb.ECertidoesDelegate_ECertidao;
import br.gov.mj.dnn.ejb.ECertidoesEstatisticaDelegate;
import br.gov.mj.dnn.ejb.ECertidoesEstatisticaDelegate_ECertidao;
import br.gov.mj.dnn.pojo.CertidaoEmitida;
import br.gov.mj.dnn.pojo.OutrosNomesCertidao;
import br.gov.mj.dnn.pojo.PaisCorporativo;
import br.gov.mj.dnn.pojo.Parentesco;
import br.gov.mj.dnn.pojo.exception.InteressadoNaturalizadoException;
import br.gov.mj.dnn.servicos.DominiosDNN;
import br.gov.mj.dnn.servicos.ECertidoes;
import br.gov.mj.dnn.util.CPF;
import br.gov.mj.dnn.util.Protocolo;
import br.gov.mj.dnn.vo.DocumentoCertidaoVO;
import br.gov.mj.dnn.vo.RequisicaoCertidaoVO;
import br.gov.mj.ecertidoes.util.Captcha;
import br.gov.mj.ecertidoes.util.DataUtil;
import br.gov.mj.ecertidoes.util.Util;
import br.gov.mj.ecertidoes.web.form.PesquisaCertidoesForm;
import br.gov.mj.struts.action.MjBaseDispatchAction;

/**
 * Action que efetua pesquisa na aplica��o.
 * 
 * @author Uni�o Federativa do Brasil
 * @author Minist�rio da Justi�a
 * @author CGTI
 * @version 1.0
 */
public class PesquisaCertidoesAction extends MjBaseDispatchAction {

	public ActionForward autenticacaoConsultar(ActionMapping mapping, ActionForm _form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;
		if (verificaCamposObrigatoriosAutenticacao(form, request)) {
			form.setCodigoSeguranca("");
			return mapping.findForward("autenticacao");
		}

		try {
			if (!Captcha.getInstance().isCaptchaOk(request, form.getCodigoSeguranca().trim())) {
				salvarMsgErro("erro.campo.caracteres", request);
				return mapping.findForward("autenticacao");
			}
		} catch (Exception e) {
			//System.out.println("Anderson: Erro E-Certidão.");
			e.printStackTrace();
			salvarMsgErro("erro.campo.caracteres", request);
			return mapping.findForward("autenticacao");
		}

		String numeroUsuariodigitou = form.getNumeroCertidao().trim();
		numeroUsuariodigitou = numeroUsuariodigitou.replace(" ", "");

		String certidaoPesquisa = Util.preencheCom(Util.retiraFormatacao(numeroUsuariodigitou) + "", "0", 16, 1);

		CertidaoEmitida certidaoEmitida = getECertidoesDelegate().getCertidaoEmitidaPorNumero(certidaoPesquisa);

		if (certidaoEmitida == null) {
			return mapping.findForward("autenticacao.negada");
		} else {
			Date dataEmissao = DataUtil.converterParaDateHora(form.getDataEmissaoCertidao() + " " + form.getHoraEmissaoCertidao() + ":00");
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(certidaoEmitida.getDataEmissao());
			calendario.set(Calendar.SECOND, 0);
			calendario.set(Calendar.MILLISECOND, 0);

			if (!dataEmissao.equals(calendario.getTime())) {
				return mapping.findForward("autenticacao.negada");
			}

			//form.setTipoCertidao(certidaoEmitida.getTipoCertidao() == CertidaoEmitida.TIPO_CERTIDAO_CNN ? "NEGATIVA" : "POSITIVA");
			
			ECertidoesEstatisticaDelegate_ECertidao.getInstance().registrarCertidaoAutenticada(request.getRemoteHost(), request.getRemoteAddr(), new Date());
			request.getRequestDispatcher("/pesquisaCertidoes/emitirCertificado.do?certidao=" + certidaoEmitida.getNumeroProtocolo()).forward(request, response);
			return null;
		}

	}

	public ActionForward emitirCertificado(ActionMapping mapping, ActionForm _form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;

		String numeroCertidao = (String) request.getParameter("certidao");

		CertidaoEmitida certidaoEmitida = getECertidoesDelegate().getCertidaoEmitidaPorNumero(Util.retiraFormatacao(numeroCertidao));

		if (certidaoEmitida == null) {
			salvarMsgErro("erro.campo.certidaoinvalida", request);
			request.getRequestDispatcher("/abrirPesquisa/abrirAutenticacao.do").forward(request, response);
			return null;
		}

		form.setNumeroCertidao(Protocolo.formataCertidao(certidaoEmitida.getNumeroProtocolo()));
		form.setNomeInteressado(certidaoEmitida.getNomeInteressado().toUpperCase());
		form.setNomeMae(certidaoEmitida.getNomeMae());
		form.setNomePai(certidaoEmitida.getNomePai());
		form.setDataEmissaoCertidao(Util.formatarData(certidaoEmitida.getDataEmissao()));
		form.setHoraEmissaoCertidao(Util.formatarHora(certidaoEmitida.getDataEmissao()));
		form.setDataNascimento(Util.formatarData(certidaoEmitida.getDataNascimento()));
		
		if (certidaoEmitida.getSexoRequerente() != null && certidaoEmitida.getSexoRequerente().equals('F')) {
			form.setSexoRequerente("Feminino");
		} else {
			form.setSexoRequerente("Masculino");
		}
		if (certidaoEmitida.getSexoRequerido() != null) {
			if (certidaoEmitida.getSexoRequerido().equals('F')) {
				form.setSexoRequerido("Feminino");
			} else {
				form.setSexoRequerido("Masculino");
			}
		}
		Parentesco parentesco = getDominiosDNNDelegate().getParentescoPorCertidao(numeroCertidao);
		form.setGrauParentesco(parentesco != null ? parentesco.getDenominacao() : null);
		form.setNaturalidade(certidaoEmitida.getPaisNaturalidade() != null ? getNomePais(form, certidaoEmitida.getPaisNaturalidade().getSiglaPais()) : null);
		String extras = "";
		if (certidaoEmitida.getMotivo() == 1) {
			if (certidaoEmitida.getPaisNaturalizacao() != null && certidaoEmitida.getPaisNaturalizacao().getSiglaPais() != null && certidaoEmitida.getPaisNaturalizacao().getSiglaPais().trim() != "") {
				extras = getNomePais(form, certidaoEmitida.getPaisNaturalizacao().getSiglaPais());
			}
		} else {
			extras = certidaoEmitida.getOutrosMotivos();
		}

		form.setMotivoSolicitacao(getNomeMotivoSolicitacao(form, String.valueOf(certidaoEmitida.getMotivo())) + " (" + extras + ")");
		String todosOsNomes = "";
		if (certidaoEmitida.getOutrosNomesCertidaos() != null) {
			try {
				for (int i = 0; i < certidaoEmitida.getOutrosNomesCertidaos().size(); i++) {
					OutrosNomesCertidao onc = (OutrosNomesCertidao) certidaoEmitida.getOutrosNomesCertidaos().toArray()[i];
					if (i == 0) {
						todosOsNomes = onc.getNome().toUpperCase();
					} else if (i == certidaoEmitida.getOutrosNomesCertidaos().size() - 1) {
						todosOsNomes = todosOsNomes + " ou " + onc.getNome().toUpperCase();
					} else {
						todosOsNomes = todosOsNomes + ", " + onc.getNome().toUpperCase();
					}
				}
			} catch (Exception e) {
				todosOsNomes = "";
			}
		}
		form.setTodosOsNomes(todosOsNomes);

		return mapping.findForward("emitirCertidao");
	}
	
	public ActionForward emissaoEmitir(ActionMapping mapping, ActionForm _form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;

		if (verificaCamposObrigatoriosEmissao(form, request)) {
			carregarCombosEmitir(form);
			form.setCodigoSeguranca("");
			return mapping.findForward("emissao");
		}

		if (form.getNaturalidade() != null && form.getNaturalidade().equalsIgnoreCase("BR")) {
			carregarCombosEmitir(form);
			form.setCodigoSeguranca("");
			salvarMsgErro("erro.campo.PaisRequeridoBrasil", request);
			return mapping.findForward("emissao");
		}

		try {
			if (!Captcha.getInstance().isCaptchaOk(request, form.getCodigoSeguranca().trim())) {
				salvarMsgErro("erro.campo.caracteres", request);
				form.setCodigoSeguranca("");
				carregarCombosEmitir(form);
				return mapping.findForward("emissao");
			}
		} catch (Exception e) {
			//System.out.println("Anderson: Erro E-Certidão.");
			e.printStackTrace();
			salvarMsgErro("erro.campo.caracteres", request);
			form.setCodigoSeguranca("");
			carregarCombosEmitir(form);
			return mapping.findForward("emissao");
		}

		Date dataCadastro = new Date();
		RequisicaoCertidaoVO requisicao = preencheCertidaoEmitida(form, dataCadastro);

		try {
			requisicao.setHost(request.getRemoteHost());
			DocumentoCertidaoVO documento = getECertidoesDelegate().emitirCertidao(requisicao);
			
			ECertidoesEstatisticaDelegate_ECertidao.getInstance().registrarCertidaoEmitada(request.getRemoteHost(), request.getRemoteAddr(), dataCadastro);
			
            ECertidoesEstatisticaDelegate.getInstance().registrarSolicitacaoCertidaoDeest(request.getRemoteHost(), request.getRemoteAddr(), dataCadastro);

			String nomeArquivo = "CNN " + documento.getProtocolo() + ".pdf";

			response.addHeader("content-disposition", "inline; filename=" + nomeArquivo);
			response.setContentType("application/pdf");
			response.setContentLength(documento.getArquivo().length);

			response.getOutputStream().write(documento.getArquivo());
			response.getOutputStream().flush();
			response.getOutputStream().close();

			return null;
		} catch (InteressadoNaturalizadoException e) {
			ECertidoesEstatisticaDelegate_ECertidao.getInstance().registrarCertidaoNaoEmitida(request.getRemoteHost(), request.getRemoteAddr(), dataCadastro);

			request.setAttribute("nomeRequerido", form.getNome());
			request.setAttribute("grauParentesco", form.getGrauParentesco());
			request.setAttribute("nomePai", form.getNomePai());
			request.setAttribute("nomeMae", form.getNomeMae());
			request.setAttribute("dataNascimento", form.getDataNascimento());
			request.setAttribute("naturalidade", form.getNaturalidade());
			request.setAttribute("motivoSolicitacao", form.getMotivoSolicitacao());
			request.setAttribute("motivoOutros", form.getMotivoOutros());
			request.setAttribute("motivoPais", form.getMotivoPais());
			request.setAttribute("nomeRequerente", form.getNomeInteressado());
			request.setAttribute("sexoRequerente", form.getSexoRequerente());
			request.setAttribute("sexoRequerido", form.getSexoRequerido());
			request.setAttribute("protocoloMj", form.getProtocoloMj());
			request.setAttribute("NumeroCPF", form.getCpf());
			request.setAttribute("nacionalidade", form.getNacionalidade());
			request.setAttribute("numeroPassaporte", form.getNumeroPassaporte());
			request.setAttribute("registroEstrangeiro", form.getRegistroEstrangeiro());
			request.setAttribute("redireciona", "true");
			return mapping.findForward("certidao.negada");
			
			/*requisicao.setHost(request.getRemoteHost());
			DocumentoCertidaoVO documento = getECertidoesDelegate().emitirCertidao(requisicao);
			
			ECertidoesEstatisticaDelegate_ECertidao.getInstance().registrarCertidaoEmitada(request.getRemoteHost(), request.getRemoteAddr(), dataCadastro);

			String nomeArquivo = "CNN " + documento.getProtocolo() + ".pdf";

			response.addHeader("content-disposition", "inline; filename=" + nomeArquivo);
			response.setContentType("application/pdf");
			response.setContentLength(documento.getArquivo().length);

			response.getOutputStream().write(documento.getArquivo());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			return null;*/
		}
	}

	/**
	 * @param form
	 * @return
	 */
	private RequisicaoCertidaoVO preencheCertidaoEmitida(PesquisaCertidoesForm form, Date dataCadastro) {
		RequisicaoCertidaoVO requisicao = new RequisicaoCertidaoVO();

		requisicao.setSexoRequerente(form.getSexoRequerente());
		requisicao.setSexoRequerido(form.getSexoRequerido());
		for (int i = 0; i < form.getNome().length; i++) {
			if (!form.getNome()[i].equalsIgnoreCase("")) {
				requisicao.addOutrosNomes(form.getNome()[i]);
			}
		}

		form.setListaSexo(Util.preencherComboSexo());
		if (!nuloOuVazio(form.getNomeMae())) {
			requisicao.setNomeMae(form.getNomeMae().trim().toUpperCase());
		} else {
			requisicao.setNomeMae(null);
		}

		if (!nuloOuVazio(form.getCpf())) {
			requisicao.setNumeroCPF(Util.retiraFormatacao(form.getCpf()));
		} else {
			requisicao.setNumeroCPF(null);
		}

		if (!nuloOuVazio(form.getRegistroEstrangeiro())) {
			requisicao.setRegistroEstrangeiro(form.getRegistroEstrangeiro());
		} else {
			requisicao.setRegistroEstrangeiro(null);
		}

		if (!nuloOuVazio(form.getNumeroPassaporte())) {
			requisicao.setNumeroPassaporte(form.getNumeroPassaporte());
		} else {
			requisicao.setNumeroPassaporte(null);
		}

		if (!nuloOuVazio(form.getNomeInteressado())) {
			requisicao.setNomeInteressado(form.getNomeInteressado().trim().toUpperCase());
		} else {
			requisicao.setNomeInteressado(null);
		}

		if (!nuloOuVazio(form.getNomePai())) {
			requisicao.setNomePai(form.getNomePai().trim().toUpperCase());
		} else {
			requisicao.setNomePai(null);
		}

		if (!nuloOuVazio(form.getDataNascimento())) {
			requisicao.setDataNascimento(Util.converteToDate(form.getDataNascimento()));
		} else {
			requisicao.setDataNascimento(null);
		}

		if (!nuloOuVazio(form.getAnoNascimento())) {
			requisicao.setAnoNascimento(new Integer(form.getAnoNascimento()));
		}

		if (form.getMotivoSolicitacao() != null && form.getMotivoSolicitacao().equalsIgnoreCase("1")) {
			requisicao.setMotivo(Util.intValue(form.getMotivoSolicitacao()));
			requisicao.setPaisNaturalizacao(form.getMotivoPais().trim());
		} else if (form.getMotivoSolicitacao() != null && form.getMotivoSolicitacao().equalsIgnoreCase("2")) {
			requisicao.setMotivo(Util.intValue(form.getMotivoSolicitacao()));
			requisicao.setOutrosMotivos(form.getMotivoOutros().trim());
			requisicao.setPaisNaturalizacao(null);
		} else if (form.getMotivoSolicitacao() != null && form.getMotivoSolicitacao().equalsIgnoreCase("")) {
			requisicao.setMotivo(0);
			requisicao.setOutrosMotivos(null);
			requisicao.setPaisNaturalizacao(null);
		}

		requisicao.setPaisNacionalidade(form.getNacionalidade());

		if (form.getNaturalidade() != null && !form.getNaturalidade().equalsIgnoreCase("")) {
			requisicao.setPaisNaturalidade(form.getNaturalidade());
		}

		if (form.getGrauParentesco() != null && !form.getGrauParentesco().equalsIgnoreCase("")) {
			requisicao.setParentesco(Util.intValue(form.getGrauParentesco()));
		} else {
			requisicao.setParentesco(0);
		}

		requisicao.setDataEmissao(dataCadastro);

		return requisicao;
	}

	private boolean verificaCamposObrigatoriosEmissao(PesquisaCertidoesForm form, HttpServletRequest request) {
		ActionErrors erros = new ActionErrors();
		boolean retorno = false;

		if (nuloOuVazio(form.getNacionalidade())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNacionalidade"));
			retorno = true;
		} else {
			if (!nuloOuVazio(form.getNacionalidade()) && form.getNacionalidade().trim().equalsIgnoreCase("BR")) {
				if (!CPF.validaCPF(form.getCpf())) {
					erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.CpfInvalido"));
					retorno = true;
				}
			} else {
				if (nuloOuVazio(form.getRegistroEstrangeiro()) && nuloOuVazio(form.getNumeroPassaporte())) {
					erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioRneOuPassaporte"));
					retorno = true;
				}
			}
		}
		/*
		 * if (nuloOuVazio(form.getCpf())) {
		 * erros.add(ActionErrors.GLOBAL_MESSAGE, new
		 * ActionMessage("erro.campo.ObrigatorioCpf")); retorno = true; }
		 */
		if (nuloOuVazio(form.getNomeInteressado())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNomeInteressado"));
			retorno = true;
		}
		if (nuloOuVazio(form.getSexoRequerente())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioSexoInteressado"));
			retorno = true;
		}
		if (nuloOuVazio(form.getSexoRequerido())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioSexoRequerido"));
			retorno = true;
		}
		if (nuloOuVazio(form.getGrauParentesco())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioParentesco"));
			retorno = true;
		}
		if (form.getNome() == null || form.getNome().length == 0 || nuloOuVazio(form.getNome()[0])) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNomeRequerido"));
			retorno = true;
		}
		if ((form.getNomeMae() == null || form.getNomeMae() == "")) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNomeMaeRequerido"));
			retorno = true;
		}
		if ((form.getDataNascimento() == null || form.getDataNascimento().equals("")) && (form.getAnoNascimento() == null || form.getAnoNascimento().equals(""))) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataNascimentoAnoNascimento"));
			retorno = true;
		} else {
			if(form.getDataNascimento() != null && !form.getDataNascimento().equals("") && form.getDataNascimento().length() != 10){
				erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataNascimentoAnoNascimentoFormatoInvalido"));
				retorno = true;
			}
			if(form.getAnoNascimento() != null && !form.getAnoNascimento().equals("") && form.getAnoNascimento().length() != 4){
				erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataNascimentoAnoNascimentoFormatoInvalido"));
				retorno = true;
			}
		}
		if (nuloOuVazio(form.getNaturalidade())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNaturalidadeRequerido"));
			retorno = true;
		}
		if (nuloOuVazio(form.getMotivoSolicitacao())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioMotivoSolicitacao"));
			retorno = true;
		}
		if (nuloOuVazio(form.getCodigoSeguranca())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioCaracteres"));
			retorno = true;
		}
		/*if (!nuloOuVazio(form.getMotivoSolicitacao())) {
			if (form.getMotivoSolicitacao().equalsIgnoreCase("1") && nuloOuVazio(form.getMotivoPais())) {
				erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioPais"));
				retorno = true;
			} else if (form.getMotivoSolicitacao().equalsIgnoreCase("2") && nuloOuVazio(form.getMotivoOutros())) {
				erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioOutro"));
				retorno = true;
			}
		}*/

		saveErrors(request, erros);

		return retorno;
	}

	private boolean verificaCamposObrigatoriosAutenticacao(PesquisaCertidoesForm form, HttpServletRequest request) {
		ActionErrors erros = new ActionErrors();
		boolean retorno = false;

		if (nuloOuVazio(form.getNumeroCertidao())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioNumeroCertidao"));
			retorno = true;
		}
		if (nuloOuVazio(form.getDataEmissaoCertidao())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioDataEmissao"));
			retorno = true;
		}
		if (!nuloOuVazio(form.getDataEmissaoCertidao()) && !validaDataParaPesquisa(form.getDataEmissaoCertidao())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.dataInvalida"));
			retorno = true;
		}
		if (nuloOuVazio(form.getHoraEmissaoCertidao())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioHoraEmissao"));
			retorno = true;
		}
		if (nuloOuVazio(form.getCodigoSeguranca())) {
			erros.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("erro.campo.ObrigatorioCaracteres"));
			retorno = true;
		}

		saveErrors(request, erros);

		return retorno;
	}

	public boolean nuloOuVazio(String campo) {

		boolean retorno = false;

		if (campo == null || campo.trim().equalsIgnoreCase("")) {
			retorno = true;
		}

		return retorno;
	}

	private boolean validaDataParaPesquisa(String dataPassada) {
		boolean dataValida = true;
		if (dataPassada != null && !dataPassada.equalsIgnoreCase("")) {
			dataValida = DataUtil.isDataValida(dataPassada);
		}
		return dataValida;
	}

	protected ECertidoes getECertidoesDelegate() {
		return ECertidoesDelegate_ECertidao.getInstancia();
	}

	protected DominiosDNN getDominiosDNNDelegate() {
		return DominiosDNNDelegate_ECertidao.getInstancia();
	}

	private String getNomePais(PesquisaCertidoesForm form, String codigoPais) {
		String retorno = "";

		if (codigoPais != null && !codigoPais.equalsIgnoreCase("0")) {
			PaisCorporativo corporativo = getDominiosDNNDelegate().getPais(codigoPais);
			if (corporativo != null) {
				retorno = corporativo.getNome();
			}
		}

		return retorno;
	}

	private String getNomeMotivoSolicitacao(PesquisaCertidoesForm form, String codigoMotivoSolicitacao) {
		String retorno = "";
		if (codigoMotivoSolicitacao != null && !codigoMotivoSolicitacao.equalsIgnoreCase("0")) {
			if (form.getListaMotivo().size() == 0) {
				carregarCombosEmitir(form);
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

	protected Corporativo getCorporativoDelegate() {
		return CorporativoDelegate.getInstancia();
	}

	private void carregarCombosEmitir(PesquisaCertidoesForm form) {
		form.setListaNacionalidade(getDominiosDNNDelegate().getListaTodosDominioPaises());
		form.setListaPais(getDominiosDNNDelegate().getListaTodosDominioPaises());
		form.setListaMotivo(Util.preencherComboMotivo());
		form.setListaGrauParentesco(getDominiosDNNDelegate().getListaTodosParentesco());
		form.setListaSexo(Util.preencherComboSexo());
	}

}