package io.ermdev.transferit.desktop.util;

import io.ermdev.transferit.integration.Item;

import java.util.List;

public class ItemManager {

    private int counter = 0;

    private List<Item> items;

    private Item item;

    public void setItems(List<Item> items) {
        this.items = items;
        counter = -1;
    }

    public boolean next() {
        counter++;
        if (items.size() > counter) {
            item = items.get(counter);
            return true;
        } else {
            return false;
        }
    }

    public Item get() {
        return item;
    }
}
