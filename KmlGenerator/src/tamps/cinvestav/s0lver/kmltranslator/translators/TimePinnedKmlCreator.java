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

/***
 * This class extends the PinnedKmlCreator by adding time information.
 * It can be "played" in Google Earth
 */
public class TimePinnedKmlCreator extends PinnedKmlCreator {
    public TimePinnedKmlCreator(String outputFilename, List<SpatialTimeElement> spatialTimeElements) {
        super(outputFilename, spatialTimeElements);
    }

    @Override
    public void create() throws ParserConfigurationException, TransformerException, FileNotFoundException {
        prepareDomPreamble();
        int i = 1;
        for (SpatialTimeElement spatialTimeElement : spatialTimeElements) {
            Element currentPlacemark = processSpatialTimeElement(spatialTimeElement, i++);
            getRootElement().appendChild(currentPlacemark);
        }

        writeFile();
    }

    protected Element processSpatialTimeElement(SpatialTimeElement spatialTimeElement, int idx) {
        Element placemarkElement = super.processSpatialTimeElement(spatialTimeElement, idx);
        Element timestampElement = createTimestampElement(spatialTimeElement);

        placemarkElement.appendChild(timestampElement);
        return placemarkElement;
    }

    private Element createTimestampElement(SpatialTimeElement spatialTimeElement) {
        String stringTime = dateFormatInKML.format(spatialTimeElement.getArrivalTime());

        Element timestampElement = dom.createElement("TimeStamp");
        Element whenElement = dom.createElement("when");
        whenElement.appendChild(dom.createTextNode(stringTime));
        timestampElement.appendChild(whenElement);

        return timestampElement;
    }

    public static TimePinnedKmlCreator createForGpsFixes(String outputFilename, ArrayList<GpsFix> gpsFixes) {
        ArrayList<SpatialTimeElement> spatialTimeElements = convertGpsFixesToSpatialTimeElements(gpsFixes);
        return new TimePinnedKmlCreator(outputFilename, spatialTimeElements);
    }

    public static TimePinnedKmlCreator createForStaypoints(String outputFilename, ArrayList<StayPoint> stayPoints) {
        ArrayList<SpatialTimeElement> spatialTimeElements = convertStaypointsToSpatialTimeElements(stayPoints);
        return new TimePinnedKmlCreator(outputFilename, spatialTimeElements);
    }
}
