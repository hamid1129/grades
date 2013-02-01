package com.example.myvaadin;

import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

public class MyvaadinApplication extends Application implements
		Button.ClickListener {

	private static final long serialVersionUID = 2381170695158532097L;
	private static final String HOME_PAGE_URL = "http://78.39.200.210";

	Window mainWindow;
	Grades gr;
	TextField txtUsername;
	PasswordField txtPassword;

	@Override
	public void init() {
		mainWindow = new Window("نمایش نمرات");
		txtUsername = new TextField("شماره دانشجویی");
		txtPassword = new PasswordField("رمز عبور");
		Button btn = new Button("نمایش نمرات");

		btn.addListener(this);
		mainWindow.addComponent(txtUsername);
		mainWindow.addComponent(txtPassword);
		mainWindow.addComponent(btn);

		setMainWindow(mainWindow);
	}

	public void buttonClick(ClickEvent event) {
		mainWindow.addWindow(createWindow());
	}

	public Window createWindow() {
		Window subwindow = new Window();
		Table table = new Table("نمرات اعلام شده");
		table.addContainerProperty("نام درس", String.class, null);
		table.addContainerProperty("نمره", String.class, null);

		gr = new Grades(txtUsername.getValue().toString(), txtPassword
				.getValue().toString());
		gr.setURL(HOME_PAGE_URL);
		if (gr.athenticated()) {
			int i =0;
			for (HtmlTableRow row : gr.getcources()) {
				if (!row.getCell(5).asText().equals("0.00")) {
					table.addItem(new Object[] {row.getCell(1).asText(),row.getCell(5).asText()},new Integer(i));
					i++;

				}
			}
		}
		subwindow.addComponent(table);
		return subwindow;

	}

}
