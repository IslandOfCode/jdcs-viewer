package it.islandofcode.jdcsviewer.struct;

/**
 * Questa eccezione viene lanciata nei seguenti casi:
 * <ul>
 * <li>quando il costruttore della classe {@link Settings} non riesce a fare l'unmarshalling del file xml relativo al modello passato come parametro.</li>
 * <li>quando in {@link it.islandofcode.jdcsviewer.SettingsUI} non si riesce a leggere/scrivere una proprietà (NB: in questo caso l'eccezione è catturata ma ignorata)</li>
 * </ul>
 * 
 * Prende in input la classe d'origine e un messaggio.
 * 
 * @author Riccardo
 */
public class InconsistentSettingException extends Exception {

	private static final long serialVersionUID = -5785570548062075190L;

	private String origin;
	private String mex;
	
	public InconsistentSettingException(String origin, String mex) {
		this.origin = origin;
		this.mex = mex;
	}
	
	public String toString() {
		if(this.origin == null || this.origin == "") {
			this.origin = "NULL";
		}
		return "Inconsistent setting found. ORIGIN["+origin+"]->MEX[" + mex +"]";
	}
	
}
