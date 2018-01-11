package br.com.auttar.common.monitoring;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.util.MonitoringUtil;

@Provider
@PreMatching
public class MetricFilter implements ContainerRequestFilter, ContainerResponseFilter {

//	@EJB
//	@Inject
	MetricService metricService;
	@Context
	private HttpServletRequest httpRequest;

	private Logger logger = LogManager.getLogger(LogConstants.MONITORING_LOGGER_NAME);
	
	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		
		try {
			Long timeInicialReq = Long.valueOf(request.getHeaderValue("timestamp"));
			Long timeFinalReq = System.currentTimeMillis();
			
			this.metricService = MonitoringUtil.getSingletonMetricService();
			String req = httpRequest.getMethod() + " " + httpRequest.getRequestURI();
			if (!req.contains("metrics")) {				
				int status = response.getStatus();
				metricService.increaseCount(req, status,timeInicialReq, timeFinalReq);
			}
			return response;
		} catch (Exception e) {
	    	logger.error(e.getMessage(), e);
			return response;
		}
	}

	@Override
	 public ContainerRequest filter(ContainerRequest request) { 
	  request.getRequestHeaders().add("timestamp", String.valueOf(System.currentTimeMillis()));
	  return request;
	 }
}
