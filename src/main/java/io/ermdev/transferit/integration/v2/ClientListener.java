package io.ermdev.transferit.integration.v2;

import io.ermdev.transferit.integration.Item;

import java.util.List;

public interface ClientListener {

    List<Item> getItems();

    void onStart(Item item);

    void onUpdate(Item item, double n);

    void onComplete(Item item);
}
