package com.ridesharing.observer;

import com.ridesharing.model.Trip;

public interface TripObserver {

    void onUpdate(Trip trip);
}
