package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;


import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

import java.util.Arrays;

import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {

	static AccessLog accesslog = null;
	static AccessCode accesscode = null;

	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service

		accesslog = new AccessLog();
		accesscode = new AccessCode();

		after((req, res) -> {
			res.type("application/json");
		});

		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson("IoT Access Control Device");
		});

		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations described in the project description

		post("/accessdevice/log/", (req, res) -> {

			Gson gson = new Gson();

			int id = accesslog.add(req.body());

			AccessEntry ae = accesslog.get(id);

			return gson.toJson(ae);
		});

		get("/accessdevice/log/", (req, res) -> {

			Gson gson = new Gson();
			AccessEntry[] AE = new AccessEntry[accesslog.getSize()];

			for (int i = 0; i < accesslog.getSize(); i++) {

				AE[i] = accesslog.get(i + 1);

			}

			return gson.toJson(AE);
		});

		get("/accessdevice/log/:id", (req, res) -> {

			Gson gson = new Gson();
			AccessEntry AE = null;
			int id = Integer.parseInt(req.params(":id"));
			AE = accesslog.get(id);

			return gson.toJson(AE);
		});

		put("/accessdevice/code", (req, res) -> {

			int[] array = accesscode.getAccesscode();
			int[] array2 = new int[array.length];

			Gson gson = new Gson();
			array2[0] = array[1];
			array2[1] = array[0];

			accesscode.setAccesscode(array2);

			return gson.toJson(accesscode);
		});

		get("/accessdevice/code", (req, res) -> {

			Gson gson = new Gson();

			return gson.toJson(accesscode);
		});
		
		delete("/accessdevice/log/", (req, res) -> {

			
			accesslog.clear();
			
			AccessEntry[] AE = new AccessEntry[accesslog.getSize()];

			for (int i = 0; i < accesslog.getSize(); i++) {

				AE[i] = accesslog.get(i + 1);

			}
			
			Gson gson = new Gson();

			return gson.toJson(AE);
		});

	}

}