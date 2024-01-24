package Cghs.CghsCardFullDetailsAPI.ResponseDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ServiceToControllerParams {
	List result;
	Map<String, String> miscParams = new HashMap<String, String>();
	String serviceStatusCode;
	BigDecimal api_execution_time_millis;

	public ServiceToControllerParams() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceToControllerParams(List result, Map<String, String> miscParams, String serviceStatusCode,
			BigDecimal api_execution_time_millis) {
		super();
		this.result = result;
		this.miscParams = miscParams;
		this.serviceStatusCode = serviceStatusCode;
		this.api_execution_time_millis = api_execution_time_millis;
	}

	@Override
	public String toString() {
		return "ServiceToControllerParams [result=" + result + ", miscParams=" + miscParams + ", serviceStatusCode="
				+ serviceStatusCode + ", api_execution_time_millis=" + api_execution_time_millis + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((api_execution_time_millis == null) ? 0 : api_execution_time_millis.hashCode());
		result = prime * result + ((miscParams == null) ? 0 : miscParams.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((serviceStatusCode == null) ? 0 : serviceStatusCode.hashCode());
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
		ServiceToControllerParams other = (ServiceToControllerParams) obj;
		if (api_execution_time_millis == null) {
			if (other.api_execution_time_millis != null)
				return false;
		} else if (!api_execution_time_millis.equals(other.api_execution_time_millis))
			return false;
		if (miscParams == null) {
			if (other.miscParams != null)
				return false;
		} else if (!miscParams.equals(other.miscParams))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (serviceStatusCode == null) {
			if (other.serviceStatusCode != null)
				return false;
		} else if (!serviceStatusCode.equals(other.serviceStatusCode))
			return false;
		return true;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}

	public Map<String, String> getMiscParams() {
		return miscParams;
	}

	public void setMiscParams(Map<String, String> miscParams) {
		this.miscParams = miscParams;
	}

	public String getServiceStatusCode() {
		return serviceStatusCode;
	}

	public void setServiceStatusCode(String serviceStatusCode) {
		this.serviceStatusCode = serviceStatusCode;
	}

	public BigDecimal getApi_execution_time_millis() {
		return api_execution_time_millis;
	}

	public void setApi_execution_time_millis(BigDecimal api_execution_time_millis) {
		this.api_execution_time_millis = api_execution_time_millis;
	}

}
