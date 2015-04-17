package ubpartner.xct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import ubpartner.logmanagement.LogManagement;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.xct.commun.ConstanteMessages;
import ubpartner.xct.commun.ConstanteXmlMapping;
/**
 * Class for the collection of the mapped facts.
 * Sorted by sheet name and range reference.
 * @author UBPartner
 */

@SuppressWarnings({ "rawtypes", "serial" })
public class MappedFacts extends TreeSet {
    /**
     * Collection of the mapped facts.
     */
    private TreeSet <MappedFact> mappedFactsCollection;
    /**
     * Location of taxonomy schema.
     */
    private String schemaLocation;
    /**
     * Parent guids.
     */
    private HashMap<String, ArrayList<String>> parentGuids = new HashMap<String, ArrayList<String>>();
    /**
     * Getter for mapped facts collection.
     * @return TreeSet of MappedFacts
     */
    public final TreeSet<MappedFact> getMappedFactsCollection() {
        return mappedFactsCollection;
    }
    /**
     * Getter pour schemaLocation.
     * @return String
     */
    public final String getSchemaLocation() {
        return schemaLocation;
    }
    /**
     * Initializing facts collection.
     * @param mappingFile - mapping file
     * @param xbrlNode - xbrli node of the contexts file.
     */
    @SuppressWarnings("unchecked")
    public final void initialiser(final File mappingFile,
    		final Element xbrlNode) {
    	LogManagement.debug("Initialize mapped facts collection.");
        mappedFactsCollection = new TreeSet<MappedFact>();
        int mappedFactsCount = 0;
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(mappingFile);
            Element rootNode = doc.getRootElement();
            Element cellsNode = rootNode.getChild("cells");
            initParents(rootNode);
            List<Element> mappedCells
                  = cellsNode.getChildren(ConstanteXmlMapping.MAPPEDCELLS);
            // schemaLocation
            schemaLocation = ConstanteUtils.getCanonicalPath(
                    mappingFile.getAbsolutePath(),
                    rootNode.getChild(
                            ConstanteXmlMapping.TAXONOMIE).getChildText(
                            ConstanteXmlMapping.SCHEMALOCATION));
            for (Element mappedCell : mappedCells) {
                MappedFact mappedFact = new MappedFact(mappedCell, xbrlNode, parentGuids);
                mappedFactsCollection.add(mappedFact);
                mappedFactsCount++;
            }
            LogManagement.info(mappedFactsCount
                    + ConstanteMessages.MSGMAPPINGSFOUND);
        } catch (JDOMException e) {
            LogManagement.error("initialiser => " + e.getMessage());
        } catch (IOException e) {
        	LogManagement.error("initialiser => " + e.getMessage());
        } catch (Exception e) {
        	LogManagement.error("initialiser => " + e.getMessage());
        } catch (Error e) {
        	LogManagement.fatal("initialiser => " + e.getMessage());
        }
    }
    /**
     * Initialize parent guids map.
     * @param rootNode - root node of the mapping file.
     */
    @SuppressWarnings("unchecked")
	private void initParents(final Element rootNode) {
    	try {
	    	List<Element> parentNodes = rootNode.getChild("cellRelationshipList").getChildren("cellRelationships");
	    	if (!parentNodes.isEmpty()) {
		    	for (Element parentNode:parentNodes) {
			 	   if (parentNode != null) {
			    		Element conceptNode = parentNode.getChild("ConceptName");
			    		List<Element> childCells = parentNode.getChildren("childCells");
			    		ArrayList<String> guids = new ArrayList<String>();
			    		if (conceptNode != null && !childCells.isEmpty()) {
			    			for (Element childNode : childCells) {
			    				guids.add(childNode.getText());
			    			}
			    			if (parentGuids.containsKey(conceptNode.getText())) {
			    				guids.addAll(parentGuids.get(conceptNode.getText()));
			    				parentGuids.remove(conceptNode.getText());
			    			}
			    			parentGuids.put(conceptNode.getText(), guids);
			    		}
			    	}
		    	}
	    	}
    	} catch (Exception e) {
    		LogManagement.debug("No parent guids found");
    	}
    }
}
