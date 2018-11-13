package com.zhixingjia.rxbus;

/**
 * @author huscarter@163.com
 * @title RxBus的事件类
 * @description RxEvent为基本事件, 可以自定义其他特定的事件比如OrderEvent.
 * <p>
 * 每种事件都有一个标示type,每种事件可以定义事件行为,比如订单事件(OrderEvent)的改价行为(CHANGE_PRICE).
 * <p>
 * 每种的事件的标示(type)相差100, 意味着每种事件里可以有99种不同的行为,因此也可以通过行为推断出它属于哪种事件.
 * <p>
 * 举例:订单事件OrderEvent的type为100,订单的改价和付款行为标示为101和102,最大的行为标示为199.
 * @date 9/30/16
 */
public class RxEvent {
    /**
     * 事件的标示
     */
    public int type = 0;
    /**
     * 事件的发送者
     */
    public Object sender = null;

    public RxEvent() {
        //
    }

    public RxEvent(int type) {
        this.type = type;
    }

    public RxEvent(int type, Object sender) {
        this.type = type;
        this.sender = sender;
    }
    /**
     * 登录,注册事件(Login and register of user==LRU)
     */
    public static class LoginEvent extends RxEvent {
        public static final int VALUE = 100;
        /**
         * 登录成功
         */
        public static final int LOGIN_SUCCESS = VALUE + 2;

        public LoginEvent() {
            super(VALUE);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getSender() {
        return sender;
    }

    public void setSender(Object sender) {
        this.sender = sender;
    }

    /**
     * 重写toString方法:展示为type值.
     *
     * @return
     */
    @Override
    public String toString() {
        return "event type:" + type;
    }

}
