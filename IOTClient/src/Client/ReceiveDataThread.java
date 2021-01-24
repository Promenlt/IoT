package Client;

import java.io.IOException;

public class ReceiveDataThread extends Thread{
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
                Thread.sleep(300);
//                yield();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                AppData.setHumd(Integer.parseInt(Test.requestPOST(Utils.GetInfoPackage(home.getUUID()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            home.rePaintButtonColor();
        }

    }

}
