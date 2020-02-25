package microservice.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ClientDTO {

	@NotNull
	@NotEmpty
	private String clientId;

	@NotNull
	@NotEmpty
	private String uri;
	
	private String additionalInformation;
	
	public ClientDTO() {
		
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	@Override
	public String toString() {
		return "ClientDTO [clientId=" + clientId + ", uri=" + uri + ", additionalInformation=" + additionalInformation
				+ "]";
	}
}
