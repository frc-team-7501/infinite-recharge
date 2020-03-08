package org.team696.TCA9548ADriver;

import edu.wpi.first.wpilibj.I2C;

import java.nio.ByteBuffer;

import org.team696.TCA9548ADriver.TCA9548A;

public class TCA9548ADevice extends I2C {
    private int muxChannel;
    private TCA9548A mux;

    public TCA9548ADevice(TCA9548A mux, int deviceAddress, int muxChannel) {
        super(mux.getPort(), deviceAddress);
        this.muxChannel = muxChannel;
        this.mux = mux;
    }

    @Override
    public synchronized boolean transaction(byte[] dataToSend, int sendSize, byte[] dataReceived, int receiveSize) {
        mux.setChannel(muxChannel);
        return super.transaction(dataToSend, sendSize, dataReceived, receiveSize);
    }

    @Override
    public synchronized boolean transaction(ByteBuffer dataToSend, int sendSize, ByteBuffer dataReceived,
            int receiveSize) {
        mux.setChannel(muxChannel);
        return super.transaction(dataToSend, sendSize, dataReceived, receiveSize);
    }

    @Override
    public boolean read(int registerAddress, int count, ByteBuffer buffer) {
        mux.setChannel(muxChannel);
        return super.read(registerAddress, count, buffer);
    }

    @Override
    public boolean read(int registerAddress, int count, byte[] buffer) {
        mux.setChannel(muxChannel);
        return super.read(registerAddress, count, buffer);
    }

    @Override
    public boolean readOnly(ByteBuffer buffer, int count) {
        mux.setChannel(muxChannel);
        return super.readOnly(buffer, count);
    }

    @Override
    public boolean readOnly(byte[] buffer, int count) {
        mux.setChannel(muxChannel);
        return super.readOnly(buffer, count);
    }

    @Override
    public synchronized boolean write(int registerAddress, int data) {
        mux.setChannel(muxChannel);
        return super.write(registerAddress, data);
    }

    @Override
    public synchronized boolean writeBulk(ByteBuffer data, int size) {
        mux.setChannel(muxChannel);
        return super.writeBulk(data, size);
    }

    @Override
    public synchronized boolean writeBulk(byte[] data) {
        mux.setChannel(muxChannel);
        return super.writeBulk(data);
    }

    @Override
    public synchronized boolean writeBulk(byte[] data, int size) {
        mux.setChannel(muxChannel);
        return super.writeBulk(data, size);
    }

    @Override
    public boolean verifySensor(int registerAddress, int count, byte[] expected) {
        mux.setChannel(muxChannel);
        return super.verifySensor(registerAddress, count, expected);
    }

}