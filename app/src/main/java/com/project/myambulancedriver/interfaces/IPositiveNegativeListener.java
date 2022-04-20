package com.project.myambulancedriver.interfaces;

public interface IPositiveNegativeListener {
    void onPositive();

    default void onNegative() {
    }
}
