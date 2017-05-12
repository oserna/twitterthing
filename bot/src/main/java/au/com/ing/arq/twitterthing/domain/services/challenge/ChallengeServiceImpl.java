package au.com.ing.arq.twitterthing.domain.services.challenge;

import au.com.ing.arq.twitterthing.domain.services.error.ServiceException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class ChallengeServiceImpl implements ChallengeService{

    @Autowired
    private Environment env;

    public String getChallenge(String token) throws ServiceException {

        //TODO proper logging :-)

        try {
            String twitterSecret = env.getRequiredProperty("twitterSecret");

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(twitterSecret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(token.getBytes()));

            return hash;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }

    }

}
