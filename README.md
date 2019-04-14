## jDCS-Viewer
A simple viewer for some D-Link IP camera, specifically the DCS-xxxx series et simila.

With the file model mechanism, however, you can use this software even with other brand (or model) of ipcam.

### Introduction
Other than viewing a realtime stream from the IP camera, this program is capable to record clip/save screen shot and use
advance function like panning (if supported), NV toggle and audio toggle.

In future realease, will be added a face highlight function, an audio threshold and a movement detection notification.


### Getting Started
It's important to know the IP address of the camera (better if is it static) or the URL (but in that case you probably need some NAT configuration),
the model name (some time even only the series is sufficent) and username/password.

### Use
Simply use the Connection/Settings menu for the first configuration, then hit Save.
Again, open the Connection menu and choose Connect to enstablish the connection to the ipcam.

If everything is OK, you should see the stream in the main window of the program.

### Error handling
For now no log is created, nor terminal output is provided.

Future update should fix this.

### Model
Because of the circumstances, only the DCS-5009L is officially supported by this program.

The model specifications are provided by xml file, one for every ip camera.

You can add more model file simply creating a **model** directory in the same directory of the program (jar file), then creating a xml file with this structure:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<ipcamdata>
	<model></model>
	<version></version>
	<author></author>
	
	<video>
		<URLstream></URLstream>
		<URLimage></URLimage>
	</video>
	
	<audio>
		<URLstream></URLstream>
	</audio>
	
	<panning>
		<pannable>true</pannable>
		<URLpanUp></URLpanUp>
		<URLpanDown></URLpanDown>
		<URLpanLeft></URLpanLeft>
		<URLpanRight></URLpanRight>
		
		<URLpreset></URLpreset>
	</panning>
	
	<command>
		<NVtogglable>true</NVtogglable>
		<URLNVon></URLNVon>
		<URLNVoff></URLNVoff>
		<URLNVauto></URLNVauto>
		
		<MotionTogglable>true</MotionTogglable>
		<URLmotionOn></URLmotionOn>
		<URLmotionOff></URLmotionOff>
	</command>
	
</ipcamdata>
```

You have to use the following variabile when add the URL:

- **%IP%** for the ip/url provided by the Settings interface
- **%DEGREE%** for the degree of movement choosed by control panel whe connected
- **%PRESET%** for the integer number which point to a specific default location.


_As example, this is the file model bundled with the application, for the DCS-5009L:_

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xml>
<ipcamdata>
	<model>DCS-5009L</model>
	<version>1.0</version>
	<author>Pier Riccardo Monzo</author>
	
	<video>
		<URLstream>http://%IP%/mjpeg.cgi</URLstream>
		<URLimage>http://%IP%/image.jpg</URLimage>
	</video>
	
	<audio>
		<!-- Funziona (nel senso che è effettivamente un flusso audio) ma nemmeno VLC riesce ad eseguirlo. 
		Sostituire "dgaudio" con "audio" permette un flusso tramite VLC, ma con 7/8 secondi di ritardo -->
		<URLstream>http://%IP%/audio.cgi</URLstream>
	</audio>
	
	<panning>
		<pannable>true</pannable>
		<!-- Pan è SX/DX, mentre Tilt è UP/DOWN
			Metto ad entrambi %DEGREE%, ma si potrebbero usare gradi differenti,
			come d'altra parte fa la GUI WEB -->
		<URLpanUp>http://%IP%/pantiltcontrol.cgi?PanSingleMoveDegree=%DEGREE%&amp;TiltSingleMoveDegree=%DEGREE%&amp;PanTiltSingleMove=1</URLpanUp>
		<URLpanDown>http://%IP%/pantiltcontrol.cgi?PanSingleMoveDegree=%DEGREE%&amp;TiltSingleMoveDegree=%DEGREE%&amp;PanTiltSingleMove=7</URLpanDown>
		<URLpanLeft>http://%IP%/pantiltcontrol.cgi?PanSingleMoveDegree=%DEGREE%&amp;TiltSingleMoveDegree=%DEGREE%&amp;PanTiltSingleMove=3</URLpanLeft>
		<URLpanRight>http://%IP%/pantiltcontrol.cgi?PanSingleMoveDegree=%DEGREE%&amp;TiltSingleMoveDegree=%DEGREE%&amp;PanTiltSingleMove=5</URLpanRight>
		
		<URLpreset>http://%IP%/pantiltcontrol.cgi?PanTiltPresetPositionMove=%PRESET%</URLpreset>
	</panning>
	
	<command>
		<NVtogglable>true</NVtogglable>
		<URLNVon>http://%IP%/daynight.cgi?DayNightMode=3&amp;ConfigReboot=no</URLNVon>
		<URLNVoff>http://%IP%/daynight.cgi?DayNightMode=2&amp;ConfigReboot=no</URLNVoff>
		<URLNVauto>http://%IP%/daynight.cgi?DayNightMode=0&amp;ConfigReboot=no</URLNVauto>
		
		<MotionTogglable>true</MotionTogglable>
		<URLmotionOn>http://%IP%/motion.cgi?MotionDetectionEnable=1&amp;ConfigReboot=no</URLmotionOn>
		<URLmotionOff>http://%IP%/motion.cgi?MotionDetectionEnable=0&amp;ConfigReboot=no</URLmotionOff>
	</command>
	
</ipcamdata>
```

**Be advise**: you have to replace all **&** with **\&amp;** within the URL, because otherwise the engine in charge of reading the xml file will throw an exception.