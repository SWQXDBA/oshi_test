package Main;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

         SystemInfo si = new SystemInfo();

        final CentralProcessor processor = si.getHardware().getProcessor();
        System.out.println(processor);
       while(true){
           final double[] processorCpuLoad = processor.getProcessorCpuLoad(1000);
           System.out.println(Arrays.toString(processorCpuLoad));
           System.out.println();
       }


    }
}
