package br.gov.mj.ecertidoes.util;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class MaxLength extends PlainDocument {
	private static final long serialVersionUID = 1L;
	int tamMax;

	// Método que recebe parâmetro da quantidade máxima de caracteres digitados
	// dentro do campos de tipo TextField e TextArea
	public MaxLength(int tam) {
		tamMax = tam;
	}

	// Método que implementa o limite da quantidade máxima de caracteres
	// digitados
	public void insertString(int offs, String str,
			javax.swing.text.AttributeSet a) throws BadLocationException {
		if ((getLength() + str.length()) <= tamMax)
			super.insertString(offs, str, a);
	}
}

// Para usa-lo em JTextField
// txtNomeCampoo.setDocument(new MaxLength(15));//Limite-Caracteres-Digitados

// Para usa-lo em JTextArea
// txtNomeTextArea.getJTextArea().setDocument(new
// MaxLength(10));//Limite-Caracteres-Digitados

// Outras Maneiras de Implementar uma classe que limita a quantidade de
// caracteres digitados
/*
 * import java.awt.TextField; import java.awt.event.TextListener; import
 * java.awt.event.TextEvent;
 */
/**
 * Classe destinada a limitar o número de caracteres que podem ser digitados
 * dentro de um TextField.
 *
 * @author: Vanei Anderson Heidemann
 * @version: 1.00 - 29/01/1999
 */
// public class FixedSizeTextField implements TextListener {
/**
 * Armazena o TextField que está sendo controlado.
 */

// private TextField field;
/**
 * Armazena o número de colunas permitido no TextField.
 */

// private int columns;

/**
 * Cria um FixedSizeTextField para limitar o tamanho do TextField especificado.
 * O número de colunas permitidas é calculado através do Método getColumns do
 * TextField.
 * 
 * @param field
 *            O TextField que terá o número de colunas limitado.
 */
/*
 * public FixedSizeTextField(TextField field) { this(field, field.getColumns());
 * }
 */

/**
 * Cria um FixedSizeTextField para limitar o tamanho do TextField para o número
 * de colunas especificado.
 * 
 * @param field
 *            O TextField que terá o número de colunas limitado.
 * @param columns
 *            O número máximo de colunas permitido ao TextField.
 */
/*
 * public FixedSizeTextField(TextField field, int columns) { this.field = field;
 * this.columns = columns;
 * 
 * field.addTextListener(this); }
 */

/**
 * Manipula as alterações no conteúdo do TextField para limitar o número de
 * carateres entrados.
 * 
 * @param event
 *            Evento disparado quando o conteúdo é alterado.
 */
/*
 * public void textValueChanged(TextEvent event) { if (field.getText().length()
 * > columns) { int pos = field.getCaretPosition();
 * field.setText(field.getText().substring(0, columns));
 * field.setCaretPosition(pos); } }
 */
// }