<?php

require 'vendor/autoload.php';

//Init the Parse service SDK
use Parse\ParseClient;
use Parse\ParseObject;
ParseClient::initialize($KEY_FROM_PARSE_ACCOUNT1,$KEY_FROM_PARSE_ACCOUNT2,$KEY_FROM_PARSE_ACCOUNT3);

//Establish time-zone
date_default_timezone_set("Europe/Madrid");

// Error Handler for any uncaught exception
// -----------------------------------------------------------------------------
// This can be silenced by turning on Slim Debugging. All exceptions thrown by
// our application will be collected here.
$app->error(function (\Exception $e) use ($app) {
    $app->render('error.php', array(
        'message' => $e->getMessage()
    ), 500);
});

// View Rendering
// -----------------------------------------------------------------------------
// Setup our renderer and add some global variables
$app->view = new \Slim\View();
$app->view->setTemplatesDirectory('templates');

// Welcome Page
// -----------------------------------------------------------------------------
// Renders the home-main page
$app->get('/', function () use ($app) {
    $app->render('main.php');
});

$app->post('/', function() use ($app) {
});

//Renders the graphs/stats page
$app->get('/graphs', function () use ($app) {
	$app->render('graphs.php');
});

//GET BIZIS INFO
//Get data from BiZis Zaragoza API. [formato] indicate the format of the recolected data: JSON or XML
$app->get('/estaciones-bizi/:formato', function($formato) use ($app){
    $ch = curl_init();
    $headers = array('Content-Type: application/json');
    $fields = array(
        'srsname' => 'wgs84',
        'start' => 0,
        'rows' => 200,
        'distance' => 500
    );

    $url = $formato == 'json' ?
        'http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta.json?' . http_build_query($fields)
        : 'http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta.xml?' . http_build_query($fields);
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, false);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_HEADER, 0);            // No header in the result
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Return, do not echo result

    $raw_data = curl_exec($ch);
    curl_close($ch);

    $response = $app->response();
    $response['Content-Type'] = 'application/json';
    $response->status(200);

    if ($formato == 'json') {
        $response->body(json_encode($raw_data, true));
    } else {
        $xml = new SimpleXMLElement($raw_data);
        $response->body(json_encode($xml, true));
    }
});

//GET COORDINATES OF BIZI-STATION
//For a route calculation, gets and returns the coordinates the BiZi destiny station of the route
$app->post('/calcular-ruta', function() use ($app){
    //Get data from the formulary
    $formato = $app->request()->post("formatoDatos");  //Data format
    $estacion = $app->request()->post("estacionBizi"); //BiZi destiny station

    //Get from the BiZis Zaragoza API the coordinates of the BiZi destiny station
    $ch = curl_init();
    $headers = array('Content-Type: application/json');
    $fields = array(
        'fl' => 'geometry',
        'srsname' => 'wgs84'
    );

    $url = $formato == 'json' ?
        'http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta/' . $estacion . '.json?' . http_build_query($fields)
        : 'http://www.zaragoza.es/api/recurso/urbanismo-infraestructuras/estacion-bicicleta/' . $estacion . '.xml?' . http_build_query($fields);
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, false);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_HEADER, 0);            // No header in the result
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true); // Return, do not echo result

    $raw_data = curl_exec($ch);
    curl_close($ch);

    //Get latitude and longitude from the coordinates	
    if ($formato == 'json') {
        $data = json_decode($raw_data, true);
        $lat = $data['geometry']['coordinates'][1];
        $long = $data['geometry']['coordinates'][0];
    } else {
        $xml = new SimpleXMLElement($raw_data);
        $coordenadasBizi =  $xml->geometry->coordinates;
        $coordenadas = explode(",",$coordenadasBizi);
        $lat = $coordenadas[1];
        $long = $coordenadas[0];
    }

    //Return latitude && longitude as an array
    $response = $app->response();
    $response['Content-Type'] = 'application/json';
    $response->status(200);
    $response->body(json_encode(array(
        'lat' => $lat,
        'long' => $long
    ), true));
});

//SAVE STATS IN PARSE.COM CLOUD DATABASE
$app->post('/control-estadistico/:accion', function($accion) use ($app){
    
    $data = $app->request()->post();
    
    $infoObject = ParseObject::create("Info_Object");
    $infoObject->set("IP", getClientIP());
    $infoObject->set("tipo", $accion);
    $infoObject->set("exito", $data['exito'] === 'true' ? true : false);
    $infoObject->set("origen", $data['origen']);
    $infoObject->set("destino", $data['destino']);
    $infoObject->set("desplazamiento", $data['desplazamiento']);
    $infoObject->set("provincia", $data['provincia']);
    $infoObject->set("municipio", $data['municipio']);
    $infoObject->set("formato", $data['formato']);
    $infoObject->save();
});

//GET THE METEO INFO
//[municipio] is the city code && [interaccion] is the data format to work with (JSON or XML)
$app->post('/prediccion/:municipio/:interaccion', function($municipio, $interaccion) use ($app){

    //Executes the jars that return directly an HTML with the meteo-info ($PATH_TO_JARS is where you have put your jars)
    if ($interaccion == "json"){
        exec('cd $PATH_TO_JARS ; java -jar Print2columnas.jar http://www.aemet.es/xml/municipios/localidad_' . $municipio . '.xml ' . $interaccion ,$result);
    }
    else {
        exec('cd $PATH_TO_JARS ; java -jar Print2columnas_xml.jar http://www.aemet.es/xml/municipios/localidad_' . $municipio . '.xml ' . $interaccion ,$result);
    }

    $response = $app->response();
    $response->status(200);
    $response->body(implode($result));
});

//GET THE METEO INFO (this time parameters are not in the query, but in the form content)
$app->post('/prediccion', function() use ($app){

    $interaccion = $app->request()->post('interaccion');
    $municipio = $app->request()->post('municipio');
    
    //Executes the jars that return directly an HTML with the meteo-info ($PATH_TO_JARS is where you have put your jars)
    if ($interaccion == "json"){
        exec('cd $PATH_TO_JARS ; java -jar Print2columnas.jar http://www.aemet.es/xml/municipios/localidad_' . $municipio . '.xml ' . $interaccion ,$result);
    }
    else {
        exec('cd $PATH_TO_JARS ; java -jar Print2columnas_xml.jar http://www.aemet.es/xml/municipios/localidad_' . $municipio . '.xml ' . $interaccion ,$result);
    }

    $response = $app->response();
    $response->status(200);
    $response->body(implode($result));

});

//GETS the client IP
function getClientIP() {

    if (isset($_SERVER)) {

        if (isset($_SERVER["HTTP_X_FORWARDED_FOR"]))
            return $_SERVER["HTTP_X_FORWARDED_FOR"];

        if (isset($_SERVER["HTTP_CLIENT_IP"]))
            return $_SERVER["HTTP_CLIENT_IP"];

        return $_SERVER["REMOTE_ADDR"];
    }

    if (getenv('HTTP_X_FORWARDED_FOR'))
        return getenv('HTTP_X_FORWARDED_FOR');

    if (getenv('HTTP_CLIENT_IP'))
        return getenv('HTTP_CLIENT_IP');

    return getenv('REMOTE_ADDR');
}

/* End of file appBizis.php */
