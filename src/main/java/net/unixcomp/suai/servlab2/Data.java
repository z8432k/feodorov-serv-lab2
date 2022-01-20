package net.unixcomp.suai.servlab2;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Data {
    private final Set<String> rooms = new HashSet<>();
    private final Set<String> people = new HashSet<>();
    private final HashMap<String, Set<String>> access = new HashMap<>();

    public Data() {
        for (int i = 1; i < 5; i++) {
            this.rooms.add("Room " + i);
            this.people.add("Person " + i);
        }

        grant("Dr.Breen", "Citadel");
    }

    public Set<String> addRoom(@NotNull String room) {
        synchronized (rooms) {
            rooms.add(room);
        }

        return rooms;
    }

    public Set<String> addPerson(@NotNull String person) {
        synchronized (people) {
            people.add(person);
        }

        return people;
    }

    public Set<String> rmRoom(@NotNull String room) {
        synchronized (rooms) {
            rooms.remove(room);
        }

        return rooms;
    }

    public Set<String> rmPerson(@NotNull String person) {
        synchronized (people) {
            people.remove(person);
        }

        return people;
    }

    public Set<String> getRooms() {
        return rooms;
    }

    public Set<String> getPeople() {
        return people;
    }

    public HashMap<String, Set<String>> getRights() {
        return access;
    }

    public HashMap<String, Set<String>> grant(@NotNull String person, @NotNull String room) {
        synchronized (access) {
            if (!access.containsKey(person)) {
                access.put(person, new HashSet<>());
            }

            access.get(person).add(room);
        }

        return access;
    }

    public HashMap<String, Set<String>> revoke(@NotNull String person, @NotNull String room) {
        synchronized (access) {
            if (access.containsKey(person)) {
                Set<String> rooms = access.get(person);

                if (rooms != null) {
                    rooms.remove(room);
                }
            }
        }

        return access;
    }
}
