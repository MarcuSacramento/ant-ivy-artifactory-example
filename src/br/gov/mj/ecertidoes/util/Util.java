package br.gov.mj.ecertidoes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.gov.mj.corporativo.dominios.Dominio;

public final class Util {

	/**
	 * Ex: util.preencheCom("532835" , "0" , 14 , 1);
	 *
	 * 1) A String “532835” representa a Linha ou texto a ser preenchido
	 *
	 * 2) A String “0” representa a Letra ou Número que será inserido
	 *
	 * 3) O Integer 14 representa o tamanho total que a String a ser preenchida
	 * precisa ter
	 *
	 * 4) O Integer 1 informa ao método que a String “0” será preenchida a
	 * Esquerda
	 */
	public static String preencheCom(String linha_a_preencher, String letra,
			int tamanho, int direcao) {

		// Checa se Linha a preencher é nula ou branco
		if (linha_a_preencher == null || linha_a_preencher.trim() == "") {
			linha_a_preencher = "";
		}

		// Enquanto Linha a preencher possuir 2 espaços em branco seguidos,
		// substitui por 1 espaço apenas
		while (linha_a_preencher.contains(" ")) {
			linha_a_preencher = linha_a_preencher.replaceAll(" ", " ").trim();
		}

		// Retira caracteres estranhos
		linha_a_preencher = linha_a_preencher.replaceAll("[./-]", "");

		StringBuffer sb = new StringBuffer(linha_a_preencher);
		if (direcao == 1) { // a Esquerda
			for (int i = sb.length(); i < tamanho; i++) {
				sb.insert(0, letra);
			}
		} else if (direcao == 2) {// a Direita
			for (int i = sb.length(); i < tamanho; i++) {
				sb.append(letra);
			}
		}
		return sb.toString();
	}

	public static String retiraCaracter(String nome) {
		String retorno = "";
		if (nome != null && !nome.equalsIgnoreCase("")) {
			retorno = nome.replaceAll("-", "");
			retorno = retorno.replaceAll("#", "");
			retorno = retorno.replaceAll("/", "");
			retorno = retorno.replaceAll(",", "");

		}
		return retorno;
	}

	public static String retiraFormatacao(String numero) {
		String retorno = "";
		if (numero != null && !numero.equalsIgnoreCase("")) {
			retorno = numero.replaceAll("-", "");
			retorno = retorno.replace('.', '#');
			retorno = retorno.replaceAll("#", "");
			retorno = retorno.replaceAll("/", "");
		}
		return retorno;
	}

	/**
	 * 
	 * Formata o texto passado para: 012.345.678.901/2345
	 * 
	 */

	public static String colocaFormatacaoNumeroCertidao(String numeroCertidao) {
		String retorno = "";
		if (numeroCertidao != null && !numeroCertidao.equalsIgnoreCase("")
				&& numeroCertidao.length() == 16) {
			retorno = numeroCertidao.substring(0, 3) + "."
					+ numeroCertidao.substring(3, 6) + "."
					+ numeroCertidao.substring(6, 9) + "."
					+ numeroCertidao.substring(9, 12) + "/"
					+ numeroCertidao.substring(12);
		}

		return retorno;
	}

	public static List<Dominio> preencherComboMotivo() {
		List<Dominio> retorno = new ArrayList<Dominio>();

		Dominio d1 = new Dominio();
		d1.setCodigo("1");
		// d1.setDescricao("Aquisi&ccedil;&atilde;o de cidadania");
		d1.setDescricao("Aquisição de cidadania");

		Dominio d2 = new Dominio();
		d2.setCodigo("2");
		d2.setDescricao("Outros");

		retorno.add(d1);
		retorno.add(d2);

		return retorno;
	}

	public static List<Dominio> preencherComboSexo() {
		List<Dominio> retorno = new ArrayList<Dominio>();

		Dominio d1 = new Dominio();
		d1.setCodigo("M");
		d1.setDescricao("Masculino");

		Dominio d2 = new Dominio();
		d2.setCodigo("F");
		d2.setDescricao("Feminino");

		retorno.add(d1);
		retorno.add(d2);

		return retorno;
	}

	/**
	 * 
	 * @param dataString
	 *            formato dd/MM/yyyy
	 * @return
	 */
	public static Date converteToDate(String dataString) {
		Date dataNascimento = new Date();
		if (dataString != null && !dataString.equalsIgnoreCase("")) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				return format.parse(dataString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return dataNascimento;
	}

	public static Integer intValue(String valor) {
		if (valor == null || valor.trim().equals("")) {
			return null;
		}
		Integer inte = new Integer(valor);

		return inte;
	}

	/**
	 * Método que converte java.util.Date em String com formação "HH:MM".
	 * 
	 * @param date
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public static String formatarHora(Date data) {
		String retorno = "";
		if (data != null) {
			int i = data.getMinutes();
			if (i <= 9) {
				retorno = data.getHours() + ":0" + data.getMinutes();
			} else {
				retorno = data.getHours() + ":" + data.getMinutes();
			}
		}
		return retorno;
	}

	/**
	 * Método que converte java.util.Date em String com formação "dd/MM/yyyy".
	 * 
	 * @param date
	 * @return String
	 */
	public static String formatarData(Date data) {
		return data != null ? new SimpleDateFormat("dd/MM/yyyy").format(data)
				: "";
	}

}