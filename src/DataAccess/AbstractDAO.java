package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Connection.ConnectionFactory;

public class AbstractDAO<T> {
    /**
     * Creates methods for accessing the database using reflexion techniques
     */
    private final Class<T> type;

    /**
     * Gets the class type
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creates a simple select query
     * @return the string containing the query
     */
    public String createSelectQuery() {
        return "SELECT " + " * " + " FROM " + type.getSimpleName();
    }

    /*public TableView<T> createTable(List<T> myList) {
        TableView<T> myTable = new TableView<>();

        for (Field currentField : myList.get(0).getClass().getDeclaredFields()) {
            currentField.setAccessible(true);
            try {
                TableColumn<T,?> column = new TableColumn<>();
                column.setCellValueFactory(new PropertyValueFactory<>(currentField.getName()));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        ObservableList<T> list= FXCollections.observableArrayList();
        list.addAll(myList);
        myTable.setItems(list);
        return myTable;
    }*/
    /**
     * Searches a specific object by id
     * @param id -the id searched in the data base
     * @return that object
     */
    public T findById(int id) {
        List<T> myList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder query=new StringBuilder();
        query.append(createSelectQuery()).append(" where ID=").append(id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            resultSet = statement.executeQuery(query.toString());
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                myList.add(instance);
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException | IntrospectionException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return myList.get(0);
    }

    /**
     * Create a list of all objects from a table
     * @return the lis
     */
    public List<T> createObjects() {
        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException | NoSuchMethodException e) {
            e.printStackTrace();
        }finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
        }
        return list;
    }

    /**
     * Connects to data base and executes a query
     * @param query -query to be executed
     */
    public void executeQuery(StringBuilder query){
        Connection connection=null;
        PreparedStatement statement=null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query.toString());
            statement.executeUpdate(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Insert an object in data base
     * @param t -Object to be inserted
     * @return the object
     */
    public T insert(T t){
        StringBuilder query = new StringBuilder();
        query.append("Insert into orderdb.").append(t.getClass().getSimpleName()).append(" (");
        try {
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                query.append(field.getName());
                if(field!=t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length - 1])
                    query.append(", ");
            }
            query.append(") values (");
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType().getSimpleName().equals("String"))
                    query.append("\"").append(field.get(t)).append("\"");
                else
                    query.append(field.get(t));
                if(field!=t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length - 1]) {
                    query.append(", ");
                }
            }
            query.append(query).append(" )");
            executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Update an object in data base
     * @param t -Object to be updated
     * @return the object
     */
    public T update(T t){
        StringBuilder query = new StringBuilder();
        StringBuilder end = new StringBuilder();
        query.append("update orderdb.").append(t.getClass().getSimpleName()).append(" set ");
        try {
            for (Field field : t.getClass().getDeclaredFields()){
                field.setAccessible(true);
                if(field.equals(t.getClass().getDeclaredFields()[0])){
                    end.append(" where ").append(field.getName()).append(" = ").append(field.get(t));
                }
                query.append(field.getName()).append(" = ");
                if (field.getType().getSimpleName().equals("String"))
                    query.append("\"").append(field.get(t)).append("\"");
                else
                    query.append(field.get(t));
                if(field!=t.getClass().getDeclaredFields()[t.getClass().getDeclaredFields().length - 1])
                    query.append(", ");
            }
            query.append(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        executeQuery(query);
        return t;
    }

    /**
     * Delete an object in data base
     * @param t -Object to be deleted
     * @return the object
     */
    public T delete(T t){
        StringBuilder query = new StringBuilder();
        try {
            t.getClass().getDeclaredFields()[0].setAccessible(true);
            query.append("Delete from orderdb.").append(t.getClass().getSimpleName()).append(" where ").append(t.getClass().getDeclaredFields()[0].getName()).append(" = ").append(t.getClass().getDeclaredFields()[0].get(t));
            executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}