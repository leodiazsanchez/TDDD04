// Generated by GraphWalker (http://www.graphwalker.org)
package com.prestashop;

import org.graphwalker.java.annotation.Model;
import org.graphwalker.java.annotation.Vertex;
import org.graphwalker.java.annotation.Edge;

@Model(file = "com/prestashop/UC01 - Normalized version.json")
public interface PrestaShop {

    @Edge()
    void e_Start();

    @Vertex()
    void v_ConfirmOrder();

    @Edge()
    void e_AddProductToCart();

    @Edge()
    void e_Checkout();

    @Vertex()
    void v_Product();

    @Edge()
    void e_Select_Product();

    @Vertex()
    void v_HomePage();

    @Edge()
    void e_Cart();

    @Vertex()
    void v_Cart();
}
