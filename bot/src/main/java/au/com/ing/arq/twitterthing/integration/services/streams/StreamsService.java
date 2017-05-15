package au.com.ing.arq.twitterthing.integration.services.streams;

/**
 * Created by gvisoc on 12/5/17.
 */
public interface StreamsService {

    void setConsumerSecret(String consumerSecret);

    void setConsumerKey(String consumerKey);

    void setAccessToken(String accessToken);

    void setAccessTokenSecret(String accessTokenSecret);

    void start();

    void stop();

    void connect();
}
