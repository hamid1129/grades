package com.example.myvaadin;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Grades implements Serializable {
	
	
	private static final long serialVersionUID = -4808347562304355838L;
	private String username;
	private String password;
	private URL url;
	private WebClient web;

	private HtmlPage page1, page2, page3, page4;

	public Grades(String username, String password) {
		this.username = username;
		this.password = password;

	}

	public void setURL(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public boolean athenticated() {

		web = new WebClient();
		try {
			page1 = web.getPage(url);
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}
		HtmlForm form = page1.getFormByName("form2");

		form.getInputByName("txtUserName").setAttribute("value", username);
		form.getInputByName("txtPassword").setAttribute("value", password);
		try {
			page2 = form.getInputByName("LoginButton").click();
		} catch (ElementNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (page2.getUrl().toString()
				.equals("http://78.39.200.210/Default.aspx")) {
			return true;
		} else {
			web.closeAllWindows();
			return false;
		}

	}

	public List<HtmlTableRow> getcources() {
		String eteraz = "eteraz_nomreh.aspx";
		try {
			page3 = web.getPage(url + "/" + eteraz);
			List<HtmlAnchor> anch = page3.getAnchors();

			page4 = page3.getAnchorByHref(
					anch.get(anch.size() - 1).getHrefAttribute()).click();

			HtmlTable tbl = page4
					.getHtmlElementById("ctl00_ContentPlaceHolder1_grdWorkBook");
			
			return tbl.getRows();
		} catch (FailingHttpStatusCodeException | IOException e) {

			e.printStackTrace();
		}
		return null;
	}
}
