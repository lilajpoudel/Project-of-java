package com.example.inventorymanagementsystem;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    Connection con= null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TextField tDescription;

    @FXML
    private TextField tId;

    @FXML
    private TextField tName;

    @FXML
    private TextField tQuantity;
    @FXML
    private TableColumn<Product,String> coldescription;

    @FXML
    private TableColumn<Product,Integer> colid;

    @FXML
    private TableColumn<Product,String> colname;

    @FXML
    private TableColumn<Product, Integer> colquantity;
    @FXML
    private TableView<Product> table;
    int id  =0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public ObservableList<Product> getProducts(){
        ObservableList<Product> products = FXCollections.observableArrayList();

        String query = "select* from products";
        con = DBConnection.getCon();
        try {
            st = con.prepareStatement(query);
            rs= st.executeQuery();
            while (rs.next()){
                Product st = new Product();
                st.setId(rs.getInt("id"));
                st.setName(rs.getString("Name"));
                st.setDescription(rs.getString("Description"));
                st.setQuantity(rs.getString("Quantity"));

                products.add(st);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }
    public void showProducts(){
        ObservableList<Product> list = getProducts();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<Product,String>("Name"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Product,String>("Description"));
        colquantity.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Quantity"));

    }

    @FXML
    void clearField(ActionEvent event) {
        clear();


    }

    @FXML
    void createProduct(ActionEvent event) {

        String query = "insert into products(Name,Description,Quantity) values (?,?,?)";
        con = DBConnection.getCon();
        try {
            st= con.prepareStatement(query);
            st.setString(1,tName.getText());
            st.setString(2,tDescription.getText());
            st.setString(3,tQuantity.getText());
            st.executeUpdate();
            clear();
            showProducts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void getData(MouseEvent event) {
        Product product = table.getSelectionModel().getSelectedItem();
        id = product.getId();

        tName.setText(product.getName());
        tDescription.setText(product.getDescription());
        tQuantity.setText(product.getQuantity());
        btnSave.setDisable(true);


    }
    void clear(){
        tName.setText(null);
        tDescription.setText(null);
        tQuantity.setText(null);
        btnClear.setDisable(false);

    }

    @FXML
    void deleteProduct(ActionEvent event) {
        String delete = "delete from products where id =?";
        con = DBConnection.getCon();
        try {
            st= con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            showProducts();
            clear();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateProduct(ActionEvent event) {
        String update ="update products set Name=?, Description = ?,Quantity =?  where id = ?";
        con = DBConnection.getCon();
        try {
            st= con.prepareStatement(update);
            st.setString(1,tName.getText());
            st.setString(2,tDescription.getText());
            st.setString(3,tQuantity.getText());
            st.setInt(4,id);
            st.executeUpdate();
            showProducts();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
