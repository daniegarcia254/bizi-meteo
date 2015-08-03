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
      CURLOPT_HTTPHEADER => array('X-Parse-Application-ID: XXXX',
                                  'X-Parse-REST-API-Key: XXXX')
    )
  );
  $resp = curl_exec($curl);
  curl_close($curl);
  return $resp;
}
