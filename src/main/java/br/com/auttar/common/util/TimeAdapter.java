package br.com.auttar.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;


import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TimeAdapter extends XmlAdapter<String, Date> {

	private final SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");

	@Override
	public String marshal(Date v) throws Exception {
		synchronized (timeFormat) {
			return timeFormat.format(v);
		}
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		synchronized (timeFormat) {
			return timeFormat.parse(v);
		}
	}

}
