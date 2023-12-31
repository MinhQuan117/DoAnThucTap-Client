package com.example.duanthuctap.Tools;

import com.example.duanthuctap.Models.Address;
import com.example.duanthuctap.Models.Banner;
import com.example.duanthuctap.Models.Bill;
import com.example.duanthuctap.Models.Cart;
import com.example.duanthuctap.Models.Category;
import com.example.duanthuctap.Models.MethodPayment;
import com.example.duanthuctap.Models.MethodShipping;
import com.example.duanthuctap.Models.Product;

import java.util.ArrayList;
import java.util.List;


public class LIST {
    public static List<Product> listProduct = new ArrayList<>();
    public static List<Category> listCategory = new ArrayList<>();
    public static List<Banner> listBanner = new ArrayList<>();
    public static List<Product> listProductByBanner = new ArrayList<>();
    public static List<Product> listProductByCategory = new ArrayList<>();


    public static List<Product> getListProductByFavourite = new ArrayList<>();
    public static List<Product> listRecently = new ArrayList<>();
    public static List<Address> listAddress = new ArrayList<>();
    public static List<Cart> listBuyCart = new ArrayList<>();

    public static List<MethodShipping> listMethodShipping() {
        List<MethodShipping> list = new ArrayList<>();
        list.add(new MethodShipping("Giao hàng hỏa tốc", 32000, R.color.ms0, "Nhận hàng từ 1 đến 2 ngày"));
        list.add(new MethodShipping("Giao hàng nhanh", 16500, R.color.ms1, "Nhận hàng từ 3 đến 5 ngày"));
        list.add(new MethodShipping("Giao hàng thường", 6000, R.color.ms2, "Nhận hàng từ 6 đến 8 ngày"));
        return list;
    }

    public static List<MethodPayment> listMethodPayment() {
        List<MethodPayment> list = new ArrayList<>();
        list.add(new MethodPayment("Thanh toán tiền mặt khi nhận hàng", true));
        list.add(new MethodPayment("Thanh toán bằng ví điện tử", false));
        list.add(new MethodPayment("Thanh toán bằng thẻ ngân hàng", false));
        return list;
    }

    public static List<Bill> listBill = new ArrayList<>();

    public static List<Bill> getListBillByStatus(int status) {
        List<Bill> list = new ArrayList<>();
        for (int i = 0; i < listBill.size(); i++) {
            if (listBill.get(i).getStatus() == status) {
                list.add(listBill.get(i));
            }
        }

        return list;
    }


}
