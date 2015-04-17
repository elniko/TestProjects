package ubpartner.xct.integratXvt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import ubpartner.xct.commun.ConstanteXmlContext;
import ubpartner.xct.commun.ConstanteXmlInstance;
import ubpartner.logmanagement.LogManagement;

/**
 * Class to parsing instance file to get taxonomy schema ID.
 *
 */
public class IXctXvtXmlInstance {

    /**
     * Class can be instantiated.
     *
     */
    protected IXctXvtXmlInstance() {

    }

    /**
     * Getting taxonomy schema href from instance file,
     * used for validation in xct.
     *
     * @param instance
     *           instance file.
     *
     * @return taxonomy schema href.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<String> getSchemahrefFromXBRL(
            final File instance) {
    	ArrayList<String> schemaHrefs = new ArrayList<String>();
        try {
            SAXBuilder builder = new SAXBuilder();

            Document doc = (Document) builder.build(instance);
            Element rootNode = doc.getRootElement();
            Namespace nsLink = rootNode.getNamespace(
                    ConstanteXmlInstance.NSLINK);
            Namespace nsXlink = rootNode.getNamespace(
                    ConstanteXmlInstance.NSXLINK);

            //get taxonomy HREF SCHEMAs from instance file
            List<Element> listschemaRef = (List<Element>) rootNode
                .getChildren(ConstanteXmlInstance.SCHEMAREF, nsLink);

            for (int i = 0; i < listschemaRef.size(); i++) {
                String sehcmaHref =  listschemaRef.get(i)
                        .getAttributeValue(
                        ConstanteXmlInstance.HREF, nsXlink);
                schemaHrefs.add(sehcmaHref);
            }
        } catch (Exception e) {
            LogManagement.error("getSchemahrefFromXBRL => Error happens in getting schema href from instance file: "
            		+ e.getMessage());
        } catch (Error e) {
        	LogManagement.fatal("getSchemahrefFromXBRL => Error happens in getting schema href from instance file: "
            		+ e.getMessage());
        }
        return schemaHrefs;
    }
    /**
     * Get the list of files for xsd schemas.
     * @param taxos - collections des node xsds dans la taxo currente
     * @param unzipPath - path to the unziped files
     * @return array list of files
     */
    public static ArrayList<File> getXsdsFromConfigFile(
    		final List<Element> taxos, final String unzipPath) {
    	LogManagement.info("Get the list of files for xsd schemas.");
    	try {
	    	ArrayList<File> taxonomies =
	         		new ArrayList<File>();
	    	File taxoFile;
	    	String xsd = "";
	    	for (Element taxo:taxos) {
	    		xsd = taxo.getAttributeValue(ConstanteXmlContext.CONTEXTID);
	    		taxoFile = new File(unzipPath + "/" + xsd);
	    		taxonomies.add(taxoFile);
	    	}
	    	return taxonomies;
    	} catch (Exception e) {
    	    LogManagement.error("getXsdsFromConfigFile => " + e.getMessage());
    		return null;
    	} catch (Error e) {
    		LogManagement.fatal("getXsdsFromConfigFile => " + e.getMessage());
    		return null;
        }  
    }
}
