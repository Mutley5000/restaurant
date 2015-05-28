package restaurant.application;

import restaurant.model.BookingQueries;
import restaurant.presenter.BookingPresenter;
import restaurant.view.BookingView;

public class Restaurant {

    public static void main(String[] args) {
        BookingQueries bq = new BookingQueries();
        BookingView bv = new BookingView();
        BookingPresenter bp = new BookingPresenter( bv, bq );
        bv.bind(bp);
    }
}