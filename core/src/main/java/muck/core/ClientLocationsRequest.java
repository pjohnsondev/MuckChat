package muck.core;

public class ClientLocationsRequest {

	public Id<ClientId> clientId;

    public ClientLocationsRequest(Id<ClientId> clientId) {
		this.clientId = clientId;

	}
    public ClientLocationsRequest() {
		clientId = new Id<ClientId>();
	}

}
