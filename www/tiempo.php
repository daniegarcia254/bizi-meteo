<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<head>
<title>Trabajo STW</title>
<link rel="stylesheet" type="text/css" href="css/prediccion.css">
<link rel="stylesheet" type="text/css" href="css/tabla2c.css">
</head>

<body>

<div id="contenedor">
<div id="prediccion">
<?php
            if (isset($_POST['provincia']) && isset($_POST['municipio'])){
                $code= $_POST['municipio'];
                $provincia=$_POST['provincia'];
                $interaccion=$_POST['interaccion'];
                $municipio=$code;
            }
            else{
                // Mostrar zaragoza por defecto
                $code=50297;
                $provincia=50;
                $municipio=$code;
                $interaccion='xml';
            }

  /*
  // No funciona, alguna cache de Tomcat está haciendo que siempre se devuelva el primer parte solicitado
  ini_set("soap.wsdl_cache_enabled", 0);
  $client = new SoapClient('http://localhost:8080/axis/services/Generar2columnas?wsdl', array('cache_wsdl' => WSDL_CACHE_NONE));
  $result = $client->serviceMethod( "http://www.aemet.es/xml/municipios/localidad_" . $municipio . ".xml" );
  print_r($result);
  */
	
  if ($interaccion == "json"){
    exec('cd /home/javier/practica6/ ; /usr/bin/java -jar Print2columnas.jar http://www.aemet.es/xml/municipios/localidad_' . $municipio . '.xml ' . $interaccion ,$result);
  }
  else {
    exec('cd /home/javier/practica6/ ; /usr/bin/java -jar Print2columnas_xml.jar http://www.aemet.es/xml/municipios/localidad_' . $municipio . '.xml ' . $interaccion ,$result);
  }

  print(implode($result)); 
?>
<script src="js/jquery.min.js"></script>
<script src="js/municipio.js"></script>
</div>

<div id="selectores">
<form id="formulario" method="post" action="#">
<select id="provincia" name="provincia" onChange="setmunicipio(this.value,'');">
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
<select name="municipio" id="municipio" onchange="this.form.submit()">
<option value="">Municipio</option>
</select>
<br>
<select name="interaccion" id="interaccion" onchange="this.form.submit()">
<option value="xml">XML</option>
<option value="json">JSON</option>
</select>

</form>
</div>
</div>

<script>
    var element = document.getElementById('provincia');
    element.value = '<?php print $provincia; ?>';
    setmunicipio('<?php echo $provincia; ?>','<?php print $municipio; ?>');
    var element = document.getElementById('interaccion');
    element.value = "<?php print $interaccion; ?>";
</script>

</body>
</html>
