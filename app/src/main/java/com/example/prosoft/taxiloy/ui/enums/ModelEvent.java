package com.example.prosoft.taxiloy.ui.enums;

/**
 * Created by prosoft on 1/12/16.
 */
public enum ModelEvent {
    INITIAL(0),

    GETSMS_SUCCESS(0),
    GETSMS_ALREADY(1),
    CHECK_SMS_CODE_SUCCESS(3),
    CHECK_SMS_CODE_FAILED(4),

    GET_DETAIL_PASSENGER_SUCCESS(5),
    PUT_DETAIL_PASSENGER_SUCCESS(6),

    GET_CAR_LIST_SUCCESS(7),

    GET_DEFAULT_LIST_CAR_SUCCESS(8),
    GET_CARTYPES_SUCCESS(9),
    GET_CARBRAND_SUCCESS(10),
    GET_CARSEATS_SUCCESS(11),
    GET_FILTER_LIST_CAR_SUCCESS(12),

    BOOK_CAR_SUCCESS(13),

    GET_DETAIL_DRIVER_SUCCESS(14),
    PICKUP_PASSENGER_SUCCESS(15),
    FINISH_SUCCESS(16),

    GET_INBOX_LIST_PASSENGER_SUCCESS(17),
    GET_INBOX_NO_LIST_PASSENGER(18),

    GET_HISTORYLIST_PASSENGER_SUCCESS(19),
    GET_HISTORY_NO_LIST_PASSENGER(20),

    CHECK_VACANT_DRIVER_SUCCESS(21),
    CHECK_VACANT_DRIVER_FAILED(22),

    RATING_DRIVER_SUCCESS(23),
    RATING_DRIVER_FAIL(24),

    GET_PASSENGER_DRIVER_SUCCESS(25),
    GET_BANNER_SUCCESS(26),
    GET_RATING_DRIVER_SUCCESS(27);

    private final int value;

    ModelEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
