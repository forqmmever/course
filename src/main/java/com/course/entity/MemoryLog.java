package com.course.entity;

public class MemoryLog {

    private int node_memory_Buffers_bytes;
    private int node_memory_Cached_bytes;
    private int node_memory_MemFree_bytes;
    private int node_memory_MemTotal_bytes;

    @Override
    public String toString() {
        return "MemoryLog{" +
                "node_memory_Buffers_bytes=" + node_memory_Buffers_bytes +
                ", node_memory_Cached_bytes=" + node_memory_Cached_bytes +
                ", node_memory_MemFree_bytes=" + node_memory_MemFree_bytes +
                ", node_memory_MemTotal_bytes=" + node_memory_MemTotal_bytes +
                '}';
    }

    public int getNode_memory_Buffers_bytes() {
        return node_memory_Buffers_bytes;
    }

    public void setNode_memory_Buffers_bytes(int node_memory_Buffers_bytes) {
        this.node_memory_Buffers_bytes = node_memory_Buffers_bytes;
    }

    public int getNode_memory_Cached_bytes() {
        return node_memory_Cached_bytes;
    }

    public void setNode_memory_Cached_bytes(int node_memory_Cached_bytes) {
        this.node_memory_Cached_bytes = node_memory_Cached_bytes;
    }

    public int getNode_memory_MemFree_bytes() {
        return node_memory_MemFree_bytes;
    }

    public void setNode_memory_MemFree_bytes(int node_memory_MemFree_bytes) {
        this.node_memory_MemFree_bytes = node_memory_MemFree_bytes;
    }

    public int getNode_memory_MemTotal_bytes() {
        return node_memory_MemTotal_bytes;
    }

    public void setNode_memory_MemTotal_bytes(int node_memory_MemTotal_bytes) {
        this.node_memory_MemTotal_bytes = node_memory_MemTotal_bytes;
    }
}
