package microservice.model;

import org.springframework.social.facebook.api.CoverPhoto;
import org.springframework.stereotype.Component;

@Component
public class AppUser {
	
	private String firstName;

	private String lastName;
	
	private String email;
	
	private CoverPhoto cover;


	public CoverPhoto getCover() {
		return cover;
	}

	public void setCover(CoverPhoto cover) {
		this.cover = cover;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "AppUser [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", cover=" + cover
				+ "]";
	}

	public AppUser() {
		super();
	}


	

	

	

}
