package com.example.duanthuctap.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BZeroFragment();
            case 1:
                return new BOneFragment();
            case 2:
                return new BTwoFragment();
            case 4:
                return new BFourFragment();
            case 3:
            default:
                return new BThreeFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String s;
        switch (position) {
            case 0:
                s = "Chờ xác nhận";
                break;
            case 1:
                s = "Chuẩn bị hàng";
                break;
            case 2:
                s = "Đang giao hàng";
                break;
            case 4:
                s = "Đã hủy";
                break;
            case 3:
            default:
                s = "Đã giao";
                break;
        }
        return s;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
