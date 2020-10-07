package com.wilfried.utils;

import com.wilfried.environment.Piece;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 Shared Data
 */

public class SharedDatas {
    public volatile Piece[][] manoir = new Piece[10][10];
    public volatile ConcurrentLinkedQueue<UpdateInterfaceEvent> updateInterfaceQueue = new ConcurrentLinkedQueue<>();
    public volatile ConcurrentLinkedQueue<UpdateNbPointsEvent> updateNbPointsQueue = new ConcurrentLinkedQueue<>();
}
