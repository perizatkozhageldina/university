package ua.foxminded.university.repository;

public class RepositoryException extends Exception {
    private static final long serialVersionUID = 1L;

    public RepositoryException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}