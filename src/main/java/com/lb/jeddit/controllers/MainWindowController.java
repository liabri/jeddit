package com.lb.jeddit.controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.lb.jeddit.Utils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;

public class MainWindowController extends AnchorPane {

	/************ FXML IMPORT ************/

	@FXML
	private AnchorPane mainAnchorPane;

	@FXML
	private AnchorPane contentAnchorPane;

	@FXML
	private TextField searchTextField;

	@FXML
	private Button homeButton;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private VBox drawerContent;

	@FXML
	private VBox drawerSubs;

	@FXML
	private ScrollPane drawerScrollPane;

	@FXML
	private MenuButton menuButton;

	/*************************************/

	private ListPostsController listPostsController = ListPostsController.getInstance();

	public MainWindowController() {
		Utils.loadFXML(this);
	}

	public void initialize() {
		//Search bar event on 'enter' key press
//		searchTextField.setOnKeyPressed(searchOnEnterEvent(""));
		//Start on listPostController
		contentAnchorPane.getChildren().add(listPostsController);


		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
					listPostsController.getFrontPage(SubredditSort.BEST, TimePeriod.ALL, false);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		}).start();



		listPostsController.prefWidthProperty().bind(contentAnchorPane.widthProperty());
		listPostsController.prefHeightProperty().bind(contentAnchorPane.heightProperty());

		//Open Settings on button press
		homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
//				listPostsController.clearPosts();
//				listPostsController.getResults("", 1, "");
			}
		});


		/** DRAWER */

		//Create Hamburger
		drawer.setSidePane(drawerContent);

		//Decline click evenets when closed
		drawer.setOnDrawerClosing(new EventHandler<JFXDrawerEvent>() {
			@Override
			public void handle(JFXDrawerEvent jfxDrawerEvent) {
				drawer.setMouseTransparent(true);
			}
		});

		//Accept click events when open
		drawer.setOnDrawerOpening(new EventHandler<JFXDrawerEvent>() {
			@Override
			public void handle(JFXDrawerEvent jfxDrawerEvent) {
				drawer.setMouseTransparent(false);
			}
		});

		drawer.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				drawer.close();
			}
		});

		//Open menu
		hamburger.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				drawer.open();
			}
		});

		/** ACCOUNT COMBO BOX */

		//Main menuButton
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0; i<1; i++) {
					//System.out.println("LOOP OUTSIDE IF");
//					if(!rc.getDoneFlag()) {
//						i--;
//
//						//Buffer
//						try {
//							Thread.sleep(200);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//					}
				}
//				Label mainLbl = new Label(rc.getUsername() + " \n" + rc.getTotalKarma() + " Karma");
//				Platform.runLater(new Runnable() {
//					@Override
//					public void run() {
//						menuButton.setGraphic(mainLbl);
//					}
//				});
			}
		}).start();

		//Log out
		Label logout = new Label(" Logout");
		Image logoutImg = new Image("com/lb/jeddit/images/logout.png");
		ImageView logoutImgView = new ImageView(logoutImg);
		logoutImgView.setFitWidth(22);
		logoutImgView.setPreserveRatio(true);
		logout.setGraphic(logoutImgView);

		Label settings = new Label(" Settings");
		Image settingsImg = new Image("com/lb/jeddit/images/settings1.png");
		ImageView settingsImgView = new ImageView(settingsImg);
		settingsImgView.setFitWidth(22);
		settingsImgView.setPreserveRatio(true);
		settings.setGraphic(settingsImgView);

		Label myAccount = new Label(" My Account");
		Image myAccountImg = new Image("com/lb/jeddit/images/myaccount.png");
		ImageView myAccountImgView = new ImageView(myAccountImg);
		myAccountImgView.setFitWidth(22);
		myAccountImgView.setPreserveRatio(true);
		myAccount.setGraphic(myAccountImgView);

		logout.setPrefWidth(161);

		//menuButton.setGraphic(mainLbl);

		MenuItem myAccountMenuItem = new MenuItem();
		myAccountMenuItem.setGraphic(myAccount);

		MenuItem settingsMenuItem = new MenuItem();
		settingsMenuItem.setGraphic(settings);

		SeparatorMenuItem separator = new SeparatorMenuItem();
		separator.setId("separator");

		MenuItem logoutMenuItem = new MenuItem();
		logoutMenuItem.setGraphic(logout);
//		logoutMenuItem.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent actionEvent) {
//				openLogin.run();
//			}
//		});

		menuButton.getItems().add(myAccountMenuItem);
		menuButton.getItems().add(settingsMenuItem);
		menuButton.getItems().add(separator);
		menuButton.getItems().add(logoutMenuItem);

		menuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(!menuButton.isShowing()) {
					contentAnchorPane.requestFocus();
				}
			}
		});

		menuButton.setOnHidden(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				contentAnchorPane.requestFocus();
			}
		});



		//Focused on background on startup
		mainAnchorPane.requestFocus();
		mainAnchorPane.setOnMouseClicked(Utils.requestFocusOnClick());
	}





}
