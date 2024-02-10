package com.example.food;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FoodMenuController extends ParentController{

    @FXML
    private VBox mainVBox;

    @FXML
    private VBox menuList;
    @FXML
    private Button activatedButton;
    @FXML
    private VBox selectedList;
    @FXML
    private HBox hbox;
    boolean check = true;

    @FXML
    private Label totalPrice;

Conn conn = new Conn();
    private final ArrayList<Menu> liste = conn.genereteMenuList();

    ArrayList<String> menuItemsCountList = new ArrayList<>();
    private double total = 0;
    private final Map<String, List<Menu>> groupedByType = liste.stream()
            .collect(Collectors.groupingBy(Menu::getType));


    @FXML
    private void initialize() {


        groupedByType.forEach((key, value) -> {

            System.out.println("Type: " + key);

            Button button = new Button();
            button.setText(key);
            button.getStyleClass().add("listButtons");
            button.setUserData(key);
            button.setOnAction(this::menuSection);
            menuList.getChildren().addAll(button);

            // ilk menu elemanını "active" etmek için
            if (check){
                activatedButton = button;
                menuSection(new ActionEvent(button,null));
                check=false;

            }
        });
    }

    @FXML
    protected void menuSection(ActionEvent event) {
        activatedButton.getStyleClass().remove("activated");
        Button clickedButton = (Button) event.getSource();
        activatedButton = clickedButton;
        clickedButton.getStyleClass().add("activated");
        String buttonText = (String) clickedButton.getUserData();
        List<Menu> list = groupedByType.get(buttonText);
        int len = list.size();

        int columnCount = 3;
        mainVBox.getChildren().clear();
        for (int i = 0; i < len; i+=3) {

            HBoxVBoxes rowHBox = new HBoxVBoxes(columnCount,i,list,len );
            for (javafx.scene.Node node : rowHBox.getChildren()) {
                node.setOnMouseClicked(this::handleVBoxClick);
            }

            mainVBox.getChildren().add(rowHBox);
        }
    }

    protected void handleVBoxClick(MouseEvent event) {

        VBox vd = (VBox) event.getSource();
        String nameStr = ((Label) vd.getChildren().get(1)).getText();
        double priceDouble = Double.parseDouble(((Label) vd.getChildren().get(2)).getText().substring(2));
        //yeni Labeller oluşturuyoruz çünkü Card'taki labellere direkt erişim kopyalamak bazı sorunlara sebep olur
        Label name = new Label(nameStr);
        Label price = new Label("₺ "+priceDouble);
            Label count = new Label("1");
            Label x = new Label("x");
            total+=priceDouble;
            totalPrice.setText(total+" ₺");
        if (menuItemsCountList.contains(nameStr)){
            System.out.println("x");
            for (javafx.scene.Node node : selectedList.getChildren()) {

                HBox hh = (HBox) node;
                Label label = ((Label) hh.getChildren().get(2));
                String item = label.getText();

                if (item.equals(nameStr)){
                    Label label1 = ((Label) hh.getChildren().get(1));
                    int num = Integer.parseInt(label1.getText())+1;
                    label1.setText(num+"");
                }
            }
        }else {
            hbox = new HBox();
            hbox.setPrefWidth(800);
            menuItemsCountList.add(nameStr);

            name.getStyleClass().add("listLabelTop");
            price.getStyleClass().add("listLabelBottom");
            count.getStyleClass().add("listLabelBottom2");
            x.getStyleClass().add("listLabelBottom2");

            hbox.getChildren().addAll(x, count, name, price);
            selectedList.getChildren().add(hbox);
        }


    }


    @FXML
    protected void saveOrder(ActionEvent event) throws IOException {
        Waiter waiter = Waiter.getInstance();
        waiter.checkTable(selectedList);
        nextPage();

    }




    private static class HBoxVBoxes extends HBox {
        HBoxVBoxes(int count,int loc, List<Menu> content, int len) {

            for (int i = 0; i < count && loc < len-1; i++) {
                setSpacing(20);
                VBox vbox = new VBox();
                vbox.setPrefWidth(100);
                vbox.setPrefHeight(100);
                vbox.getStyleClass().add("card");
                Rectangle rectangle = new Rectangle(0, 0, 110, 90);
                rectangle.setArcWidth(30.0);
                rectangle.setArcHeight(30.0);


                    System.out.println(content.get(loc).getName().toLowerCase().replace(" ",""));

                Image image = null;
                try {
                    String imagePath = "file:src/main/resources/com/example/food/img/" +
                            content.get(loc).getName().toLowerCase().replace(" ", "") + ".png";
                    image = new Image(imagePath, 110, 90, false, false);
                } catch (IllegalArgumentException e) {
                    System.out.println("Resim dosyası bulunamadı veya hatalı: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Bilinmeyen bir hata oluştu: " + e.getMessage());
                }

                if (image == null || image.isError()) {
                    // Eğer image hala null veya bir hata durumu varsa, varsayılan resmi kullan
                    System.out.println("Varsayılan resim kullanılıyor.");
                    image = new Image("file:src/main/resources/com/example/food/download.jpg", 110, 90, false, false);
                }


                rectangle.setFill(new ImagePattern(image));


                rectangle.setEffect(new DropShadow(5, Color.BLACK));  // Shadow
                Label label1 = new Label(content.get(loc).getName());
                Label label2 = new Label("₺ "+content.get(loc).getPrice());
                label2.getStyleClass().add("cardLabelBottom");
                vbox.getChildren().addAll(rectangle, label1, label2);

                getChildren().add(vbox);
                loc+=1;
            }
        }
    }


    @FXML
    protected void sendOrder(ActionEvent event) throws IOException{
        Waiter waiter = Waiter.getInstance();
        Table table = Table.getInstance();
        waiter.checkTable(selectedList);
        waiter.updateTable("0",table.getTableID());
        nextPage();

    }


    @Override
    public void prevPage() {

    }

    @Override
    public void nextPage() {
        setContent("hello-view");

    }
}