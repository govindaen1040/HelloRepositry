package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

import java.util.List;

public class ResponseAPIDetails {
	ResponseMetaData apiDetails = new ResponseMetaData();
	List result;

	@Override
	public String toString() {
		return "ResponseAPIDetails [apiDetails=" + apiDetails + ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apiDetails == null) ? 0 : apiDetails.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseAPIDetails other = (ResponseAPIDetails) obj;
		if (apiDetails == null) {
			if (other.apiDetails != null)
				return false;
		} else if (!apiDetails.equals(other.apiDetails))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	public ResponseMetaData getApiDetails() {
		return apiDetails;
	}

	public void setApiDetails(ResponseMetaData apiDetails) {
		this.apiDetails = apiDetails;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

}
