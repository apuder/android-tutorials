package org.xmlvm.tutorial.android.service.bound;

public interface FibonacciResultListener {

    public void resultAvailable(final int n, final int res);
}
