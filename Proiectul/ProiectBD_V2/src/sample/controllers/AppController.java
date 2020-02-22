package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AppController implements Initializable {


    @FXML
    private TableView<ModelTableApp> tabel;
    @FXML
    private TableColumn<ModelTableApp, Integer> col_ID;
    @FXML
    private TableColumn<ModelTableApp, String> col_NumePizza;
    @FXML
    private TableColumn<ModelTableApp, String> col_Ingrediente;
    @FXML
    private TableColumn<ModelTableApp, String> col_Origine;
    @FXML
    private TableColumn<ModelTableApp, String> col_Pret;
    @FXML
    private TableColumn<ModelTableApp, String> col_Imagine;

    @FXML
    private TextField filterField;

    ObservableList<ModelTableApp> oblist = FXCollections.observableArrayList();

    HashMap<Integer, String> pizzaList = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pizzaList.put(1,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza1.png");
        pizzaList.put(2,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza2.png");
        pizzaList.put(3,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza3.png");
        pizzaList.put(4,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza4.png");
        pizzaList.put(5,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza5.png");
        pizzaList.put(6,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza6.png");
        pizzaList.put(7,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza8.png");
        pizzaList.put(8,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza9.png");
        pizzaList.put(9,"file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza10.png");
        //pizzaList.put("10","file:///C:/Development/Workspace/ProiectBD_V2/src/sample/controllers/Pizza/pizza10.png");

        try {
            Connection con = DbConnect.getInstance().getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Pizza");

            while (rs.next()) {

                int PizzaID = rs.getInt("Pizza_ID");
                String nume = rs.getString("Nume");
                String Origine = rs.getString("Origine");
                int pret = rs.getInt("Pret");

                String imgSource  = pizzaList.get(PizzaID);

                String ingrediente = "";
                ArrayList<String> ingredientePePizza = new ArrayList<>();
                ResultSet PizzaIngredient = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.IngredientPizza where Pizza_ID = '"+PizzaID+"' ");
                while(PizzaIngredient.next()){

                    ResultSet IngredienteResult = con.createStatement().executeQuery("select * from PIZZADANIEL.dbo.Ingrediente ");
                    //where Ingredient_ID = '"+rs.getString("Ingredient_ID")+"'
                    while(IngredienteResult.next()){
                        if(PizzaIngredient.getString("Ingredient_ID").equals(IngredienteResult.getString("Ingredient_ID"))) {
                            ingrediente += IngredienteResult.getString("Nume") + " ";
                            break;
                        }
                    }
                }



                Image img = new Image(imgSource);
                ImageView photo = new ImageView(img);
                ModelTableApp modelTableApp = new ModelTableApp(PizzaID, nume,ingrediente,Origine, pret, photo);

                oblist.add(modelTableApp);
                tabel.setItems(oblist);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_NumePizza.setCellValueFactory(new PropertyValueFactory<>("numePizza"));
        col_Ingrediente.setCellValueFactory(new PropertyValueFactory<>("ingrediente"));
        col_Origine.setCellValueFactory(new PropertyValueFactory<>("origine"));
        col_Pret.setCellValueFactory(new PropertyValueFactory<>("pret"));
        col_Imagine.setPrefWidth(200);
        col_Imagine.setCellValueFactory(new PropertyValueFactory<>("imagine"));


        tabel.setItems(oblist);

        FilteredList<ModelTableApp> filteredList = new FilteredList<>(oblist, b->true );

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(modelTableApp -> {

                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }


                String lowerCaseFilter = newValue.toLowerCase();
                Integer x = Integer.valueOf(newValue);

                if(modelTableApp.getIngrediente().toLowerCase().contains(lowerCaseFilter)) {
                    return  true;
                } else if(modelTableApp.getNumePizza().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if(modelTableApp.getOrigine().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if(x.intValue() == modelTableApp.getPret()){
                    return true;
                }else{
                    return false;
                }
            } );
        });

        SortedList<ModelTableApp> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(tabel.comparatorProperty());

        tabel.setItems(sortedList);

    }

    @FXML
    private Label Update;

    @FXML
    public void addToCart(ActionEvent actionEvent) {

        ObservableList<ModelTableApp> cos = FXCollections.observableArrayList();

        try{
            cos.add(tabel.getSelectionModel().getSelectedItem());
            System.out.println(cos);
//            System.out.println("====");

            String x = "";
            for(int i = 0; i<cos.size(); i++){
                x += cos.get(i).getNumePizza() + " ,";
            }
            Update.setText(x + " a fost adaugata la cos");
            Cos cosCumparaturi = new Cos(cos);

//            cosCumparaturi.setCos(checkDuplicate(cosCumparaturi.getCoslist()));
//            System.out.println(cosCumparaturi);

        } catch (NullPointerException e){
            System.out.println(e.getMessage());
            Update.setText("Nu ai selectat nimic!");
        }



    }

    public ObservableList<ModelTableApp> checkDuplicate(ObservableList<ModelTableApp> coslist) {

        ObservableList<ModelTableApp> coslistNew = FXCollections.observableArrayList();
        coslistNew.addAll(coslist);
        int pos[] = new int[coslistNew.size()];
        int k = 0;
        for (int i = 0; i < coslistNew.size(); i++) {
            for (int j = 1; j < coslistNew.size(); j++) {
                if (coslistNew.get(i).getNumePizza().equals(coslistNew.get(j).getNumePizza())) {
                    coslistNew.get(i).setCantitate(coslistNew.get(i).getCantitate() + 1);
                    pos[k++] = j;
                }
            }
        }
        for (int i = 0; i < coslistNew.size(); i++) {
            coslistNew.remove(pos[i]);
        }
        return coslistNew;
    }

    public void goToCos(MouseEvent mouseEvent) throws IOException {
//        Cos cosnou = new Cos();
//        System.out.println(cosnou.getCoslist());
        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/cos.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root, 1540, 800);

        stage.setScene(scene);


    }

    public void signup(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/login.fxml"));
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root, 1540, 800));

        Cos cosDeStergere = new Cos();
        for(int i = cosDeStergere.getCoslist().size(); i>=0; i--){
            cosDeStergere.getCoslist().remove(i);
        }

    }

    public void goToCont(MouseEvent mouseEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/sample/views/cont.fxml"));

        Node node = (Node) mouseEvent.getSource();

        Stage stage  = (Stage) node.getScene().getWindow();

        Scene scene = new Scene(root, 1540, 800);

        stage.setScene(scene);

    }
}
