import java.util.ArrayList;
import java.util.Queue;

public class SystemController {
    Greenhouse greenhouse;
    int checkRate;
    ArrayList<System> systems;
    Queue<Report> reportQueue;

    public void init(){
        for (System system : systems) {
            system.powerOn();
        }
    }

    public void stop(){
        for (System system : systems) {
            system.powerOff();
        }
    }

    private void startSystem(int plantId /*...*/){
        //...
    }

    public void mainloop(){
        try {
            int counter = 600;
            while (true) {
                counter--;
                if (counter == 0) {
                    //
                    /*greenhouse.getAllSharedSensorData();
                    greenhouse.getAllPlantsSensorData();
                    if (true ...){
                        startSystem(plantId);
                    }
                    */
                }

                if (!reportQueue.isEmpty()) {
                    Report report = reportQueue.poll();
                    //
                }

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            stop();
        }
    }

}
