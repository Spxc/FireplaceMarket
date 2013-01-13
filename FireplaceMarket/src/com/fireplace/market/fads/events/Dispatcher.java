package com.fireplace.market.fads.events;

public interface Dispatcher {
	void addListener(String type, EventListener listener);

	void removeListener(String type, EventListener listener);

	boolean hasListener(String type, EventListener listener);

	void dispatchEvent(Event event);
}