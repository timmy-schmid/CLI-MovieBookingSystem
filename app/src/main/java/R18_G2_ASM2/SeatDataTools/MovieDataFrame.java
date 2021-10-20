package R18_G2_ASM2.SeatDataTools;

import java.util.*;


public class MovieDataFrame implements DataFrame <String> {
    private List<String> columnNames;
    private String [][] data;
    private Map<String,Integer> columnMap;



    public MovieDataFrame(List<String> columnNames, String [][] data) {
        this.columnNames = columnNames;
        this.data = data;
        columnMap = new HashMap<>();
        for (int i = 0; i < columnNames.size(); i++){
            columnMap.put(columnNames.get(i), i);
        }
    }
    // Class implementation goes here

    /**
     * The number of rows stored in this matrix
     *
     * @return the number of rows
     */
    @Override
    public int getRowCount() {
        return data.length;
    }

    /**
     * The number of columns stored in this matrix
     *
     * @return the number of columns
     */
    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    /**
     * The names of the columns stored in this matrix
     *
     * @return a list of column names
     */
    @Override
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * Sets a value to a particular entry in the data frame.
     *
     * @param rowIndex the row index of the entry
     * @param colName  the name of the column in which the entry is stored
     * @param value    the new value of the entry
     * @throws IndexOutOfBoundsException if the row index is illegal
     * @throws IllegalArgumentException  if the column name is illegal
     */
    @Override
    public void setValue(int rowIndex, String colName, String value) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (!columnMap.containsKey(colName)){
            throw new IllegalArgumentException();
        }
        data[rowIndex][columnMap.get(colName)] = value;
    }

    /**
     * Retrieve the value associated with a given entry in the data frame
     *
     * @param rowIndex the row index of the entry
     * @param colName  the name of the column in which the entry is stored
     * @return the value currently stored at the entry
     * @throws IndexOutOfBoundsException if the row index is illegal
     * @throws IllegalArgumentException  if the column name is illegal
     */
    @Override
    public String getValue(int rowIndex, String colName) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (!columnMap.containsKey(colName)){
            throw new IllegalArgumentException();
        }
        return data[rowIndex][columnMap.get(colName)];
    }

    /**
     * Produces a data vector for a particular row in the data frame
     *
     * @param rowIndex the row index of the entry
     * @return a data vector derived from a row in the data frame
     * @throws IndexOutOfBoundsException if the row index is illegal
     */
    @Override
    public DataVector<String> getRow(int rowIndex) throws IndexOutOfBoundsException {
        String name = String.valueOf((char) 65+rowIndex);
        List<String> entryNames = columnNames;
        List<String> entry = Arrays.asList(data[rowIndex]);
        return new MovieDataVector(name, entryNames, entry);
    }

    /**
     * Produces a data vector for a particular column in the data frame
     *
     * @param colName the name of the column
     * @return a data vector derived from a column in the data frame
     * @throws IllegalArgumentException if the column name is illegal
     */
    @Override
    public DataVector<String> getColumn(String colName) throws IllegalArgumentException {
        if (!columnMap.containsKey(colName)){
            throw new IllegalArgumentException();
        }
        String name = colName;
        List<String> entryNames = new ArrayList<>();
        List<String> entry = new ArrayList<>();
        //
        for (int i = 0; i < getRowCount(); i++){
            entryNames.add(String.valueOf((char) 65+i));
            entry.add(getValue(i, colName));
        }

        return new MovieDataVector(name, entryNames, entry);
    }

    /**
     * Produces a list of data vectors derived from every row in the matrix
     *
     * @return a list of data vectors
     */
    @Override
    public List<DataVector<String>> getRows() {
        List<DataVector<String>> rows = new ArrayList<>();
        for (int i = 0; i < getRowCount(); i++){
            rows.add(getRow(i));
        }
        return rows;
    }

    /**
     * Produces a list of data vectors derived from every column in the matrix
     *
     * @return a list of data vectors
     */
    @Override
    public List<DataVector<String>> getColumns() {
        List<DataVector<String>> columns = new ArrayList<>();
        for (String name : columnNames){
            columns.add(getColumn(name));
        }
        return columns;
    }





    @Override
    public String[][] getData() {
        // TODO Auto-generated method stub
        return data;
    }
}