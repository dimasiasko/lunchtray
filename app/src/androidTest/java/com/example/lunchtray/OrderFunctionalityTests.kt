package com.example.lunchtray

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.lunchtray.ui.order.AccompanimentMenuFragment
import com.example.lunchtray.ui.order.CheckoutFragment
import com.example.lunchtray.ui.order.EntreeMenuFragment
import com.example.lunchtray.ui.order.SideMenuFragment
import org.hamcrest.core.StringContains.containsString
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class OrderFunctionalityTests : BaseTest() {

    @Test
    fun `radio_buttons_update_entree_menu_subtotal`() {
        launchFragmentInContainer<EntreeMenuFragment>(themeResId = R.style.Theme_LunchTray)

        onView(withId(R.id.cauliflower)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $7.00"))))

        onView(withId(R.id.chili)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $4.00"))))

        onView(withId(R.id.pasta)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $5.50"))))

        onView(withId(R.id.skillet)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $5.50"))))
    }

    @Test
    fun `radio_buttons_update_side_menu_subtotal`() {
        launchFragmentInContainer<SideMenuFragment>(themeResId = R.style.Theme_LunchTray)

        onView(withId(R.id.salad)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $2.50"))))

        onView(withId(R.id.soup)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $3.00"))))

        onView(withId(R.id.potatoes)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $2.00"))))

        onView(withId(R.id.rice)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $1.50"))))
    }

    @Test
    fun `radio_buttons_update_accompaniment_menu_subtotal`() {
        launchFragmentInContainer<AccompanimentMenuFragment>(themeResId = R.style.Theme_LunchTray)

        onView(withId(R.id.bread)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $0.50"))))

        onView(withId(R.id.berries)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $1.00"))))

        onView(withId(R.id.pickles)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $0.50"))))
    }

    @Test
    fun `subtotal_updates_in_full_order_flow`() {
        launchActivity<MainActivity>()
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withId(R.id.cauliflower)).perform(click())
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.salad)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $9.50"))))
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.bread)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $10.00"))))
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $10.00"))))
    }

    @Test
    fun `subtotal_tax_total_in_checkout`() {
        fullOrderFlow()
        onView(withId(R.id.subtotal))
            .check(matches(withText(containsString("Subtotal: $10.00"))))
        onView(withId(R.id.tax))
            .check(matches(withText(containsString("Tax: $0.80"))))
        onView(withId(R.id.total))
            .check(matches(withText(containsString("Total: $10.80"))))
    }

    @Test
    fun `order_reset_after_cancel_from_entree_menu`() {
        launchActivity<MainActivity>()
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withId(R.id.cauliflower)).perform(click())
        onView(withId(R.id.cancel_button)).perform(click())
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withText("Subtotal: $0.00")).check(matches(isDisplayed()))
    }

    @Test
    fun `order_reset_after_cancel_from_side_menu`() {
        launchActivity<MainActivity>()
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withId(R.id.cauliflower)).perform(click())
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.soup)).perform(click())
        onView(withId(R.id.cancel_button)).perform(click())
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withText("Subtotal: $0.00")).check(matches(isDisplayed()))
    }

    @Test
    fun `order_reset_after_cancel_from_accompaniment_menu`() {
        launchActivity<MainActivity>()
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withId(R.id.cauliflower)).perform(click())
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.soup)).perform(click())
        onView(withId(R.id.next_button)).perform(click())
        onView(withId(R.id.bread)).perform(click())
        onView(withId(R.id.cancel_button)).perform(click())
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withText("Subtotal: $0.00")).check(matches(isDisplayed()))
    }

    @Test
    fun `order_reset_after_cancel_from_checkout`() {
        fullOrderFlow()
        onView(withId(R.id.cancel_button)).perform(click())
        onView(withId(R.id.start_order_btn)).perform(click())
        onView(withText("Subtotal: $0.00")).check(matches(isDisplayed()))
    }

    @Test
    fun `order_snackbar`() {
        fullOrderFlow()
        onView(withId(R.id.submit_button)).perform(click())
        onView(withText(containsString("Order Submitted!")))
            .check(matches(isDisplayed()))
    }
}