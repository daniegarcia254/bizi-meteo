# BiZi-Meteo
Web-app that provides info to the user about the BiZi stations in Zaragoza && about the weather in any city, town or village of Spain.

<p>The app uses the <a href="https://developers.google.com/maps/documentation/javascript/" target="_blank">Google Maps API</a> in order to show in te map the best route from any point to the desire BiZi station and the <a href="http://www.zaragoza.es/docs-api/" target="_blank">Ayuntamiento de Zaragoza API</a> in order to get all the information related to the BiZi stations (current status, bicycles available, address...).</p>

<p>The weather information is extracted from the AEMET web page, through the open XML files that they provide.</p>

<p>The user can choose in any case, if the application must work with the information in JSON or XML format.</p>
Used technologies for the development:
  <ul>
    <li>Frontend: HTML5/CSS3, JQuery, ChartJS</li>
    <li>Backend:
      <ul>
        <li>REST API between server and client: Slim PHP Framework</li>
        <li>BiZi's info and generated stats are also managed through Slim PHP Framework with PHP5</li>
        <li>The Meteo info, however it's handled with Java and some libaries (Freemarker, Gson, JDOM...)</li>
			</ul>
		</li>
		<li>DB:
			<ul>
				<li>Parse.com: cloud database to store the stats info</li>
				<li>MariaDB 10 to store the Meteo info from AEMET</li>
			</ul>
		</li>
	</ul>

## Directories
* [java-meteo](java-meteo): Java code for compiling and generate the necessary _jars_ in order to get the meteo info
* [www](www): Web-app
  * _app.php_: REST API with PHP Slim Framework
  * _get_stats_data.php_: Get data from the Parse.com cloud database for painting the graphs
  * _provincias.json_: File with the information (code && name) of the Spain cities, towns && villages
  * _templates/_ --> views (main && graphs/stats)
  * _css/_ --> styles
  * _img/_ --> all the necessary images for the meteo-info table

## Installation && Configuration
* First of all, you should compile the java code in the [java-meteo](java-meteo) directory, this command should help:
      
      `javac -d bin src/*.java`
* Then, run the [mkjars.sh](java-meteo/mkjars.sh) script in order to generate the necessary _jars_:

      ``sh mkjars.sh`
      
    Two _jars_ should be generated!      
* In the [app.php](www/app.php) file, replace the var `$PATH_TO_JARS` with the path where you've generated and put the _jars_.
* In order to store the meteo info, you must create a database with MySQL, and replace the vars `$DB`,`$PASSWORD`,`$USER` in the file [Mysql.java](java-meteo/src/Mysql.java) that handles the connection with the database.
* You will also need an account on the [Parse.com](https://parse.com/) for a cloud database where the stats are stored.<br>
  When you create the account, you are given with a bunch of _API keys_ that you need to use either for store data or get data. You must:
  * Replace the vars `$APPLICATION_ID`,`$REST_API_KEY`,`$MASTER_KEY` in the [app.php](www/app.php) file
  * Replace the vars `$APPLICATION_ID`,`$REST_API_KEY` in the [get_stats_data.php](www/get_stats_data.php) file

* Once you're logged in your _Parse.com_ account and you've created and app, you must go to the _Core_ section and in the _Data_ section create a new class _*Info_Object*_ with the next attributes (columns):
  * IP: String
  * tipo: String
  * exito: Boolean
  * origen: String
  * destino: String
  * desplazamiento: String
  * provincia: String
  * municipio: String
  * formato: String

* Finally, you need to download and install the [PHP Slim Framework](http://www.slimframework.com/) by putting the Slim/ folder in the [www](www) directory

## DEMO
If you don't have the guts to go through all this process, just enjoy the demo: [BiZi-Meteo](http://www.danigarcia-dev.com/stw/)
