package Client.Connection;

import Client.ChartGUI;
import Client.Entites.AppData;
import Client.HomeGUI;
import Client.Utils.Utils;

import java.io.IOException;

public class ReceiveDataThread extends Thread{
    String temp = "0";
    private boolean isRunning = true;
    HomeGUI home;
    ChartGUI chart;
//    public ReceiveDataThread(HomeGUI home){
//        this.home = home;
//    }
    public ReceiveDataThread(ChartGUI chart){
        this.chart = chart;
    }

    @Override
    public void run() {
        super.run();
        while(isRunning){
            try {
                Thread.sleep(2000);
//                yield();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                temp = Connection.requestPOST(Utils.GetInfoPackage("111"));
                System.out.println("temp: "+temp);


            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                AppData.setHumd(Integer.parseInt(temp));
                chart.addNewData(AppData.humd);
            } catch (Exception e){
                System.out.println(e);
            }

        }

    }

}
