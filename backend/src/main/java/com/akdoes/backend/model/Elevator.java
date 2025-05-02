package com.akdoes.backend.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Elevator implements Runnable {
    private final int id;
    private int currentFloor = 0;
    private Direction direction = Direction.IDLE;
    private final BlockingQueue<Integer> requestQueue = new LinkedBlockingQueue<>();
    private boolean doorsOpen = false;

    public Elevator(int id) {
        this.id = id;
    }

    public void addRequest(int floor) {
        requestQueue.offer(floor);
    }

    public void moveToFloor(int target) {
        while (currentFloor != target) {
            if (currentFloor < target) {
                currentFloor++;
                direction = Direction.UP;
            } else {
                currentFloor--;
                direction = Direction.DOWN;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        direction = Direction.IDLE;
        doorsOpen = true;
        try {
            Thread.sleep(1000); // simulate door open time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        doorsOpen = false;
    }

    public void run() {
        while (true) {
            try {
                Integer nextFloor = requestQueue.take();
                moveToFloor(nextFloor);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public Direction getDirection() { return direction; }
    public boolean isDoorsOpen() { return doorsOpen; }
}
