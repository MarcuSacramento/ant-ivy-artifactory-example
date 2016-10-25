package br.gov.mj.ecertidoes.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.gov.mj.corporativo.dominios.Dominio;
import br.gov.mj.corporativo.ejb.CorporativoDelegate;
import br.gov.mj.corporativo.servicos.Corporativo;
import br.gov.mj.dnn.ejb.DominiosDNNDelegate_ECertidao;
import br.gov.mj.dnn.ejb.ECertidoesDelegate_ECertidao;
import br.gov.mj.dnn.servicos.DominiosDNN;
import br.gov.mj.dnn.servicos.ECertidoes;
import br.gov.mj.ecertidoes.util.Util;
import br.gov.mj.ecertidoes.web.form.PesquisaCertidoesForm;
import br.gov.mj.ecertidoes.web.form.SolicitaCertidoesForm;
import br.gov.mj.struts.action.MjBaseDispatchAction;

public class AbrirPesquisaAction extends MjBaseDispatchAction {

	public ActionForward abrirApresentacao(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("apresentacao");
	}

	public ActionForward abrirEmissao(ActionMapping mapping, ActionForm _form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;
		form.limparForm();
		carregarCombosEmitir(form);
		return mapping.findForward("emissao");
	}

	public ActionForward gerarCaptchaEmitir(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;
		form.setCodigoSeguranca("");
		carregarCombosEmitir(form);
		return mapping.findForward("emissao");
	}

	public ActionForward gerarCaptchaAutenticar(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;
		form.setCodigoSeguranca("");
		return mapping.findForward("autenticacao");
	}

	public ActionForward gerarCaptchaSolicitar(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;
		form.setCodigoSeguranca("");
		return mapping.findForward("solicita");
	}

	public ActionForward abrirAutenticacao(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PesquisaCertidoesForm form = (PesquisaCertidoesForm) _form;
		form.limparForm();
		return mapping.findForward("autenticacao");
	}

	protected ECertidoes getECertidoesDelegate() {
		return ECertidoesDelegate_ECertidao.getInstancia();
	}

	public ActionForward abrirCertidaoNegada(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SolicitaCertidoesForm form = (SolicitaCertidoesForm) _form;

		carregarForm(request, form);

		carregarCombosSolicitacao(form);
		return mapping.findForward("solicitacao");
	}

	public ActionForward limparSolicitacao(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SolicitaCertidoesForm form = (SolicitaCertidoesForm) _form;
		form.limparForm();
		carregarCombosSolicitacao(form);
		return mapping.findForward("solicitacao");
	}

	/**
	 * @param request
	 * @param form
	 */
	private void carregarForm(HttpServletRequest request,
			SolicitaCertidoesForm form) {
		String[] nomeRequerido = (String[]) request
				.getAttribute("nomeRequerido");
		String nomePai = (String) request.getAttribute("nomePai");
		String nomeMae = (String) request.getAttribute("nomeMae");
		String dataNascimento = (String) request.getAttribute("dataNascimento");
		String naturalidade = (String) request.getAttribute("naturalidade");
		String motivoSolicitacao = (String) request
				.getAttribute("motivoSolicitacao");
		String motivoOutros = (String) request.getAttribute("motivoOutros");
		String motivoPais = (String) request.getAttribute("motivoPais");
		String nomeRequerente = (String) request.getAttribute("nomeRequerente");
		String grauParentesco = (String) request.getAttribute("grauParentesco");
		String sexoRequerente = (String) request.getAttribute("sexoRequerente");
		String sexoRequerido = (String) request.getAttribute("sexoRequerido");
		String protocoloMj = (String) request.getAttribute("protocoloMj");
		String NumeroCPF = (String) request.getAttribute("NumeroCPF");
		String numeroPassaporte = (String) request
				.getAttribute("numeroPassaporte");
		String registroEstrangeiro = (String) request
				.getAttribute("registroEstrangeiro");

		form.limparForm();
		form.setDdi("55");
		form.setGrauParentesco(grauParentesco);
		form.setNomeRequerente(nomeRequerente);
		form.setNomeRequerido(nomeRequerido);
		form.setNomePaiRequerido(nomePai);
		form.setNomeMaeRequerido(nomeMae);
		form.setDataNascimentoRequerido(dataNascimento);
		form.setNaturalidadeRequerido(naturalidade);
		form.setMotivo(motivoSolicitacao);
		form.setMotivoOutros(motivoOutros);
		form.setMotivoPais(motivoPais);
		form.setSexoRequerente(sexoRequerente);
		form.setSexoRequerido(sexoRequerido);
		form.setProtocoloMj(protocoloMj);
		form.setCpf(NumeroCPF);
		form.setPassaporte(numeroPassaporte);
		form.setRneRgRequerente(registroEstrangeiro);
	}

	public ActionForward gerarCaptchaSolicitacao(ActionMapping mapping,
			ActionForm _form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SolicitaCertidoesForm form = (SolicitaCertidoesForm) _form;
		form.setCodigoSeguranca("");
		carregarCombosSolicitacao(form);
		return mapping.findForward("solicitacao");
	}

	private void carregarCombosEmitir(PesquisaCertidoesForm form) {
		form.setListaNacionalidade(getDominiosDNN()
				.getListaTodosDominioPaises());
		form.setListaPais(getDominiosDNN().getListaTodosDominioPaises());
		form.setListaMotivo(Util.preencherComboMotivo());
		form.setListaGrauParentesco(getDominiosDNN().getListaTodosParentesco());
		form.setListaSexo(Util.preencherComboSexo());
	}

	private void carregarCombosSolicitacao(SolicitaCertidoesForm form) {
		form.setListaPais(getDominiosDNN().getListaTodosDominioPaises());
		form.setListaNacionalidade(getDominiosDNN()
				.getListaTodosDominioPaises());
		form.setListaUF(getListaPaises());

		if (form.getEnderecoUF() != null
				&& !form.getEnderecoUF().equalsIgnoreCase("")) {
			form.setListaMunicipio(getDominiosDNN().getListaMunicipio(
					form.getEnderecoUF().trim().toUpperCase()));
		}

		List<Dominio> estadoCivil = getDominiosDNN()
				.getListaTodosDominioEstadoCivil();
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

	protected Corporativo getCorporativoDelegate() {
		return CorporativoDelegate.getInstancia();
	}

	protected DominiosDNN getDominiosDNN() {
		return DominiosDNNDelegate_ECertidao.getInstancia();
	}
}