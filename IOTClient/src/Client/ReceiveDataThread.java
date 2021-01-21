package Client;

public class ReceiveDataThread extends Thread{
    private boolean isRunning = true;
    int[] wet = new int[16];
    HomeGUI home;
    public ReceiveDataThread(HomeGUI home){
        this.home = home;
        for(int i=0;i<16;i++){
            wet[i]=(20*i)%100;
        }
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

//            System.out.println("receiving...");
            for(int i=0;i<16;i++){
                wet[i]+=7*i;
                wet[i]=wet[i]%100;
            }
            home.setWet(wet);
            home.rePaintButtonColor();

//            try{
//                home.setWet(wet);
////                HomeGUI.setWetColor(2);
//                home.rePaintButtonColor();
////                HomeGUI.getWet1().setStyle("-fx-background-color: #FFFFFF");
//            } catch(Exception e) {
//                System.out.println(e);
//                continue;
//            }


        }

    }

}
