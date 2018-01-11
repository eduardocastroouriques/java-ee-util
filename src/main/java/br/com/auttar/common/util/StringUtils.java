package br.com.auttar.common.util;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.swing.text.MaskFormatter;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.com.auttar.common.constants.LogConstants;

@ApplicationScoped
public class StringUtils {

	private static Logger logger = LogManager.getLogger(LogConstants.UTIL_LOGGER_NAME);
	
	public static String formatForPOS(String nonAsciiString) {
		if (nonAsciiString == null) {
			return "";
		}
		String asciiString = Normalizer.normalize(nonAsciiString, Normalizer.Form.NFD);
		return asciiString.replaceAll("[^\\p{ASCII}]", "");
	}

	public static String formatCurrency(Float currencyValue) {
		if (currencyValue == null) return "";
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
		DecimalFormat df = (DecimalFormat) nf;
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(3);
		return df.format(currencyValue);
	}
	
	public static String formatInteger(Integer integerValue) {
		if (integerValue == null) return "";
		return integerValue.toString();
	}

	public static String leftPaddingSpaces(String str, int num) {
		if (str == null) return "";
		
		return String.format("%1$" + num + "s", str);
	}

	public static String leftPaddingZeros(String s, int n) {
		if (s == null) return "";
		
		if (n > s.length()) {
			return String.format("%0" + (n - s.length()) + "d", 0) + s;
		} else {
			return s;
		}
	}

	public static String rightPaddingSpaces(String str, int num) {
		if (str == null) return "";
		
		return String.format("%1$-" + num + "s", str);
	}

	public static Object formatNumeric(Float numericValue) {
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
		DecimalFormat df = (DecimalFormat) nf;
		df.setMinimumFractionDigits(0);
		return df.format(numericValue);
	}

	public static String formatStateRegistration(String state_registration) {
		if (state_registration == null) return "";
		
		state_registration = state_registration.replaceAll("[^\\d]", "");
		state_registration = StringUtils.leftPaddingZeros(state_registration, 12);

		try {
			MaskFormatter mf = new MaskFormatter("############");
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(state_registration);
		} catch (ParseException e) {
	    	logger.error(e.getMessage(), e);
			return state_registration;
		}
	}

	public static String formatCnpj(String cnpj) {
		if (cnpj == null) return "";
		
		cnpj = cnpj.replaceAll("[^\\d]", "");
		cnpj = StringUtils.leftPaddingZeros(cnpj, 14);

		try {
			MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(cnpj);
		} catch (ParseException e) {
	    	logger.error(e.getMessage(), e);
			return cnpj;
		}
	}

	public static String formatConsumerIdentification(String consumer_identification) {
		if (consumer_identification == null) return "";
		
		consumer_identification = consumer_identification.replaceAll("[^\\d]", "");
		String format = "";

		if (consumer_identification.length() == 11) {
			// CPF
			format = "###.###.###-##";
		} else if (consumer_identification.length() == 14) {
			// CNPJ
			format = "##.###.###/####-##";
		} else {
			return consumer_identification;
		}

		try {
			MaskFormatter mf = new MaskFormatter(format);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(consumer_identification);
		} catch (ParseException e) {
	    	logger.error(e.getMessage(), e);
			return consumer_identification;
		}
	}

	public static String formatMapAsQueryParameters(MultivaluedMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, List<String>> entry : map.entrySet()) {
			for (String value : entry.getValue()) {
				sb.append(sb.length() > 0 ? "&" : "");
				sb.append(entry.getKey() + "=" + value);
			}
		}
		return sb.toString();
	}

	public static String formatMapAsJson(MultivaluedMap<String, String> map) {
		JSONObject jsonObj = new JSONObject();
		for (Entry<String, List<String>> entry : map.entrySet()) {
			List<String> values = entry.getValue();
			try {
				if (values.size() == 1) {
					jsonObj.put(entry.getKey(), values.get(0));
				} else {
					jsonObj.append(entry.getKey(), values);
				}
			} catch (JSONException e) {
		    	logger.error(e.getMessage(), e);
			}

		}
		return jsonObj.toString();
	}
	
	public static boolean isEmptyString(final String s) {
	  // Null-safe, short-circuit evaluation.
	  return s == null || s.trim().isEmpty();
	}

}
