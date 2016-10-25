package br.gov.mj.ecertidoes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public final class DataUtil {

	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat formatDataHora = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	private DataUtil() {
	}

	public static String converterParaString(Date data) {
		return format.format(data);
	}

	public static String dateToString(Date data) {
		String retorno = "";

		if (data != null) {
			retorno = format.format(data);
		}

		return retorno;
	}

	/**
	 * Retorna hh:mm
	 */
	@SuppressWarnings("deprecation")
	public static String retornaHoraMinuto(Date data) {
		String retorno = "";
		if (data != null) {
			String hora = Util.preencheCom(String.valueOf(data.getHours()),
					"0", 2, 1);
			String minuto = Util.preencheCom(String.valueOf(data.getMinutes()),
					"0", 2, 1);
			retorno = hora + ":" + minuto;
		}
		return retorno;
	}

	public static boolean dataValida(String texto) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		try {
			sdf.parse(texto);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	public static Date stringToDate(String data) {
		Date retorno = new Date();
		;

		if (data != null && !data.equalsIgnoreCase("")) {
			try {
				retorno = format.parse(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			retorno = null;
		}

		return retorno;
	}

	public static Date converterParaDate(String data) {
		try {
			return format.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date converterParaDateHora(String data) {
		try {
			return formatDataHora.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isDataValida(String data) {
		try {
			if (data.length() != 10) {
				return false;
			}

			StringTokenizer tokens = new StringTokenizer(data, "/");

			Integer dia = Integer.parseInt(tokens.nextToken());
			Integer mes = Integer.parseInt(tokens.nextToken());
			Integer ano = Integer.parseInt(tokens.nextToken());

			if (dia < 1 || dia > 32) {
				return false;
			}
			if (mes < 1 || mes > 12) {
				return false;
			}
			if (ano < 1799 || ano > 2100) {
				return false;
			}
			format.parse(data);

			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
