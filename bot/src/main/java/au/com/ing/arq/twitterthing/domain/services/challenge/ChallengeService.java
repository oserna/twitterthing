package au.com.ing.arq.twitterthing.domain.services.challenge;

import au.com.ing.arq.twitterthing.domain.services.error.ServiceException;

public interface ChallengeService {

     String getChallenge(String token) throws ServiceException;

}
