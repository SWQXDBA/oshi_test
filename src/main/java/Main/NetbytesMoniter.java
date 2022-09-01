package Main;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;

public class NetbytesMoniter {
    private SystemInfo si = new SystemInfo();
    private HardwareAbstractionLayer hal = si.getHardware();

    private long historySend = 0;
    private long historyReceived = 0;
    private long sendBytesPerSecond = 0;
    private long receivedBytesPerSecond = 0;
    private  long lastTimeStamp = 0;

    public long getSendBytesPerSecond() {
        return sendBytesPerSecond;
    }

    public long getReceivedBytesPerSecond() {
        return receivedBytesPerSecond;
    }


    public static String parseSpeed(long bytesPerSecond) {
        long kb = 1024;
        long mb = kb * 1024;

        long bits = bytesPerSecond * 8;
        if (bits < kb) {
            return bits + " bps";
        }
        if (bits < mb) {
            return bits / kb + " kbps";
        }

        return bits / mb + " mbps";


    }

    public void update() {
        long currentTimeStamp = 0;
        List<NetworkIF> networkInfos = hal.getNetworkIFs();
        long currentSend = 0;
        long currentReceived = 0;
        for (NetworkIF networkInfo : networkInfos) {

            currentSend += networkInfo.getBytesSent();
            currentReceived += networkInfo.getBytesRecv();
            currentTimeStamp = networkInfo.getTimeStamp();
        }
        final long passedTime = currentTimeStamp - lastTimeStamp;
        lastTimeStamp = currentTimeStamp;



        long millsPerSecond = 1000;
        sendBytesPerSecond = (long)((currentSend - historySend) /(1f*passedTime)*millsPerSecond);

        receivedBytesPerSecond = (long)((currentReceived - historyReceived) /(1f*passedTime)*millsPerSecond);
        historySend = currentSend;
        historyReceived = currentReceived;
    }
}
