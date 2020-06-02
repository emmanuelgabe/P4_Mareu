package com.test.example.maru.DI;

import com.test.example.maru.service.DummyMeetingApiService;
import com.test.example.maru.service.MeetingApiService;

/**
 * Dependency injector to get instance of services
 */
public class DI {

    private static DummyMeetingApiService service = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     *
     * @return
     */
    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link MeetingApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return
     */
    public static MeetingApiService getNewInstanceApiService() {
        return new DummyMeetingApiService();
    }


}
