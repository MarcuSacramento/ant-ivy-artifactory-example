package br.gov.mj.ecertidoes.web.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.gov.mj.corporativo.servicos.exceptions.ParametroInvalidoException;
import br.gov.mj.dnn.ejb.ECertidoesDelegate_ECertidao;
import br.gov.mj.dnn.ejb.ECertidoesEstatisticaDelegate_ECertidao;
import br.gov.mj.dnn.ejb.SisCertidoesDelegate_ECertidao;
import br.gov.mj.dnn.pojo.CertidaoEmitida;
import br.gov.mj.dnn.pojo.exception.CertidaoEmAnaliseException;
import br.gov.mj.dnn.pojo.exception.CertidaoEmExigenciaException;
import br.gov.mj.dnn.pojo.exception.CertidaoEmitidaCPNException;
import br.gov.mj.dnn.pojo.exception.CertidaoEsperandoAnaliseException;
import br.gov.mj.dnn.pojo.exception.CertidaoGeradaCPNException;
import br.gov.mj.dnn.pojo.exception.CertidaoIndeferidaException;
import br.gov.mj.dnn.pojo.exception.NumeroProtocoloInexistenteException;
import br.gov.mj.dnn.servicos.ECertidoes;
import br.gov.mj.dnn.servicos.SisCertidoes;
import br.gov.mj.dnn.vo.DocumentoCertidaoVO;
import br.gov.mj.ecertidoes.util.Captcha;
import br.gov.mj.ecertidoes.util.Util;
import br.gov.mj.ecertidoes.web.form.ManterSolicitacaoForm;
import br.gov.mj.struts.action.MjBaseDispatchAction;
import br.gov.mj.web.exception.NegocioException;

public class ManterSolicitacaoAction extends MjBaseDispatchAction {

	public ActionForward abrirConsultarCertidao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("abrir.consulta");
	}

	public ActionForward consultarSolicitacao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterSolicitacaoForm formulario = (ManterSolicitacaoForm) form;
		Date dataCadastro = new Date();
		try {
			if (!Captcha.getInstance().isCaptchaOk(request,
					formulario.getCodigoSeguranca().trim())) {
				throw new NegocioException("erro.campo.caracteres");
			}
		} catch (Exception e) {
			System.out.println("Anderson: Erro E-Certidão.");
			e.printStackTrace();
			throw new NegocioException("erro.campo.caracteres", e);
		}

		try {
			CertidaoEmitida certidaoEmitida = null;

			if (formulario.getOpcaoConsulta().equals("protocoloMJ")) {
				certidaoEmitida = getECertidoes()
						.getCertidaoEmitidaProtocoloMJ(
								formulario.getProtocoloMJ());
			} else {
				certidaoEmitida = getECertidoes()
						.getCertidaoEmitidaSolicitacao(
								Util.retiraFormatacao(formulario
										.getNumeroCertidao()));
			}

			if (certidaoEmitida != null) {
				formulario.setCodigoCertidaoEmitida(certidaoEmitida
						.getCodigoCertidaoEmitida() > 0 ? ""
						+ certidaoEmitida.getCodigoCertidaoEmitida() : "");
			}

			if (certidaoEmitida.getDataNascimento() != null) {
				formulario.setDataNascimento(Util.formatarData(certidaoEmitida
						.getDataNascimento()));
			}
			formulario.setNomeInteressado(certidaoEmitida.getNomeInteressado());
			formulario.setNomeMae(certidaoEmitida.getNomeMae());
			formulario.setNomePai(certidaoEmitida.getNomePai());
			formulario.setNumeroCertidao(Util
					.colocaFormatacaoNumeroCertidao(certidaoEmitida
							.getNumeroProtocolo()));

			ECertidoesEstatisticaDelegate_ECertidao.getInstance()
					.consultaSolicitacaoDeferida(request.getRemoteHost(),
							request.getRemoteAddr(), dataCadastro);
			return mapping.findForward("certidao.emitida.sucesso");
		} catch (ParametroInvalidoException e) {
			throw new NegocioException(e.getMessage());
		} catch (CertidaoEmAnaliseException e) {
			ECertidoesEstatisticaDelegate_ECertidao.getInstance()
					.consultaSolicitacaoEmAnalise(request.getRemoteHost(),
							request.getRemoteAddr(), dataCadastro);
			throw new NegocioException("Certid&atilde;o em an&aacute;lise.");
		} catch (CertidaoEsperandoAnaliseException e) {
			ECertidoesEstatisticaDelegate_ECertidao.getInstance()
					.consultaSolicitacaoEsperandoAnalise(
							request.getRemoteHost(), request.getRemoteAddr(),
							dataCadastro);
			throw new NegocioException(
					"Certid&atilde;o aguardando an&aacute;lise.");
		} catch (NumeroProtocoloInexistenteException e) {
			ECertidoesEstatisticaDelegate_ECertidao.getInstance()
					.consultaSolicitacaoProtocoloInexistente(
							request.getRemoteHost(), request.getRemoteAddr(),
							dataCadastro);
			throw new NegocioException("Protocolo n&atilde;o cadastrado.");
		} catch (CertidaoIndeferidaException e) {
			ECertidoesEstatisticaDelegate_ECertidao.getInstance()
					.consultaSolicitacaoIndeferida(request.getRemoteHost(),
							request.getRemoteAddr(), dataCadastro);
			formulario.setMotivoCertidaoIndeferida(e.getMotivo());
			return mapping.findForward("certidao.emitida.problemas");
		} catch (CertidaoEmExigenciaException e) {
			throw new NegocioException("Certid&atilde;o em Exig&ecirc;ncia.");
		} catch (CertidaoEmitidaCPNException e) {
			CertidaoEmitida ce = getSisCertidoes()
					.getCertidaoEmitidaSolicitacao(
							Util.retiraFormatacao(formulario
									.getNumeroCertidao()));

			String correio = "";

			if (ce.getRegistroCorreio() != null
					&& !ce.getRegistroCorreio().trim().equalsIgnoreCase("")) {
				correio = "Voc&ecirc; poder&aacute; acompanhar a tramita&ccedil;&atilde;o desta no site dos Correios (www.correios.com.br) informando o n&ordm; "
						+ ce.getRegistroCorreio() + ".<br>";
			}

			String texto = "<br>A solicita&ccedil;&atilde;o acima foi analisada e resultou na emiss&atilde;o de uma Certid&atilde;o Positiva. "
					+ "<br>A referida Certid&atilde;o foi encaminhada, por Carta Registrada, para o endere&ccedil;o informado no Sistema e-Certid&atilde;o. <br>"
					+ correio
					+ "Caso ocorra qualquer problema, entre em contato conosco atrav&eacute;s do e-mail naturalizacao@mj.gov.br<BR>";

			throw new NegocioException(texto);
		} catch (CertidaoGeradaCPNException e) {
			String texto = "A solicita&ccedil;&atilde;o acima foi analisada e resultou na emiss&atilde;o de uma Certid&atilde;o Positiva, "
					+ "<br>que est&aacute; sendo ultimada para posterior envio pelos Correios para o endere&ccedil;o informado no Sistema e-Certid&atilde;o. "
					+ "<br>Em breve poder&aacute;, tamb&eacute;m, consultar o nº do registro dos Correios para acompanhamento pelo site dos Correios (www.correios.com.br)";
			throw new NegocioException(texto);
		}

	}

	public ActionForward downloadCertidao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ManterSolicitacaoForm formulario = (ManterSolicitacaoForm) form;
		CertidaoEmitida certidaoEmitida = new CertidaoEmitida();

		Integer codigo = new Integer(formulario.getCodigoCertidaoEmitida());
		certidaoEmitida.setCodigoCertidaoEmitida(codigo);

		DocumentoCertidaoVO documento = getECertidoes().getDocumentoGerado(
				certidaoEmitida);

		response.setContentType("application/pdf");
		response.getOutputStream().write(documento.getArquivo());

		return null;
	}

	private ECertidoes getECertidoes() {
		return ECertidoesDelegate_ECertidao.getInstancia();
	}

	private SisCertidoes getSisCertidoes() {
		return SisCertidoesDelegate_ECertidao.getInstancia();
	}
}
