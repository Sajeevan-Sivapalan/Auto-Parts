package com.example.automate;

import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class TransformationMethodMatcher extends TypeSafeMatcher<View> {

    private final Class<? extends TransformationMethod> expectedTransformationMethod;

    public TransformationMethodMatcher(Class<? extends TransformationMethod> transformationMethod) {
        this.expectedTransformationMethod = transformationMethod;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (!(view instanceof EditText)) {
            return false;
        }
        TransformationMethod method = ((EditText) view).getTransformationMethod();
        return expectedTransformationMethod.isInstance(method);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with transformation method: " + expectedTransformationMethod.getSimpleName());
    }

    public static TransformationMethodMatcher withTransformationMethod(Class<? extends TransformationMethod> transformationMethod) {
        return new TransformationMethodMatcher(transformationMethod);
    }
}
