package com.company.modelimplementations;


import com.company.NewOwner;
import com.github.javafaker.Faker;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.or;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import java.util.concurrent.locks.Condition;

/**
 
Implements the model (and interface) NewOwnerSharedState
The default path generator is Random Path.
Stop condition is 100% coverage of all edges.*/
@GraphWalker(value = "random(edge_coverage(100))")
public class NewOwnerTest extends ExecutionContext implements NewOwner {

    @Override
    public void v_OwnerInformation() {
    }

    @Override
    public void e_CorrectData() {
        fillOwnerData();
        $(By.id("telephone")).sendKeys(String.valueOf(new Faker().number().digits(10)));
        $(By.id("address")).sendKeys("Main Street 1");
        $(By.id("firstName")).sendKeys("Per"); //change
        $("button[type="submit"]").click();
    }

    @Override
    public void e_IncorrectData() {
        fillOwnerData();
        $(By.id("telephone")).sendKeys(String.valueOf(new Faker().number().digits(20)));

        $(By.id("address")).sendKeys("Street 1");

        $(By.id("firstName")).sendKeys("Peter"); //change
        $("button[type="submit"]").click();
    }

    @Override
    public void v_IncorrectData() {
        $(By.cssSelector(".help-inline"))
        .shouldHave(or("", //change
                text("must not be empty"),
                text("numeric value out of bounds (<10 digits>.<0 digits> expected)"),
                text("size must be between 0 and 3"),
                text("size must be between 10 and 15")
    ));
    }

    @Override
    public void v_NewOwner() {
        $(By.tagName("h2")).shouldHave(text("Owner"));
        $("button[type="submit"]").shouldBe(visible);
    }

    private void fillOwnerData() {


        $(By.id("lastName")).clear();
        $(By.id("lastName")).sendKeys(new Faker().name().lastName());

        $(By.id("city")).clear();
        $(By.id("city")).sendKeys(new Faker().address().city());

        $(By.id("address")).clear();
        $(By.id("telephone")).clear();
        $(By.id("firstName")).clear(); //change
    }
}