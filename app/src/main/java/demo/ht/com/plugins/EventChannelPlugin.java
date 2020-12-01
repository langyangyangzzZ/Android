package demo.ht.com.plugins;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;

/**
 *  szj 2020/11/26
 *  CSDN博客:https://blog.csdn.net/weixin_44819566/
 *  微信公众号:码上变有钱
 *  EventChannelPlugin
 *  用于数据流（event streams）的通信，持续通信，通过长用于Native向Dart的通信，
 *  如：手机电量变化，网络连接变化，陀螺仪，传感器等；
 */
public class EventChannelPlugin implements EventChannel.StreamHandler {
    private final String ElectricityQuantity;

    /**
     *
     * @param messenger 发送消息工具
     * @param ElectricityQuantity   当前电量
     */
    public static void registerWith(BinaryMessenger messenger, String ElectricityQuantity) {
        new EventChannelPlugin(messenger, ElectricityQuantity);
    }

    private EventChannelPlugin(BinaryMessenger messenger,String ElectricityQuantity) {
        this.ElectricityQuantity = ElectricityQuantity;

        EventChannel eventChannelPlugin = new EventChannel(messenger, "demo.ht.com.androidproject/EventChannelPlugin");

        eventChannelPlugin.setStreamHandler(this);
    }
    @Override
    public void onListen(Object args, EventChannel.EventSink eventSink) {
        //要发送消息
        eventSink.success(ElectricityQuantity);
    }

    @Override
    public void onCancel(Object o) {
    }

}