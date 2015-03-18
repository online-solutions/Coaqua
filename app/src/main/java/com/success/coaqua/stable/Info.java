package com.success.coaqua.stable;

import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.Locale;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

import com.success.coaqua.entity.TransactionRequest;

public class Info {

	private static final String LOG = "ANDROID DEMO";

	static NumberFormat currencyFormat = NumberFormat
			.getCurrencyInstance(Locale.US);

	public static final double rate = 89.99;

	public static final double gst = 0.15;

	public static final String customerID = "91368936";

	public static final boolean debugMode = true;

	public static String getRate() {
		return currencyFormat.format(Info.rate);
	}

	public static String getPaymentUrl() {
		if (debugMode)
			return "https://api.sandbox.ewaypayments.com/DirectPayment.xml";
		return "https://www.eway.com.au/gateway_cvn/xmlpayment.asp";
	}

	public static String getTotalAmount(Integer totalBox) {
		return currencyFormat.format(totalBox * Info.rate);
	}

	public static String getGst(Integer totalBox) {
		return currencyFormat.format(totalBox * Info.rate * gst);
	}

	public static String getGrandTotal(int totalBox) {
		return currencyFormat.format((totalBox * Info.rate)
				+ (totalBox * Info.rate * gst));
	}

	public static long getGrandTotalCent(int totalBox) {
		return Math
				.round(((totalBox * Info.rate) + (totalBox * Info.rate * gst)) * 100);
	}

	public static String objectToXml(TransactionRequest req) {
		// Serialization begins:
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			// start document
			serializer.startDocument("UTF-8", false);
			// serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output",
			// true);

			serializer.startTag("", "ewaygateway");
			serializer.startTag("", "ewayCustomerID");
			serializer.text(req.getEwayCustomerID());
			serializer.endTag("", "ewayCustomerID");

			serializer.startTag("", "ewayTotalAmount");
			serializer.text(String.valueOf(req.getEwayTotalAmount()));
			serializer.endTag("", "ewayTotalAmount");

			serializer.startTag("", "ewayCustomerFirstName");
			serializer.text(req.getEwayCustomerFirstName());
			serializer.endTag("", "ewayCustomerFirstName");

			serializer.startTag("", "ewayCustomerLastName");
			serializer.text(req.getEwayCustomerLastName());
			serializer.endTag("", "ewayCustomerLastName");

			serializer.startTag("", "ewayCustomerEmail");
			serializer.text(req.getEwayCustomerEmail());
			serializer.endTag("", "ewayCustomerEmail");

			serializer.startTag("", "ewayCustomerAddress");
			serializer.text(req.getEwayCustomerAddress());
			serializer.endTag("", "ewayCustomerAddress");

			serializer.startTag("", "ewayCustomerPostcode");
			serializer.endTag("", "ewayCustomerPostcode");

			serializer.startTag("", "ewayCustomerInvoiceDescription");
			serializer.text(req.getEwayCustomerInvoiceDescription());
			serializer.endTag("", "ewayCustomerInvoiceDescription");

			serializer.startTag("", "ewayCustomerInvoiceRef");
			serializer.endTag("", "ewayCustomerInvoiceRef");

			serializer.startTag("", "ewayCardHoldersName");
			serializer.text(req.getEwayCardHoldersName());
			serializer.endTag("", "ewayCardHoldersName");

			serializer.startTag("", "ewayCardNumber");
			serializer.text(req.getEwayCardNumber());
			serializer.endTag("", "ewayCardNumber");

			serializer.startTag("", "ewayCardExpiryMonth");
			serializer.text(req.getEwayCardExpiryMonth());
			serializer.endTag("", "ewayCardExpiryMonth");

			serializer.startTag("", "ewayCardExpiryYear");
			serializer.text(req.getEwayCardExpiryYear());
			serializer.endTag("", "ewayCardExpiryYear");

			serializer.startTag("", "ewayTrxnNumber");
			serializer.endTag("", "ewayTrxnNumber");

			serializer.startTag("", "ewayOption1");
			serializer.endTag("", "ewayOption1");

			serializer.startTag("", "ewayOption2");
			serializer.endTag("", "ewayOption2");

			serializer.startTag("", "ewayOption3");
			serializer.endTag("", "ewayOption3");

			serializer.startTag("", "ewayCVN");
			serializer.text(req.getEwayCVN());
			serializer.endTag("", "ewayCVN");

			serializer.endTag("", "ewaygateway");

			serializer.endDocument();
			// end document.

			Log.i(LOG, writer.toString());

			return writer.toString();
			// Toast.makeText(getApplicationContext(), ""+ writer.toString(),
			// 0).show();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
