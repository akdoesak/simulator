package com.akdoes.backend.service;

import com.akdoes.backend.model.Elevator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElevatorSystemService {
    private final List<Elevator> elevators = new ArrayList<>();

    public ElevatorSystemService() {
        int elevatorCount = 5; // configurable later
        for (int i = 0; i < elevatorCount; i++) {
            Elevator elevator = new Elevator(i);
            elevators.add(elevator);
            new Thread(elevator).start();
        }
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public void selectFloor(int elevatorId, int floor) {
        elevators.get(elevatorId).addRequest(floor);
    }
}
