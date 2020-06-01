package microservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "devices")
public class Device {

	
	@Column(name = "email")
	private String email;

	@Id
	@Column(name = "device_id")
	private Long deviceId;

	@Column(name = "device_name")
	private String device;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Device() {
	}

	public Device(String email, Long deviceId, String device) {
		super();
		this.email = email;
		this.deviceId = deviceId;
		this.device = device;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	@Override
	public String toString() {
		return "Device [userEmail=" + email + ", deviceId=" + deviceId + ", device=" + device + "]";
	}

}
