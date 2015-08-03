<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="es" xml:lang="es"><head>

<head>
  <link rel="stylesheet" type="text/css" media="screen" href="style.css" />
</head>
<body>

<div class="contenedor_central_izq">

<table id="tabla_prediccion" class="tabla_datos marginbottom10px" cellspacing="2">
   <thead >
    <tr class="cabecera_niv1">
      <th class="borde_rlb_th_avisos_cab borde_t_cab" title="Fecha" abbr="Fec." rowspan="2"><div  class="cabecera_celda">Fecha</div></th>

      <th class="borde_rb_cab borde_t_cab" title="sabado 07" abbr="sab." colspan="2" >${d0_f}</th>           
            <th class="borde_rb_cab borde_t_cab" title="domingo 08" abbr="dom." colspan="4" >${d1_f}</th>            
            <th class="borde_rb_cab borde_t_cab" title="lunes 09" abbr="lun." colspan="2" >${d2_f}</th>            
            <th class="borde_rb_cab borde_t_cab" title="martes 10" abbr="mar." colspan="2" >${d3_f}</th>           
            <th class="borde_rb_cab borde_t_cab" title="miercoles 11" abbr="mie." rowspan="2" >${d4_f}</th>
            <th class="borde_rb_cab borde_t_cab" title="jueves 12" abbr="jue." rowspan="2" >${d5_f}</th>
            <th class="borde_rb_cab borde_t_cab" title="viernes 13" abbr="vie." rowspan="2" >${d6_f}</th>
            </tr>
    <tr class="cabecera_niv2">        
      <th class="borde_rb no_wrap">12&ndash;18</th>     
            <th class="borde_rb no_wrap">18&ndash;24</th>     
            <th class="borde_rb no_wrap">0&ndash;6</th>     
            <th class="borde_rb no_wrap">6&ndash;12</th>    
            <th class="borde_rb no_wrap">12&ndash;18</th>     
            <th class="borde_rb no_wrap">18&ndash;24</th>     
            <th class="borde_rb no_wrap">0&ndash;12</th>    
            <th class="borde_rb no_wrap">12&ndash;24</th>     
            <th class="borde_rb no_wrap">0&ndash;12</th>    
            <th class="borde_rb no_wrap">12&ndash;24</th>     
            </tr>
  </thead>
  <tbody>
    <tr >
      <th title="Estado del cielo" abbr="Cielo" class="borde_rlb_th_avisos">Estado del cielo</th>
                <td class="borde_b"><img src="img/${d0_i1_estados}.gif" title="Cielo despejado" alt="Cielo despejado" /></td>
                <td class="borde_rb"><img src="img/${d0_i2_estados}.gif" title="Cielo despejado" alt="Cielo despejado" /></td>            
                <td class="borde_b"><img src="img/${d1_i1_estados}.gif" title="Cielo despejado" alt="Cielo despejado" /></td>
                <td class="borde_b"><img src="img/${d1_i2_estados}.gif" title="Cielo despejado" alt="Cielo despejado" /></td>
                <td class="borde_b"><img src="img/${d1_i3_estados}.gif" title="Poco nuboso" alt="Poco nuboso" /></td>
                <td class="borde_rb"><img src="img/${d1_i4_estados}.gif" title="Nuboso" alt="Nuboso" /></td>            
                <td class="borde_b"><img src="img/${d2_i1_estados}.gif" title="Nubes altas" alt="Nubes altas" /></td>
                <td class="borde_rb"><img src="img/${d2_i2_estados}.gif" title="Poco nuboso" alt="Poco nuboso" /></td>           
                <td class="borde_b"><img src="img/${d3_i1_estados}.gif" title="Poco nuboso" alt="Poco nuboso" /></td>
                <td class="borde_rb"><img src="img/${d3_i2_estados}.gif" title="Poco nuboso" alt="Poco nuboso" /></td>           
                <td class="borde_rb"><img src="img/${d4_i1_estados}.gif" title="Poco nuboso" alt="Poco nuboso" /></td>           
                <td class="borde_rb"><img src="img/${d5_i1_estados}.gif" title="Cielo despejado" alt="Cielo despejado" /></td>           
                <td class="borde_rb"><img src="img/${d6_i1_estados}.gif" title="Intervalos nubosos" alt="Intervalos nubosos" /></td>           
                </tr>
    <tr>
      <th title="Probabilidad de precipitacion" abbr="Pro." class="borde_rlb_th_avisos">Prob. precip.</th>
              <td class="borde_b" >${d0_i1_precip}%&nbsp;</td>           
              <td class="borde_rb" >${d0_i2_precip}%&nbsp;</td>
              <td class="borde_b" >${d1_i1_precip}%&nbsp;</td>
              <td class="borde_rb" >${d1_i2_precip}%&nbsp;</td>
              <td class="borde_b" >${d1_i3_precip}%&nbsp;</td>           
              <td class="borde_b" >${d1_i4_precip}%&nbsp;</td>
              <td class="borde_b" >${d2_i1_precip}%&nbsp;</td>           
              <td class="borde_rb" >${d2_i2_precip}%&nbsp;</td>
              <td class="borde_b" >${d3_i1_precip}%&nbsp;</td>           
              <td class="borde_rb" >${d3_i2_precip}%&nbsp;</td>
              <td class="borde_rb" >${d4_i1_precip}%&nbsp;</td>           
              <td class="borde_rb" >${d5_i1_precip}%&nbsp;</td>           
              <td class="borde_rb" >${d6_i1_precip}%&nbsp;</td>            
              
              </tr>   
  <tr >
        <th title="Cota de nieve a nivel de provincia" abbr="Cot." class="borde_rlb_th_avisos">Cota nieve prov.(m)</th>
          <td class="borde_b" >&nbsp;</td>  
                      <td class="borde_rb" >&nbsp;</td> 
                      <td class="borde_b" >&nbsp;</td>  
                      <td class="borde_b" >&nbsp;</td>  
                      <td class="borde_b" >&nbsp;</td>  
                      <td class="borde_rb" >&nbsp;</td> 
                      <td class="borde_b" >&nbsp;</td>  
                      <td class="borde_rb" >&nbsp;</td> 
                      <td class="borde_b" >&nbsp;</td>  
                      <td class="borde_rb" >&nbsp;</td> 
                      <td class="borde_rb" >&nbsp;</td> 
                      <td class="borde_rb" >&nbsp;</td> 
                      <td class="borde_rb" >&nbsp;</td> 
                      </tr>
<tr>
      <th title="Temperatura minima y maxima (&#176;C)" abbr="Max/Min." class="borde_rlb_th_avisos">Temp. min./max. (&#176;C)</th>
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="2"><span class="texto_azul">${d0_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d0_tmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="4"><span class="texto_azul">${d1_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d1_tmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="2"><span class="texto_azul">${d2_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d2_tmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="2"><span class="texto_azul">${d3_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d3_tmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" ><span class="texto_azul">${d4_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d4_tmax}&nbsp;</span></td>
            <td class="borde_rb alinear_texto_centro no_wrap" ><span class="texto_azul">${d5_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d5_tmax}&nbsp;</span></td>
            <td class="borde_rb alinear_texto_centro no_wrap" ><span class="texto_azul">${d6_tmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d6_tmax}&nbsp;</span></td>
            </tr>
    <tr class="ocultar_filas_tabla">
      <th title="Sensacion termica minima y maxima (&#176;C)" abbr="Sen. term. min./max. (&#176;C)." class="borde_rlb_th_avisos">Sen. term. min./max. (&#176;C)</th>
      <td class="borde_rb alinear_texto_centro no_wrap" colspan="2"><span class="texto_azul">${d0_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d0_stmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="4"><span class="texto_azul">${d1_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d1_stmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="2"><span class="texto_azul">${d2_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d2_stmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" colspan="2"><span class="texto_azul">${d3_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d3_stmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro no_wrap" ><span class="texto_azul">${d4_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d4_stmax}&nbsp;</span></td>
            <td class="borde_rb alinear_texto_centro no_wrap" ><span class="texto_azul">${d5_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d5_stmax}&nbsp;</span></td>
            <td class="borde_rb alinear_texto_centro no_wrap" ><span class="texto_azul">${d6_stmin}</span>&nbsp;/&nbsp;<span class="texto_rojo">${d6_stmax}&nbsp;</span></td>
            </tr>
    <tr class="ocultar_filas_tabla">
      <th title="Humedad relativa minima y maxima (%)" abbr="Hum. rel. min./max. (%)." class="borde_rlb_th_avisos">Hum. rel. min./max. (%)</th>
            <td class="borde_rb alinear_texto_centro" colspan="2"><span class="texto_marron">${d0_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d0_hmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro" colspan="4"><span class="texto_marron">${d1_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d1_hmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro" colspan="2"><span class="texto_marron">${d2_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d2_hmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro" colspan="2"><span class="texto_marron">${d3_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d3_hmax}&nbsp;</span></td>           
            <td class="borde_rb alinear_texto_centro" ><span class="texto_marron">${d4_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d4_hmax}&nbsp;</span></td>
            <td class="borde_rb alinear_texto_centro" ><span class="texto_marron">${d5_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d5_hmax}&nbsp;</span></td>
            <td class="borde_rb alinear_texto_centro" ><span class="texto_marron">${d6_hmin}</span>&nbsp;/&nbsp;<span class="texto_verde">${d6_hmax}&nbsp;</span></td>
            </tr>
    <tr>
      <th title="Direccion del viento" abbr="Vie." class="borde_rlb_th_viento">Viento</th>
              <td class="alinear_texto_centro"><img src="img/viento/${d0_i1_viento}.gif" title="Calma" alt="Calma" /></td>
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d0_i2_viento}.gif" title="Calma" alt="Calma" /></td>           
              
              <td class="alinear_texto_centro"><img src="img/viento/${d1_i1_viento}.gif" title="Oeste" alt="Oeste" /></td>
              <td class="alinear_texto_centro"><img src="img/viento/${d1_i2_viento}.gif" title="Norte" alt="Norte" /></td>
              <td class="alinear_texto_centro"><img src="img/viento/${d1_i3_viento}.gif" title="Sudeste" alt="Sudeste" /></td>
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d1_i3_viento}.gif" title="Noroeste" alt="Noroeste" /></td>            
              
              <td class="alinear_texto_centro"><img src="img/viento/${d2_i1_viento}.gif" title="Noroeste" alt="Noroeste" /></td>
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d2_i2_viento}.gif" title="Oeste" alt="Oeste" /></td>           
              
              <td class="alinear_texto_centro"><img src="img/viento/${d3_i1_viento}.gif" title="Noroeste" alt="Noroeste" /></td>
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d3_i2_viento}.gif" title="Calma" alt="Calma" /></td>           
              
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d4_i1_viento}.gif" title="Noroeste" alt="Noroeste" /></td>            
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d5_i1_viento}.gif" title="Noroeste" alt="Noroeste" /></td>            
              <td class="borde_r alinear_texto_centro"><img src="img/viento/${d6_i1_viento}.gif" title="Noroeste" alt="Noroeste" /></td>            
              </tr> 
    <tr>
      <th title="Velocidad del viento en kilometros por hora" abbr="km/h." class="borde_rlb_th_avisos">(km/h)</th>
              <td class="borde_b" >${d0_i1_vviento}&nbsp;</td>            
              <td class="borde_rb" >${d0_i2_vviento}&nbsp;</td>
              <td class="borde_b" >${d1_i1_vviento}&nbsp;</td>
              <td class="borde_b" >${d1_i2_vviento}&nbsp;</td>
              <td class="borde_b" >${d1_i3_vviento}&nbsp;</td>           
              <td class="borde_rb" >${d1_i4_vviento}&nbsp;</td>
              <td class="borde_b" >${d2_i1_vviento}&nbsp;</td>            
              <td class="borde_rb" >${d2_i2_vviento}&nbsp;</td>
              <td class="borde_b" >${d3_i1_vviento}&nbsp;</td>            
              <td class="borde_rb" >${d3_i2_vviento}&nbsp;</td>           
              <td class="borde_rb" >${d4_i1_vviento}&nbsp;</td>           
              <td class="borde_rb" >${d5_i1_vviento}&nbsp;</td>           
              <td class="borde_rb" >${d6_i1_vviento}&nbsp;</td>
              </tr>
    <tr class="ocultar_filas_tabla">
      <th title="Racha maxima (km/h)" abbr="acha." class="borde_rlb_th_avisos">Racha max. (km/h)</th>
              <td class="borde_b" >&nbsp;</td>
              <td class="borde_rb" >&nbsp;</td>           
              <td class="borde_b" >&nbsp;</td>
              <td class="borde_b" >&nbsp;</td>
              <td class="borde_b" >&nbsp;</td>
              <td class="borde_rb" >&nbsp;</td>           
              <td class="borde_b" >&nbsp;</td>
              <td class="borde_rb" >&nbsp;</td>           
              <td class="borde_b" >&nbsp;</td>
              <td class="borde_rb" >&nbsp;</td>           
              <td class="borde_rb" >&nbsp;</td>           
              <td class="borde_rb" >&nbsp;</td>           
              <td class="borde_rb" >&nbsp;</td>           
              </tr>       
    <tr class="ocultar_filas_tabla">
      <th title="Temperaturas (&#176;C)" abbr="Temp. (&#176;C)." class="borde_rlb_th_avisos">Temp. (&#176;C)</th>
            <td class="borde_b no_wrap" >${d0_i1_temp}&nbsp;</td>
            <td class="borde_rb no_wrap">${d0_i2_temp}&nbsp;</td>            
            <td class="borde_b no_wrap" >${d1_i1_temp}&nbsp;</td>
            <td class="borde_b no_wrap" >${d1_i2_temp}&nbsp;</td>
            <td class="borde_b no_wrap" >${d1_i3_temp}&nbsp;</td>
            <td class="borde_rb no_wrap">${d1_i4_temp}&nbsp;</td>           
            <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            </tr> 
    <tr class="ocultar_filas_tabla">
      <th title="Sensacion termica (&#176;C)" abbr="Sen. termica (&#176;C)." class="borde_rlb_th_avisos">Sen. termica (&#176;C)</th>
            <td class="borde_b no_wrap" >${d0_i1_stemp}&nbsp;</td>
            <td class="borde_rb no_wrap">${d0_i2_stemp}&nbsp;</td>            
            <td class="borde_b no_wrap" >${d1_i1_stemp}&nbsp;</td>
            <td class="borde_b no_wrap" >${d1_i2_stemp}&nbsp;</td>
            <td class="borde_b no_wrap" >${d1_i3_stemp}&nbsp;</td>
            <td class="borde_rb no_wrap">${d1_i4_stemp}&nbsp;</td>              
                  <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            </tr> 
    <tr class="ocultar_filas_tabla">
      <th title="Humedad relativa (%)" abbr="Humedad relativa (%)." class="borde_rlb_th_avisos">Humedad relativa (%)</th>
            <td class="borde_b no_wrap" >${d0_i1_humedad}&nbsp;</td>
            <td class="borde_rb no_wrap">${d0_i2_humedad}&nbsp;</td>            
            <td class="borde_b no_wrap" >${d1_i1_humedad}&nbsp;</td>
            <td class="borde_b no_wrap" >${d1_i2_humedad}&nbsp;</td>
            <td class="borde_b no_wrap" >${d1_i3_humedad}&nbsp;</td>
            <td class="borde_rb no_wrap">${d1_i4_humedad}&nbsp;</td>  
            <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="2">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            </tr>
    <tr>
      <th title="Indice ultravioleta maximo" abbr="UV" class="borde_rlb_th_avisos">Indice UV maximo</th>
      <td class="borde_rb" colspan="2"><span class="raduv_pred_nivel2" title="indice ultravioleta moderado">&nbsp;&nbsp;${d0_uv}</span></td>
            <td class="borde_rb" colspan="4"><span class="raduv_pred_nivel2" title="indice ultravioleta moderado">&nbsp;&nbsp;${d1_uv}</span></td>
            <td class="borde_rb" colspan="2"><span class="raduv_pred_nivel2" title="indice ultravioleta moderado">&nbsp;&nbsp;${d2_uv}</span></td>
            <td class="borde_rb" colspan="2"><span class="raduv_pred_nivel2" title="indice ultravioleta moderado">&nbsp;&nbsp;${d3_uv}</span></td>
            <td class="borde_rb" colspan="1"><span class="raduv_pred_nivel2" title="indice ultravioleta moderado">&nbsp;&nbsp;${d4_uv}</span></td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            <td class="borde_rb fondo_celda_azul_claro" colspan="1">&nbsp;</td>
            </tr> 
   </tbody>
  </table>
  </div>
</body>
</html>
