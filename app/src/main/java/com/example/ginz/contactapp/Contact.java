package com.example.ginz.contactapp;

public class Contact {
    private String mDisplayName;
    private String mPhoneNumber;

    public Contact(String displayName, String phoneNumber) {
        this.mDisplayName = displayName;
        this.mPhoneNumber = phoneNumber;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }
}
