package com.akdoes.backend.controller;


import com.akdoes.backend.model.Elevator;
import com.akdoes.backend.service.DispatcherService;
import com.akdoes.backend.service.ElevatorSystemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/elevators")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ElevatorController {
    private final ElevatorSystemService elevatorSystemService;
    private final DispatcherService dispatcherService;

    public ElevatorController(ElevatorSystemService elevatorSystemService, DispatcherService dispatcherService) {
        this.elevatorSystemService = elevatorSystemService;
        this.dispatcherService = dispatcherService;
    }

    @PostMapping("/request")
    public void requestElevator(@RequestBody Map<String, Integer> request) {
        dispatcherService.submitFloorRequest(request.get("floor"));
    }

    @PostMapping("/{id}/select")
    public void selectFloor(@PathVariable int id, @RequestParam int floor) {
        elevatorSystemService.selectFloor(id, floor);
    }

    @GetMapping
    public List<Elevator> getElevators() {
        return elevatorSystemService.getElevators();
    }
}
