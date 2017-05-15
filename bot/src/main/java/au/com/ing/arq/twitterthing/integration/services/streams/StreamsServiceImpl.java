package au.com.ing.arq.twitterthing.integration.services.streams;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.UserstreamEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jUserstreamClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by gvisoc on 12/5/17. This is a service and not a controller bc is invoking endpoints,
 * rather than exposing them
 * TBC
 */
@Service("StreamsService")
public class StreamsServiceImpl implements DisposableBean, StreamsService{

    private String consumerSecret;
    private String consumerKey;
    private String accessToken;
    private String accessTokenSecret;
    private Client hosebirdClient;

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);

    @Override
    public void stop() {
        if (this.hosebirdClient != null)
            this.hosebirdClient.stop();
    }

    @Override
    public void connect() {
        if ( (this.consumerKey != null) &&
                (this.consumerSecret != null) &&
                (this.accessToken != null) &&
                (this.accessTokenSecret != null))
        {
            //System.out.println("All properties set. Connecting to Twitter");

            Hosts hosebirdHosts = new HttpHosts(Constants.USERSTREAM_HOST);

            UserstreamEndpoint hosebirdEndpoint = new UserstreamEndpoint();

            Authentication hosebirdAuth = new OAuth1(this.consumerKey,
                    this.consumerSecret,
                    this.accessToken,
                    this.accessTokenSecret);
            ClientBuilder builder = new ClientBuilder()
                    .name("Hosebird-Client-01")                              // optional: mainly for the logs
                    .hosts(hosebirdHosts)
                    .authentication(hosebirdAuth)
                    .endpoint(hosebirdEndpoint)
                    .processor(new StringDelimitedProcessor(msgQueue));
            this.hosebirdClient = builder.build();

        }
        else
        {
            System.out.println("Some properties are null");
        }
    }

    @Override
    public void start() {

        int numProcessingThreads = 4;
        ExecutorService executorService = Executors.newFixedThreadPool(numProcessingThreads);
        Twitter4jUserstreamClient t4jClient = new Twitter4jUserstreamClient(hosebirdClient, msgQueue, Lists.newArrayList(new UserStreamListener() {
            @Override
            public void onDeletionNotice(long l, long l1) {

            }

            @Override
            public void onFriendList(long[] longs) {

            }

            @Override
            public void onFavorite(User user, User user1, Status status) {

            }

            @Override
            public void onUnfavorite(User user, User user1, Status status) {

            }

            @Override
            public void onFollow(User user, User user1) {

            }

            @Override
            public void onUnfollow(User user, User user1) {

            }

            @Override
            public void onDirectMessage(DirectMessage directMessage) {

                System.out.println(directMessage);
            }

            @Override
            public void onUserListMemberAddition(User user, User user1, UserList userList) {

            }

            @Override
            public void onUserListMemberDeletion(User user, User user1, UserList userList) {

            }

            @Override
            public void onUserListSubscription(User user, User user1, UserList userList) {

            }

            @Override
            public void onUserListUnsubscription(User user, User user1, UserList userList) {

            }

            @Override
            public void onUserListCreation(User user, UserList userList) {

            }

            @Override
            public void onUserListUpdate(User user, UserList userList) {

            }

            @Override
            public void onUserListDeletion(User user, UserList userList) {

            }

            @Override
            public void onUserProfileUpdate(User user) {

            }

            @Override
            public void onBlock(User user, User user1) {

            }

            @Override
            public void onUnblock(User user, User user1) {

            }

            @Override
            public void onStatus(Status status) {

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {

            }
        }), executorService);
        t4jClient.connect();

        // Call this once for every thread you want to spin off for processing the raw messages.
        // This should be called at least once.
        for (int i = 0; i < numProcessingThreads; i++)
            t4jClient.process(); // a Runnable is submitted to the executorService to process the msgQueue

    }

    @Override
    public void destroy() throws Exception {
        this.hosebirdClient.stop();
    }
}
