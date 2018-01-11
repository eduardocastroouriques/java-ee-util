package br.com.auttar.common.monitoring;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import br.com.auttar.common.constants.LogConstants;
import br.com.auttar.common.util.MonitoringUtil;

@Stateless
public class MetricService {

	private ConcurrentMap<String, ConcurrentHashMap<Integer, List<MonitoringModel>>> timeMap;
	private ConcurrentHashMap<Integer, List<MonitoringModel>> metricStatus;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private Logger logger = LogManager.getLogger(LogConstants.MONITORING_LOGGER_NAME);

	public MetricService() {
		timeMap = new ConcurrentHashMap<>();
	}

	public void increaseCount(String request, int status, Long timeInitial, Long timeFinal) {
		cleanMonitoring();
		String time = dateFormat.format(timeInitial);
		Long durationTime = timeFinal - timeInitial;

		// A chave do timemap é o request
		// Para retornar se já existe um objeto daquela chave a gente usa o get
		// Caso nao tenha nenhum dado com aquela chave a gente instancia com
		// essa chave
		metricStatus = timeMap.get(request);
		if (metricStatus == null) {
			metricStatus = new ConcurrentHashMap<>();
		}
		if (metricStatus.get(status) == null) {
			metricStatus.put(status, new ArrayList<MonitoringModel>());
			MonitoringModel mm = new MonitoringModel(durationTime, time);
			metricStatus.get(status).add(mm);
		} else {
			MonitoringModel mm = new MonitoringModel(durationTime, time);
			metricStatus.get(status).add(mm);
		}
		timeMap.put(request, metricStatus);
	}

	private void cleanMonitoring() {
		if (metricStatus != null) {
			Enumeration<Integer> chaves = metricStatus.keys();
			while (chaves.hasMoreElements()){
				Integer chave = chaves.nextElement();
				List<MonitoringModel> timestamp = metricStatus.get(chave);
				for (Iterator<MonitoringModel> i = timestamp.iterator(); i.hasNext();) {
					try {
						MonitoringModel mm = i.next();
						Long timestampLong = dateFormat.parse(mm.getTimestamp()).getTime();
						if (timestampLong < System.currentTimeMillis() - 300000) {
							i.remove();
							metricStatus.replace(chave, timestamp);
						}
					} catch (ParseException e) {
				    	logger.error(e.getMessage(), e);
					}
				}

			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Map getFullMetric() {
		return timeMap;
	}

	public Map<String, Map<String, Object>> getMetricPagina() {
		Map<String, Map<String, Object>> metricPagina = new ConcurrentHashMap<>();
		Map<String, Object> details;

		Map<String, Map<String, Long>> metrics = getResumedMetric();
		double countSucess;
		double countErrors;
		for (String nomeDoMetodo : metrics.keySet()) {
			details = new ConcurrentHashMap<>();
			countSucess = 0;
			countErrors = 0;
			Map<String, Long> status = metrics.get(nomeDoMetodo);
			for (String statusAux : status.keySet()) {
				Long totalReqStatus = status.get(statusAux);
				if (statusAux.startsWith("2")) {
					countSucess += totalReqStatus;
				} else {
					if (statusAux.startsWith("mean")) {
						long millis = status.get(statusAux);
						String hms = String.format("%02d:%02d:%02d.%03d", TimeUnit.MILLISECONDS.toHours(millis),
								TimeUnit.MILLISECONDS.toMinutes(millis)
										- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
								TimeUnit.MILLISECONDS.toSeconds(millis)
										- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
								TimeUnit.MILLISECONDS.toMillis(millis)
										- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)));
						details.put(statusAux, hms);
					} else {
						countErrors += totalReqStatus;
					}
				}
			}
			double total = countSucess + countErrors;
			double percentSucess = 0;
			double percentErrors = 0;
			if (total != 0) {
				percentSucess = MonitoringUtil.round((countSucess / total) * 100, 2);
				percentErrors = MonitoringUtil.round((countErrors / total) * 100, 2);
			}
			details.put("total_requisitions", (long) total);
			details.put("tax_sucess", percentSucess);
			details.put("tax_errors", percentErrors);
			metricPagina.put(nomeDoMetodo, details);
		}
		return metricPagina;
	}

	public Map<String, Map<String, Long>> getResumedMetric() {
		Map<String, Map<String, Long>> result = new HashMap<>();
		Map<String, Long> detail = new HashMap<>();

		long durationTotal = 0;
		int total = 0;


		if (timeMap != null) {
			Set<String> methodNames = timeMap.keySet();
			for (String methodName : methodNames) {
				ConcurrentHashMap<Integer, List<MonitoringModel>> tempConcurrentHashMap = timeMap.get(methodName);
				if(tempConcurrentHashMap == null){
					tempConcurrentHashMap = new ConcurrentHashMap<Integer, List<MonitoringModel>>();
				}
				Map<Integer, List<MonitoringModel>> mapThreadSafe = new HashMap<>(tempConcurrentHashMap);
				Set<Integer> statusCodes = mapThreadSafe.keySet();
				for (Integer statusCode : statusCodes) {
					detail.put(String.valueOf(statusCode), Long.valueOf(mapThreadSafe.get(statusCode).size()));
					for(Integer key : mapThreadSafe.keySet()){
						for (MonitoringModel m : mapThreadSafe.get(key)) {
							durationTotal += m.getDuration();
							total++;
						}
					}
					if (total != 0)
						detail.put("mean_duration", durationTotal / total);
					else
						detail.put("mean_duration", 0L);
					result.put(methodName, detail);
				}
				detail = new HashMap<>();
				durationTotal = 0;
				total = 0;
			}
		}
		return result;
	}

}
