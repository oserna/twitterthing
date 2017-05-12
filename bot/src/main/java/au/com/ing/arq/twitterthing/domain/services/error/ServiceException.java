package au.com.ing.arq.twitterthing.domain.services.error;

/**
 * Created by gvisoc on 12/5/17.
 */
public class ServiceException extends Exception {
    Exception cause = null;
    public ServiceException(Exception _cause)
    {
        this.cause = _cause;
    }
}
