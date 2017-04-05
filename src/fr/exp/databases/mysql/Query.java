package fr.exp.databases.mysql;

public class Query {

	private String select;
	private String from;
	private String where;
	private String as;

	public void setSelect(String select) {
		this.select = select;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT " + select + " ");
		sb.append(as != null ? "AS " + as + " " : "");
		sb.append("FROM " + from + " ");
		sb.append(where != null ? "WHERE " + where + " " : "");
		return sb.toString();
	}

	public void setAs(String as) {
		this.as = as;
	}

}
