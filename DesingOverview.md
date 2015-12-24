# Introduction #
The application is divided into 3 projects
  * RealGraffiti - the android application code
  * RealGraffitiServer - the google app engine server code
  * RealGraffitiCommon - the common objects shared by the android client and the server

# Projects Overview #

## Activities ##
  * realgraffiti.android.activities holds all the Android activities
    * GraffitisLocationsMap - shows a map with the graffiti locations
    * ApplicationDemo - a demo to the independent applications components

## Data objects ##
  * realgraffiti.common.dataObjects holds the projects data objects
    * GraffitiLocationParameters - an object that holds all the parameters we can have that describes the location of the graffiti (GPS coordinates, device orientation, camera image descriptors, etc.)
    * Graffiti - holds all the information about a Graffiti - location parameters, image, db key, etc.

## Data access interface ##
  * realgraffiti.common.data.RealGraffitiData is an interface the defines the data access interface
```
public interface RealGraffitiData {
	boolean addNewGraffiti(Graffiti graffiti);
	Collection<Graffiti> getNearByGraffiti(GraffitiLocationParameters graffitiLocationParameters);
	byte[] getGraffitiImage(Long graffitiKey);
	byte[] getGraffitiWallImage(Long graffitiKey);
}
```

Some implementation to this interface are
  * realgraffiti.android.web.RealGraffitiDataProxy - Receives data from server
  * realgraffiti.android.web.RealGraffitiDataBufferdProxy - Similar to RealGraffitiDataProxy but polls the server every couple of seconds and returns the polled data instead of querying the server each time.
  * realgraffiti.android.data.RealGraffitiLocalData - holds data locally (for testing purposes)

## Graffiti Location Parameters generation ##

The GraffitiLocationParameters holds all the parameters we can have that describes the location of the graffiti (GPS coordinates, device orientation, camera image descriptors, etc.). Since this is a complex object another class that creates it is required.
  * GraffitiLocationParametersGenerator is the interface that defines this generator class
```
public interface GraffitiLocationParametesrGenerator {
	GraffitiLocationParameters getCurrentLocationParameters();
}
```
    * The current implementation is realgraffiti.android.data.GraffitiLocationParametersFakeGenerator that generates a random location parameters.
  * In order to quickly change the used implementation all over the application there is also a Factory that creates the generator: GraffitiLocationParametersGeneratorFactory. Therefore in order to create a GraffitiLocationParameters you need to :
```
GraffitiLocationParameters glp = GraffitiLocationParametersGeneratorFactory.
					getGaffitiLocationParametersGenerator().
					getCurrentLocationParameters();
```


## Maps ##
realgraffiti.android.map holds all the classes that relates to the applications maps
  * CurrentLocationOverlay - a map overlay that displays the current location of the users, and keeps it in the center of the map
  * GraffitiesLocationOverlay - a map overlay that displays the graffiti locations
  * GraffitiMiniMapView - the mini map view, shows the graffiti locations map in a small round mini map

## Server Access ##
  * realgraffiti.android.web holds all the server access objects
    * WebServiceClient - a simple web service client, can send HTTP request with parameters to a web service
    * GraffitiServerPoller - polls the server every few seconds for new graffities

## RealGraffiti Server side ##
**realgraffiti.server.data.RealGraffitiDataStore implements RealGraffitiData on the server. It accesses the data store in order to store, retrieve graffiti information**

### Servlets ###
The server has some java Servlets that serve as web services.
  * RealGraffitiDataServlet - a web service that can perform add/retrieve graffiti actions
  * ServerInfo - a web service that can provide information about the server

## FingerPaint ##
This activity allows to paint by finger over a given image.
The image location should be add to the Intent prior to excecution.
For example:

```
Intent myIntent = new Intent(ApplicationDemo.this, FingerPaintActivity.class);
//backgroundLocation is the location of the file
myIntent.putExtra(FingerPaintActivity.WALL_IMAGE_LOC, backgroundLocation);
startActivityForResult(myIntent, FINGER_PAINT_ACTIVITY);
```
The Finger painting is sent back from the FingerPaintActivity once the paint is saved (via the menu).
The location of the png file can be extracted at the onActivityResult function:
```
//data is the returned Intent
String newText = data.getStringExtra(FingerPaintActivity.PAINTING_LOC);
```