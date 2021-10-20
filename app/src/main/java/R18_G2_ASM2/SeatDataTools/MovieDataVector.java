package R18_G2_ASM2.SeatDataTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDataVector implements DataVector <String > {

    private String name;
    private List<String> entryNames;
    private List<String> entry;
    private Map<String, String> map;

    public MovieDataVector(String name, List<String> entryNames, List<String> entry){
        this.name = name;
        this.entryNames = entryNames;
        this.entry = entry;
        map = new HashMap<>();
        for (int i = 0; i < entry.size(); i++){
            map.put(entryNames.get(i), entry.get(i));
        }
    }



    /**
     * Provides the name of this data vector For vectors derived from a column, this
     * is typically the column name. For vectors derived from a row, it is typically
     * "row_0", "row_1", etcetera.
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gives a list of the names of the entries in this vector. For a vector derived
     * from a column, these are typically "row_0", "row_1", etctera. For a vector
     * derived from a row, these are the names of the columns
     *
     * @return the names of the entries of this vector
     */
    @Override
    public List<String> getEntryNames() {
        return entryNames;
    }

    /**
     * Getter for the value associated with an entry that has a given entry name.
     *
     * @param entryName the name of the entry to extract
     * @return the value of the entry with the entryName
     */
    @Override
    public String getValue(String entryName) {
        if (!map.containsKey(entryName)){
            throw new IllegalArgumentException();
        }
        return map.get(entryName);
    }

    /**
     * Obtain a list of all the values of the entries in this vector.
     *
     * @return a list of all values
     */
    @Override
    public List<String> getValues() {
        return entry;
    }

    /**
     * Produces a map that contains key-value pairs of the entries in this vector.
     *
     * @return a map with the entries of this vector as key-value pairs
     */
    @Override
    public Map<String, String> asMap() {
        return map;
    }
}
