package ua.foxminded.university.dao;

public class DAOException extends Exception {
    private static final long serialVersionUID = 1L;

    public DAOException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}