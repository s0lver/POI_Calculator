package tamps.cinvestav.s0lver.kmltranslator.translators;

import org.w3c.dom.Element;
import tamps.cinvestav.s0lver.kmltranslator.entities.SpatialTimeElement;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LinedKmlCreator extends KmlFileCreator {
    private String coordinates;
    private final String LINE_STYLE_NAME = "theline";
    private final String LINE_COLOR = "FF00FFFF";

    public LinedKmlCreator(String outputFilename, List<SpatialTimeElement> spatialTimeElements) {
        super(outputFilename, spatialTimeElements);
    }

    @Override
    public void create() throws ParserConfigurationException, TransformerException, FileNotFoundException {
        prepareDomPreamble();
        createStructure();
        buildCoordinatesList();
        attachCoordinates();
        writeFile();

    }

    /***
     * Remember that for the lined kml files, there is only a HUGE list of locations in CSV style
     */
    private void buildCoordinatesList() {
        StringBuilder sbCoordinates = new StringBuilder();

        for (SpatialTimeElement spatialTimeElement : spatialTimeElements) {
            String stringElement = processSpatialTimeElement(spatialTimeElement);
            sbCoordinates.append(stringElement);
        }

        coordinates = sbCoordinates.toString();
    }

    protected String processSpatialTimeElement(SpatialTimeElement spatialTimeElement) {
        String latitude = String.valueOf(spatialTimeElement.getLatitude());
        String longitude = String.valueOf(spatialTimeElement.getLongitude());
        String altitude = String.valueOf(spatialTimeElement.getAltitude());

        return String.format("%s,%s,%s ", longitude, latitude, altitude);
    }

    private void attachCoordinates() {
        Element coordinatesElement = dom.createElement("coordinates");
        coordinatesElement.appendChild(dom.createTextNode(coordinates));

        getLineStringElement().appendChild(coordinatesElement);
    }

    private void createStructure() {
        Element styleElement = createStyleElement();
        Element folderElement = createFolderElement();
        Element placemarkElement = createPlacemarkElement("Locations");
        Element lineStringElement = createLineStringElement();

        placemarkElement.appendChild(lineStringElement);
        folderElement.appendChild(placemarkElement);

        getRootElement().appendChild(styleElement);
        getRootElement().appendChild(folderElement);
    }

    private Element createStyleElement() {
        Element styleElement = dom.createElement("Style");
        styleElement.setAttribute("id", LINE_STYLE_NAME);

        Element lineStyleElement = dom.createElement("LineStyle");

        Element colorElement = dom.createElement("color");
        colorElement.appendChild(dom.createTextNode(LINE_COLOR));

        Element widthElement = dom.createElement("width");
        widthElement.appendChild(dom.createTextNode("5"));

        lineStyleElement.appendChild(colorElement);
        lineStyleElement.appendChild(widthElement);

        styleElement.appendChild(lineStyleElement);

        return styleElement;
    }

    private Element createFolderElement() {
        Element folderElement = dom.createElement("Folder");

        Element folderNameElement = dom.createElement("name");
        folderNameElement.appendChild(dom.createTextNode("Tracks"));
        folderElement.appendChild(folderNameElement);

        return folderElement;
    }

    private Element createPlacemarkElement(String placemarkName) {
        Element placemarkElement = dom.createElement("Placemark");

        Element placemarkNameElement = dom.createElement("name");
        placemarkNameElement.appendChild(dom.createTextNode(placemarkName));

        Element placemarkStyleUrl = dom.createElement("styleUrl");
        placemarkStyleUrl.appendChild(dom.createTextNode("#" + LINE_STYLE_NAME));

        placemarkElement.appendChild(placemarkStyleUrl);

        return placemarkElement;
    }

    private Element createLineStringElement() {
        Element lineStringElement = dom.createElement("LineString");

        Element tessellateElement = dom.createElement("tessellate");
        tessellateElement.appendChild(dom.createTextNode("1"));

        lineStringElement.appendChild(tessellateElement);

        return lineStringElement;
    }

    private Element getLineStringElement() {
        Element folder = (Element) getRootElement().getElementsByTagName("Folder").item(0);
        Element placemark = (Element) folder.getElementsByTagName("Placemark").item(0);

        return (Element) placemark.getElementsByTagName("LineString").item(0);
    }

    public static LinedKmlCreator createForGpsFixes(String outputFilename, ArrayList<GpsFix> gpsFixes) {
        ArrayList<SpatialTimeElement> spatialTimeElements = convertGpsFixesToSpatialTimeElements(gpsFixes);
        return new LinedKmlCreator(outputFilename, spatialTimeElements);
    }

    public static LinedKmlCreator createForStaypoints(String outputFilename, ArrayList<StayPoint> stayPoints) {
        ArrayList<SpatialTimeElement> spatialTimeElements = convertStaypointsToSpatialTimeElements(stayPoints);
        return new LinedKmlCreator(outputFilename, spatialTimeElements);
    }
}
