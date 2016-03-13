/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitchbot.windows;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import twitchbot.ircClient;

/**
 * FXML Controller class
 *
 * @author Jakob
 */
public class FXML_ChatController implements Initializable {

    private TwitchBot main;
    private ircClient myClient;
    @FXML
    private TextField textInput;
     @FXML
    private TextField newChanelName;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab newTab;
    @FXML
    void handleButtonAction(ActionEvent event) {
        String chanelName = tabPane.getSelectionModel().getSelectedItem().getText();
        main.myClient.sendChat(chanelName,textInput.getText());
        printMessage(chanelName,"you",textInput.getText());
        textInput.setText("");
    }
    
        @FXML
    void handleNewTab(Event event) 
    {
        this.addTab(newChanelName.getText());
        newChanelName.setText("");
    }
    private Tab getTab(String tabName)
    {
        Tab[] array= tabPane.getTabs().toArray(new Tab[ tabPane.getTabs().size()]);
        for (Tab array1 : array) 
            if (array1.getText().toLowerCase().equals(tabName.toLowerCase()))
                return array1;
        return null;
    }
    
    public Tab addTab(String neuerTab)
    {
        if(neuerTab.equals(""))
            return null;
        if(getTab(neuerTab)!=null)
            return getTab(neuerTab);
        tabPane.getSelectionModel().clearSelection();
        myClient.joinChat(neuerTab);
        Tab addedTab = new Tab(neuerTab);
        TextArea newTxtArea = new TextArea(">"+neuerTab);
        newTxtArea.setEditable(false);
        newTxtArea.wrapTextProperty().set(true);
        addedTab.setContent(newTxtArea);
        tabPane.getTabs().remove(newTab);
        tabPane.getTabs().add(addedTab);
        tabPane.getSelectionModel().select(addedTab);
        tabPane.getTabs().add(newTab);
        return addedTab;
    }
    public void removeTab(String tab)
    {
        Tab act = getTab(tab);
        if(act!=null)
            tabPane.getTabs().remove(act);
        myClient.leaveChat(tab);
    }
    public void printMessage(String tab,String username,String message)
    {
        Tab act = getTab(tab);
        if(act == null)
            act =addTab(tab);
        TextArea txtArea = (TextArea)act.getContent();
        txtArea.appendText("\n"+username+": "+message);
        
        //Scroll to bottom
    }
    public void setMain(TwitchBot main)
    {
        this.main = main;
        myClient = main.myClient;
        myClient.chatController = this;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
