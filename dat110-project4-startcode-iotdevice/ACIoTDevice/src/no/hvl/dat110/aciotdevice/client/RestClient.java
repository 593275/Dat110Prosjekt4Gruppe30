package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class RestClient {
	


	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		AccessMessage melding = new AccessMessage(message);
		RequestBody body = RequestBody.create(gson.toJson(melding), MediaType.parse("application/json; charset=utf-8"));
		
		 Request request = new Request.Builder()
			      .url("http://localhost:8080" + logpath)
			      .post(body)
			      .build();
			 
			   try(Response response = client.newCall(request).execute()) {
				   
			   }catch (IOException e) {
				   e.printStackTrace();
			   }
		
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() throws IOException {

		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		
		Request request = new Request.Builder()
				  .url("http://localhost:8080"+codepath)
				  .get()
				  .build();
		
		AccessCode code = null;
		
		
		// TODO: implement a HTTP GET on the service to get current access code
		try (Response response = client.newCall(request).execute()) {
			code = gson.fromJson(response.body().string(), AccessCode.class);
		 }catch (IOException e) {
			   e.printStackTrace();
		 }
		
		return code;
	}
}
