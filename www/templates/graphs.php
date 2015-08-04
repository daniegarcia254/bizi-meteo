<!DOCTYPE html>
<html>
<head>
	<meta charset=utf-8 />
	<title>BiZi-Meteo Graphs && Stats</title>
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <script src="../js/chart.min.js"></script>
        <script src="../js/chart.extension.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>
<body>

<div class="navbar navbar-inverse">
    <div class="container-fluid">
        <ul class="nav nav-pills">
            <li role="presentation"><a class="navbar-brand" href="/index.php">Inicio</a></li>
            <li role="presentation"><a class="navbar-brand" href="/index.php/graphs">Graficas</a></li>
        </ul>
    </div>
</div>


<?php
  include_once "get_stats_data.php";
  
  // Get data from Parse.com and calculate the needed valors
  $raw_data=get_parse(array('limit' => '10000'));
  $json_data=json_decode($raw_data);
  $result=$json_data->{'results'};

  $dias = array();
  foreach($result as $evento){
    if(!isset($dias[substr($evento->{'updatedAt'},0,10)])) $dias[substr($evento->{'updatedAt'},0,10)]=0;
    $dias[substr($evento->{'updatedAt'},0,10)]++;
  }

  foreach($dias as $k => $p){
    $d[]='"'.$k.'"';
    $v[]=$p;
  }

  $ips = array();
  $eventos_totales=0;
  foreach($result as $evento){
    if(!isset($ips[$evento->{'IP'}])) $ips[$evento->{'IP'}]=0;
    $ips[$evento->{'IP'}]++;
    $eventos_totales++;
  }

  foreach($result as $evento){
    if(!isset($formato[$evento->{'formato'}])) $formato[$evento->{'formato'}]=0;
    $formato[$evento->{'formato'}]++;
  }

  foreach($formato as $f => $g){
    if (!empty($f)){
      $estilo_r[]='"'.$f.'"';
      $uso_r[]=$g;
    }
  }



  // BiZi stations graph <-> Times each Bizi station has been selected as destiny in a route calculation
  $raw_data=get_parse(array('where' => '{"tipo":"rutaBizi"}','limit' => '1000'));
  $json_data=json_decode($raw_data);
  $result=$json_data->{'results'};

  $dias = array();
  foreach($result as $evento){
    if(!isset($estaciones[$evento->{'destino'}])) $estaciones[$evento->{'destino'}]=0;
    $estaciones[$evento->{'destino'}]++;
  }

  foreach($estaciones as $estacion => $veces){
    if (!strpos($estacion, ',')==0){
      $estacion = substr($estacion, 0, strpos($estacion, ','));
      $estacion_r[]='"'.$estacion.'"';
      $veces_r[]=$veces;
    }
  }

?>

<!-- Graph: Events by date  -->
<div class="grafica">
Eventos por fecha<br>
<canvas id="chart1" width="800" height="300"></canvas>
</div>

<!-- Graph: Nº of meteo-info request by province  -->
<div class="grafica">
Número de consultas por provincia<br>
<canvas id="chart2" width="300" height="300"></canvas>
</div>

<!-- Graph: Nº of route calculations requests by BiZi station as destiny  -->
<div class="grafica">
Número de consultas por estacion Bizi<br>
<canvas id="chart3" width="600" height="300"></canvas>
</div>

<!-- Interact styles (JSON/XML) and its use -->
<div class="grafica">
Estilos de interacción y su uso<br>
<canvas id="chart4" width="300" height="300"></canvas>
</div>
<br>

<!-- More data of interest -->
<div><table class="table">
    <tbody>
    <tr>
        <th colspan="2">Otros datos de interés</th>
    </tr>

    <tr>
    	<!-- Unique IP's that have visited the web-page -->
        <td>IPs únicas que han visitado la web</td><td><?php print count($ips); ?> </td>
    </tr><tr>
    </tr><tr>
    	<!-- Total nº of events registered (users total interactions with the page) -->
        <td>Número total de eventos registrados</td><td><?php print $eventos_totales; ?></td>
    </tr>
    </tbody></table>
</div>

<script>
//CHARTS DATA && STYLES

//Data && style of the Line chart with nº of events registered by date
var data = {
    labels: [<?php print(implode(',',$d)); ?>],
    datasets: [
        {
            fillColor: "rgba(151,187,205,0.2)",
            strokeColor: "rgba(151,187,205,1)",
            pointColor: "rgba(151,187,205,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            pointHighlightStroke: "rgba(151,187,205,1)",
            data: [<?php print(implode(',',$v)); ?>]

        }
    ]
};

//Data && style of the Pie chart with route calculation requests by Bizi-station as destiny
var data3 = {
    labels: [<?php print(implode(',',$estacion_r)); ?>],
    datasets: [
        {
            fillColor: "rgba(151,187,205,0.2)",
            strokeColor: "rgba(151,187,205,1)",
            data: [<?php print(implode(',',$veces_r)); ?>]

        }
    ]
};

//Data && style of the Pie chart with interact styles by use
var data4 = {
    labels: [<?php print(implode(',',$estilo_r)); ?>],
    datasets: [
        {
            fillColor: "orange",
            strokeColor: "orangered",
            data: [<?php print(implode(',',$uso_r)); ?>]

        }
    ]
};


var highlight = ['coral', 'lightgreen', 'lightblue', 'yellow'];
var color = ['red', 'green', 'blue', 'orange'];

//Data && style of the Pie chart with meteo-info requests by province
var data2 = [
<?php
  include_once "provincias.php";
  include_once "get_data.php";
  $raw_data=get_parse(array('where' => '{"tipo":"prediccion"}','limit' => '1000'));
  $json_data=json_decode($raw_data);
  $result=$json_data->{'results'};

  foreach($result as $evento){
    if(!isset($provincia[$evento->{'provincia'}])) $provincia[$evento->{'provincia'}]=0;
    $provincia[$evento->{'provincia'}]++;
  }

  $nc=0;
  foreach($provincia as $k => $p){
    $label=$nprovincia[$k]; //"Etiqueta";
    $value=$p;
    echo("{ value: " . $value . ",\n");
    echo("highlight: highlight[" . $nc . "],\ncolor: color[" . $nc . "],\n");
    echo('label: "' . $label . "\"\n},\n");

    $nc=$nc+1;
    $nc=($nc%4);
  }
?>

]


//CHARTS DEFINITIONS
Chart.defaults.global.responsive = true;

//Line chart with nº of events registered by date
var ctx1 = document.getElementById("chart1").getContext("2d");
var myLineChart = new Chart(ctx1).Line(data, {
    bezierCurve: true,
    responsive:  false,
    
});

// Pie chart with meteo-info requests by province
var ctx2 = document.getElementById("chart2").getContext("2d");
var myDoughnutChart = new Chart(ctx2).Doughnut(data2,{
    responsive:  false,
});

// Pie chart with route calculation requests by Bizi-station as destiny
var ctx3 = document.getElementById("chart3").getContext("2d");
var bizi = new Chart(ctx3).Bar(data3,{
    responsive:  false,
});

// Pie chart with interact styles by use
var ctx4 = document.getElementById("chart4").getContext("2d");
var bizi = new Chart(ctx4).Bar(data4,{
    responsive:  false,
});
</script>
 
</body>
</html>
