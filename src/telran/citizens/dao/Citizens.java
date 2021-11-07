package telran.citizens.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import telran.citizens.intefaces.ICitizens;
import telran.citizens.model.Person;

public class Citizens implements ICitizens {
    private static Comparator<Person> ageComparator;
    private static Comparator<Person> lastNameComparator;
    private List<Person> idList;
    private List<Person> ageList;
    private List<Person> lastNameList;

    static {
        ageComparator = (p1, p2) -> {
            int res = Integer.compare(p1.getAge(), p2.getAge());
            return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
        };
        lastNameComparator = (p1, p2) -> {
            int res = p1.getLastName().compareTo(p2.getLastName());
            return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
        };
    }

    public Citizens() {
        idList = new ArrayList<>();
        ageList = new ArrayList<>();
        lastNameList = new ArrayList<>();
    }

    public Citizens(List<Person> citizens) {
        this();
        for (Person person : citizens) {
            if (person != null && !idList.contains(person)) {
                idList.add(person);
                ageList.add(person);
                lastNameList.add(person);
            }
        }
        Collections.sort(idList);
        Collections.sort(ageList, ageComparator);
        Collections.sort(lastNameList, lastNameComparator);

    }

    @Override
    public boolean add(Person person) {
        if (person == null) {
            return false;
        }
        int index = Collections.binarySearch(idList, person);
        if (index >= 0) {
            return false;
        }
        index = -index - 1;
        idList.add(index, person);
        index = Collections.binarySearch(ageList, person, ageComparator);
        index = index >= 0 ? index : -index - 1;
        ageList.add(index, person);
        index = Collections.binarySearch(lastNameList, person, lastNameComparator);
        index = index >= 0 ? index : -index - 1;
        lastNameList.add(index, person);
        return true;
    }

    @Override
    public boolean remove(int id) {
        Person victim = find(id);
        if (victim == null) {
            return false;
        }
        idList.remove(victim);
        ageList.remove(victim);
        lastNameList.remove(victim);
        return true;
    }

    @Override
    public Person find(int id) {
        Person pattern = new Person(id, null, null, 0);
        int index = Collections.binarySearch(idList, pattern);
        return index < 0 ? null : idList.get(index);
    }

    @Override
    public Iterable<Person> find(int minAge, int maxAge) {
        Person pattern = new Person(idList.get(0).getId() - 1, null, null, minAge);
        int from = -Collections.binarySearch(ageList, pattern, ageComparator) - 1;
        pattern = new Person(idList.get(idList.size() - 1).getId() + 1, null, null, maxAge);
        int to = -Collections.binarySearch(ageList, pattern, ageComparator) - 1;
        return ageList.subList(from, to);
    }

    @Override
    public Iterable<Person> find(String lastName) {
        Person pattern = new Person(idList.get(0).getId() - 1, null, lastName, 0);
        int from = -Collections.binarySearch(lastNameList, pattern, lastNameComparator) - 1;
        pattern = new Person(idList.get(idList.size() - 1).getId() + 1, null, lastName, 0);
        int to = -Collections.binarySearch(lastNameList, pattern, lastNameComparator) - 1;
        return lastNameList.subList(from, to);
    }

    @Override
    public Iterable<Person> getAllPersonSortedById() {
        return idList;
    }

    @Override
    public Iterable<Person> getAllPersonSortedByLastName() {
        return lastNameList;
    }

    @Override
    public Iterable<Person> getAllPersonSortedByAge() {
        return ageList;
    }

    @Override
    public int size() {
        return idList.size();
    }

}
