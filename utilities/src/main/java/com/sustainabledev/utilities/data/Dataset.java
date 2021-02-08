package com.sustainabledev.utilities.data;



import java.util.List;

public class Dataset {

    List<Variable> variables;
    int size;

    public Dataset() {
        this.variables = null;
    }

    public Dataset(List<Variable> variables) {
        this.variables = variables;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }


    @Override
    public boolean equals(Object o) {
        if (this.variables.size() != ((Dataset)o).getVariables().size()) return false;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dataset d = (Dataset) o;
        boolean result = true;
        List<Variable> v = d.getVariables();
        int i = 0;
        for (Variable var : v) {
            if (! var.equals(this.variables.get(i))) result = false;
            ++i;
        }
        return result;
    }

    @Override
    public String toString() {
        String repr = "";
        for (Variable variable : this.variables) {
            repr += variable.getName() + " => " + variable.getData() + "\n";
        }
        return repr;
    }
}
