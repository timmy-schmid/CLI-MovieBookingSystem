package R18_G2_ASM2.SeatDataTools;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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

    /**
     * Copies the values stored in this data frame into a larger data frame, and
     * returns the result.
     *
     * @param additionalRows the number of rows to add to the new data frame
     * @param newCols        the names of the column to add to the new data frame.
     * @return the expanded resulting data frame
     * @throws IllegalArgumentException the number of additional rows in negative or
     *                                  column names are duplicated
     */
    @Override
    public DataFrame<String> expand(int additionalRows, List<String> newCols) throws IllegalArgumentException {
        String[][] newData = new String[getRowCount()+additionalRows][getColumnCount()+newCols.size()];
        List<String> newNames = new ArrayList<>(columnNames);
        newNames.addAll(newCols);
        for (int i = 0; i< getRowCount(); i++){
            for (int j = 0; j < getColumnCount(); j++){
                newData[i][j] = data[i][j];
            }
        }
        return new MovieDataFrame(newNames ,newData);
    }

    /**
     * Produces a smaller data frame that only contains the columns with names that
     * occur in the provided set of column names
     *
     * @param retainColumns the names of column that should be retained
     * @return a smaller data frame
     * @throws IllegalArgumentException if one of the column names is illegal
     */
    @Override
    public DataFrame<String> project(Collection<String> retainColumns) throws IllegalArgumentException {
        String[][] newData = new String[getRowCount()][retainColumns.size()];
        List<String> newNames = new ArrayList<>();
        for (String name : columnNames){
            if(retainColumns.contains(name)){
                newNames.add(name);
                for (int i = 0; i< getRowCount(); i++){
                    newData[i][newNames.size()-1] = getColumn(name).getValue("row_"+String.valueOf(i));
                }
            }
        }
        return new MovieDataFrame(newNames, newData);
    }

    /**
     * Produces a smaller data frame the only keeps the rows that are accepted by
     * the predicate
     *
     * @param rowFilter a predicate that can indicate whether or not a row should be
     *                  maintained
     * @return a smaller data frame
     */
    @Override
    public DataFrame<String> select(Predicate<DataVector<String>> rowFilter) {
        List<List<String>> dataList = new ArrayList<>();
        for (int i =0; i< getRowCount(); i++){
            if (rowFilter.test(getRow(i))){
                dataList.add(getRow(i).getValues());
            }
        }
        String[][] newData = new String[dataList.size()][getColumnCount()];
        for (int i = 0; i < dataList.size(); i++){
            for (int j = 0; j < getColumnCount(); j++){
                newData[i][j] = dataList.get(i).get(j);
            }
        }

        return new MovieDataFrame(columnNames, newData);
    }

    // @Override
    // public DataVector<String> getRow(int rowIndex) throws IndexOutOfBoundsException {
    //     // TODO Auto-generated method stub
    //     return null;
    // }

    @Override
    public DataFrame<String> computeColumn(String columnName, Function<DataVector<String>, Double> function) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataVector<String> summarize(String name, BinaryOperator<String> summaryFunction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String[][] getData() {
        // TODO Auto-generated method stub
        return data;
    }

    // /**
    //  * Produces a larger data frame with one additional column. The values stored in
    //  * this column are computed using a give function that is applied to each row
    //  * currently in the data frame
    //  *
    //  * @param columnName the name of the new column
    //  * @param function   the function to apply to each row
    //  * @return the resulting data frame
    //  */
    // @Override
    // public DataFrame<String> computeColumn(String columnName, Function<DataVector<String>, Double> function) {
    //     DataFrame<String> newData = this.expand(0, columnName);
    //     for (int i = 0; i < getRowCount(); i++){
    //         newData.setValue(i, columnName, function.apply(getRow(i)));
    //     }
    //     return newData;
    // }

    // /**
    //  * Summarize each column using a given BinaryOperator using a reduce action. The
    //  * result is produces as a data vector.
    //  *
    //  * @param name            the name of the resulting data vector
    //  * @param summaryFunction the binary operator that should be used to reduce the
    //  *                        values in each column
    //  * @return a data vector with the result for each column
    //  */
    // @Override
    // public DataVector<String> summarize(String name, BinaryOperator<String> summaryFunction) {
    //     List<Double> entry = new ArrayList<>();
    //     //use reduce method in stream
    //     for (DataVector<Double> column: getColumns()){
    //         Optional<Double> result = column.getValues().stream()
    //                 .reduce((obj1, obj2)
    //                         -> summaryFunction.apply(obj1, obj2));
    //         entry.add(result.get());
    //     }
    //     return new MovieDataVector(name, columnNames, entry);
    // }
}