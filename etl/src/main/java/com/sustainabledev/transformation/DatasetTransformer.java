package com.sustainabledev.transformation;

import com.sustainabledev.utilities.data.Dataset;

@FunctionalInterface
public interface DatasetTransformer {

    <T> T transform(T t);
}
