package com.success.coaqua.helper;

import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.success.coaqua.entity.TransactionResponse;

public class XMLPullParserHandler {
	private XmlPullParserFactory factory;
	private XmlPullParser parser;
	private String text;
	private TransactionResponse res;

	public TransactionResponse parseResponse(InputStream stream) {

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();

			// insert response here

			// parser.setInput(response.getEntity().getContent(), null);
			parser.setInput(stream, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagname = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagname.equalsIgnoreCase("ewayresponse")) {
						// create a new instance of employee
						res = new TransactionResponse();
					}
					break;

				case XmlPullParser.TEXT:
					text = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					// if (tagname.equalsIgnoreCase("employee")) {
					// // add employee object to list
					// employees.add(employee);
					// } else

					if (tagname.equals("ewayTrxnStatus")) {
						res.setEwayTrxnStatus(text);
					}

					else if (tagname.equals("ewayTrxnNumber")) {
						res.setEwayTrxnNumber(text);
					} else if (tagname.equals("ewayTrxnReference")) {
						res.setEwayTrxnReference(text);
					} else if (tagname.equals("ewayTrxnOption1")) {
						res.setEwayTrxnOption1(text);
					} else if (tagname.equals("ewayTrxnOption2")) {
						res.setEwayTrxnOption2(text);
					} else if (tagname.equals("ewayTrxnOption3")) {
						res.setEwayTrxnOption3(text);
					} else if (tagname.equals("ewayAuthCode")) {
						res.setEwayAuthCode(text);
					} else if (tagname.equals("ewayReturnAmount")) {
						res.setEwayReturnAmount(text);
					} else if (tagname.equals("ewayTrxnError")) {
						res.setEwayTrxnError(text);
					}
					break;

				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return res;
	}

}
