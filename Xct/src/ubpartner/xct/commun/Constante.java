package ubpartner.xct.commun;

import ubpartner.utils.commun.ConstanteUtils;

/**
 * General constants for XCT.
 */
public final class Constante {
    /**
     * Class cannot be Instantiated.
     */
    private Constante() {
        // Class cannot be Instantiated.
    }
    /**
     * Application full name.
     */
    public static final String NOMAPPLICATION = "XBRL Converting Tools";
    /**
     * Application short name.
     */
    public static final String NOMCOURTAPPLICATION = "xct";
    /**
     * Application version.
     */
    public static final String VERSIONAPPLICATION = Constante.class.getPackage().getImplementationVersion();//"1.11.0";
    /**
     * Separator.
     */
    public static final String SEPARATEUR = "\\.";
    /**
     * Extension XML.
     */
    public static final String XML = "xml";
    /**
     * Extension XBRL.
     */
    public static final String XBRL = "xbrl";
    /**
     * Extension XLS.
     */
    public static final String XLS = "xls";
    /**
     * Extension XLSX.
     */
    public static final String XLSX = "xlsx";
    /**
     * Extension CSV.
     */
    public static final String CSV = "csv";
    /**
     * Numeric data type.
     */
    public static final String AMOUNTTYPE = "amount";
    /**
     * String data type.
     */
    public static final String STRINGTYPE = "string";
    /**
     * Type des donnees date.
     */
    public static final String DATATYPE = "data";
    /**
     * Constante.
     * Pour le type de donnees utilise pour les parametres SatrtDate/EndDate.
     */
    public static final String DATETYPE = "date";
    /**
     *Constante pour le type d'input "Saisie manuelle".
     */
    public static final String INPUTMODE = "input";
    /**
     *Constante pour le type d'input "Adresse de cellule".
     */
    public static final String ADDRESSMODE = "address";
    /**
     *Constante pour le type d'input "Nom de cellule".
     */
    public static final String NAMEMODE = "name";
    /**
     *Constante pour le chemin de licence.
     */
    public static final String LICENSEPATH = "./";
    /**
     *Constante pour le nom de licence.
     */
    public static final String LICENSENAME = "License.dat";
    /**
     *Extension DAT.
     */
    public static final String DAT = "dat";
    /**
     * Nom de fichier d'instance complet.
     */
    public static final String FULLINSTANCE = "_full.xbrl";
    /**
     * Nom de fichier de mapping restreint.
     */
    public static final String MAPRESTRICTED = "_RestrictedMapping.xml";
    /**
     * possible formats list (priority - europe format).
     */
    public static final String [] DATEFORMATSEUR =
        {"yyyy-MM-dd", "dd/MM/yyyy", "MM/dd/yyyy", "dd/MM/yy", "MM/dd/yy",
        "MM/yyyy", "dd.MM.yyyy", "MM.dd.yyyy", "dd MMMMM yyyy"};
    /**
     * possible formats list (priority - english format).
     */
    public static final String [] DATEFORMATSENG =
        {"yyyy-MM-dd", "MM/dd/yyyy", "dd/MM/yyyy", "dd/MM/yy", "MM/dd/yy",
        "MM/yyyy", "dd.MM.yyyy", "MM.dd.yyyy", "dd MMMMM yyyy"};
    /**
     * Nom de fichier d'excel avec les resultat de validation.
     */
    public static final String VALIDATEDINSTANCE = "_Validated.xlsx";
    /**
     * Mode d'inclusion des tabs.
     */
    public static final String INC = "inclusion";
    /**
     * Mode d'exclusion des tabs.
     */
    public static final String EXC = "exclusion";
    /**
     * Namespace prefix for ISO 4217.
     */
    public static final String ISO = "iso4217";    
    /**
     * Node mesure in units node.
     */
    public static final String MESURE = "measure";
    
    /**
     * Chemin de fichier de date.
     */
    public static final String DATEPATH = ConstanteUtils.stringIsNull(
            System.getenv("UBP_HOME"), System.getProperty("user.home"))
            + "/UBPartner/";
    /**
     * Path to the stylesheet for xbrl validation report.
     */
    public static final String STYLEPATH = ConstanteUtils.stringIsNull(
            System.getenv("UBP_STYLE"), "" + "/ubp-stylesheet.xsl");
    
}
