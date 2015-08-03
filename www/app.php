<?php

//Inicializamos el SDK del servicio Parse
require 'vendor/autoload.php';
use Parse\ParseClient;
use Parse\ParseObject;
ParseClient::initialize($KEY_FROM_PARSE_ACCOUNT1,$KEY_FROM_PARSE_ACCOUNT2,$KEY_FROM_PARSE_ACCOUNT3);

//Establecer el time-zone
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

// Twig View Rendering
// -----------------------------------------------------------------------------
// Setup our renderer and add some global variables
$app->view = new \Slim\View();
$app->view->setTemplatesDirectory('templates');

// Welcome Page
// -----------------------------------------------------------------------------
// Renderiza la página de inicio
$app->get('/', function () use ($app) {
    $app->render('main.php');
});

$app->post('/', function() use ($app) {
});

$app->get('/graphs', function () use ($app) {
	$app->render('graphs.php');
});

//Según el formato elegido (JSON, XML) --> coger los datos de las estaciones de la API de Bizis Zaragoza y devolverlos
$app->get('/estaciones-bizi/:formato', function($formato) use ($app){
    $ch = curl_init();
    $headers = array(
        'Content-Type: application/json',
    );
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

    // Fetch and return content, save it.
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

//Calcular ruta de un punto origen a una estación BiZi destino
$app->post('/calcular-ruta', function() use ($app){

    //Leemos los datos del formulario
    $formato = $app->request()->post("formatoDatos");
    $estacion = $app->request()->post("estacionBizi");


    //Pedimos a la API de Bizis Zaragoza las coordenadas de la estación de Bizi seleccionada en el formulario
    $ch = curl_init();
    $headers = array(
        'Content-Type: application/json',
    );
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

    // Fetch and return content, save it.
    $raw_data = curl_exec($ch);
    curl_close($ch);

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

    $response = $app->response();
    $response['Content-Type'] = 'application/json';
    $response->status(200);
    $response->body(json_encode(array(
        'lat' => $lat,
        'long' => $long
    ), true));
});

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

$app->post('/prediccion/:municipio/:interaccion', function($municipio, $interaccion) use ($app){

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

$app->post('/prediccion', function() use ($app){

    /*// First idea was through SOAP service, not working
    ini_set("soap.wsdl_cache_enabled", 0);
    $client = new SoapClient('http://localhost:8080/axis/services/Generar2columnas?wsdl', array('cache_wsdl' => WSDL_CACHE_NONE));
    $result = $client->serviceMethod( "http://www.aemet.es/xml/municipios/localidad_" . $code . ".xml" );
    print_r($result);*/

    $interaccion = $app->request()->post('interaccion');
    $municipio = $app->request()->post('municipio');

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
