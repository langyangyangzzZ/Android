package demo.ht.com.androidproject;

public interface IShowMessage {
    void onShowMessage(String message);
    void sendMessage(String message,boolean useEventChannel);
}