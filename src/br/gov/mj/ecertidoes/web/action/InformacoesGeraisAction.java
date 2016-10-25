package br.gov.mj.ecertidoes.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.gov.mj.dnn.ejb.ECertidoesEstatisticaDelegate_ECertidao;
import br.gov.mj.struts.action.MjBaseDispatchAction;

public class InformacoesGeraisAction extends MjBaseDispatchAction {

	public ActionForward abrirOrientacoesGerais(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("apresentacao");
	}

	public ActionForward abrirBaseLegal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		getEstatisticas().registrarLeituraBaseLegal(request.getRemoteHost(),
				request.getRemoteAddr(), new Date());

		return mapping.findForward("apresentacao");
	}

	private ECertidoesEstatisticaDelegate_ECertidao getEstatisticas()
			throws Exception {
		return ECertidoesEstatisticaDelegate_ECertidao.getInstance();
	}
}
