package io.ermdev.transferit.desktop.util;

import io.ermdev.transferit.integration.Item;

import java.util.List;

public class ItemManager {

    private int counter = 0;

    private List<Item> items;

    public void setItems(List<Item> items) {
        this.items = items;
        counter = -1;
    }

    public Item next() {
        counter++;
        if (items.size() > counter) {
            return items.get(counter);
        } else {
            return null;
        }
    }

}
