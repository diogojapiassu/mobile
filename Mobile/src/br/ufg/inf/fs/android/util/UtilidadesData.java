package br.ufg.inf.fs.android.util;

import java.util.Date;

public class UtilidadesData {

	public static String getDataString(Date data){
		if(data == null){
			return null;
		}
		
		return 	data.getDate() + "/" + 
				(data.getMonth() + 1) + "/" + 
				(1900 + data.getYear());
	}
	
}
