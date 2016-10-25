package br.gov.mj.ecertidoes.util;

import javax.swing.DefaultDesktopManager;
import javax.swing.JComponent;

public class MJDocDesktopManager extends DefaultDesktopManager {

	private static final long serialVersionUID = 1;

	@Override
	public void endDraggingFrame(JComponent f) {
		f.setLocation(0, 0);
	}

	@Override
	public void dragFrame(JComponent f, int newX, int newY) {
		f.setLocation(0, 0);
	}
}
