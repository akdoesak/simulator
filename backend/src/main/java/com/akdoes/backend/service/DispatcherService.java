package com.akdoes.backend.service;

import com.akdoes.backend.model.Elevator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class DispatcherService {
    private final BlockingQueue<Integer> globalRequests = new LinkedBlockingQueue<>();
    private final List<Elevator> elevators;

    public DispatcherService(ElevatorSystemService systemService) {
        this.elevators = systemService.getElevators();
    }

    public void submitFloorRequest(int floor) {
        globalRequests.offer(floor);
    }

    @Scheduled(fixedRate = 500)
    public void assignRequests() {
        Integer request = globalRequests.poll();
        if (request != null) {
            Elevator best = elevators.stream()
                .min((a, b) -> Integer.compare(Math.abs(a.getCurrentFloor() - request), 
                                               Math.abs(b.getCurrentFloor() - request)))
                .orElse(elevators.get(0));
            best.addRequest(request);
        }
    }
}
