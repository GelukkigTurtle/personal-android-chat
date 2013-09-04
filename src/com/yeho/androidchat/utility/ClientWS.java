package com.yeho.androidchat.utility;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;



public class ClientWS {
	
	private static final String URL_LOGIN_BR = "http://192.168.1.126/joomla/ingreso.php"; // JC
	private static ClientWS ClientWS;
	private static String login_tag = "login";
	

	public static ClientWS getInstance() {
		if (ClientWS == null)
			ClientWS = new ClientWS();
		return ClientWS;
	}
	
	
/*
	public ArrayList<HashMap<String, Object>> getStockByProduct(String producto)
			throws SocketTimeoutException, IOException, XmlPullParserException,
			JSONException {

		
		ParseWSStock parserWS = new ParseWSStock();
		SoapObject request = new SoapObject(NAMESPACE,
				METHOD_NAME_PRODUCT_STOCK);
		SoapObject resultsRequestSOAP = null;

		String id = "" + UserData.getCompId();
		String idstore = "" + UserData.getStoreId();

		request.addProperty("Compagnia", id);
		request.addProperty("Tienda", idstore);
		request.addProperty("Producto", producto);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);


		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		androidHttpTransport.call(SOAP_ACTION_PRODUCT_STOCK, envelope);
		resultsRequestSOAP = (SoapObject) envelope.bodyIn;
		return parserWS
				.parseSoapObjectsResponseProductStock(resultsRequestSOAP);
	}
*/


	public JSONObject login(String usuario, String password)
			throws SocketTimeoutException, IOException, XmlPullParserException,
			JSONException {
		Log.v("[YEHO]", "Calling login.");
		JSONParser jsonParser = new JSONParser();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("username", usuario));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(URL_LOGIN_BR, params);

		return json;

	}

}
