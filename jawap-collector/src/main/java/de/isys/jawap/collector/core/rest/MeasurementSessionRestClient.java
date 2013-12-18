package de.isys.jawap.collector.core.rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import de.isys.jawap.entities.MeasurementSession;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class MeasurementSessionRestClient {

	private Client client;
	private String serverUrl;

	public MeasurementSessionRestClient(String serverUrl) {
		this.serverUrl = serverUrl;
		client = ClientBuilder.newClient(new ClientConfig()
				.register(JacksonJaxbJsonProvider.class));
	}

	public String saveMeasurementSession(MeasurementSession measurementSession) {
		if (serverUrl != null && !serverUrl.isEmpty()) {
			return client.target(serverUrl)
					.path("measurementSession")
					.request().post(Entity.entity(measurementSession, MediaType.APPLICATION_JSON_TYPE))
					.getHeaderString("Location");
		} else return null;
	}

	public void updateMeasurementSession(MeasurementSession measurementSession, String location) {
		if (serverUrl != null && !serverUrl.isEmpty() && location != null) {
			WebTarget resource;
			if (location.startsWith("/")) {
				resource = client.target(serverUrl).path(location);
			} else {
				resource = client.target(location);
			}
			resource.request().async().put(Entity.entity(measurementSession, MediaType.APPLICATION_JSON_TYPE));
		}
	}
}
