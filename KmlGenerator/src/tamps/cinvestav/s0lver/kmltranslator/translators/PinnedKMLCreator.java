package tamps.cinvestav.s0lver.kmltranslator.translators;

import org.w3c.dom.Element;
import tamps.cinvestav.s0lver.kmltranslator.entities.SpatialTimeElement;
import tamps.cinvestav.s0lver.locationentities.GpsFix;
import tamps.cinvestav.s0lver.locationentities.StayPoint;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/***
 * This class is an implementation of a KmlFileCreator
 * It takes an ArrayList of SpatialTimeElement, and store it as kml file.
 * Can be seen in Google Earth
 */
public class PinnedKmlCreator extends KmlFileCreator {
    protected PinnedKmlCreator(String outputFilename, ArrayList<SpatialTimeElement> spatialTimeElements) {
        super(outputFilename, spatialTimeElements);
    }

    @Override
    public void create() throws ParserConfigurationException, TransformerException, FileNotFoundException {
        prepareDomPreamble();

        for (SpatialTimeElement spatialTimeElement : spatialTimeElements) {
            Element currentPlacemark = processSpatialTimeElement(spatialTimeElement);
            getRootElement().appendChild(currentPlacemark);
        }

        writeFile();
    }

    protected Element processSpatialTimeElement(SpatialTimeElement spatialTimeElement) {
        Element placemarkElement = dom.createElement("Placemark");

        Element pointElement = createPointElement(spatialTimeElement);
        Element extendedDataElement = createExtendDataElement(spatialTimeElement);

        placemarkElement.appendChild(extendedDataElement);
        placemarkElement.appendChild(pointElement);

        return placemarkElement;
    }

    private Element createPointElement(SpatialTimeElement spatialTimeElement){
        Element pointElement = dom.createElement("Point");

        String latitude = String.valueOf(spatialTimeElement.getLatitude());
        String longitude = String.valueOf(spatialTimeElement.getLongitude());
        String altitude = String.valueOf(spatialTimeElement.getAltitude());

        Element coordinates = dom.createElement("coordinates");
        coordinates.appendChild(dom.createTextNode(String.format("%s,%s,%s", longitude, latitude, altitude)));

        pointElement.appendChild(coordinates);
        return pointElement;
    }

    private Element createExtendDataElement(SpatialTimeElement spatialTimeElement) {
        Element extendedDataElement = dom.createElement("ExtendedData");

        Element dataLongitude = createDataElement("Longitude", String.valueOf(spatialTimeElement.getLongitude()));
        Element dataLatitude = createDataElement("Latitude", String.valueOf(spatialTimeElement.getLatitude()));
        Element dataAltitude = createDataElement("Altitude", String.valueOf(spatialTimeElement.getAltitude()));
        Element dataAccuracy = createDataElement("Accuracy", String.valueOf(spatialTimeElement.getAccuracy()));

        extendedDataElement.appendChild(dataLongitude);
        extendedDataElement.appendChild(dataLatitude);
        extendedDataElement.appendChild(dataAltitude);
        extendedDataElement.appendChild(dataAccuracy);

        String arrivalTS = dateFormatInKML.format(spatialTimeElement.getArrivalTime());

        if (spatialTimeElement.isStayPoint()){
            Element dataArrivalTime = createDataElement("Arrival time", arrivalTS);

            String departureTS = dateFormatInKML.format(spatialTimeElement.getDepartureTime());
            Element dataDepartureTime = createDataElement("Departure time", departureTS);

            extendedDataElement.appendChild(dataArrivalTime);
            extendedDataElement.appendChild(dataDepartureTime);
        }else{
            Element dataTimestamp = createDataElement("Timestamp", arrivalTS);
            extendedDataElement.appendChild(dataTimestamp);
        }

        return extendedDataElement;
    }

    public static PinnedKmlCreator createForGpsFixes(String outputFilename, ArrayList<GpsFix> gpsFixes){
        ArrayList<SpatialTimeElement> spatialTimeElements = convertGpsFixesToSpatialTimeElements(gpsFixes);
        return new PinnedKmlCreator(outputFilename, spatialTimeElements);
    }

    public static PinnedKmlCreator createForStaypoints(String outputFilename, ArrayList<StayPoint> stayPoints) {
        ArrayList<SpatialTimeElement> spatialTimeElements = convertStaypointsToSpatialTimeElements(stayPoints);
        return new PinnedKmlCreator(outputFilename, spatialTimeElements);
    }
}
