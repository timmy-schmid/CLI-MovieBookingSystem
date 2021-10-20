package R18_G2_ASM2.SeatDataTools;


import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * A data frame holds a matrix of data, with the difference that column have
 * names rather than indices.
 * 
 * It supports a number of useful operations to manipulate the data that can
 * transform and aggregate the data stored in the matrix.
 * 
 * Note that the order of the column is fixed.
 * 
 *
 * @param <E> the type the entry values stored in this data vector
 */
public interface DataFrame<E> extends Iterable<DataVector<E>>
{

	/**
	 * Defauft number of characters to use for the width of a single column when
	 * formatting a DataFrame or DataVector object.
	 */
	public static int DEFAULT_FORMAT_WIDTH = 9;

	/**
	 * The number of rows stored in this matrix
	 * 
	 * @return the number of rows
	 */
	public int getRowCount();

	/**
	 * The number of columns stored in this matrix
	 * 
	 * @return the number of columns
	 */
	public int getColumnCount();

	/**
	 * The names of the columns stored in this matrix
	 * 
	 * @return a list of column names
	 */
	public List<String> getColumnNames();

	/**
	 * 
	 * @return an array of data
	 */
	public E[][] getData();
	/**
	 * Sets a value to a particular entry in the data frame.
	 * 
	 * @param rowIndex the row index of the entry
	 * @param colName  the name of the column in which the entry is stored
	 * @param value    the new value of the entry
	 * @throws IndexOutOfBoundsException if the row index is illegal
	 * @throws IllegalArgumentException  if the column name is illegal
	 */
	public void setValue(int rowIndex, String colName, E value)
			throws IndexOutOfBoundsException, IllegalArgumentException;

	/**
	 * Retrieve the value associated with a given entry in the data frame
	 * 
	 * @param rowIndex the row index of the entry
	 * @param colName  the name of the column in which the entry is stored
	 * @return the value currently stored at the entry
	 * @throws IndexOutOfBoundsException if the row index is illegal
	 * @throws IllegalArgumentException  if the column name is illegal
	 */
	public E getValue(int rowIndex, String colName) throws IndexOutOfBoundsException, IllegalArgumentException;

	/**
	 * Produces a data vector for a particular row in the data frame
	 * 
	 * @param rowIndex the row index of the entry
	 * @return a data vector derived from a row in the data frame
	 * @throws IndexOutOfBoundsException if the row index is illegal
	 */
	public DataVector<E> getRow(int rowIndex) throws IndexOutOfBoundsException;

	/**
	 * Produces a data vector for a particular column in the data frame
	 * 
	 * @param colName the name of the column
	 * @return a data vector derived from a column in the data frame
	 * @throws IllegalArgumentException if the column name is illegal
	 */
	public DataVector<E> getColumn(String colName) throws IllegalArgumentException;

	/**
	 * Produces a list of data vectors derived from every row in the matrix
	 * 
	 * @return a list of data vectors
	 */
	public List<DataVector<E>> getRows();

	/**
	 * Produces a list of data vectors derived from every column in the matrix
	 * 
	 * @return a list of data vectors
	 */
	public List<DataVector<E>> getColumns();


	/**
	 * Prints the contents of this data frame to System.out using a default column
	 * width
	 */
	public default void print()
	{
		//System.out.println("\033[1;93;45mhello\033m");
		// System.out.println("\033[1;93;45m"+formatMatrix(DEFAULT_FORMAT_WIDTH)+"\033m");
		System.out.println(formatMatrix(DEFAULT_FORMAT_WIDTH));
	}

	/**
	 * Formats the entries stored in this data frame to a fixed with using the
	 * String.format() method. The display is row-based: the first line contains all
	 * column names, then the second line contains the data from the first rows
	 * etcetera.
	 * 
	 * @param colWidth the number of character to use for a single column
	 * @return a string representation of this data frame
	 */
	public default String formatMatrix(int colWidth)
	{
		String fmt = "%-" + colWidth + "." + colWidth + "s";
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(fmt, ""));
		List<String> colNames = getColumnNames();
		for (String colName : colNames)
		{
			sb.append(" ");
			sb.append(String.format(Locale.ROOT, fmt, colName));
		}
		sb.append("\n");
		for (int i = 0; i < getRowCount(); i++)
		{
			sb.append(String.format(Locale.ROOT, fmt, (char) (65+i)));
			for (String colName : colNames)
			{
				sb.append(" ");
				
				if (getValue(i, colName).equals("Reserved")){
					sb.append("\033[35m"+String.format(Locale.ROOT, fmt, getValue(i, colName))+"\033[0m");
				}else{
					sb.append(String.format(Locale.ROOT, fmt, getValue(i, colName)));
				}
				
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public default Iterator<DataVector<E>> iterator()
	{
		return getRows().iterator();
	}
}
