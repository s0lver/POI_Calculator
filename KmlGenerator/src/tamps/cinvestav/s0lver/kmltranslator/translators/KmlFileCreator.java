package tamps.cinvestav.s0lver.kmltranslator.translators;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tamps.cinvestav.s0lver.kmltranslator.entities.SpatialTimeElement;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class KmlFileCreator {
    protected ArrayList<SpatialTimeElement> spatialTimeElements;
    protected String outputFilename;
    protected Document dom;
    protected final SimpleDateFormat dateFormatInKML = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    KmlFileCreator(String outputFilename, ArrayList<SpatialTimeElement> spatialTimeElements) {
        this.outputFilename = outputFilename;
        this.spatialTimeElements = spatialTimeElements;
    }

    protected void prepareDomPreamble() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        DOMImplementation domImplementation = db.getDOMImplementation();

        dom = domImplementation.createDocument("http://earth.google.com/kml/2.2", "kml", null);

        Element root = dom.createElement("Document");

        dom.getDocumentElement().appendChild(root);
    }

    protected Element getRootElement(){
        return (Element) dom.getDocumentElement().getFirstChild();
    }

    public abstract void create() throws ParserConfigurationException, TransformerException, FileNotFoundException;

    protected Element createDataElement(String name, String value) {
        Element dataElement = dom.createElement("Data");
        dataElement.setAttribute("name", name);
        dataElement.appendChild(dom.createTextNode(value));

        return dataElement;
    }

    protected void writeFile() throws TransformerException, FileNotFoundException {
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.STANDALONE, "yes");

        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(outputFilename)));
    }

    protected static ArrayList<SpatialTimeElement> convertStaypointsToSpatialTimeElements(ArrayList<StayPoint> stayPoints) {
        ArrayList<SpatialTimeElement> spacetime = new ArrayList<>();
        for (StayPoint stayPoint : stayPoints) {
            spacetime.add(new SpatialTimeElement(stayPoint));
        }
        return spacetime;
    }

    protected static ArrayList<SpatialTimeElement> convertGpsFixesToSpatialTimeElements(ArrayList<GpsFix> gpsFixes) {
        ArrayList<SpatialTimeElement> spacetime = new ArrayList<>();
        for (GpsFix gpsFix : gpsFixes) {
            spacetime.add(new SpatialTimeElement(gpsFix));
        }
        return spacetime;
    }
}
