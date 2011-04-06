package softarch.portal.db.flatfile;

public class CsvValues {
	
	Object[] values;
	
	CsvValues(Object[] values) {
		this.values = values;
	}
	
	CsvValues() {
		
	}
	
	public String toString() {
		String str = "";
		
		int i;
		
		for (i = 0; i<values.length-1; i++) {
			str += values[i].toString() + ",";
		}
		
		str += values[i];
		
		return str;
	}
	
	public Object get(int idx) {
		return values[idx];
	}
	
	public Object[] getValues() {
		return values;
	}
	
}
