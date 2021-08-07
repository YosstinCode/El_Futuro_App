package View;

import controller.ActionsApp;
import model.Crud;
import model.DataBase;
import model.models.Product;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class App extends JFrame {
    //get screen size
    private static final Dimension sizeScreen = Toolkit.getDefaultToolkit().getScreenSize();
    //calculate height and width frame whit screen size
    protected static final int heightFrame = (int) (sizeScreen.height * 0.7);
    protected static final int widthFrame = (int) (sizeScreen.width * 0.7);

    //variable database
    Connection dbConnection;
    private ArrayList<Product> products;

    //variable section registrar
    private String[] successRegisterProductText;

    //variables table
    //flag column insert
    private boolean isColumns = false;
    // data
    private DefaultTableModel modelData = new DefaultTableModel();

    //variables of GUI form
    private JPanel root;
    private JPanel registrar;
    private JLabel itemRegistrar;
    private JPanel items;
    private JLabel titleRegistrar;
    private JPanel formPanel;
    private JPanel buttonRegistrarPanel;
    private JPanel titleRegistrarPanel;
    private JTextField nombreInputRegistrar;
    private JPanel formName;
    private JLabel nombreLabel;
    private JPanel formCount;
    private JLabel cantidadLabel;
    private JPanel formPrice;
    private JLabel precioLabel;
    private JPanel formCategory;
    private JLabel categoriaLabel;
    private JComboBox categoriaComboBoxRegistrar;
    private JTextField cantidadInputRegistrar;
    private JTextField precioInputRegistrar;
    private JPanel consultar;
    private JLabel itemConsultar;
    private JPanel forMoveWindowNav;
    private JLabel title;
    private JTextField nombreInputConsultar;
    private JPanel controlPanel;
    private JPanel titleConsultarPanel;
    private JLabel titleConsultar;
    private JPanel tableConsutarPanel;
    private JPanel formAndButtonsPanel;
    private JPanel formConsultarPanel;
    private JPanel buttonConsultarPanel;
    private JButton consultarButton;
    private JButton actualizarButton;
    private JButton deleteButton;
    private JButton registrarButton;
    private JComboBox categoriaComboBoxConsultar;
    private JPanel spacer;
    private JTextField cantidadInputConsultar;
    private JTextField precioInputConsultar;
    private JButton minimizedButton;
    private JButton closeButton;
    private JPanel nav;
    private JPanel forMoveWindow;
    private JTextField idInputConsultar;
    private JScrollPane scrollTable;
    private JLabel nombreInputRegistrarError;
    private JLabel cantidadInputRegistrarError;
    private JLabel precioInputRegistrarError;
    private JLabel categoriaComboBoxRegistrarError;
    private JLabel registerErrorText;
    private JTable table;
    private JButton editButton;
    private JLabel successLabel;
    private JLabel idInputTextErrorConsultar;
    private JLabel nombreInputTextErrorConsultar;
    private JLabel precioInputTextErrorConsultar;
    private JLabel categoriaComboBoxTextErrorConsultar;
    private JLabel cantidadInputErrorTextConsultar;
    private JPanel panelTable;

    public App() {
        //config JFrame
        //quitar marco
        setUndecorated(true);
        //Set size app 70% of the screen and position app in the center
        setBounds((int) (sizeScreen.width * 0.15), (int) (sizeScreen.height * 0.15), widthFrame, heightFrame);

        //rounded frame work but it's ugly in java, borders pixeled
        //setShape(new RoundRectangle2D.Double(0,0,widthFrame,heightFrame,40,40));

        //set icon frame
        ImageIcon icon = new ImageIcon("src/images/icon.png");
        setIconImage(icon.getImage());
        //Plus Styles
        //add cursor pointer or cursor hand
        addAllCursorPointer();

        //delete border of Inputs from App
        emptyBorderAll();

        //effect place holder in all the forms
        effectPLaceHolderRegistrar();
        effectPLaceHolderConsultar();

        //style scrollpane
        //style table
        //color fields table
        table.getTableHeader().setBackground(new Color(40, 66, 112));
        //color text head
        table.getTableHeader().setForeground(Color.WHITE);

        //logic and interaction.

        //close and minimized app
        openedClosedAndMinized();

        //event click items from nav
        clickEventNav(itemRegistrar);
        clickEventNav(itemConsultar);

        //event click button register section register
        clickButtonRegister();
        //event click button consult section consult
        clickButtonConsult();
        //event click button edit section consult
        clickButtonEdit();
        //event click button update section consult
        clickButtonUpdate();
        //event click button delete section consult
        clickButtonDelete();

        //add components
        add(root);
    }

    //logic methods
    private void openedClosedAndMinized() {

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                dbConnection = Crud.setConnection(DataBase.connect());
                products = ActionsApp.listProduct();
                //creating data in table
                insertDataInTable();
            }
        });

        minimizedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    setExtendedState(JFrame.NORMAL);
                }
            }
        });

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Close connection data base
                try {
                    dbConnection.close();
                    System.out.println("desconect connection!");
                } catch (SQLException exeption) {
                    System.out.println("CONNECTION| " + exeption);
                }
                System.exit(0);
            }
        });
    }

    private void clickEventNav(JLabel item) {
        //listener item nav
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //show Consultar Paner else Registrar Panel
                if (item.getText().equals("CONSULTAR")) {
                    registrar.setVisible(false);
                    consultar.setVisible(true);
                } else {
                    registrar.setVisible(true);
                    consultar.setVisible(false);
                }

            }
        });
    }
    private void clickButtonRegister(){
        registrarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //save text of field from register in variable for make a product
                String nombre=nombreInputRegistrar.getText();
                String cantidad = cantidadInputRegistrar.getText();
                String categoria = (String)categoriaComboBoxRegistrar.getSelectedItem();
                String precio = precioInputRegistrar.getText();

                //register product and return textError
                                                                                            //solution other nullpointer
                successRegisterProductText = ActionsApp.registerProducts(nombre, cantidad, (categoria == null) ? "": categoria, precio);

                //set text validation for label
                for (String errorText : successRegisterProductText) {
                    switch (errorText){
                        case "¡Tienes que ingresar un nombre!":
                            nombreInputRegistrarError.setText(successRegisterProductText[0]);
                            break;
                        case "¡Tienes que ingresar un cantidad!":
                        case "¡La cantidad tiene que ser un numero!":
                            cantidadInputRegistrarError.setText(successRegisterProductText[1]);
                            break;
                        case "¡Tienes que ingresar un categoria!":
                            categoriaComboBoxRegistrarError.setText(successRegisterProductText[2]);
                            break;
                        case "¡Tienes que ingresar un precio!":
                        case "¡El precio tiene que ser un numero!":
                            precioInputRegistrarError.setText(successRegisterProductText[3]);
                            break;
                        case "Lo sentimos ha ocurrido un error.":
                            registerErrorText.setText(successRegisterProductText[4]);
                            registerErrorText.setForeground(new Color(251,88,88));
                        case "¡El producto ha sido registrado con exito!":
                            registerErrorText.setText(successRegisterProductText[4]);
                            registerErrorText.setForeground(new Color(55,168,73));
                            //inputs register set original text
                            nombreInputRegistrar.setText("Ingresar Nombre...");
                            cantidadInputRegistrar.setText("Ingresar Cantidad...");
                            precioInputRegistrar.setText("Ingresar Precio...");
                            //label errors set text in blank
                            nombreInputRegistrarError.setText("");
                            cantidadInputRegistrarError.setText("");
                            precioInputRegistrarError.setText("");
                            categoriaComboBoxRegistrarError.setText("");
                    }
                }

            }
        });
    }

    private void clickButtonConsult(){
        consultarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                successLabel.setText("");
                String id =(idInputConsultar.getText().equalsIgnoreCase("Ingresar Id..."))? "" : idInputConsultar.getText();
                String nombre =(nombreInputConsultar.getText().equalsIgnoreCase("Ingresar Nombre..."))? "" : nombreInputConsultar.getText();
                String categoria = (String) categoriaComboBoxConsultar.getSelectedItem();
                String cantidad = (cantidadInputConsultar.getText().equalsIgnoreCase("Ingresar Cantidad..."))? "" : cantidadInputConsultar.getText();
                String precio = (precioInputConsultar.getText().equalsIgnoreCase("Ingresar Precio..."))? "" : precioInputConsultar.getText();

                products = ActionsApp.consultProducts(id,nombre,categoria,cantidad,precio);

                removeDataInTable();
                insertDataInTable();
            }
        });
    }
    private void clickButtonEdit(){
        editButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //get row from table
                int row = table.getSelectedRow();

                if(row >= 0){
                    //get data row
                    String idRow = table.getModel().getValueAt(row,0).toString();
                    String nombreRow = table.getModel().getValueAt(row,1).toString();
                    String categoriaRow = table.getModel().getValueAt(row,2).toString();
                    String cantidadRow = table.getModel().getValueAt(row,3).toString();
                    String precioRow = table.getModel().getValueAt(row,4).toString();

                    //set data in labels and comboBox

                    idInputConsultar.setText(idRow);
                    nombreInputConsultar.setText(nombreRow);
                    cantidadInputConsultar.setText(cantidadRow);
                    categoriaComboBoxConsultar.setSelectedItem(categoriaRow);
                    precioInputConsultar.setText(precioRow);

                }else{
                    //set result not found product
                    successLabel.setText("¡no ha seleccionado un producto!");
                    successLabel.setForeground(new Color(251,88,88));
                }
            }
        });
    }
    private void clickButtonUpdate(){
        actualizarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //save text of field from consult in variables for make a product
                String id = idInputConsultar.getText();
                String nombre = nombreInputConsultar.getText();
                String cantidad = cantidadInputConsultar.getText();
                String categoria = (String) categoriaComboBoxConsultar.getSelectedItem();
                String precio = precioInputConsultar.getText();

                                                                                        //solution other nullpointer
                String[] errorsTexts = ActionsApp.updateProducts(id, nombre, cantidad, (categoria == null) ? "": categoria, precio);

                //set text validation for label
                for (String errorText : errorsTexts) {
                    switch (errorText) {
                        case "¡Tienes que ingresar un id!":
                            idInputTextErrorConsultar.setVisible(true);
                            idInputTextErrorConsultar.setText(errorsTexts[5]);
                            break;
                        case "¡Tienes que ingresar un nombre!":
                            nombreInputTextErrorConsultar.setVisible(true);
                            nombreInputTextErrorConsultar.setText(errorsTexts[0]);
                            break;
                        case "¡Tienes que ingresar un cantidad!":
                        case "¡La cantidad tiene que ser un numero!":
                            cantidadInputErrorTextConsultar.setVisible(true);
                            cantidadInputErrorTextConsultar.setText(errorsTexts[1]);
                            break;
                        case "¡Tienes que ingresar un categoria!":
                            cantidadInputErrorTextConsultar.setVisible(true);
                            categoriaComboBoxTextErrorConsultar.setText(errorsTexts[2]);
                            break;
                        case "¡Tienes que ingresar un precio!":
                        case "¡El precio tiene que ser un numero!":
                            precioInputTextErrorConsultar.setVisible(true);
                            precioInputTextErrorConsultar.setText(errorsTexts[3]);
                            break;
                        case "Lo sentimos ha ocurrido un error.":

                            successLabel.setText(errorsTexts[4]);
                            successLabel.setForeground(new Color(251, 88, 88));
                            break;
                        case "¡El producto ha sido Actualizado!":
                            successLabel.setText(errorsTexts[4]);
                            successLabel.setForeground(new Color(55, 168, 73));
                            //reset original values fields or inputs
                            idInputConsultar.setText("Ingresar Id...");
                            nombreInputConsultar.setText("Ingresar Nombre...");
                            cantidadInputConsultar.setText("Ingresar Cantidad...");
                            precioInputConsultar.setText("Ingresar Precio...");
                            //aaaaahhh fuck labels T.T aaaaaah finisheeddd aaaaah!!!
                            idInputTextErrorConsultar.setVisible(false);
                            nombreInputTextErrorConsultar.setVisible(false);
                            cantidadInputErrorTextConsultar.setVisible(false);
                            precioInputTextErrorConsultar.setVisible(false);
                            categoriaComboBoxTextErrorConsultar.setVisible(false);
                            break;
                    }
                }
            }
        });
    }
    private void clickButtonDelete(){
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //get row from table
                int row = table.getSelectedRow();

                if(row >= 0){
                    //get id row
                    int idRow = Integer.parseInt(table.getModel().getValueAt(row,0).toString());
                    //delete product and set result in label
                    successLabel.setText(ActionsApp.deleteProducts(idRow));
                    successLabel.setForeground(new Color(55,168,73));
                }else{
                    //set result not found product
                    successLabel.setText("¡no ha seleccionado un producto!");
                    successLabel.setForeground(new Color(251,88,88));
                }
            }
        });
    }

    //style methods
    private void emptyBorderAll(){

        //Registrar text field
        //clear border of components
        nombreInputRegistrar.setBorder(BorderFactory.createEmptyBorder());
        cantidadInputRegistrar.setBorder(BorderFactory.createEmptyBorder());
        precioInputRegistrar.setBorder(BorderFactory.createEmptyBorder());

        //Consultar text field
        //clear border of components
        idInputConsultar.setBorder(BorderFactory.createEmptyBorder());
        nombreInputConsultar.setBorder(BorderFactory.createEmptyBorder());
        cantidadInputConsultar.setBorder(BorderFactory.createEmptyBorder());
        precioInputConsultar.setBorder(BorderFactory.createEmptyBorder());
        //table.setBorder(BorderFactory.createEmptyBorder());
        table.setShowGrid(false);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        scrollTable.setBorder(BorderFactory.createEmptyBorder());
    }
    private void addAllCursorPointer(){
        //add Cursor pointer or cursor hand in components
        itemRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        itemConsultar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //panel section
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        minimizedButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //register section
        registrarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //consult section
        consultarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        actualizarButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    }
    private void effectPLaceHolderRegistrar(){
        nombreInputRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nombreInputRegistrar.setText(null);
                nombreInputRegistrar.requestFocus();
                //delete error text nombre
                nombreInputRegistrarError.setText("");
                //delete succes text register
                registerErrorText.setText("");
            }
        });

        cantidadInputRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cantidadInputRegistrar.setText(null);
                cantidadInputRegistrar.requestFocus();
                //delete error text cantidad
                cantidadInputRegistrarError.setText("");
                //delete succes text register
                registerErrorText.setText("");
            }
        });

        precioInputRegistrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                precioInputRegistrar.setText(null);
                precioInputRegistrar.requestFocus();
                //delete error text precio
                precioInputRegistrarError.setText("");
                //delete succes text register
                registerErrorText.setText("");
            }
        });
    }
    private void effectPLaceHolderConsultar(){
        idInputConsultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                idInputConsultar.setText(null);
                idInputConsultar.requestFocus();
//                delete error text nombre
//                idInputConsultarError.setText("");
//                delete succes text register
//                ConsultarErrorText.setText("");
            }
        });

        nombreInputConsultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nombreInputConsultar.setText(null);
                nombreInputConsultar.requestFocus();
//                delete error text nombre
//                nombreInputConsultarError.setText("");
//                delete succes text register
//                ConsultarErrorText.setText("");
            }
        });

        cantidadInputConsultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cantidadInputConsultar.setText(null);
                cantidadInputConsultar.requestFocus();
//                delete error text cantidad
//                cantidadInputConsultarError.setText("");
//                delete succes text register
//                ConsultarErrorText.setText("");
            }
        });

        precioInputConsultar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                precioInputConsultar.setText(null);
                precioInputConsultar.requestFocus();
//                delete error text precio
//                precioInputConsultarError.setText("");
//                delete succes text register
//                ConsultarErrorText.setText("");
            }
        });
    }
    private void styleTable(){

        DefaultTableCellRenderer editColumn = new DefaultTableCellRenderer();

        //set aling center in editor column
        editColumn.setHorizontalAlignment(SwingConstants.CENTER);

        //get columns
        TableColumn id = table.getColumnModel().getColumn(0);
        TableColumn nombre =table.getColumnModel().getColumn(1);
        TableColumn categoria=table.getColumnModel().getColumn(2);
        TableColumn cantidad=table.getColumnModel().getColumn(3);
        TableColumn precio=table.getColumnModel().getColumn(4);

        //set size column
        id.setPreferredWidth(20);
        nombre.setPreferredWidth(150);
        categoria.setPreferredWidth(80);
        cantidad.setPreferredWidth(80);
        //set aling column
        id.setCellRenderer(editColumn);
        categoria.setCellRenderer(editColumn);
        cantidad.setCellRenderer(editColumn);
        precio.setCellRenderer(editColumn);


    }
    //methods table
    private void insertDataInTable(){
        //insert columns in table
        if(!isColumns) {
            modelData.addColumn("ID");
            modelData.addColumn("NOMBRE");
            modelData.addColumn("CATEGORIA");
            modelData.addColumn("CANTIDAD");
            modelData.addColumn("PRECIO");

            isColumns=true;
        }

        //insert row in table
        for (Product product : products){
            modelData.addRow(new Object[] { product.getId(),product.getNombre(),product.getCategoria(),product.getCantidad(),product.getPrecio() });
        }

        //insert data in table
        table.setModel(modelData);
        styleTable();
    }
    private void removeDataInTable(){
        //remove data in table
        if(isColumns){
            DefaultTableModel dataForRemove =(DefaultTableModel) table.getModel();
            int rowCount = dataForRemove.getRowCount();
            //Remove rows one by one from the end of the table
            for (int i = rowCount - 1; i >= 0; i--) {
                dataForRemove.removeRow(i);
            }
        }
    }

    //getter and setters
    public void setConnection(Connection connection) {
        dbConnection = connection;
    }
}