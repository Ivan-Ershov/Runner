public class ThreadIn implements Runnable{
    private SendProtocol sendProtocol;
    private MainFrame mainFrame;

    public ThreadIn(SendProtocol sendProtocol, MainFrame mainFrame){
        this.sendProtocol = sendProtocol;
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        try {
            while (true) {
                mainFrame.addInputText(sendProtocol.getMessage());
            }
        } catch (Exception ex){
            //ex.printStackTrace();
        }
    }
}
