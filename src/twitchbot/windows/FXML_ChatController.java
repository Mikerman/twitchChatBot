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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private TabPane tabPane;
    @FXML
    private Tab newTab;
    @FXML
    void handleButtonAction(ActionEvent event) {
        main.myClient.send(textInput.getText());
    }
    
        @FXML
    void handleNewTab(Event event) {
        if(newTab.isSelected())
            this.addTab(textInput.getText());
    }
    private Tab getTab(String tabName)
    {
        Tab[] array= (Tab[]) tabPane.getTabs().toArray();
        for (int i = 0; i < array.length; i++) {
            if(array[i].getText().equals(tabName))
                return array[i];
        }
        return null;
    }
    
    public Tab addTab(String tab)
    {
        if(getTab(tab)!=null)
            return getTab(tab);
        Tab addedTab = new Tab(tab);
        addedTab.setContent(new TextArea());
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
    }
    public void printMessage(String tab,String message)
    {
        Tab act = getTab(tab);
        if(act == null)
            act =addTab(tab);
        TextArea txtArea = (TextArea)act.getContent();
        txtArea.setText(txtArea.getText()+"\n"+message);
    }
    public void setMain(TwitchBot main)
    {
        this.main = main;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
