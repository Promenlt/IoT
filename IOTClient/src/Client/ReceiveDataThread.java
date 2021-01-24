package Client;

import java.io.IOException;

public class ReceiveDataThread extends Thread{
    String temp = "0";
    private boolean isRunning = true;
    HomeGUI home;
    public ReceiveDataThread(HomeGUI home){
        this.home = home;
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
                temp = Test.requestPOST(Utils.GetInfoPackage(home.getUUID()));
                System.out.println("temp: "+temp);


            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                AppData.setHumd(Integer.parseInt(temp));
            } catch (Exception e){
                System.out.println(e);
            }
            home.rePaintButtonColor();
        }

    }

}
