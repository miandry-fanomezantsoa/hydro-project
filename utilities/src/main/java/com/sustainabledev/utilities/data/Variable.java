package com.sustainabledev.utilities.data;

import java.util.List;

public class Variable<T> {
    String name;
    Long size;
    List<T> data;


    public Variable(String name) {
        this.name = name;
    }

    public Variable(String name, List<T> data) {
        this.name = name;
        this.data = data;
        this.size = Integer.valueOf(data.size()).longValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable v = (Variable) o;
        return (this.name.equals(v.getName()) && this.data.equals(v.getData()));
    }
}
