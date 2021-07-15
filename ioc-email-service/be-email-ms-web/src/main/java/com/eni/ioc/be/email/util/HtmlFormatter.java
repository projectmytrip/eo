package com.eni.ioc.be.email.util;

import java.util.Arrays;
import java.util.List;

public class HtmlFormatter {
	final static String TABLE_STYLE = " style='border:1px solid black;font-family:Calibri,sans-serif;border-collapse: collapse;' cellspacing='0' cellpadding='0' ";
	final static String TH_PRIMARY_STYLE = " style='font-weight:bold; background:#BDD7EE; text-align:center' ";
	final static String TH_SECONDARY_STYLE = " style='font-weight:bold; background:#DDEBF7; text-align:left' ";
	static int valuesReplacedIdx = 0;

	public static String createTable(String title, String subtitle, List<List<String>> headers, List<String> rows,
			int colNums, boolean includeNumRows, List<String> valuesToReplace) {
		valuesReplacedIdx = 0;
		StringBuilder table = new StringBuilder();

		if (title != null && !title.equals("")) {
			table.append("<h1>").append(title).append("</h1>");
		}

		if (subtitle != null && !subtitle.equals("")) {
			table.append("<p>").append(subtitle).append("</p>");
		}

		table.append("<table").append(TABLE_STYLE).append(">");

		for (List<String> header : headers) {
			int colspan = (colNums - header.size() + 1) + (includeNumRows && header.size() == 1? 1 : 0);
			String head = createRow(header, "th", colspan, valuesToReplace, includeNumRows, headers.indexOf(header));
			table.append(head);
		}

		for (String line : rows) {
			List<String> columns = Arrays.asList(line.split(","));
			String row = createRow(columns, "td", 1, valuesToReplace, includeNumRows, rows.indexOf(line));
			table.append(row);
		}

		table.append("</table>");

		return table.toString();

	}

	static String createRow(List<String> columns, String rowTag, int colspan, List<String> valuesToReplace,
			boolean includeNumRows, int rowNum) {
		StringBuilder row = new StringBuilder();
		row.append("<tr>");

		if (includeNumRows && (rowTag.equals("td") || (rowTag.equals("th") && colspan <= 1))) {
			row.append("<").append(rowTag).append(">");
			row.append(rowTag.equals("th") ? "" : rowNum + 1);
			row.append("</").append(rowTag).append(">");
		}

		for (String column : columns) {

			if (rowTag.equals("th") && colspan > 1) {
				row.append("<").append(rowTag).append(TH_PRIMARY_STYLE).append("colspan='").append(colspan)
						.append("'>");
			} else if (rowTag.equals("th")) {
				row.append("<").append(rowTag).append(TH_SECONDARY_STYLE).append(">");
			} else {
				row.append("<").append(rowTag).append(">");
			}

			if (column.contains("§") && valuesToReplace != null) {
				column = column.replaceAll("§([^<]*)§", valuesToReplace.get(valuesReplacedIdx));
				valuesReplacedIdx++;
			}

			if (column.equals("OK")) {
				column = "<span style='color:green'>OK</span>";
			} else if (column.equals("KO")) {
				column = "<span style='color:red'>KO</span>";
			}

			row.append(column);
			row.append("</").append(rowTag).append(">");
		}
		row.append("</tr>");
		return row.toString();
	}

}
