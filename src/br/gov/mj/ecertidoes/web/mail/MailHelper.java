package br.gov.mj.ecertidoes.web.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.MessageFormat;

import br.gov.mj.corporativo.componentes.sendmail.ejb.SendMailDelegate;
import br.gov.mj.corporativo.servicos.exceptions.ErroInternoException;

@SuppressWarnings("deprecation")
public class MailHelper {

	private static String templateEmailSolicitacao;
	private static final String EMAIL_SISTEMA = "sistemae-certidao@mj.gov.br";

	public static MailHelper instance = null;

	public static MailHelper getInstance() throws ErroInternoException {
		if (instance == null) {
			instance = new MailHelper();
		}
		return instance;
	}

	private MailHelper() throws ErroInternoException {
		try {
			templateEmailSolicitacao = getConteudo("/br/gov/mj/ecertidoes/web/mail/EmailECertidao.html");
		} catch (IOException e) {
			throw new ErroInternoException(e.getMessage(), e.getCause());
		}
	}

	public void enviarEmailSolicitacaoSucesso(String to, Object[] parametros)
			throws ErroInternoException {
		String assunto = "Pedido de Certidão Negativa de Naturalização - Sistema e-Certidão";
		String corpoEmail = MessageFormat.format(templateEmailSolicitacao,
				parametros);
		enviarEmail(to, assunto, corpoEmail);
	}

	private static String getConteudo(String arquivo) throws IOException {
		StringBuilder retorno = new StringBuilder();
		Reader ler;

		ler = new InputStreamReader(
				MailHelper.class.getResourceAsStream(arquivo));
		BufferedReader origem = new BufferedReader(ler);
		String linha;
		while ((linha = origem.readLine()) != null) {
			retorno.append(linha);
		}
		origem.close();
		ler.close();

		return retorno.toString();
	}

	public void enviarEmail(String to, String assunto, String corpoEmail)
			throws ErroInternoException {
		try {
			SendMailDelegate.getInstance().sendSimpleMail(EMAIL_SISTEMA, to,
					assunto, corpoEmail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErroInternoException(e.getMessage(), e.getCause());
		}
	}

}
