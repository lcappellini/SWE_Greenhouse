public abstract class System {
    boolean isRunning;
    boolean powered;
    float consumedPower;
    SystemController systemController;

    public void powerOn(){
        powered = true;
    }
    public void powerOff(){
        powered = false;
    }

    public void start(/*...*/){
        isRunning = true;
        //...
    }

    public void stop(){
        //...
        isRunning = false;
    }
}
