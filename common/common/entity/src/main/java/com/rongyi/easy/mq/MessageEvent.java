package com.rongyi.easy.mq;

import com.rongyi.core.common.util.DateUtil;
import com.rongyi.core.common.util.JsonUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Map;

/**	
 * @Author:  柯军
 * @Description: 消息发送体 
 * @datetime:2015年5月11日下午6:59:13
 *
 **/
public class MessageEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5884985566319453772L;
	private String type;
	private String source;
	private Object body;
	private String target;
	private long timestamp;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
    /**
     * 事件消息转换为RpbEvent对象
     * 
     * @param message
     * @return
     */
    @SuppressWarnings("unchecked")
    public static MessageEvent messageToEvent(String message) {
        MessageEvent event = new MessageEvent();
        Map<String, Object> eventMap = JsonUtil.getMapFromJson(message);
        event.setBody(eventMap.get("body"));
        event.setSource((eventMap.get("source") != null) ? eventMap.get("source").toString() : null);
        event.setTarget((eventMap.get("target") != null) ? eventMap.get("target").toString() : null);
        if (eventMap.get("timestamp") != null)
            event.setTimestamp(Long.valueOf(eventMap.get("timestamp").toString()));
        event.setType((eventMap.get("type") != null) ? eventMap.get("type").toString() : null);
        return event;
    }

    /**
     * @Description: mq消息发送
     * @param bodyMap
     * @param source
     * @param target
     * @param type
     * @Author: 柯军
     * @datetime:2015年5月11日下午6:57:23
     **/
    public static MessageEvent getMessageEvent(Map<String, Object> bodyMap, String source, String target, String type) {
        MessageEvent event = new MessageEvent();
        // TODO bodyMap 存放业务数据
        event.setBody(bodyMap);
        event.setSource(source);
        event.setTarget(target);
        event.setTimestamp(DateUtil.getCurrDateTime().getTime());
        event.setType(type);
        return event;
    }

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("type", type)
				.append("source", source)
				.append("body", body)
				.append("target", target)
				.append("timestamp", timestamp)
				.toString();
	}
}
