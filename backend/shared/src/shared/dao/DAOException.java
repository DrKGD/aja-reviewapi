package shared.dao;

public class DAOException extends Exception {
	private static final long serialVersionUID = 1L;
	public DAOException(String message, Exception e){
		super(String.format("%s: %s", message, e));
	}
}
