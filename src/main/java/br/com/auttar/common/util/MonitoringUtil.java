package br.com.auttar.common.util;

import java.math.BigDecimal;

import javax.enterprise.context.ApplicationScoped;

import br.com.auttar.common.monitoring.MetricService;

@ApplicationScoped
public class MonitoringUtil {
	private static MetricService singletonMetricService = null;

	public static MetricService getSingletonMetricService() {
		if (singletonMetricService == null) {
			singletonMetricService = new MetricService();
		}
		return singletonMetricService;
	}
	
	public static double round(double d, int decimalPlace) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
        bigDecimal = bigDecimal.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}
