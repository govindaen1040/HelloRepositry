package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

public class ResponseMetaData {
	//private String initialToken;
	//private String latestToken;
	private String apiVersion;
	private String timeStamp;
	private String statusCode;
	private String message;
	
	
	

//	@Override
//	public String toString() {
//		return "ResponseMetaData [initialToken=" + initialToken + ", latestToken=" + latestToken + ", apiVersion="
//				+ apiVersion + ", timeStamp=" + timeStamp + ", statusCode=" + statusCode + ", message=" + message + "]";
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((apiVersion == null) ? 0 : apiVersion.hashCode());
//		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
//		result = prime * result + ((initialToken == null) ? 0 : initialToken.hashCode());
//		result = prime * result + ((latestToken == null) ? 0 : latestToken.hashCode());
//		result = prime * result + ((message == null) ? 0 : message.hashCode());
//		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		ResponseMetaData other = (ResponseMetaData) obj;
//		if (apiVersion == null) {
//			if (other.apiVersion != null)
//				return false;
//		} else if (!apiVersion.equals(other.apiVersion))
//			return false;
//		if (statusCode == null) {
//			if (other.statusCode != null)
//				return false;
//		} else if (!statusCode.equals(other.statusCode))
//			return false;
//		if (initialToken == null) {
//			if (other.initialToken != null)
//				return false;
//		} else if (!initialToken.equals(other.initialToken))
//			return false;
//		if (latestToken == null) {
//			if (other.latestToken != null)
//				return false;
//		} else if (!latestToken.equals(other.latestToken))
//			return false;
//		if (message == null) {
//			if (other.message != null)
//				return false;
//		} else if (!message.equals(other.message))
//			return false;
//		if (timeStamp == null) {
//			if (other.timeStamp != null)
//				return false;
//		} else if (!timeStamp.equals(other.timeStamp))
//			return false;
//		return true;
//	}

//	public String getInitialToken() {
//		return initialToken;
//	}
//
//	public void setInitialToken(String initialToken) {
//		this.initialToken = initialToken;
//	}
//
//	public String getLatestToken() {
//		return latestToken;
//	}
//
//	public void setLatestToken(String latestToken) {
//		this.latestToken = latestToken;
//	}

	@Override
	public String toString() {
		return "ResponseMetaData [apiVersion=" + apiVersion + ", timeStamp=" + timeStamp + ", statusCode=" + statusCode
				+ ", message=" + message + "]";
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
