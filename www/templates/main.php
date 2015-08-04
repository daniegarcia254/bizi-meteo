<!DOCTYPE html>
<html>
<head>
    <title>Trabajo STW - Bizis</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/tabla2c.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
</head>

<body>
    <!--  MENU BAR -->
    <div class="navbar navbar-inverse">
        <div class="container-fluid">
            <ul class="nav nav-pills">
                <li role="presentation"><a class="navbar-brand" href="/">Inicio</a></li>
                <li role="presentation"><a class="navbar-brand" href="index.php/graphs">Graficas</a></li>
            </ul>
        </div>
    </div>
    <!-- BiZis form container  -->
    <div id="contenedor-bizis">
        <fieldset id="bizis-fieldset">
            <legend id="bizis-legend" class="label label-info">Llegar a una estación Bizi</legend>
                <div class='form-group'>
                    <input id='input-origen' name='origen-bizi' type='text' class='form-control' placeholder='Dirección de comienzo' required>
                </div>
                <div class='form-group'>
                    <select id='formato-bizis-select' name='formato-bizi' class='form-control' required onchange='getEstaciones(this.value);'>
                        <option value="" disabled selected> Elija un formato</option>
                        <option value='json'>JSON</option>
                        <option value='xml'>XML</option>
                    </select>
                </div>
                <div class='form-group'>
                    <select id='estaciones-bizi' name='estacion-bizi' class='form-control' onselect='' required>
                        <option value="" disabled selected>Estación BiZi</option>
                    </select>
                </div>
                <div class='form-group'>
                    <select id='modo-desplazamiento' name='modo-desplazamiento' class='form-control' onselect='' required>
                        <option value="" disabled selected>Modo desplazamiento</option>
                        <option value='WALKING'>A pie</option>
                        <option value='TRANSIT'>Transporte urbano</option>
                        <option value='DRIVING'>En coche</option>
                    </select>
                </div>
                <input id='buscar-ruta' type='submit' class='btn btn-primary' value="Buscar ruta" onclick="getRuta()"/>
        </fieldset>
	<div id="directions-panel"></div>
    </div>
    <!-- End of BiZis FORM container  -->
    
    <!--  MAP CONTAINER  -->
    <div id="contenedor-mapa">
        <div id="googleMap"></div>
    </div>
    <!--  End of MAP CONTAINER  -->
    
    <!--  METEO INFO PREDICTION container  -->
    <div id="contenedor">
        <div id="prediccion">
            <?php
            	//Default: shows Zaragoza meteo info 
	   	$code=50297;
            	$provincia=50;
                $municipio=$code;
                $interaccion='xml';
            ?>
        </div>
	<!-- Selectors of "provincia" and city -->
        <div id="selectores" class="form-group">
            <form method="post" id="prediccion-form">
               	 <label id="provincia-label">Provincia:</label>
		 <select id="provincia" class="form-control" name="provincia" onChange="setmunicipio(this.value);">
                    <option value="">Provincia</option>
                    <option value="15">A coru&#241;a</option>
                    <option value="1">&#193;lava</option>
                    <option value="2">Albacete</option>
                    <option value="3">Alicante</option>
                    <option value="4">Almer&#237;a</option>
                    <option value="33">Asturias</option>
                    <option value="5">&#193;vila</option>
                    <option value="6">Badajoz</option>
                    <option value="7">Baleares</option>
                    <option value="8">Barcelona</option>
                    <option value="9">Burgos</option>
                    <option value="10">C&#225;ceres</option>
                    <option value="11">C&#225;diz</option>
                    <option value="39">Cantabria</option>
                    <option value="12">Castell&#243;n</option>
                    <option value="51">Ceuta</option>
                    <option value="13">Ciudad Real</option>
                    <option value="14">C&#243;rdoba</option>
                    <option value="16">Cuenca</option>
                    <option value="99">Extranjero</option>
                    <option value="17">Girona</option>
                    <option value="18">Granada</option>
                    <option value="19">Guadalajara</option>
                    <option value="20">Guip&#250;zcoa</option>
                    <option value="21">Huelva</option>
                    <option value="22">Huesca</option>
                    <option value="23">Ja&#233;n</option>
                    <option value="26">La rioja</option>
                    <option value="35">Las palmas</option>
                    <option value="24">Le&#243;n</option>
                    <option value="25">Lleida</option>
                    <option value="27">Lugo</option>
                    <option value="28">Madrid</option>
                    <option value="29">M&#225;laga</option>
                    <option value="52">Melilla</option>
                    <option value="30">Murcia</option>
                    <option value="31">Navarra</option>
                    <option value="32">Ourense</option>
                    <option value="34">Palencia</option>
                    <option value="36">Pontevedra</option>
                    <option value="37">Salamanca</option>
                    <option value="38">Santa cruz de tenerife</option>
                    <option value="40">Segovia</option>
                    <option value="41">Sevilla</option>
                    <option value="42">Soria</option>
                    <option value="43">Tarragona</option>
                    <option value="44">Teruel</option>
                    <option value="45">Toledo</option>
                    <option value="46">Valencia</option>
                    <option value="47">Valladolid</option>
                    <option value="48">Vizcaya</option>
                    <option value="49">Zamora</option>
                    <option value="50">Zaragoza</option>
                </select>
                <br>
		<label id="municipio-label">Municipio:</label>
                <select name="municipio" class="form-control" id="municipio" onchange="getPrediccion()">
                    <option value="">Municipio</option>
                </select>
                <br>
		<label id="interaccion-label">Formato datos:</label>
                <select name="interaccion" class="form-control" id="interaccion" onchange="getPrediccion()">
                    <option value="xml">XML</option>
                    <option value="json">JSON</option>
                </select>

            </form>
        </div>
    </div>
    <!-- End of METEO INFO PREDICTION container  -->

<!-- JS libraries load  -->
<script src='https://maps.googleapis.com/maps/api/js?v=3.exp&amp;signed_in=true&amp;libraries=places'></script>
<script src="js/jquery.min.js"></script>
<script src="js/municipio.js"></script>

<!-- Depending on the "provincia" selected, search for the cities of that "provincia" and fills the city selector  -->
<script>
    var element = document.getElementById('provincia');
    element.value = '<?php print $provincia; ?>';
    setmunicipio('<?php echo $provincia; ?>','<?php print $municipio; ?>');
    var element = document.getElementById('interaccion');
    element.value = "<?php print $interaccion; ?>";
</script>

<!-- AJAX calls && interaction with the Google Maps Javascript API -->
<script>
    var map;
    var directionsDisplay;
    var geocoder;
    var directionsService = new google.maps.DirectionsService();

    //When the document is loaded --> Gets the Zaragoza meteo info by default
    $(document).ready(function(){
        $.post("index.php/prediccion/50297/xml", function(data){
            if (data.length == 0){
                $('#prediccion').html("<p>Error al conseguir la predicción</p>");
            } else {
                $('#prediccion').html(data);
            }
        });
    });

    //Inits the Map && the autocomplete field  -->  Google Maps JS services
    function initialize() {
        //Inits autocmplete from Google for the origin field
        var input = document.getElementById('input-origen');
        var options = {
            componentRestrictions: {country: 'es'}
        };
        var autocomplete = new google.maps.places.Autocomplete(input, options);

        //Inits map
        directionsDisplay= new google.maps.DirectionsRenderer();
        geocoder = new google.maps.Geocoder();
        var mapProp = {
            center:new google.maps.LatLng(41.656006, -0.883131),
            zoom:14,
            mapTypeId:google.maps.MapTypeId.ROADMAP
        };
        map=new google.maps.Map(document.getElementById("googleMap"), mapProp);
        directionsDisplay.setMap(map);
	directionsDisplay.setPanel(document.getElementById("directions-panel"));
	getEstaciones("json");
    }
    google.maps.event.addDomListener(window, 'load', initialize);


    //GET all the existing BiZi stations
    function getEstaciones(value){
        $.get("index.php/estaciones-bizi/"+value, function(data){

            var select_estaciones = $('#estaciones-bizi');

            //Add every BiZi station to the destiny select field && to the map as Markers
            //Working with data in JSON format
            if (value == 'json'){
                var estaciones = JSON.parse(data);
                estaciones.result.forEach(function(item){
                    select_estaciones.append(new Option(item.title, item.id));

                    var marker = new google.maps.Marker({
                        position: new google.maps.LatLng(item.geometry.coordinates[1], item.geometry.coordinates[0]),
                        icon: item.icon
                    });
                    var contentString = "<p id='dir'>" + item.title + "</p><p>" + item.description + "</p>";
                    var infowindow = new google.maps.InfoWindow({
                        content: contentString
                    });
                    marker.setMap(map);
                    google.maps.event.addListener(marker, 'click', function(mark) {
                        infowindow.open(map,marker); //Añadir información al Marker

                        //Clicking on a Map Marker --> Reg as a stat in Parse.com cloud database
                        geocoder.geocode( { 'latLng': this.position}, function(results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                $.post("index.php/control-estadistico/infoBizi", {exito: true, origen: results[0].formatted_address, destino: '', desplazamiento: '', provincia: '', municipio: '', formato: ''});
                            } else {
                                $.post("index.php/control-estadistico/infoBizi", {exito: true, origen: '', destino: '', desplazamiento: '', provincia: '', municipio: '', formato: ''});
                            }
                        });
                    });
                });
	    //Working with data in XML format
            } else {
                data.result.estacion.forEach(function(item){
                    select_estaciones.append(new Option(item.title, item.id));

                    var coordenadas = item.geometry.coordinates.split(",");

                    var marker = new google.maps.Marker({
                        position: new google.maps.LatLng(coordenadas[1], coordenadas[0]),
                        icon: item.icon
                    });
                    var lastUpdate = new Date(item.lastUpdated);
                    var contentString = "<p id='dir'>" + item.title + "</p>" +
                        "<p><ul>" +
                        "<li>Estado: " + item.estado + "</li>" +
                        "<li>Bicis disponibles: " + item.bicisDisponibles + "</li>" +
                        "<li>Anclajes disponibles" + item.anclajesDisponibles + "</li>" +
                        "</ul></p>" +
                        "<p>Actualizado: "+ lastUpdate.getUTCHours() + ":"+ lastUpdate.getMinutes() +" </p>";
                    var infowindow = new google.maps.InfoWindow({
                        content: contentString
                    });
                    marker.setMap(map);
                    google.maps.event.addListener(marker, 'click', function() {
                        infowindow.open(map,marker);  //Añadir información al Marker

                        //Clicking on a Map Marker --> Reg as a stat in Parse.com cloud database
                        geocoder.geocode( { 'latLng': this.position}, function(results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                $.post("index.php/control-estadistico/infoBizi", {exito: true, origen: results[0].formatted_address, destino: '', desplazamiento: '', provincia: '', municipio: '', formato: ''});
                            } else {
                                $.post("index.php/control-estadistico/infoBizi", {exito: false, origen: '', destino: '', desplazamiento: '', provincia: '', municipio: '', formato: ''});
                            }
                        });
                    });
                });
            }
        });
    }

    //GET route from Google Maps JS API and display it in the map
    function getRuta(){
        var formato = $('#formato-bizis-select').val();
        var start = $('#input-origen').val();
        var desplazamiento = $('#modo-desplazamiento').val();
	var estacionBizi = $('#estaciones-bizi').val();

        $.post("index.php/calcular-ruta", {formatoDatos: formato, estacionBizi: estacionBizi}, function(data){
            var end = data.lat + "," + data.long;
            var request = {
                origin: start,
                destination: end,
                unitSystem: google.maps.UnitSystem.METRIC,
                travelMode: google.maps.TravelMode.DRIVING
            };

            switch (desplazamiento){
                case 'DRIVING': request.travelMode = google.maps.TravelMode.DRIVING; break;
                case 'TRANSIT': request.travelMode = google.maps.TravelMode.TRANSIT; break;
                case 'WALKING': request.travelMode = google.maps.TravelMode.WALKING; break;
            }

            //Display response into the map
            directionsService.route(request, function(response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                    directionsDisplay.setDirections(response);

                    var origen = response.routes[0].legs[0].start_address;
                    var destino = response.routes[0].legs[0].end_address;

                    //Register the route calculation in the Parse.com cloud database
                    $.post("index.php/control-estadistico/rutaBizi", {exito: true, origen: origen, destino: destino, desplazamiento: desplazamiento, provincia: '', municipio: '', formato: formato});
                } else {
                    $.post("index.php/control-estadistico/rutaBizi", {exito: false, origen: '', destino: '', desplazamiento: '', provincia: '', municipio: '', formato: formato});
                }
            });
        });
    }

    //GET meteo info prediction from a city, display it, and register the action in Parse.com cloud database
    function getPrediccion(){

        var provincia = $('#provincia').val();
        var municipio = $('#municipio').val();
        var formato = $('#interaccion').val();

        $.post("index.php/prediccion", $('#prediccion-form').serialize(), function(data){
            if (data.length == 0){
                $('#prediccion').html("<p>Error al conseguir la predicción</p>");
                $.post("index.php/control-estadistico/prediccion", {exito: false, origen: '', destino: '', desplazamiento: '', provincia: provincia, municipio: municipio, formato: formato});
            } else {
                $('#prediccion').html(data);
                $.post("index.php/control-estadistico/prediccion", {exito: true, origen: '', destino: '', desplazamiento: '', provincia: provincia, municipio: municipio, formato: formato});
            }
        });
    }
</script>

</body>
</html>

