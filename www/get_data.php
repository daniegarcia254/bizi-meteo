<?php

// http://tallerdelola.com/stw/graphs.php?tipo=prediccion
// infoBizi
// rutaBizi


function get_parse($params){
  $url = "https://api.parse.com/1/classes/Info_Object";
  $curl = curl_init();
  $params = $params;
  $params = http_build_query($params);
  curl_setopt_array($curl,
    array(
      CURLOPT_RETURNTRANSFER => 1,
      CURLOPT_URL => $url . '?' . $params,
      CURLOPT_USERAGENT => 'UserAgentString',
      CURLOPT_HTTPHEADER => array('X-Parse-Application-ID: 80N6VQEMuBhOGV4F8TfzqnXnXlrEx9bSOQqhrMM6',
                                  'X-Parse-REST-API-Key: 2F3RBbLIBLppo6EVVOZ52bTPbcdAIMJMGbKqFq4s')
    )
  );
  $resp = curl_exec($curl);
  curl_close($curl);
  return $resp;
}


/*if (isset($_GET['tipo'])) {
  $params = array('where' => '{"tipo":"' . $_GET['tipo'] . '"}');
}
else{
  die;
}

$resp=get_parse($params);

header('Content-Type: application/json');
$json = json_decode($resp);
print(json_encode($json,JSON_PRETTY_PRINT));
*/

// Obtener predicciones
// curl -X GET -H "X-Parse-Application-ID: 80N6VQEMuBhOGV4F8TfzqnXnXlrEx9bSOQqhrMM6" -H "X-Parse-REST-API-Key: 2F3RBbLIBLppo6EVVOZ52bTPbcdAIMJMGbKqFq4s"  -G --data-urlencode 'where={"tipo":"prediccion"}' https://api.parse.com/1/classes/Info_Object

