//Handles the behaviour of the city select for the meteo info
function setmunicipio($municipio,$s) {
  var $select = $('#municipio');
  $select.html('');

  if ($municipio=="") {
    $select.append('<option value="">Municipio</option>');
  }
  else {
    $.getJSON('provincias/' + $municipio +'.json', function(data){
      if ("" == $s){ $selected="selected"; }
      else { $selected=""; }
      $select.append('<option disabled ' + $selected + ' value="">Municipio</option>');
      //iterate over the data and append a select option
      $.each(data, function(key, val){
        if (data[key].provincia + data[key].municipio == $s){
          $selected="selected";
        }
        else
          $selected="";
        $select.append('<option ' + $selected + ' value="' + data[key].provincia + data[key].municipio + '">' + data[key].nombre + '</option>');
      })
    });
  }
}
