package br.gov.mj.ecertidoes.util;

import java.awt.Color;

import javax.servlet.http.HttpServletRequest;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class Captcha {

	private ImageCaptchaService service;
	private static Captcha instance = new Captcha();

	private Captcha() {
		DefaultManageableImageCaptchaService serv = new DefaultManageableImageCaptchaService(
				180, 1000, 3);
		serv.setCaptchaEngine(new EngineNumeros());
		service = serv;
	}

	public static Captcha getInstance() {
		return instance;
	}

	public ImageCaptchaService getService() {
		return service;
	}

	/* Especializando um Engine para gerar apenas n�meros */
	class EngineNumeros extends ListImageCaptchaEngine {

		protected void buildInitialFactories() {
			/*
			 * Cria um um TextPaster, o tamanho m�nimo � de 5 caracteres Maximo
			 * de 8, e a cor do texto ser� branca
			 */
			TextPaster textPaster = new RandomTextPaster(4, 4, Color.BLACK);
			/* Um gerador de background, a imagem ter� 100 x 50 pixels */
			BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(
					150, 60);
			/*
			 * Um gerador de fonte, � respons�vel por distorcer o texto, o
			 * tamanho m�nimo da fonte � 25 e o Maximo 30
			 */
			FontGenerator fontGenerator = new TwistedAndShearedRandomFontGenerator(
					35, 35);
			/*
			 * O objeto respons�vel por juntar o background, a fonte e o texto
			 * para gerar a imagem
			 */
			WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
					backgroundGenerator, textPaster);
			/*
			 * Adiciona o Factory RandomWordGenerator recebe os caracteres
			 * v�lidos, no caso queremos apenas n�meros
			 */
			this.addFactory(new GimpyFactory(new RandomWordGenerator(
					"0123456789abcdefghijklmnopqrstuvxzwky"), wordToImage));
		}
	}

	public boolean isCaptchaOk(HttpServletRequest request, String codSeguranca) {
		String id = (String) request.getSession().getAttribute("id");

		if (id == null) {
			id = request.getSession().getId();
		}

		try {
			if (!instance.getService().validateResponseForID(id, codSeguranca)) {
				return false;
			}
		} catch (CaptchaServiceException e) {
			return true;
		}

		return true;
	}
}