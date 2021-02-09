package com.sustainabledev.hydro.data;



import java.util.List;

public class Dataset {

    List<Variable> variables;
    int size;

    @FunctionalInterface
    public static interface Filler<T> {

        List<Variable> process(Dataset d);
    }

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

    /**
     * Filling null values of all data contained in the variables inside this
     * dataset.
     * @return this dataset
     */
    public Dataset fillNullValues(Filler<?> f) {
        this.setVariables(f.process(this));
        return this;
    }

    /**
     * Correct invalid formatted data by replacing them with new valid values.
     * @param f a filler that generate a valid value
     * @return new Dataset having no invalid data
     */
    public Dataset correctInvalidData(Filler<?> f) {
        this.setVariables(f.process(this));
        return this;
    }


}
