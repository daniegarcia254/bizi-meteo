/*
 * AUTOR: Javier Briz Alastrue
 * NIA: 576695
 */

package stw.p6;

import java.util.*;

public class Prediccion {
	// Variables simples
	String fecha;
	String tmin;
	String tmax;
	String stmin;
	String stmax;
	String hmin;
	String hmax;
	String uv;

	// Variables por Stringervalos
	int count = 0;
	String[] intervalo = new String[4];
	String[] estados = new String[4];
	String[] precip = new String[4];
	String[] viento = new String[4];
	String[] vviento = new String[4];
	String[] temp = new String[4];
	String[] stemp = new String[4];
	String[] humedad = new String[4];;


	public void setSimples(String fecha, String tmin, String tmax, String stmin, String stmax, String hmin, String hmax, String uv) {
		this.fecha = fecha;
		this.tmin = tmin;
		this.tmax = tmax;
		this.stmin = stmin;
		this.stmax = stmax;
		this.hmin = hmin;
		this.hmax = hmax;
		this.uv = uv;
	}

	public void setIntervalo(String intervalo, String estados, String precip, String viento, String vviento, String temp, String stemp, String humedad) {
		this.intervalo[count] = intervalo;
		this.estados[count] = estados;
		this.precip[count] = precip;
		this.viento[count] = viento;
		this.vviento[count] = vviento;
		this.temp[count] = temp;
		this.stemp[count] = stemp;
		this.humedad[count] = humedad;
		count++;
	}

	public void print() {
		System.out.println(fecha);
		System.out.println("  tm:  " + tmin);
		System.out.println("  tM:  " + tmax);
		System.out.println("  stm: " + stmin);
		System.out.println("  stM: " + stmax);
		System.out.println("  hm:  " + hmin);
		System.out.println("  hM:  " + hmax);
		System.out.println("  uv:  " + uv);
		for (int i = 0; i < count; i++) {
			System.out.println("  int: " + intervalo[i] +
				"  est: " + estados[i] +
				"  pre: " + precip[i] +
				"  vie: " + viento[i] +
				"  vvi: " + vviento[i] +
				"  tem: " + temp[i] +
				"  ste: " + stemp[i] +
				"  hum: " + humedad[i]);
		}
	}
}
