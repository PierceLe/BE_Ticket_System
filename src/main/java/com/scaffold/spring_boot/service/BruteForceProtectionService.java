package com.scaffold.spring_boot.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class BruteForceProtectionService {
    private static final int MAX_ATTEMPT = 5; // Number of failed attempts before locking
    private static final long BASE_LOCK_TIME = TimeUnit.MINUTES.toMillis(15); // Base lock duration (15 minutes)

    // Stores the number of failed attempts for each user
    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();
    // Stores the lock start time for each user
    private final Map<String, Long> lockCache = new ConcurrentHashMap<>();
    // Stores the lock level for each user
    private final Map<String, Integer> lockLevelCache = new ConcurrentHashMap<>();

    // Handle successful login
    public void loginSucceeded(String key) {
        attemptsCache.remove(key); // Clear the failed attempts
        lockCache.remove(key); // Clear the lock time
        lockLevelCache.remove(key); // Clear the lock level
    }

    // Handle failed login
    public void loginFailed(String key) {
        int attempts = attemptsCache.getOrDefault(key, 0) + 1;
        attemptsCache.put(key, attempts);

        if (attempts >= MAX_ATTEMPT) {
            int lockLevel = lockLevelCache.getOrDefault(key, 0) + 1; // Increment the lock level
            lockLevelCache.put(key, lockLevel);

            long lockTime = BASE_LOCK_TIME * lockLevel; // Calculate the lock duration based on the lock level
            lockCache.put(key, System.currentTimeMillis() + lockTime); // Store the lock expiration time

            // Reset the failed attempts to calculate for the next lock level
            attemptsCache.put(key, 0);
        }
    }

    // Check if the user is currently locked
    public boolean isBlocked(String key) {
        if (!lockCache.containsKey(key)) {
            return false; // User is not locked
        }

        long unlockTime = lockCache.get(key);
        if (System.currentTimeMillis() > unlockTime) {
            // If the lock duration has expired, remove the lock state
            lockCache.remove(key);
            lockLevelCache.remove(key);
            return false;
        }

        return true; // User is still locked
    }
}